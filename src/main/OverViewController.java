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
            String sqlQuery = "SELECT DISTINCT Name FROM Course";
            Connection connection = db.getConnection();
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
            String courseIdQuery = "SELECT CourseID FROM Course WHERE Name = ?";
            Connection connection = db.getConnection();

            try (PreparedStatement courseIdStatement = connection.prepareStatement(courseIdQuery)) {
                courseIdStatement.setString(1, courseName);
                ResultSet courseIdResultSet = courseIdStatement.executeQuery();

                if (courseIdResultSet.next()) {
                    int courseId = courseIdResultSet.getInt("CourseID");

                    String progressQuery = "SELECT Title, AVG(PercentageWatched) AS AverageProgress " +
                            "FROM Module " +
                            "JOIN WatchedContent ON Module.ContentItemID = WatchedContent.ContentItemID " +
                            "WHERE CourseID = ? " +
                            "GROUP BY Title";

                    try (PreparedStatement progressStatement = connection.prepareStatement(progressQuery)) {
                        progressStatement.setInt(1, courseId);
                        ResultSet progressResultSet = progressStatement.executeQuery();

                        while (progressResultSet.next()) {
                            String moduleTitle = progressResultSet.getString("Title");
                            double averageProgress = progressResultSet.getDouble("AverageProgress");

                            result.append(moduleTitle).append(": ").append(averageProgress).append("%\n");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public String getProgressPerModule(String accountEmail, String courseName) {
        StringBuilder result = new StringBuilder();

        try {
            String query = "SELECT m.Title AS ModuleTitle, AVG(wc.PercentageWatched) AS AverageProgress " +
                    "FROM WatchedContent wc " +
                    "JOIN ContentItem ci ON wc.ContentItemID = ci.ContentItemID " +
                    "JOIN Module m ON ci.ModuleID = m.ModuleID " +
                    "JOIN Course c ON m.CourseID = c.CourseID " +
                    "JOIN Cursist cu ON wc.CursistID = cu.CursistID " +
                    "WHERE cu.EmailAddress = ? AND c.Name = ? " +
                    "GROUP BY m.Title";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, accountEmail);
                statement.setString(2, courseName);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    String moduleTitle = rs.getString("ModuleTitle");
                    double averageProgress = rs.getDouble("AverageProgress");

                    result.append(moduleTitle).append(": ").append(String.format("%.2f%%", averageProgress))
                            .append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();
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
