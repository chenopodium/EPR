/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.physics.epr.gui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.physics.epr.Main;
import org.physics.epr.Measurements;
import org.physics.epr.Setup;
import org.physics.epr.hiddenvars.CloseFormula;
import org.physics.epr.hiddenvars.CloseHiddenVars;
import org.physics.epr.hiddenvars.HiddenVariablesIF;
import org.physics.epr.hiddenvars.MeasurementFormulaIF;
import tools.InMemoryCompiler;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.physics.epr.gui//Simulation//EN",
autostore = false)
@TopComponent.Description(preferredID = "SimulationTopComponent",
iconBase = "org/physics/epr/gui/atlantik-2.png",
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.physics.epr.gui.SimulationTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SimulationAction",
preferredID = "SimulationTopComponent")
public final class SimulationTopComponent extends TopComponent {

    RabPanel chart;
    JScrollPane scroll;
    HiddenVariablesIF hiddenVars;
    MeasurementFormulaIF formula;
    int nr;
    JTextArea txtHidden;
    JTextArea txtDect;
    JTextArea txtFormula;
    Measurements measurements ;
    Main exp;
    public SimulationTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(SimulationTopComponent.class, "CTL_SimulationTopComponent"));
        setToolTipText(NbBundle.getMessage(SimulationTopComponent.class, "HINT_SimulationTopComponent"));

        hiddenVars = new CloseHiddenVars();
       //  detection = new ChantalsDetection1();
        formula = new CloseFormula();
        
        doRunAction();
    }

    private void getHiddenVarsModel() throws HeadlessException {
        String name = "MyModel"+nr;
        String pack = "org.physics.epr.hiddenvars";
        String code = this.txtHidden.getText();
        String source = "package " + pack + ";\n\n";
        source += "import org.physics.epr.Particle;\n";
        source += "import org.physics.epr.hiddenvars.HiddenVariablesIF;\n\n";
        source += "public class "+name+" implements HiddenVariablesIF {\n";
        source += code + "\n\n";

        source += "\tpublic String getFormula() {\n";
        source += "\t\treturn null;\n";
        source += "\t}\n";
        source += "}\n";
       // JOptionPane.showMessageDialog(this, "About to compile:\n" + code);
        InMemoryCompiler comp = new InMemoryCompiler(pack, name,  source);
        Class classmodel = null;
        try {
            // String res=comp.compile();
            //classmodel = comp.getCompiledClass();
            //String res=comp.compile();
            classmodel = comp.compile();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            JOptionPane.showMessageDialog(this, "Failed to compile code:" + ex.getMessage());
        }
        HiddenVariablesIF newmodel = null;
        if (classmodel != null) {
            try {
                newmodel = (HiddenVariablesIF) classmodel.newInstance();
            } catch (InstantiationException ex) {
                Exceptions.printStackTrace(ex);
                JOptionPane.showMessageDialog(this, "Failed to compile code:" + ex.getMessage());
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
                JOptionPane.showMessageDialog(this, "Failed to compile code:" + ex.getMessage());
            }
        }
        
        if (newmodel != null) {
            hiddenVars = newmodel;
            //JOptionPane.showMessageDialog(this, "Using model "+hiddenVars.getClass().getName());
        } else {
            JOptionPane.showMessageDialog(this, "I couldn't use the code:\n" + code);
        }
    }
     private void getFormulaModel() throws HeadlessException {
        String name = "MyFormula"+nr;
        String pack = "org.physics.epr.hiddenvars";
        String code = this.txtFormula.getText();
        String source = "package " + pack + ";\n\n";
        source += "import org.physics.epr.Particle;\n";
        source += "import org.physics.epr.hiddenvars.MeasurementFormulaIF;\n\n";
        source += "public class "+name+" implements MeasurementFormulaIF {\n";
        source += code + "\n\n";

        source += "\tpublic String getFormula() {\n";
        source += "\t\treturn null;\n";
        source += "\t}\n";
        source += "}\n";
       // JOptionPane.showMessageDialog(this, "About to compile:\n" + code);
        InMemoryCompiler comp = new InMemoryCompiler(pack, name,  source);
        Class classmodel = null;
        try {
            // String res=comp.compile();
            //classmodel = comp.getCompiledClass();
            //String res=comp.compile();
            classmodel = comp.compile();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            JOptionPane.showMessageDialog(this, "Failed to compile code:" + ex.getMessage());
        }
        MeasurementFormulaIF newmodel = null;
        if (classmodel != null) {
            try {
                newmodel = (MeasurementFormulaIF) classmodel.newInstance();
            } catch (InstantiationException ex) {
                Exceptions.printStackTrace(ex);
                JOptionPane.showMessageDialog(this, "Failed to compile code:" + ex.getMessage());
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
                JOptionPane.showMessageDialog(this, "Failed to compile code:" + ex.getMessage());
            }
        }
        
        if (newmodel != null) {
            formula = newmodel;
            //JOptionPane.showMessageDialog(this, "Using model "+hiddenVars.getClass().getName());
        } else {
            JOptionPane.showMessageDialog(this, "I couldn't use the code:\n" + code);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panNorth = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnFormula = new javax.swing.JButton();
        btnVars = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnRun = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        panResults = new javax.swing.JPanel();
        panCHSH = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblCHSH = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        panNorth.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.panNorth.border.title"))); // NOI18N

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {new Float(0.0), new Float(90.0), new Float(45.0), new Float(135.0)}
            },
            new String [] {
                "Angle A1", "Angle A2", "Angle B1", "Angle B2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.table.columnModel.title0")); // NOI18N
        table.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.table.columnModel.title1")); // NOI18N
        table.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.table.columnModel.title2")); // NOI18N
        table.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.table.columnModel.title3")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnFormula, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.btnFormula.text")); // NOI18N
        btnFormula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormulaActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnVars, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.btnVars.text")); // NOI18N
        btnVars.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVarsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFormula, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(btnVars))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(btnFormula)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVars)
                .addGap(16, 16, 16))
        );

        org.openide.awt.Mnemonics.setLocalizedText(btnRun, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.btnRun.text")); // NOI18N
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.jLabel1.text")); // NOI18N

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(10000, 5000, 100000, 1000));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(btnRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(83, 83, 83))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRun)
                .addContainerGap())
        );

        javax.swing.GroupLayout panNorthLayout = new javax.swing.GroupLayout(panNorth);
        panNorth.setLayout(panNorthLayout);
        panNorthLayout.setHorizontalGroup(
            panNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panNorthLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panNorthLayout.setVerticalGroup(
            panNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panNorthLayout.createSequentialGroup()
                .addGroup(panNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panNorthLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panNorthLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panNorthLayout.createSequentialGroup()
                        .addContainerGap(28, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        add(panNorth, java.awt.BorderLayout.NORTH);

        panResults.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.panResults.border.title"))); // NOI18N
        panResults.setLayout(new java.awt.BorderLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(7);
        jScrollPane2.setViewportView(jTextArea1);

        lblCHSH.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(lblCHSH, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.lblCHSH.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.jLabel5.text")); // NOI18N

        txtCo.setColumns(10);
        txtCo.setEditable(false);
        txtCo.setText(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.txtCo.text")); // NOI18N
        txtCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCoActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.jLabel3.text")); // NOI18N

        txtNo.setColumns(10);
        txtNo.setEditable(false);
        txtNo.setText(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.txtNo.text")); // NOI18N

        txtTotal.setColumns(10);
        txtTotal.setEditable(false);
        txtTotal.setText(org.openide.util.NbBundle.getMessage(SimulationTopComponent.class, "SimulationTopComponent.txtTotal.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(txtCo, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(txtNo, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panCHSHLayout = new javax.swing.GroupLayout(panCHSH);
        panCHSH.setLayout(panCHSHLayout);
        panCHSHLayout.setHorizontalGroup(
            panCHSHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCHSHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panCHSHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panCHSHLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panCHSHLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCHSH, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        panCHSHLayout.setVerticalGroup(
            panCHSHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCHSHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panCHSHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblCHSH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panCHSHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panResults.add(panCHSH, java.awt.BorderLayout.PAGE_START);

        add(panResults, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        doRunAction();
}//GEN-LAST:event_btnRunActionPerformed

    private void btnVarsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVarsActionPerformed
        txtHidden = new JTextArea(30, 50);
        txtHidden.setText(this.hiddenVars.getFormula());
        int ans = JOptionPane.showConfirmDialog(this, new JScrollPane(this.txtHidden), "Sharing of hidden variables" , JOptionPane.OK_CANCEL_OPTION);
        if (ans != JOptionPane.OK_OPTION) return;
        getHiddenVarsModel();
    }//GEN-LAST:event_btnVarsActionPerformed

    private void txtCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCoActionPerformed

    private void btnFormulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormulaActionPerformed
        txtFormula = new JTextArea(30, 50);
        txtFormula.setText(this.formula.getFormula());
        int ans = JOptionPane.showConfirmDialog(this, new JScrollPane(this.txtFormula), "Probability of detecting sping +1" , JOptionPane.OK_CANCEL_OPTION);
        if (ans != JOptionPane.OK_OPTION) return;
        getFormulaModel();
    }//GEN-LAST:event_btnFormulaActionPerformed
    private void doRunAction() {
        p("Running experiment");
        gatherParameters();
        exp = new Main();
        int times = (Integer) this.jSpinner1.getValue();        
        nr++;
        measurements = exp.runExperiment(times, formula, hiddenVars);
        
        showResults();
        
    }
    private void showResults() {
        int tot= measurements.getTotal();
        if (tot <=0) return;
        int no = measurements.getNoCoincidences();
        int co = measurements.getCoincidences();
        DecimalFormat f = new DecimalFormat ("00.00%");
        this.txtTotal.setText(""+tot+" = 100.0%");
        this.txtNo.setText(""+no+" = "+f.format((double)no/(double)tot));
        this.txtCo.setText(""+co+" = "+f.format((double)co/(double)tot));
        double chsh = measurements.getCHSH();
        String color =  "'990000'";
        String msg = " -> We didn't break it";
        if (chsh > 2.5) {
            color = "'009900'";
            msg = " -> Looks like we broke it!";
        }
        else if (chsh > 2.0001) {
            color = "'0099'";
            msg = " -> Getting close :-)";
        }
        f = new DecimalFormat(" 0.00");
        this.lblCHSH.setText("<html><b><font color="+color+">"+f.format(chsh)+msg+"</font></b></html>" );
        this.jTextArea1.setText(measurements.getCHSHResult());        
        showChart();
    }
    private void showChart() {
        if (chart != null) {
            this.panResults.remove(scroll);
        }
        chart = new RabPanel(measurements);
        chart.setSize(400, 300);
        chart.setMaximumSize(new Dimension(600, 400));
        scroll = new JScrollPane(chart);
        //anChart.add("Center", chart);
        panResults.add("Center", scroll);
        repaint();
        chart.paintImmediately(0,0,800,600);
        paintImmediately(0,0,800,600);
        this.invalidate();;
        this.revalidate();
    }
    private void gatherParameters() {
        Setup.useAllMicroframes = true;//!checkOneMicro.isSelected();
        Setup.usePredifinedAnglesAB = false;
        Setup.A1 = ((Float) table.getValueAt(0, 0)).floatValue();
        Setup.A2 = ((Float) table.getValueAt(0, 1)).floatValue();
        Setup.B1 = ((Float) table.getValueAt(0, 2)).floatValue();
        Setup.B2 = ((Float) table.getValueAt(0, 3)).floatValue();

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFormula;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnVars;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblCHSH;
    private javax.swing.JPanel panCHSH;
    private javax.swing.JPanel panNorth;
    private javax.swing.JPanel panResults;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtCo;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    private void p(String string) {
        System.out.println("Simulation: "+string);
    }
}
