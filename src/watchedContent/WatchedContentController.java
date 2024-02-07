package watchedContent;

import DatabaseManager.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WatchedContentController {
    private DatabaseManager databaseManager;
    private Connection connection;

    public WatchedContentController() {
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

    public void addPercentage(int contentItemID, int cursistID, double percentageWatched) {
        connection = databaseManager.getConnection();
        String query = "INSERT INTO WatchedContent (ContentItemID, CursistID, PercentageWatched) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.valueOf(contentItemID));
            statement.setString(2, String.valueOf(cursistID));
            statement.setInt(3, (int) percentageWatched);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAllWatchedContent() {
        ArrayList<String> wachtedContent = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM WatchedContent");

            while (rs.next()) {
                String contentItemId = rs.getString("ContentItemID");
                wachtedContent.add(contentItemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wachtedContent;
    }

    public void deleteWatchedContent(int contentItemID) {
        try {
            String query = "DELETE FROM ContentItem WHERE ContentItemID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(contentItemID));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WatchedContent getWatchedContent(int contentItemID, int cursistID) {
        try {
            String query = "SELECT * FROM WatchedContent WHERE ContentItemID = ? AND CursistID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(contentItemID));
                statement.setString(2, String.valueOf(cursistID));
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    WatchedContent watchedContent = new WatchedContent();
                    watchedContent.setContentItemID(rs.getInt("ContentItemID"));
                    watchedContent.setCursistID(rs.getInt("CursistID"));
                    watchedContent.setPercentageWatched(rs.getDouble("PercentageWatched"));
                    return watchedContent;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WatchedContent getWatchedContentById(int contentItemID) {
        try {
            String query = "SELECT * FROM WatchedContent WHERE ContentItemID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(contentItemID));
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    WatchedContent watchedContent = new WatchedContent();
                    watchedContent.setContentItemID(rs.getInt("ContentItemID"));
                    watchedContent.setCursistID(rs.getInt("CursistID"));
                    watchedContent.setPercentageWatched(rs.getDouble("PercentageWatched"));
                    return watchedContent;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}