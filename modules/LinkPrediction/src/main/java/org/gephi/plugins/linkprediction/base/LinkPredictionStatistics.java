package org.gephi.plugins.linkprediction.base;

import org.gephi.graph.api.Column;
import org.gephi.graph.api.Table;
import org.gephi.plugins.linkprediction.util.Complexity;
import org.gephi.statistics.spi.Statistics;

/**
 * Statistic that predicts the next edge based on different link-prediction
 * algorithm implementations.
 * <p>
 * Per iteration a new edge is added to the existing network. After every
 * iteration, a new situation is assumed and the next edge is calculated
 * on the basis of this new, expanded network.
 * <p>
 * The number of iterations can be limited using the UI.
 *
 * @author Saskia Schueler
 * @see LinkPredictionStatisticsBuilder
 */
public abstract class LinkPredictionStatistics implements Statistics {
    /** Default number of iteration used to predict next edges **/
    public static final int ITERATION_LIMIT_DEFAULT = 1;

    /* Column names for data labour */
    public static final String ADDED_IN_RUN = "added_in_run";
    public static final String LAST_VALUE = "last_link_prediction_value";
    public static final String LP_ALGORITHM = "link_prediction_algorithm";

    /* Columns for data labour */
    protected static Column colLP;
    protected static Column colAddinRun;
    protected static Column colLastValue;

    // Number of edge prediction iterations
    protected int iterationLimit = ITERATION_LIMIT_DEFAULT;
    // Big o complexity of algorithm
    protected Complexity complexity;

    /**
     * Generates a report after link prediction calculation has finished.
     *
     * @return HTML report
     */
    public String getReport() {
        //This is the HTML report shown when execution ends.
        //One could add a distribution histogram for instance
        return "<HTML> <BODY> <h1>Count Common Neighbours</h1> " + "<hr>"
                + "<br> No global results to show. Check Data Laboratory for results" + "<br />" + "</BODY></HTML>";
    }

    /**
     * Gives an estimate of the assumed duration.
     *
     * @return If the calculation will takes a long time
     */
    public boolean longRuntimeExpected(){
        // TODO Implement
        return false;
    }

    public int getIterationLimit() {
        return iterationLimit;
    }

    public void setIterationLimit(int iterationLimit) {
        this.iterationLimit = iterationLimit;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    protected static void initializeColumns(Table edgeTable) {
        colLP = edgeTable.getColumn(LP_ALGORITHM);
        if (colLP == null) {
            colLP = edgeTable.addColumn(LP_ALGORITHM, "Chosen Link Prediction Alogirthm", Integer.class, 0);
        }

        colAddinRun = edgeTable.getColumn(ADDED_IN_RUN);
        if (colAddinRun == null) {
            colAddinRun = edgeTable.addColumn(ADDED_IN_RUN, "Added in Run", Integer.class, 0);
        }

        colLastValue = edgeTable.getColumn(LAST_VALUE);
        if (colLastValue == null) {
            colLastValue = edgeTable.addColumn(LAST_VALUE, "Last Link Prediction Value", Integer.class, 0);
        }
    }
}
