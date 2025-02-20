/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dormitory;

import java.awt.Color;
import java.awt.Component;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Payments extends javax.swing.JFrame {

    /**
     * Creates new form Payments
     */
    public Payments() {
        initComponents();
        loadRenterNames();
        loadPaymentData();
        loadTotalIncome(); // Refresh total income after payment

    }

    private void loadRenterNames() {
        jComboBoxRoom.removeAllItems();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM renter")) {

            while (rs.next()) {
                jComboBoxRoom.addItem(rs.getString("name"));
            }

            if (jComboBoxRoom.getItemCount() == 0) {
                jComboBoxRoom.addItem("No Renters Available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading renter names", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Get renter ID by name
    private int getRenterIdByName(String renterName) {
        int renterId = -1;
        String query = "SELECT renter_id FROM renter WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, renterName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                renterId = rs.getInt("renter_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return renterId;
    }

    public void loadPaymentData() {
        String query = "SELECT rt.name, r.fixed_rent, p.electricity_bill, p.water_bill, "
                + "(r.fixed_rent + p.electricity_bill + p.water_bill) AS total_due, "
                + "p.amount_paid, ((r.fixed_rent + p.electricity_bill + p.water_bill) - p.amount_paid) AS balance "
                + "FROM payment p "
                + "JOIN renter rt ON p.renter_id = rt.renter_id "
                + "JOIN room r ON rt.room_id = r.room_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            DefaultTableModel model = (DefaultTableModel) jTablePayments.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                double balance = rs.getDouble("balance");
                Object balanceDisplay = (balance == 0) ? "Paid" : balance;

                model.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getDouble("total_due"),
                        rs.getDouble("amount_paid"),
                        balanceDisplay, // Show "Paid" instead of 0.00
                        rs.getDouble("electricity_bill"),
                        rs.getDouble("water_bill")
                });
            }

            // Apply custom renderer to all columns for entire row coloring
            for (int i = 0; i < jTablePayments.getColumnCount(); i++) {
                jTablePayments.getColumnModel().getColumn(i).setCellRenderer(new PaymentStatusRenderer());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading payment data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        class PaymentStatusRenderer extends DefaultTableCellRenderer {
        @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Object balanceValue = table.getValueAt(row, 3); // Balance column index 3

                // Check the balance value
                if (balanceValue != null) {
                    if (balanceValue.equals("Paid")) {
                        c.setBackground(Color.GREEN); // Fully paid, color the row green
                    } else {
                        try {
                            double balance = Double.parseDouble(balanceValue.toString());
                            if (balance > 0) {
                                c.setBackground(Color.RED); // Unpaid, color the row red
                            } else {
                                c.setBackground(Color.WHITE); // Default background
                            }
                        } catch (NumberFormatException e) {
                            c.setBackground(Color.WHITE); // Default background for any errors
                        }
                    }
                } else {
                    c.setBackground(Color.WHITE); // Default background
                }

                // Apply this background color to the whole row
                if (isSelected) {
                    c.setBackground(c.getBackground().darker()); // Darken selected row
                }

                return c;
            }
        }
        
        private void loadTotalIncome() {
            String query = "SELECT SUM(amount_paid) AS total_income FROM payment";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                if (rs.next()) {
                    double totalIncome = rs.getDouble("total_income");
                    TotalIncomeText.setText(String.valueOf(totalIncome));
                } else {
                    TotalIncomeText.setText("0");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading total income", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePayments = new javax.swing.JTable();
        jComboBoxRoom = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldPayment = new javax.swing.JTextField();
        PayButton = new javax.swing.JButton();
        BackButton = new javax.swing.JButton();
        TotalIncomeText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jLabel1.setText("MIYAGI DORMITORY");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jTablePayments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Renter Name", "Total Due", "Amount Paid", "Balance", "Electricity Bill", "Water Bill"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTablePayments);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 580, 280));

        jComboBoxRoom.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jPanel1.add(jComboBoxRoom, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 100, 30));

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel2.setText("Total Income:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 22, -1, -1));
        jPanel1.add(jTextFieldPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 90, 30));

        PayButton.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        PayButton.setText("Pay");
        PayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayButtonActionPerformed(evt);
            }
        });
        jPanel1.add(PayButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, 90, 30));

        BackButton.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });
        jPanel1.add(BackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 360, 90, 30));

        TotalIncomeText.setEditable(false);
        TotalIncomeText.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        TotalIncomeText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalIncomeTextActionPerformed(evt);
            }
        });
        jPanel1.add(TotalIncomeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 120, 30));

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel4.setText("Enter Payment:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 364, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayButtonActionPerformed
        String renterName = (String) jComboBoxRoom.getSelectedItem();
        if (renterName == null || renterName.equals("No Renters Available")) {
            JOptionPane.showMessageDialog(this, "Please select a valid renter.", "Invalid Renter", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String paymentText = jTextFieldPayment.getText();
        if (paymentText.isEmpty() || !paymentText.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid payment amount.", "Invalid Payment", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double paymentAmount = Double.parseDouble(paymentText);
        int renterId = getRenterIdByName(renterName);

        if (renterId == -1) {
            JOptionPane.showMessageDialog(this, "Invalid renter selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fetch the balance due
        String balanceQuery = "SELECT ((r.fixed_rent + p.electricity_bill + p.water_bill) - p.amount_paid) AS balance_due "
                + "FROM payment p "
                + "JOIN renter rt ON p.renter_id = rt.renter_id "
                + "JOIN room r ON rt.room_id = r.room_id "
                + "WHERE rt.renter_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement balanceStmt = conn.prepareStatement(balanceQuery)) {

            balanceStmt.setInt(1, renterId);
            ResultSet rs = balanceStmt.executeQuery();

            if (rs.next()) {
                double balanceDue = rs.getDouble("balance_due");

                // Check if the entered payment is the correct amount (i.e., balance due)
                if (paymentAmount != balanceDue) {
                    JOptionPane.showMessageDialog(this, "The payment does not match the balance due. Please enter the correct amount.", 
                                                  "Payment Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If the payment matches the balance, update the payment record
                String updateQuery = "UPDATE payment SET amount_paid = amount_paid + ? WHERE renter_id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setDouble(1, paymentAmount);
                    updateStmt.setInt(2, renterId);
                    int updatedRows = updateStmt.executeUpdate();

                    if (updatedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Payment made successfully! Remaining Balance Updated.", 
                                                      "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadPaymentData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Payment update failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Payment record not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating payment", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    
    }//GEN-LAST:event_PayButtonActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        new MainDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButtonActionPerformed

    private void TotalIncomeTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalIncomeTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalIncomeTextActionPerformed

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
            java.util.logging.Logger.getLogger(Payments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payments().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JButton PayButton;
    private javax.swing.JTextField TotalIncomeText;
    private javax.swing.JComboBox<String> jComboBoxRoom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePayments;
    private javax.swing.JTextField jTextFieldPayment;
    // End of variables declaration//GEN-END:variables
}
