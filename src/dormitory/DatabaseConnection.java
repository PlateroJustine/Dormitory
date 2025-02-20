package dormitory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/dormitory_db";
    private static final String USER = "root";  // Change if needed
    private static final String PASSWORD = "";  // Change if needed

    // Method to establish database connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure MySQL JDBC Driver is loaded
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Database Connection Failed: " + e.getMessage());
        }
        return conn;
    }

    // Method to fetch owner details by username
    public static void getOwnerByUsername(String username) {
        String sql = "SELECT o.owner_name FROM owner o " +
                     "JOIN admin a ON o.owner_id = a.admin_id " +
                     "WHERE a.username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("Owner Found: " + rs.getString("owner_name"));
            } else {
                System.out.println("Owner not found.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching owner: " + e.getMessage());
        }
    }
}
