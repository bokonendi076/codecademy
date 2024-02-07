package enrollment;

import DatabaseManager.*;
import cursist.Cursist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class EnrollmentController {
    private DatabaseManager databaseManager;
    private Connection connection;

    public EnrollmentController() {
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

    public void addEnrollment(Enrollment enrollment) {
        connection = databaseManager.getConnection();
        String query = "INSERT INTO Enrollment (EnrollmentDate, CourseName, CursistEmailAddress) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.valueOf(enrollment.getEnrollmentDate()));
            statement.setString(2, String.valueOf(enrollment.getCourseName()));
            statement.setString(3, String.valueOf(enrollment.getCursistEmailAddress()));

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAllEnrollments() {
        ArrayList<String> wachtedContent = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM Enrollment");

            while (rs.next()) {
                String contentItemId = rs.getString("CursistEmailAddress");
                wachtedContent.add(contentItemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wachtedContent;
    }

    public void deleteEnrollment(String courseName, String cursistEmailAddress) {
        try {
            String query = "DELETE FROM Enrollment WHERE CourseName = ? AND CursistEmailAddress = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, courseName);
                statement.setString(2, cursistEmailAddress);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Enrollment getEnrollmentByNameAndEmail(String name, String email) {
        try {
            String query = "SELECT * FROM Enrollment WHERE CourseName = ? AND CursistEmailAddress = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setString(2, email);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setCourseName(rs.getString("CourseName"));
                    enrollment.setCursistEmailAddress(rs.getString("CursistEmailAddress"));
                    enrollment.setEnrollmentDate(rs.getObject("EnrollmentDate", LocalDate.class));

                    return enrollment;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Enrollment getEnrollmentByName(String name) {
        try {
            String query = "SELECT * FROM Enrollment WHERE CursistEmailAddress = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setCourseName(rs.getString("CourseName"));
                    enrollment.setCursistEmailAddress(rs.getString("CursistEmailAddress"));
                    enrollment.setEnrollmentDate(rs.getObject("EnrollmentDate", LocalDate.class));

                    return enrollment;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}