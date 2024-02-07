// package db;

// import java.sql.*;

// /**
// * Dit is een voorbeeld Java toepassing waarin je verbinding maakt met een
// * SQLServer database.
// */
// public class Db {
// // Dit zijn de instellingen voor de verbinding. Vervang de databaseName
// indien
// // deze voor jou anders is.
// private final String connectionUrl =
// "jdbc:sqlserver://localhost;databaseName=codecademy;username=admin;password=admin123;integratedSecurity=false;encrypt=true;trustServerCertificate=true;";

// // Connection beheert informatie over de connectie met de database.
// private Connection con = null;

// // Statement zorgt dat we een SQL query kunnen uitvoeren.
// private Statement stmt = null;

// // ResultSet is de tabel die we van de database terugkrijgen.
// // We kunnen door de rows heen stappen en iedere kolom lezen.
// private ResultSet rs = null;

// public void connect() {
// try {
// // 'Importeer' de driver die je gedownload hebt.
// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

// // Maak de verbinding met de database.
// con = DriverManager.getConnection(connectionUrl);

// // Voorbeeldquery uitvoeren (test query)
// // query("SELECT TOP 10 * FROM Users");

// } catch (ClassNotFoundException | SQLException e) {
// e.printStackTrace();
// }
// // finally {
// // // Sluit alle recources in apparte function/method
// // closeResources();
// // }
// }

// public ResultSet query(String sqlQuery) {
// try {
// stmt = con.createStatement();
// // Voer de query uit op de database.
// rs = stmt.executeQuery(sqlQuery);
// return rs;
// } catch (SQLException e) {
// e.printStackTrace();
// }
// return null;
// }

// private void closeResources() {
// try {
// if (rs != null)
// rs.close();
// if (stmt != null)
// stmt.close();
// if (con != null)
// con.close();
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }
// }
