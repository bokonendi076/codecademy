package contentItem;

import DatabaseManager.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class contentItemController {
    private DatabaseManager databaseManager;
    private Connection connection;

    // Constructor
    public contentItemController() {
        this.databaseManager = new DatabaseManager();
    }

    // This function executes a SQL query
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

    // This function returns all content item IDs
    public ArrayList<Integer> getAllContentItems() {
        ArrayList<Integer> contentItems = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM ContentItem");

            while (rs.next()) {
                int contentItemId = rs.getInt("ContentItemID");
                contentItems.add(contentItemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contentItems;
    }

    // This function adds a new content item
    public void addContentItem(ContentItem contentItem) {
        try {
            connection = databaseManager.getConnection();
            String query = "INSERT INTO ContentItem (ContentItemID, PublicationDate, Status) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(contentItem.getContentItemID()));
                statement.setObject(2, contentItem.getPublicationDate());
                statement.setString(3, contentItem.getStatus());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This function deletes a content item
    public void deleteContentItem(int contentItemID) {
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

    // This function returns a content item
    public ContentItem getContentItemByID(int contentItemID) {
        try {
            String query = "SELECT * FROM ContentItem WHERE ContentItemID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(contentItemID));
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    ContentItem contentItem = new ContentItem();
                    contentItem.setContentItemID(rs.getInt("ContentItemID"));
                    contentItem.setPublicationDate(rs.getDate("PublicationDate").toLocalDate());
                    contentItem.setStatus(rs.getString("Status"));

                    return contentItem;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // This function updates a content item
    public void updateContentItemFields(ContentItem contentItem) {
        String query = "UPDATE ContentItem SET ContentItemID = ?, PublicationDate = ?, Status = ? WHERE ContentItemID = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
            updateStatement.setString(1, String.valueOf(contentItem.getContentItemID()));
            updateStatement.setObject(2, contentItem.getPublicationDate());
            updateStatement.setString(3, contentItem.getStatus());
            updateStatement.setString(4, String.valueOf(contentItem.getContentItemID()));

            updateStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
