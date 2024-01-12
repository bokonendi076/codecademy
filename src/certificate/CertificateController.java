package certificate;

import DatabaseManager.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CertificateController {
    private DatabaseManager databaseManager;
    private Connection connection;

    public CertificateController() {
        this.databaseManager = new DatabaseManager();
    }

    public ResultSet query(String sqlQuery) {
        try {
            connection = databaseManager.getConnection();
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Certificate> getAllCertificates() {
        ArrayList<Certificate> certificates = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM Certificate");

            while (rs.next()) {
                int certificateId = rs.getInt("CertificateID");
                int grade = rs.getInt("Grade");
                String approverName = rs.getString("ApproverName");
                String cursistEmailAddress = rs.getString("CursistEmailAddress");
                String courseName = rs.getString("CourseName");

                Certificate certificate = new Certificate(certificateId, grade, approverName, cursistEmailAddress,
                        courseName);
                certificates.add(certificate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certificates;
    }

    public void saveCertificate(Certificate certificate) {
        try {
            connection = databaseManager.getConnection();
            String query = "INSERT INTO Certificate (CertificateID, Grade, ApproverName, CursistEmailAddress, CourseName) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, certificate.getCertificateId());
                statement.setInt(2, certificate.getGrade());
                statement.setString(3, certificate.getApproverName());
                statement.setString(4, certificate.getCursistEmailAddress());
                statement.setString(5, certificate.getCourseName());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
