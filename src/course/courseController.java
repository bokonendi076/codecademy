package course;

import DatabaseManager.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class courseController {
    private DatabaseManager databaseManager;
    private Connection connection;

    public courseController() {
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

    public ArrayList<String> getAllCourses() {
        ArrayList<String> courseNames = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM Course");

            while (rs.next()) {
                String name = rs.getString("Name");
                courseNames.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseNames;
    }

    public void saveCourse(Course course) {
        try {
            connection = databaseManager.getConnection();
            String query = "INSERT INTO Course (Name, Subject, IntroductionText, DifficultyLevel, CourseID, ModuleID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, course.getName());
                statement.setString(2, course.getSubject());
                statement.setString(3, course.getIntroductionText());
                statement.setInt(4, course.getDifficultyLevel());
                statement.setInt(5, course.getCourseId());
                statement.setInt(6, course.getModuleId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(String courseName) {
        try {
            String query = "DELETE FROM Course WHERE Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, courseName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Course getCourseByName(String name) {
        try {
            String query = "SELECT * FROM Course WHERE Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Course course = new Course();
                    course.setName(rs.getString("Name"));
                    course.setSubject(rs.getString("Subject"));
                    course.setIntroductionText(rs.getString("IntroductionText"));
                    course.setDifficultyLevel(rs.getInt("DifficultyLevel"));
                    course.setCourseId(rs.getInt("CourseID"));
                    course.setModuleId(rs.getInt("ModuleID"));

                    return course;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCourseFields(Course course) {
        String query = "UPDATE Course SET Subject = ?, IntroductionText = ?, DifficultyLevel = ?, CourseID = ?, ModuleID = ? WHERE Name = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
            updateStatement.setString(1, course.getSubject());
            updateStatement.setString(2, course.getIntroductionText());
            updateStatement.setInt(3, course.getDifficultyLevel());
            updateStatement.setInt(4, course.getCourseId());
            updateStatement.setInt(5, course.getModuleId());
            updateStatement.setString(6, course.getName());

            updateStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCompletedCursistCount(String courseName) {
        try {
            connection = databaseManager.getConnection();
            String query = "SELECT COUNT(*) FROM Enrollment e " +
                           "JOIN Course c ON e.CourseName = c.Name " +
                           "WHERE c.Name = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, courseName);
                ResultSet rs = statement.executeQuery();
    
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; 
    }
}
