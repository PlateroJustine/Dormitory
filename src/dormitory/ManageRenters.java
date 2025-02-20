/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dormitory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class ManageRenters extends javax.swing.JFrame {

    /**
     * Creates new form ManageRenters
     */
    public ManageRenters() {
        initComponents();
        loadRenterData();
    }

    private void loadRenterData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear table before loading new data

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT renter_id, name, room_id, balance, penalty, last_payment_date FROM renter";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int renterId = rs.getInt("renter_id");
                String name = rs.getString("name");
                int roomId = rs.getInt("room_id");
                double balance = rs.getDouble("balance");
                double penalty = rs.getDouble("penalty");
                String lastPaymentDate = rs.getString("last_payment_date");

                // If the balance is zero, set it to "Paid"
                String balanceDisplay = (balance == 0) ? "Paid" : String.valueOf(balance);

                // Add row to table
                model.addRow(new Object[]{renterId, name, roomId, balanceDisplay, penalty, lastPaymentDate});
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set custom renderer for rows
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Get the balance value from the row (4th column)
                double balance = (jTable1.getValueAt(row, 3) != null && !jTable1.getValueAt(row, 3).equals("Paid")) ?
                                 Double.parseDouble(jTable1.getValueAt(row, 3).toString()) : 0;

                // Set the row color based on the balance
                if (balance > 0) {
                    cell.setBackground(new java.awt.Color(255, 204, 204)); // Light red for unpaid renters
                } else {
                    cell.setBackground(new java.awt.Color(204, 255, 204)); // Light green for paid renters
                }

                // Set the text color
                if (isSelected) {
                    cell.setForeground(java.awt.Color.WHITE); // White text for selected row
                } else {
                    cell.setForeground(java.awt.Color.BLACK); // Black text for normal rows
                }

                return cell;
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BackButton = new javax.swing.JButton();
        RemoveRenterButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jLabel1.setText("MIYAGI DORMITORY");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Renter ID", "Name", "Room", "Balance", "Penalty"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 480, 280));

        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });
        jPanel1.add(BackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 360, 120, 30));

        RemoveRenterButton1.setText("Remove Renter");
        RemoveRenterButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveRenterButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(RemoveRenterButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        new MainDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButtonActionPerformed

    private void RemoveRenterButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveRenterButton1ActionPerformed
        int selectedRow = jTable1.getSelectedRow();

            if (selectedRow == -1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Please select a renter to remove.");
                return;
            }

            // Get Renter ID from the selected row
            int renterId = (int) jTable1.getValueAt(selectedRow, 0);

            int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to remove this renter?", 
                    "Confirm Removal", 
                    javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                try {
                    Connection conn = DatabaseConnection.getConnection();

                    // First, delete the corresponding ledger records
                    String deleteLedgerSql = "DELETE FROM ledger WHERE renter_id = ?";
                    PreparedStatement deleteLedgerStmt = conn.prepareStatement(deleteLedgerSql);
                    deleteLedgerStmt.setInt(1, renterId);
                    deleteLedgerStmt.executeUpdate();
                    deleteLedgerStmt.close();

                    // Now, delete the renter record
                    String deleteRenterSql = "DELETE FROM renter WHERE renter_id = ?";
                    PreparedStatement deleteRenterStmt = conn.prepareStatement(deleteRenterSql);
                    deleteRenterStmt.setInt(1, renterId);
                    int rowsAffected = deleteRenterStmt.executeUpdate();

                    deleteRenterStmt.close();
                    conn.close();

                    if (rowsAffected > 0) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Renter removed successfully.");
                        loadRenterData(); // Refresh the table after deletion
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(this, "Failed to remove renter.");
                    }
                } catch (Exception e) {
                    // Show an error message if an exception occurs
                    javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }
            }
    }//GEN-LAST:event_RemoveRenterButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ManageRenters.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageRenters.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageRenters.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageRenters.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageRenters().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JButton RemoveRenterButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
