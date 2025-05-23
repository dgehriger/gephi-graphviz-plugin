package org.gephi.plugins.neo4j.importer.wizard;

import javax.swing.*;
import java.awt.*;

public class Neo4jDatabaseImporterConnectionPanel extends javax.swing.JPanel {

    /**
     * Creates new form Neo4jDatabaseImporterConnectionPanel
     */
    public Neo4jDatabaseImporterConnectionPanel() {
        initComponents();
        this.actionText.setVisible(false);
    }

    @Override
    public String getName() {
        return "Neo4j connection";   //this will be the title of the panel in the wizard
    }

    /**
     * Check that the form is valid.
     */
    public void checkValidity() throws Exception {
        // Check inputs
        if (Utils.isEmptyOrNull(dbUrl.getText())) throw new Exception("Url field is mandatory");
        if (this.dbAuthType.getSelectedIndex() == 0 && (Utils.isEmptyOrNull(dbUsername.getText()) || Utils.isEmptyOrNull(new String(dbPassword.getPassword()))))
            throw new Exception("Username and password are mandatory");

        // Check connection
        if (this.dbAuthType.getSelectedIndex() == 0)
            Utils.neo4jCheckConnection(dbUrl.getText(), dbUsername.getText(), new String(dbPassword.getPassword()), dbName.getText());
        else
            Utils.neo4jCheckConnection(dbUrl.getText(), null, null, dbName.getText());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dbUrlLabel = new javax.swing.JLabel();
        dbUrl = new javax.swing.JTextField();
        dbUsernameLabel = new javax.swing.JLabel();
        dbUsername = new javax.swing.JTextField();
        dbPasswordLabel = new javax.swing.JLabel();
        dbPassword = new javax.swing.JPasswordField();
        checkBtn = new javax.swing.JButton();
        dbNameLabel = new javax.swing.JLabel();
        dbName = new javax.swing.JTextField();
        dbAuthTypeLabel = new javax.swing.JLabel();
        dbAuthType = new javax.swing.JComboBox<>();
        actionTextScroll = new javax.swing.JScrollPane();
        actionText = new javax.swing.JTextArea();

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        setLayout(layout);

        dbUrlLabel.setText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.dbUrlLabel.text_1_1")); // NOI18N
        dbUrlLabel.setName("dbUrlLabel"); // NOI18N
        dbUrlLabel.setPreferredSize(new java.awt.Dimension(100, 25));
        dbUrlLabel.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dbUrlLabel, gridBagConstraints);

        dbUrl.setText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.dbUrl.text_1_1")); // NOI18N
        dbUrl.setName("dbUrl"); // NOI18N
        dbUrl.setPreferredSize(new java.awt.Dimension(300, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dbUrl, gridBagConstraints);

        dbUsernameLabel.setText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.dbUsernameLabel.text_1_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dbUsernameLabel, gridBagConstraints);

        dbUsername.setText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.dbUsername.text_1_1")); // NOI18N
        dbUsername.setName("dbUsername"); // NOI18N
        dbUsername.setPreferredSize(new java.awt.Dimension(10, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dbUsername, gridBagConstraints);

        dbPasswordLabel.setText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.dbPasswordLabel.text_1_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dbPasswordLabel, gridBagConstraints);

        dbPassword.setName("dbPassword"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dbPassword, gridBagConstraints);

        checkBtn.setText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.checkBtn.text_1")); // NOI18N
        checkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(checkBtn, gridBagConstraints);

        dbNameLabel.setText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.dbNameLabel.text_1_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(dbNameLabel, gridBagConstraints);

        dbName.setToolTipText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.dbName.toolTipText_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dbName, gridBagConstraints);

        dbAuthTypeLabel.setText(org.openide.util.NbBundle.getMessage(Neo4jDatabaseImporterConnectionPanel.class, "Neo4jDatabaseImporterConnectionPanel.dbAuthTypeLabel.text_1_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(dbAuthTypeLabel, gridBagConstraints);

        dbAuthType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Username / Password", "No authentication" }));
        dbAuthType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dbAuthTypeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dbAuthType, gridBagConstraints);

        actionTextScroll.setBorder(null);

        actionText.setEditable(false);
        actionText.setColumns(20);
        actionText.setLineWrap(true);
        actionText.setRows(2);
        actionText.setBorder(null);
        actionText.setFocusable(false);
        actionTextScroll.setViewportView(actionText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(actionTextScroll, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void checkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBtnActionPerformed
        this.checkBtn.setEnabled(false);
        this.dbUrl.setEditable(false);
        this.dbUsername.setEditable(false);
        this.dbPassword.setEnabled(false);
        this.dbName.setEditable(false);
        this.actionText.setVisible(false);
        try {
            this.checkValidity();
            this.actionText.setText("Connection successful");
            this.actionText.setForeground(Color.GREEN);
        } catch (Exception e) {
            this.actionText.setText(e.getMessage());
            this.actionText.setForeground(Color.RED);
        } finally {
            this.checkBtn.setEnabled(true);
            this.dbUrl.setEditable(true);
            this.dbUsername.setEditable(true);
            this.dbPassword.setEnabled(true);
            this.dbName.setEditable(true);
            this.actionText.setVisible(true);
        }
    }//GEN-LAST:event_checkBtnActionPerformed

    private void dbAuthTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbAuthTypeActionPerformed
        this.dbUsername.setVisible(this.dbAuthType.getSelectedIndex() == 0);
        this.dbUsernameLabel.setVisible(this.dbAuthType.getSelectedIndex() == 0);
        this.dbPassword.setVisible(this.dbAuthType.getSelectedIndex() == 0);
        this.dbPasswordLabel.setVisible(this.dbAuthType.getSelectedIndex() == 0);
    }//GEN-LAST:event_dbAuthTypeActionPerformed


    public JComboBox<String> getDbAuthType() {
        return dbAuthType;
    }

    public JTextField getDbName() {
        return dbName;
    }

    public JPasswordField getDbPassword() {
        return dbPassword;
    }

    public JTextField getDbUrl() {
        return dbUrl;
    }

    public JTextField getDbUsername() {
        return dbUsername;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea actionText;
    private javax.swing.JScrollPane actionTextScroll;
    private javax.swing.JButton checkBtn;
    private javax.swing.JComboBox<String> dbAuthType;
    private javax.swing.JLabel dbAuthTypeLabel;
    private javax.swing.JTextField dbName;
    private javax.swing.JLabel dbNameLabel;
    private javax.swing.JPasswordField dbPassword;
    private javax.swing.JLabel dbPasswordLabel;
    private javax.swing.JTextField dbUrl;
    private javax.swing.JLabel dbUrlLabel;
    private javax.swing.JTextField dbUsername;
    private javax.swing.JLabel dbUsernameLabel;
    // End of variables declaration//GEN-END:variables
}
