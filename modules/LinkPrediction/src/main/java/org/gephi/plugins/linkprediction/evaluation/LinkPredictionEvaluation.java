package org.gephi.plugins.linkprediction.evaluation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gephi.graph.api.GraphModel;
import org.gephi.plugins.linkprediction.base.EvaluationMetric;
import org.gephi.statistics.plugin.ChartUtils;
import org.gephi.statistics.spi.Statistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

/**
 * Macro class that triggers the evaluation calculation for all selected algorithms.
 *
 * @author Marco Romanutti
 */
public class LinkPredictionEvaluation implements Statistics {

    // List of link prediction evaluations
    private List<EvaluationMetric> evaluations = new ArrayList<>();

    // Console Logger
    private static Logger consoleLogger = LogManager.getLogger(LinkPredictionEvaluation.class);

    /**
     * Calculates evaluation metrics for all evaluations.
     */
    public void execute(GraphModel graphModel) {

        evaluations.stream().forEach(evaluation -> {
            evaluation.run();
        });
    }

    @Override public String getReport() {
        consoleLogger.debug("Create report");
        //This is the HTML report shown when execution ends.
        //One could add a distribution histogram for instance
        String html = "<HTML> <BODY> <h1>Evaluation of different prediction algorithms</h1> " + "<hr>";
        // Append parameters section
        html = appendParameters(html);

        Map<String, Double> finalResults = new HashMap<>();
        for (EvaluationMetric e : evaluations) {
            finalResults.put(e.getAlgorithmName(), e.getFinalResult());
        }

        // Sort values
        Map<String, Double> sortedValues = sortByValue(finalResults);

        // Append result section
        html = appendResults(html, sortedValues);

        // Render diagram
        String imageFile = renderDiagram();

        // Append iterations result section
        html = appendIterationResults(html, imageFile);

        // Append algorithms section
        html = appendAlgorithms(html);

        html += "</BODY></HTML>";
        return html;
    }

    /**
     * Gets all evaluations.
     *
     * @return All evaluations
     */
    public List<EvaluationMetric> getEvaluations() {
        return evaluations;
    }

    /**
     * Get specific link prediction algorithm from evaluation list.
     *
     * @param evaluation Class of searched evaluation
     * @return LinkPredictionStatistic
     */
    public EvaluationMetric getEvaluation(EvaluationMetric evaluation) {
        consoleLogger.debug("Attempt to get metric for " + evaluation.getAlgorithmName());
        return evaluations.get(evaluations.indexOf(evaluation));
    }

    /**
     * Adds evaluation to list.
     *
     * @param evaluation Metric to evaluate.
     */
    public void addEvaluation(EvaluationMetric evaluation) {
        consoleLogger.debug("Attempt to add metric for " + evaluation.getAlgorithmName());
        if (!evaluations.contains(evaluation))
            evaluations.add(evaluation);
    }

    /**
     * Removes evaluation from list.
     *
     * @param evaluation Metric to evaluate.
     */
    public void removeEvaluation(EvaluationMetric evaluation) {
        consoleLogger.debug("Attempt to remove metric for " + evaluation.getAlgorithmName());
        if (evaluations.contains(evaluation))
            evaluations.remove(evaluation);
    }

    /**
     * Sort metrics by highest calculated values.
     *
     * @param allValues Unsorted map of calculated values
     * @return Sorted map, highest first
     */
    private static Map<String, Double> sortByValue(Map<String, Double> allValues) {
        consoleLogger.debug("Sort metrics by value");

        LinkedHashMap<String, Double> allValuesSorted = new LinkedHashMap<>();

        allValues.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> allValuesSorted.put(x.getKey(), x.getValue()));

        return allValuesSorted;
    }

    /**
     * Formats chart appearance.
     *
     * @param chart Chart to format
     */
    private static void formatChart(JFreeChart chart) {
        consoleLogger.debug("Format chart");

        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        Font legendFont = new Font("SansSerif", Font.PLAIN, 16);
        renderer.setLegendTextFont(0, legendFont);
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(0.0D, 0.0D, 2.0D, 2.0D));
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setRenderer(renderer);
    }

    /**
     * Appends algorithms to html report.
     *
     * @param html Existing html
     * @return Completed html
     */
    private String appendAlgorithms(String html) {
        consoleLogger.debug("Append algorithms section to report");

        html += "<h2>Algorithms:</h2>";
        html += "Michael Henninger,<i> Link Prediction</i>, in Soziale Netzwerkanalyse 2018 (p. 96)";
        return html;
    }

    /**
     * Appends iteration results section.
     *
     * @param html      Existing html
     * @param imageFile XY chart
     * @return Completed html
     */
    private String appendIterationResults(String html, String imageFile) {
        consoleLogger.debug("Append iteration results section to report");

        html += "<h2>Iteration Results:</h2>";
        html += "<br /><br />" + imageFile + "<br /><br />";
        return html;
    }

    /**
     * Renders a xy-series char.
     *
     * @return Rendered diagram
     */
    private String renderDiagram() {
        consoleLogger.debug("Render diagram");

        XYSeriesCollection dataset = new XYSeriesCollection();
        for (EvaluationMetric e : evaluations) {
            consoleLogger.debug("Add metric for " + e.getAlgorithmName() + " to dataset");
            dataset.addSeries(ChartUtils.createXYSeries(e.getIterationResults(), e.getAlgorithmName()));
        }

        // Create chart
        JFreeChart chart = ChartFactory
                .createXYLineChart("Development of Accuracy", "Iteration", "Accuracy in %", dataset,
                        PlotOrientation.VERTICAL, true, false, false);

        // Format chart
        formatChart(chart);

        return ChartUtils.renderChart(chart, "iteration-results.png");
    }

    /**
     * Appends results to html report.
     *
     * @param html         Existing html
     * @param sortedValues Map of final results
     * @return Completed html
     */
    private String appendResults(String html, Map<String, Double> sortedValues) {
        consoleLogger.debug("Append result section to report");

        html += "<h2>Results:</h2>";
        int counter = 1;
        for (Map.Entry<String, Double> elem : sortedValues.entrySet()) {
            html += counter + ". " + elem.getKey() + ": " + String.valueOf(elem.getValue());
            html += "<br /><br />";
            counter++;
        }
        return html;
    }

    /**
     * Appends parameters to html report.
     *
     * @param html Existing html
     * @return Completed html
     */
    private String appendParameters(String html) {
        consoleLogger.debug("Append parameter section to report");

        html += "<h2>Parameters:</h2>";
        int iterationCount = evaluations.isEmpty() ? 0 : evaluations.get(0).getDiffEdgeCount();
        html += "Number of Iterations: " + iterationCount;
        return html;
    }
}
