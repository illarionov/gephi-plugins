/*
 * Copyright (c) 2011, INRIA
 * All rights reserved.
 */
package fr.inria.edelweiss.sparql.corese;

import fr.inria.edelweiss.sparql.DriverParametersPanel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**fr.inria.edelweiss.sparql.corese.CoreseDriverParametersPanel
 *
 * @author edemairy
 */
public class CoreseDriverParametersPanel extends DriverParametersPanel<CoreseDriverParameters> {

    CoreseDriverParameters parameters;

    public CoreseDriverParametersPanel() {
        super();
        initComponents();
    }

    /** Creates new form CoreseDriverParametersPanel */
    public CoreseDriverParametersPanel(CoreseDriverParameters driverParameters) {
        super();
        initComponents();
        setParameters(driverParameters);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdfResourcesLabel = new javax.swing.JLabel();
        addRdfResourceButton = new javax.swing.JButton();
        removeRdfResourceButton = new javax.swing.JButton();
        rdfResourceList = new javax.swing.JList();
        addUrl = new javax.swing.JButton();
        urlField = new javax.swing.JTextField();

        setToolTipText(org.openide.util.NbBundle.getMessage(CoreseDriverParametersPanel.class, "CoreseDriverParametersPanel.toolTipText")); // NOI18N

        rdfResourcesLabel.setText(org.openide.util.NbBundle.getMessage(CoreseDriverParametersPanel.class, "CoreseDriverParametersPanel.rdfResourcesLabel.text")); // NOI18N

        addRdfResourceButton.setActionCommand(org.openide.util.NbBundle.getMessage(CoreseDriverParametersPanel.class, "CoreseDriverParametersPanel.+.actionCommand")); // NOI18N
        addRdfResourceButton.setLabel(org.openide.util.NbBundle.getMessage(CoreseDriverParametersPanel.class, "CoreseDriverParametersPanel.+.label")); // NOI18N
        addRdfResourceButton.setName("+"); // NOI18N
        addRdfResourceButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addRdfResourceButtonMouseClicked(evt);
            }
        });
        addRdfResourceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRdfResourceButtonActionPerformed(evt);
            }
        });

        removeRdfResourceButton.setActionCommand(org.openide.util.NbBundle.getMessage(CoreseDriverParametersPanel.class, "CoreseDriverParametersPanel.-.actionCommand")); // NOI18N
        removeRdfResourceButton.setLabel(org.openide.util.NbBundle.getMessage(CoreseDriverParametersPanel.class, "CoreseDriverParametersPanel.-.label")); // NOI18N
        removeRdfResourceButton.setName("-"); // NOI18N
        removeRdfResourceButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeRdfResourceButtonMouseClicked(evt);
            }
        });
        removeRdfResourceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRdfResourceButtonActionPerformed(evt);
            }
        });

        rdfResourceList.setName("listResources"); // NOI18N

        addUrl.setText(org.openide.util.NbBundle.getMessage(CoreseDriverParametersPanel.class, "CoreseDriverParametersPanel.addUrl.text")); // NOI18N
        addUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUrlActionPerformed(evt);
            }
        });

        urlField.setText(org.openide.util.NbBundle.getMessage(CoreseDriverParametersPanel.class, "CoreseDriverParametersPanel.urlField.text")); // NOI18N
        urlField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdfResourceList, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdfResourcesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addRdfResourceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeRdfResourceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addUrl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(urlField, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rdfResourcesLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(removeRdfResourceButton)
                        .addComponent(addRdfResourceButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdfResourceList, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addUrl)
                    .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addRdfResourceButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRdfResourceButtonMouseClicked
        JFileChooser rdfResourceChooser = new JFileChooser("");
        rdfResourceChooser.setMultiSelectionEnabled(true);
        int result = rdfResourceChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            parameters.addResources(rdfResourceChooser.getSelectedFiles());
        }
}//GEN-LAST:event_addRdfResourceButtonMouseClicked

    private void addRdfResourceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRdfResourceButtonActionPerformed
}//GEN-LAST:event_addRdfResourceButtonActionPerformed

    private void removeRdfResourceButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeRdfResourceButtonMouseClicked
        int[] selectedIndexes = this.rdfResourceList.getSelectedIndices();
        for (int index : selectedIndexes) {
            parameters.getRdfResourcesModel().remove(index);
        }
}//GEN-LAST:event_removeRdfResourceButtonMouseClicked

    private void addUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUrlActionPerformed
        if (!urlField.getText().isEmpty()) {
            parameters.addResource(urlField.getText());
        }
}//GEN-LAST:event_addUrlActionPerformed

    private void urlFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlFieldActionPerformed
}//GEN-LAST:event_urlFieldActionPerformed

    private void removeRdfResourceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRdfResourceButtonActionPerformed

    }//GEN-LAST:event_removeRdfResourceButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRdfResourceButton;
    private javax.swing.JButton addUrl;
    private javax.swing.JList rdfResourceList;
    private javax.swing.JLabel rdfResourcesLabel;
    private javax.swing.JButton removeRdfResourceButton;
    private javax.swing.JTextField urlField;
    // End of variables declaration//GEN-END:variables
//    DefaultListModel getRdfResourceListModel() {
//        return parameters.getdfResourceListModel;
//    }

    public void addResource(String lastFileName) {
        parameters.addResource(lastFileName);
    }

    public List<String> getResourceList() {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < parameters.getRdfResourcesModel().getSize(); ++i) {
            result.add((String) parameters.getRdfResourcesModel().get(i));
        }
        return result;
    }

    public void setParameters(CoreseDriverParameters parameters) {
        this.parameters = parameters;
        rdfResourceList.setModel(parameters.getRdfResourcesModel());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CoreseDriver driver = new CoreseDriver();
        CoreseDriverParameters driverParameters = driver.getParameters();
        CoreseDriverParametersPanel panel = new CoreseDriverParametersPanel(driverParameters);
        frame.setContentPane(panel);
        frame.setSize(frame.getPreferredSize());
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}