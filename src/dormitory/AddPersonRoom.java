/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dormitory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class AddPersonRoom extends javax.swing.JFrame {

    /**
     * Creates new form AddPersonRoom
     */
    public AddPersonRoom() {
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        AddButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jLabel1.setText("MIYAGI DORMITORY");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        jLabel2.setText("NAME:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        jLabel3.setFont(new java.awt.Font("Sitka Text", 0, 14)); // NOI18N
        jLabel3.setText("ROOM ID:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        jTextField1.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 143, 120, 30));

        jTextField2.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 73, 120, 30));

        AddButton.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        AddButton.setText("Add");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });
        jPanel1.add(AddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 100, 30));

        CancelButton.setFont(new java.awt.Font("Sitka Text", 0, 12)); // NOI18N
        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(CancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 100, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        String name = jTextField2.getText().trim();
        String roomId = jTextField1.getText().trim();

        if (name.isEmpty() || roomId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement checkRoomStmt = null;
        PreparedStatement insertStmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement insertPaymentStmt = null;
        PreparedStatement insertLedgerStmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            // Check if the room exists and has space
            String checkRoomQuery = "SELECT max_capacity, current_occupants FROM room WHERE room_id = ?";
            checkRoomStmt = conn.prepareStatement(checkRoomQuery);
            checkRoomStmt.setInt(1, Integer.parseInt(roomId));
            rs = checkRoomStmt.executeQuery();

            if (rs.next()) {
                int maxCapacity = rs.getInt("max_capacity");
                int currentOccupants = rs.getInt("current_occupants");

                if (currentOccupants >= maxCapacity) {
                    JOptionPane.showMessageDialog(this, "Room is already full!", "Room Full", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Insert the person into the renter table
                String insertResidentQuery = "INSERT INTO renter (name, room_id, balance, penalty, last_payment_date) VALUES (?, ?, ?, ?, ?)";
                insertStmt = conn.prepareStatement(insertResidentQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                insertStmt.setString(1, name);
                insertStmt.setInt(2, Integer.parseInt(roomId));
                insertStmt.setDouble(3, 0.0);  // Default balance
                insertStmt.setDouble(4, 0.0);  // Default penalty
                insertStmt.setNull(5, java.sql.Types.DATE);  // No payment date yet

                int rowsInserted = insertStmt.executeUpdate();
                rs = insertStmt.getGeneratedKeys();
                int renterId = -1;
                if (rs.next()) {
                    renterId = rs.getInt(1); // Get the generated renter_id
                }

                if (rowsInserted > 0 && renterId != -1) {
                    // Update the room's current_occupants count
                    String updateRoomQuery = "UPDATE room SET current_occupants = current_occupants + 1 WHERE room_id = ?";
                    updateStmt = conn.prepareStatement(updateRoomQuery);
                    updateStmt.setInt(1, Integer.parseInt(roomId));
                    updateStmt.executeUpdate();

                    // Insert initial payment record for the renter
                    String insertPaymentQuery = "INSERT INTO payment (renter_id, room_id, amount_paid, electricity_bill, water_bill) VALUES (?, ?, 0.00, 700.00, 300.00)";
                    insertPaymentStmt = conn.prepareStatement(insertPaymentQuery);
                    insertPaymentStmt.setInt(1, renterId);
                    insertPaymentStmt.setInt(2, Integer.parseInt(roomId));
                    insertPaymentStmt.executeUpdate();

                    // Insert into ledger automatically
                    String insertLedgerQuery = "INSERT INTO ledger (renter_id, room_id, total_due, total_paid, balance) VALUES (?, ?, 0.00, 0.00, 0.00)";
                    insertLedgerStmt = conn.prepareStatement(insertLedgerQuery);
                    insertLedgerStmt.setInt(1, renterId);
                    insertLedgerStmt.setInt(2, Integer.parseInt(roomId));
                    insertLedgerStmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Person added successfully with default bills and ledger entry!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh the Payments frame if it's open
                    Payments paymentsFrame = new Payments();
                    paymentsFrame.loadPaymentData(); // Ensure this method exists in Payments class
                    paymentsFrame.setVisible(true);

                    new ManageRoom().setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Room not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding person!", "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (checkRoomStmt != null) checkRoomStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (updateStmt != null) updateStmt.close();
                if (insertPaymentStmt != null) insertPaymentStmt.close();
                if (insertLedgerStmt != null) insertLedgerStmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_AddButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        new ManageRoom().setVisible(true);
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
            java.util.logging.Logger.getLogger(AddPersonRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPersonRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPersonRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPersonRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddPersonRoom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
