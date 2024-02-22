package DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost;databaseName=CodeCademy;username=admin;password=admin123;integratedSecurity=false;encrypt=true;trustServerCertificate=true;");
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public ResultSet query(String sqlQuery) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }

}
