package cursist;

import DatabaseManager.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CursistController {
    private DatabaseManager databaseManager;
    private Connection connection;

    public CursistController() {
        this.databaseManager = new DatabaseManager();
        this.connection = databaseManager.getConnection();
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

    public ArrayList<String> getAllCursist() {
        ArrayList<String> cursistNames = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM Cursist");

            while (rs.next()) {
                String name = rs.getString("Name");
                cursistNames.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursistNames;
    }

    public void saveCursist(Cursist cursist) {
        try {
            String query = "INSERT INTO Cursist (EmailAddress, Name, BirthDate, Sex, Address, City, Country) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, cursist.getEmailAddress());
                statement.setString(2, cursist.getName());
                statement.setObject(3, cursist.getBirthDate());
                statement.setString(4, cursist.getSex());
                statement.setString(5, cursist.getAddress());
                statement.setString(6, cursist.getCity());
                statement.setString(7, cursist.getCountry());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCursist(String cursistName) {
        try {
            String query = "DELETE FROM Cursist WHERE Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, cursistName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cursist getCursistByName(String name) {
        try {
            String query = "SELECT * FROM Cursist WHERE Name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Cursist cursist = new Cursist();
                    cursist.setName(rs.getString("Name"));
                    cursist.setEmailAddress(rs.getString("EmailAddress"));
                    cursist.setBirthDate(rs.getObject("BirthDate", LocalDate.class));
                    cursist.setSex(rs.getString("Sex"));
                    cursist.setAddress(rs.getString("Address"));
                    cursist.setCity(rs.getString("City"));
                    cursist.setCountry(rs.getString("Country"));

                    return cursist;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCursistFields(Cursist cursist) {
        try {
            String query = "UPDATE Cursist SET Name = ?, BirthDate = ?, Sex = ?, Address = ?, City = ?, Country = ? WHERE EmailAddress = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
                updateStatement.setString(1, cursist.getName());
                updateStatement.setObject(2, cursist.getBirthDate());
                updateStatement.setString(3, cursist.getSex());
                updateStatement.setString(4, cursist.getAddress());
                updateStatement.setString(5, cursist.getCity());
                updateStatement.setString(6, cursist.getCountry());
                updateStatement.setString(7, cursist.getEmailAddress());

                System.out.println(cursist.getEmailAddress());

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

    public ArrayList<String> getAllCursistEmailAddress() {
        ArrayList<String> cursistEmailAddress = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM Cursist");

            while (rs.next()) {
                String name = rs.getString("EmailAddress");
                cursistEmailAddress.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursistEmailAddress;
    }

    public int getCursistID(String emailAddress) {
        try {
            String query = "SELECT CursistID FROM Cursist WHERE EmailAddress = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, emailAddress);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1); // Fetch by index instead of column name
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Integer> getAllCursistIDs() {
        ArrayList<Integer> cursistIDs = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT * FROM Cursist");

            while (rs.next()) {
                Integer cursistID = rs.getInt("CursistID");
                cursistIDs.add(cursistID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursistIDs;
    }

    public Map<String, Double> getProgressPercentageByModule(int cursistID, int cursusID) {
        Map<String, Double> progressMap = new HashMap<>();

        try {
            String query = "SELECT Module.ModuleName, COALESCE(SUM(ModuleProgress.PercentageComplete), 0) AS TotalPercentage "
                    +
                    "FROM Module " +
                    "LEFT JOIN ModuleProgress ON Module.ModuleID = ModuleProgress.ModuleID " +
                    "AND ModuleProgress.CursistID = ? " +
                    "AND ModuleProgress.CursusID = ? " +
                    "GROUP BY Module.ModuleName";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, cursistID);
                statement.setInt(2, cursusID);

                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        String moduleName = rs.getString("ModuleName");
                        double totalPercentage = rs.getDouble("TotalPercentage");

                        progressMap.put(moduleName, totalPercentage);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handel SQLException af op een passende manier voor je applicatie
        }

        return progressMap;
    }

    public ArrayList<String> getWebcastTitles() {
        ArrayList<String> webcastTitles = new ArrayList<>();

        try {
            ResultSet rs = query("SELECT TitleWebcast FROM Webcast");

            while (rs.next()) {
                String title = rs.getString("TitleWebcast");
                webcastTitles.add(title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return webcastTitles;
    }

    public int getContentItemID(String title) {
        try {
            String query = "SELECT ContentItemID FROM Webcast WHERE TitleWebcast = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, title);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    return rs.getInt("ContentItemID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getPercentageWatched() {
        // Implement this method based on your requirements
        return 0;
    }

    public void updatePercentageWatched(int cursistID, int contentItemID, int percentageWatched) {
        try {
            String query = "UPDATE WatchedContent SET PercentageWatched = ? WHERE CursistID = ? AND ContentItemID = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(query)) {
                updateStatement.setInt(1, percentageWatched);
                updateStatement.setInt(2, cursistID);
                updateStatement.setInt(3, contentItemID);

                int rowsAffected = updateStatement.executeUpdate();
                connection.commit();

                if (rowsAffected == 0) {
                    // Handle if no rows were updated
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

}
