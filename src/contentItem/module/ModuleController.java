package module;

import DatabaseManager.*;
import course.Course;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModuleController {
    private DatabaseManager databaseManager;
    private Connection connection;

    public ModuleController() {
        this.databaseManager = new DatabaseManager();
        this.connection = databaseManager.getConnection();
    }

    // This function returns all module names
    public ArrayList<String> getAllModules() {
        ArrayList<String> moduleNames = new ArrayList<>();

        try {
            ResultSet rs = databaseManager.query("SELECT * FROM Module");

            while (rs.next()) {
                String title = rs.getString("ModuleID");
                moduleNames.add(title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return moduleNames;
    }

    // Module gets created and stored into the database
    public void saveModule(Module module) {
        try {
            String query = "INSERT INTO Module (Title, Version, ContactPersonName, ContactPersonEmail, FollowNumber, ContentItemID, ModuleID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, module.getTitle());
                statement.setString(2, module.getVersion());
                statement.setString(3, module.getContactPersonName());
                statement.setString(4, module.getContactPersonEmail());
                statement.setInt(5, module.getFollowNumber());
                statement.setInt(6, module.getContentItemID());
                statement.setInt(7, module.getModuleID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Module already exists.");
            alert.showAndWait();
            return;

        }
    }

    // Function gets saved in database
    public void deleteModule(String moduleId) {
        try {
            String query = "DELETE FROM Module WHERE ModuleID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, moduleId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This function returns the searched module
    public Module getModuleByTitle(int id) {
        try {
            String query = "SELECT * FROM Module WHERE ModuleID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, String.valueOf(id));
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Module module = new Module();
                    module.setTitle(rs.getString("Title"));
                    module.setVersion(rs.getString("Version"));
                    module.setContactPersonName(rs.getString("ContactPersonName"));
                    module.setContactPersonEmail(rs.getString("ContactPersonEmail"));
                    module.setFollowNumber(rs.getInt("FollowNumber"));
                    module.setContentItemID(rs.getInt("ContentItemID"));
                    module.setModuleID(rs.getInt("ModuleID"));

                    return module;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateModuleFields(Module module) {
        String query = "UPDATE Module SET Title = ?, Version = ?, ContactPersonName = ?, ContactPersonEmail = ?, Follownumber = ? WHERE Title = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
            updateStatement.setString(1, module.getTitle());
            updateStatement.setString(2, module.getVersion());
            updateStatement.setString(3, module.getContactPersonName());
            updateStatement.setString(4, module.getContactPersonEmail());
            updateStatement.setInt(5, module.getFollowNumber());

            // search in the query, on the module title
            updateStatement.setString(6, module.getTitle());

            updateStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getAllModuleIDs() {
        ArrayList<Integer> moduleIDs = new ArrayList<>();

        try {
            ResultSet rs = databaseManager.query("SELECT * FROM Module");

            while (rs.next()) {
                int id = rs.getInt("ModuleID");
                moduleIDs.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return moduleIDs;
    }

}
