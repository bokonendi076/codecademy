package enrollment;

import DatabaseManager.*;

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

     // Method to execute an SQL query and return a ResultSet
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

     // Method to add a new enrollment to the database
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

    // Method to update an existing enrollment in the database
    public void updateEnrollment(Enrollment enrollment) {
        try {
            String query = "UPDATE Enrollment SET EnrollmentDate = ?, CouseName = ?, CursistEmailAddress = ?, WHERE EnrollmentDate = ? AND CourseName = ? AND CursistEmailAddress = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
                updateStatement.setObject(1, enrollment.getEnrollmentDate());
                updateStatement.setString(2, enrollment.getCourseName());
                updateStatement.setString(3, enrollment.getCursistEmailAddress());
                
                System.out.println(enrollment.getEnrollmentDate());
                System.out.println(enrollment.getCourseName());
                System.out.println(enrollment.getCursistEmailAddress());

                int rowsAffected = updateStatement.executeUpdate();
                connection.commit();

                if (rowsAffected == 0) {
                    SQLException e = new SQLException();
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    // Method to retrieve all enrollments from the database
    public ArrayList<String> getAllEnrollments() {
        ArrayList<String> watchedContent = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM Enrollment");

            while (rs.next()) {
                String contentItemId = rs.getString("CursistEmailAddress");
                watchedContent.add(contentItemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return watchedContent;
    }

    // Method to delete an enrollment from the database based on course name and cursist email address
    public void deleteEnrollment(String courseName, String cursistEmailAddress, LocalDate enrollmenDate) {
        try {
            String query = "DELETE FROM Enrollment WHERE CourseName = ? AND CursistEmailAddress = ? AND EnrollmentDate = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, courseName);
                statement.setString(2, cursistEmailAddress);
                statement.setString(3, String.valueOf(enrollmenDate));

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve an enrollment from the database based on course name and cursist email address
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

    // Method to retrieve an enrollment from the database based on cursist email address
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