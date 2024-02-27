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

    // overview 1 method
    public String calculatePercentageCoursesWithCertificate(String gender) {
        StringBuilder result = new StringBuilder();

        try {
            String query = "SELECT " +
                    "    E.CourseName, " +
                    "    100.0 * SUM(CASE WHEN C.CertificateID IS NOT NULL THEN 1 ELSE 0 END) / COUNT(*) AS PercentageCoursesWithCertificate "
                    +
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

            db = new DatabaseManager();
            connection = db.getConnection();
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
                                .append(" = ")
                                .append(String.format("%.2f%%", percentage))
                                .append("\n");
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
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
        StringBuilder result = new StringBuilder();

        String sql = "SELECT COUNT(DISTINCT e.CursistEmailAddress) AS AantalCursisten " +
                "FROM Enrollment e " +
                "JOIN Certificate c ON e.CourseName = c.CourseName AND e.CursistEmailAddress = c.CursistEmailAddress " +
                "WHERE e.CourseName = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, selectedCourse);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int count = rs.getInt("AantalCursisten");

                result.append("Number of participants who completed the course: ").append(count);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    // Overview 5 method
    public String getTop3WatchedWebcasts() {
        StringBuilder result = new StringBuilder();

        try {
            String query = "SELECT TOP 3 " +
                    "    W.Title AS WebcastTitle, " +
                    "    W.Duration AS WebcastDuration, " +
                    "    W.PublicationDate AS WebcastPublicationDate, " +
                    "    W.URL AS WebcastURL, " +
                    "    W.NameSpeaker AS SpeakerName, " +
                    "    W.OrganisationSpeaker AS SpeakerOrganisation, " +
                    "    W.Description AS WebcastDescription, " +
                    "    COUNT(WC.ContentItemID) AS Views " +
                    "FROM " +
                    "    dbo.Webcast W " +
                    "JOIN " +
                    "    dbo.WatchedContent WC ON W.ContentItemID = WC.ContentItemID " +
                    "GROUP BY " +
                    "    W.Title, W.Duration, W.PublicationDate, W.URL, W.NameSpeaker, W.OrganisationSpeaker, W.Description "
                    +
                    "ORDER BY " +
                    "    Views DESC";

            db = new DatabaseManager();
            connection = db.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();

                boolean hasRows = rs.next();

                if (!hasRows) {
                    result.append("No data found for the top 3 watched webcasts.");
                } else {
                    do {
                        String webcastTitle = rs.getString("WebcastTitle");
                        int webcastDuration = rs.getInt("WebcastDuration");
                        int views = rs.getInt("Views");

                        result.append("Webcast Title: ").append(webcastTitle)
                                .append("\nDuration: ").append(webcastDuration)
                                .append("\nViews: ").append(views)
                                .append("\n\n");
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving top 3 watched webcasts.");
        }

        return result.toString();
    }

    public String getCompletedCertificates(String cursistEmailAddress) {

        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try {
            String query = "SELECT c.CourseName, c.Grade, c.ApproverName, c.EnrollmentDate " +
                    "FROM Certificate c " +
                    "JOIN Enrollment e ON c.CourseName = e.CourseName AND c.CursistEmailAddress = e.CursistEmailAddress "
                    +
                    "WHERE c.CursistEmailAddress = ?";

            db = new DatabaseManager();
            connection = db.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, cursistEmailAddress);

                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    String courseName = rs.getString("CourseName");
                    String grade = rs.getString("Grade");
                    String approverName = rs.getString("ApproverName");

                    sb.append(courseName + " || Grade: " + grade + " || Approver: " + approverName + "\n");
                    String enrollmentDate = rs.getString("EnrollmentDate");
                    result.add(courseName + ", Grade: " + grade + ", Approver: " + approverName + ", Date: "
                            + enrollmentDate);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        }
        return sb.toString();

    }
}
