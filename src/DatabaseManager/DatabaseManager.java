package DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;

    // Constructor to establish the database connection
    public DatabaseManager() {
        try {
            // Establishing connection with SQL Server database
            connection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost;databaseName=CodeCademy;username=admin;password=admin123;integratedSecurity=false;encrypt=true;trustServerCertificate=true;");
        } catch (SQLException e) {
            // Print stack trace if connection fails
            e.printStackTrace();
        }
    }

    // Method to execute SQL queries and return result set
    public ResultSet query(String sqlQuery) {
        try {
            Statement stmt = connection.createStatement();
            // Execute the SQL query and return the result set
            return stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            // Print stack trace if query execution fails
            e.printStackTrace();
        }
        return null;
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }
}
