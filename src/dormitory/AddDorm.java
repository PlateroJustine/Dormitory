/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dormitory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class AddDorm extends javax.swing.JFrame {

    /**
     * Creates new form AddDorm
     */
    public AddDorm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldDormID = new javax.swing.JTextField();
        jTextFieldDormName = new javax.swing.JTextField();
        AddButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldLocation = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldOwnerID2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jLabel1.setText("MIYAGI DORMITORY");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        jLabel3.setText("DORM ID:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jLabel4.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        jLabel4.setText("DORM NAME:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 105, -1, -1));
        jPanel1.add(jTextFieldDormID, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 120, 30));
        jPanel1.add(jTextFieldDormName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 95, 120, 30));

        AddButton.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        AddButton.setText("Add");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });
        jPanel1.add(AddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 100, 30));

        CancelButton.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(CancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, 100, 30));

        jLabel5.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        jLabel5.setText("LOCATION:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 193, -1, -1));
        jPanel1.add(jTextFieldLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 185, 120, 30));

        jLabel6.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        jLabel6.setText("OWNER ID:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));
        jPanel1.add(jTextFieldOwnerID2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
            String dormID = jTextFieldDormID.getText().trim();
        String dormName = jTextFieldDormName.getText().trim();
        String ownerID = jTextFieldOwnerID2.getText().trim();
        String location = jTextFieldLocation.getText().trim();  // Get the location text

        if (dormID.isEmpty() || dormName.isEmpty() || ownerID.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dormitory_db", "root", "");
            String sql = "INSERT INTO dorm (dorm_id, dorm_name, owner_id, location) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dormID);
            pstmt.setString(2, dormName);
            pstmt.setString(3, ownerID);
            pstmt.setString(4, location);  // Insert location into the database

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Dorm added successfully!");
                this.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_AddButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddDorm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddDorm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddDorm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddDorm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddDorm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldDormID;
    private javax.swing.JTextField jTextFieldDormName;
    private javax.swing.JTextField jTextFieldLocation;
    private javax.swing.JTextField jTextFieldOwnerID2;
    // End of variables declaration//GEN-END:variables
}
