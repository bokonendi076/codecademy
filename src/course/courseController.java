package course;

import DatabaseManager.*; // Import the DatabaseManager package

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class courseController {
    private DatabaseManager databaseManager; // DatabaseManager object for managing database connections
    private Connection connection; // Variable to hold the database connection

    public courseController() {
        this.databaseManager = new DatabaseManager(); // Initialization of DatabaseManager object
    }

    // Method to execute an SQL query and return a ResultSet
    public ResultSet query(String sqlQuery) {
        try {
            connection = databaseManager.getConnection(); // Get database connection from DatabaseManager
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sqlQuery); // Execute the query and return the result
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all course names and return them as an ArrayList
    public ArrayList<String> getAllCourses() {
        ArrayList<String> courseNames = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM Course"); // Execute a query to retrieve all courses

            while (rs.next()) {
                String name = rs.getString("Name");
                courseNames.add(name); // Add the name of each course to the ArrayList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseNames;
    }

    // Method to save a new course to the database
    public void saveCourse(Course course) {
        try {
            connection = databaseManager.getConnection(); // Get database connection
            String query = "INSERT INTO Course (Name, Subject, IntroductionText, DifficultyLevel) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Replace placeholders in the query with the data from the course
                statement.setString(1, course.getName());
                statement.setString(2, course.getSubject());
                statement.setString(3, course.getIntroductionText());
                statement.setString(4, course.getDifficultyLevel());

                statement.executeUpdate(); // Execute the insert query to add the course
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a course from the database based on the name
    public void deleteCourse(String courseName) {
        try {
            // Set the delete query with a placeholder for the course name
            String query = "DELETE FROM Course WHERE Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, courseName); // Replace the placeholder with the course name
                statement.executeUpdate(); // Execute the delete query
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a course from the database based on the name
    public Course getCourseByName(String name) {
        try {
            String query = "SELECT * FROM Course WHERE Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    // Create a new Course object and populate it with data from the ResultSet
                    Course course = new Course();
                    course.setName(rs.getString("Name"));
                    course.setSubject(rs.getString("Subject"));
                    course.setIntroductionText(rs.getString("IntroductionText"));
                    course.setDifficultyLevel(rs.getString("DifficultyLevel"));
                    
                    return course; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update fields of a course in the database
    public void updateCourseFields(Course course) {
        String query = "UPDATE Course SET Subject = ?, IntroductionText = ?, DifficultyLevel = ? WHERE Name = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
            // Replace placeholders in the query with the updated data of the course
            updateStatement.setString(1, course.getSubject());
            updateStatement.setString(2, course.getIntroductionText());
            updateStatement.setString(3, course.getDifficultyLevel());
            updateStatement.setString(4, course.getName());

            updateStatement.executeUpdate(); // Execute the update query
            connection.commit(); // Commit the transaction
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the count of completed cursists based on the course name
    public int getCompletedCursistCount(String courseName) {
        try {
            connection = databaseManager.getConnection(); // Get database connection
            String query = "SELECT COUNT(*) FROM Enrollment e " +
                           "JOIN Course c ON e.CourseName = c.Name " +
                           "WHERE c.Name = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, courseName); // Replace the placeholder with the course name
                ResultSet rs = statement.executeQuery();
    
                if (rs.next()) {
                    return rs.getInt(1); // Return the count of completed cursists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // If an error occurs, return 0
    }
}
