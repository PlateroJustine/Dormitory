/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dormitory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class ManageRoom extends javax.swing.JFrame {

    /**
     * Creates new form ManageRoom
     */
    public ManageRoom() {
        initComponents();
        loadRoomData();
    }

    private void loadRoomData() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Clear existing data

    try {
        Connection conn = DatabaseConnection.getConnection(); // Get database connection

        // Query to join 'room' and 'dorm' to get dorm name based on dorm_id
        String sql = "SELECT r.room_id, r.room_number, r.status, r.current_occupants, d.dorm_name "
                   + "FROM room r "
                   + "JOIN dorm d ON r.dorm_id = d.dorm_id"; // Using dorm_id to link the room to dorm

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int roomId = rs.getInt("room_id");
            int roomNumber = rs.getInt("room_number");
            String roomStatus = rs.getString("status");
            int occupants = rs.getInt("current_occupants");
            String dormName = rs.getString("dorm_name"); // Fetching dorm name from the dorm table

            // If the room has 4 occupants, update the status to "Full"
            if (occupants >= 4) {
                roomStatus = "Full";
            }

            // Add the row to the table model, ensure Dorm Name is in the first column
            model.addRow(new Object[]{dormName, roomId, roomNumber, roomStatus, occupants});
        }

        // Set custom renderer for the whole row based on the room status
            jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    javax.swing.JPanel panel = new javax.swing.JPanel();
                    panel.setLayout(new java.awt.BorderLayout());
                    javax.swing.JLabel label = new javax.swing.JLabel();

                    label.setText(value != null ? value.toString() : "");

                    // Check room status and set row color
                    String status = (String) table.getValueAt(row, 3); // Get room status from the 4th column (index 3)
                    if ("Full".equals(status)) {
                        panel.setBackground(new java.awt.Color(255, 102, 102)); // Lighter red for full rooms
                        label.setForeground(java.awt.Color.WHITE); // White text for full rooms
                    } else {
                        panel.setBackground(new java.awt.Color(102, 255, 102)); // Lighter green for available rooms
                        label.setForeground(java.awt.Color.BLACK); // Black text for available rooms
                    }

                    panel.add(label, java.awt.BorderLayout.CENTER);
                    return panel;
                }
            });

        rs.close();
        pstmt.close();
        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        RemoveRoomButton = new javax.swing.JButton();
        AddRoomButton1 = new javax.swing.JButton();
        AddPersonButton = new javax.swing.JButton();
        BackButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        jLabel1.setText("MIYAGI DORMITORY");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        jTable1.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Dorm Name", "Room ID", "Room Number", "Room Status", "Occupants"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 480, 270));

        RemoveRoomButton.setText("Remove Room");
        RemoveRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveRoomButtonActionPerformed(evt);
            }
        });
        jPanel1.add(RemoveRoomButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 350, 110, 30));

        AddRoomButton1.setText("Add Room");
        AddRoomButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRoomButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(AddRoomButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 110, 30));

        AddPersonButton.setText("Add Person");
        AddPersonButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPersonButtonActionPerformed(evt);
            }
        });
        jPanel1.add(AddPersonButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 350, 110, 30));

        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });
        jPanel1.add(BackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, 110, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddRoomButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRoomButton1ActionPerformed
        AddRoom addRoomWindow = new AddRoom();
        addRoomWindow.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_AddRoomButton1ActionPerformed

    private void AddPersonButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPersonButtonActionPerformed
        AddPersonRoom addPersonRoomForm = new AddPersonRoom();
        addPersonRoomForm.setVisible(true);
        this.dispose();

        // After adding a person, update the ledger
        addRenterToLedger();
    }  

    private void addRenterToLedger() {
        try {
            Connection conn = DatabaseConnection.getConnection();

            // Fetch the latest renter added from the renter table
            String fetchLatestRenter = "SELECT renter_id, room_id, balance, penalty FROM renter ORDER BY renter_id DESC LIMIT 1";
            PreparedStatement fetchStmt = conn.prepareStatement(fetchLatestRenter);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                int renterId = rs.getInt("renter_id");
                int roomId = rs.getInt("room_id");
                double balance = rs.getDouble("balance");
                double penalty = rs.getDouble("penalty");

                // Insert renter details into ledger
                String insertLedger = "INSERT INTO ledger (renter_id, room_id, total_due, total_paid, balance) VALUES (?, ?, 0, 0, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertLedger);
                insertStmt.setInt(1, renterId);
                insertStmt.setInt(2, roomId);
                insertStmt.setDouble(3, balance);  // Set the initial balance
                insertStmt.executeUpdate();
                insertStmt.close();
            }

            rs.close();
            fetchStmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_AddPersonButtonActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        new MainDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButtonActionPerformed

    private void RemoveRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveRoomButtonActionPerformed
        int selectedRow = jTable1.getSelectedRow(); // Get selected row index
    
        if (selectedRow == -1) { // No row selected
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a room to remove.", "No Selection", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roomId = (int) jTable1.getValueAt(selectedRow, 0); // Get room_id from the selected row

        // Confirm deletion
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this room?", "Confirm Deletion", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Connection conn = DatabaseConnection.getConnection();

            // Check if there are occupants in the room
            String checkOccupantsQuery = "SELECT current_occupants FROM room WHERE room_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkOccupantsQuery);
            checkStmt.setInt(1, roomId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int occupants = rs.getInt("current_occupants");
                if (occupants > 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Cannot remove a room with occupants!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Delete room from database
            String deleteQuery = "DELETE FROM room WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, roomId);

            int rowsDeleted = pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            if (rowsDeleted > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Room removed successfully!");
                loadRoomData(); // Refresh table
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Failed to remove room!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error removing room!", "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_RemoveRoomButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ManageRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageRoom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPersonButton;
    private javax.swing.JButton AddRoomButton1;
    private javax.swing.JButton BackButton;
    private javax.swing.JButton RemoveRoomButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
