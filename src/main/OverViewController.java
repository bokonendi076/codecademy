package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DatabaseManager.*;

public class OverViewController {
    private DatabaseManager db;
    private Connection connection;

    public List<String> getCourseNames() {
        List<String> courseNames = new ArrayList<>();

        try {
            db = new DatabaseManager();
            connection = db.getConnection();
            String sqlQuery = "SELECT DISTINCT Name FROM Course";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                String courseName = resultSet.getString("Name");
                courseNames.add(courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseNames;
    }

    public String getAverageProgressPerModule(String courseName) {
        StringBuilder result = new StringBuilder();

        try {
            String sqlQuery = "SELECT Module.Title, AVG(PercentageWatched) AS AveragePercentageWatched "
                    + "FROM Module " + '\n'
                    + "JOIN WatchedContent ON Module.ContentItemID = WatchedContent.ContentItemID "
                    + "WHERE CourseName = ? " + "GROUP BY Module.Title;";

            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setString(1, courseName);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    String moduleTitle = rs.getString("Title");
                    double averagePercentageWatched = rs.getDouble("AveragePercentageWatched");

                    result.append(moduleTitle).append(": ").append(averagePercentageWatched).append("%\n");

                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    //overview 1 method
    public String calculatePercentageCoursesWithCertificate(String gender) {
        StringBuilder result = new StringBuilder();
    
        try {
            String query = "SELECT " +
                    "    E.CourseName, " +
                    "    100.0 * SUM(C.CertificateID IS NOT NULL) / COUNT(*) AS PercentageCoursesWithCertificate " +
                    "FROM " +
                    "    Enrollment E " +
                    "INNER JOIN " +
                    "    Cursist CU ON E.CursistEmailAddress = CU.EmailAddress " +
                    "LEFT JOIN " +
                    "    Certificate C ON E.EnrollmentDate = C.EnrollmentDate " +
                    "        AND E.CourseName = C.CourseName " +
                    "        AND E.CursistEmailAddress = C.CursistEmailAddress " +
                    "WHERE " +
                    "    CU.Sex = ? " +
                    "GROUP BY " +
                    "    E.CourseName";
    
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, gender);
    
                ResultSet rs = statement.executeQuery();
    
                boolean hasRows = rs.next();
    
                if (!hasRows) {
                    result.append("No data found for the given gender.");
                } else {
                    do {
                        String courseName = rs.getString("CourseName");
                        double percentage = rs.getDouble("PercentageCoursesWithCertificate");
    
                        result.append("Course Name: ").append(courseName)
                                .append(", Percentage Courses With Certificate: ").append(String.format("%.2f%%", percentage))
                                .append("\n");
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return result.toString();
    }


    // Overwiew 3 method
    public String getProgressPerModule(String accountEmail, String courseName) {

        StringBuilder result = new StringBuilder();

        try {
            String query = "SELECT M.Title AS ModuleTitle, WC.PercentageWatched AS PercentageWatched " +
                    "FROM Module M " +
                    "JOIN Course C ON M.CourseName = C.Name " +
                    "JOIN ContentItem CI ON M.ContentItemID = CI.ContentItemID " +
                    "LEFT JOIN WatchedContent WC ON CI.ContentItemID = WC.ContentItemID " +
                    "LEFT JOIN Cursist Cu ON WC.CursistEmailAddress = Cu.EmailAddress " +
                    "WHERE C.Name = ? " +
                    "AND Cu.EmailAddress = ? ";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, courseName);
                statement.setString(2, accountEmail);

                ResultSet rs = statement.executeQuery();

                boolean hasRows = rs.next();

                if (!hasRows) {
                    result.append("This user has no progress in this course");
                } else {
                    do {
                        String moduleTitle = rs.getString("ModuleTitle");
                        double averageProgress = rs.getDouble("PercentageWatched");

                        result.append(moduleTitle).append(": ").append(String.format("%.2f%%", averageProgress))
                                .append("\n");
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public String getCompletedCourseAccounts(String selectedCourse) {
        return null;
    }

    // Get top three watched webcasts

    public String getTopThreeWatchedWebcastsTitles() {
        // Make arraylist that holds top three watched webcasts in strings from the
        // title
        ArrayList<String> topThreeWatchedWebcasts = new ArrayList<>();

        String sqlQuery = "SELECT TOP 3 TitleWebcast, CursistID, PercentageWatched FROM Webcast "
                + "JOIN WatchedContent ON WatchedContent.ContentItemID = Webcast.ContentItemID " +
                "ORDER BY PercentageWatched DESC";
        try {
            Connection connection = db.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                String title = rs.getString("TitleWebcast");
                int cursistId = rs.getInt("CursistID");
                int percentageWatched = rs.getInt("PercentageWatched");

                topThreeWatchedWebcasts.add(title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topThreeWatchedWebcasts.toString();
    }

    public String getTopThreeWatchedWebcastsPercentage() {
        // Make arraylist that holds top three watched webcasts in strings from the
        // title
        ArrayList<Integer> topThreeWatchedWebcasts = new ArrayList<>();

        String sqlQuery = "SELECT TOP 3 TitleWebcast, CursistID, PercentageWatched FROM Webcast "
                + "JOIN WatchedContent ON WatchedContent.ContentItemID = Webcast.ContentItemID " +
                "ORDER BY PercentageWatched DESC";
        try {
            Connection connection = db.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                String title = rs.getString("TitleWebcast");
                int cursistId = rs.getInt("CursistID");
                int percentageWatched = rs.getInt("PercentageWatched");

                topThreeWatchedWebcasts.add(percentageWatched);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topThreeWatchedWebcasts.toString();
    }
}
