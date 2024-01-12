package main;

import DatabaseManager.*;
import certificate.Certificate;
import certificate.CertificateController;
import contentItem.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.GUI;
import webcast.Webcast;

public class OverViewGUI extends Application {
    private DatabaseManager db;

    private Connection connection;
    private ListView<String> list;
    private ObservableList<String> items;
    private Scene mainScene;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;
    private BorderPane homePane;
    private Webcast webcast;
    private String firstTitleWebcast;
    private String secondTitleWebcast;
    private String thirdTitleWebcast;
    private Button viewCertificates;
    private Certificate certificate;

    // Constructor
    public OverViewGUI() {
    }

    GUI gui = new GUI();

    // public scene to get the cursist scene
    public Scene OverViewScene(Stage stage) {
        db = new DatabaseManager();
        connection = db.getConnection();

        Button backToHomeButton = new Button("< Home");
        backToHomeButton.setStyle("-fx-background-color: #d2b48c;");
        backHome = new Button("< Home");
        backHome.setStyle("-fx-background-color: #d2b48c;");
        backHome.setOnAction(k -> {
            stage.setScene(homeScene);
            stage.show();
        });

        // Create a welcome message for the homepage
        Button courseOverviewButton = new Button("Course overviews");
        Button certificateOverviewButton = new Button("Certificate overviews");
        Button webcastOverview = new Button("Webcast overview");

        Label welcomeLabel = new Label("Welcome to the overview page! \n" + "    What would you like to see?");
        Insets welcomeLabelPadding = new Insets(
                25);
        welcomeLabel.setPadding(welcomeLabelPadding);
        welcomeLabel.setStyle("-fx-font-size: 24;");
        backToCodeCademy = new Button("< CodeCademy");

        backToCodeCademy.setOnAction(l -> {
            stage.setScene(gui.getHomeScene(stage));
            stage.show();
        });

        // Create layout for the homepage
        homePane = new BorderPane();
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
        homePane.setTop(welcomeLabel);
        VBox homeLayout = new VBox(10, courseOverviewButton, certificateOverviewButton, webcastOverview,
                backToCodeCademy);

        courseOverviewButton.setPrefSize(150, 50);
        courseOverviewButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
        certificateOverviewButton.setPrefSize(150, 50);
        certificateOverviewButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
        webcastOverview.setPrefSize(150, 50);
        webcastOverview.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
        backToCodeCademy.setPrefSize(150, 50);
        backToCodeCademy.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");

        homeLayout.setAlignment(Pos.CENTER);
        homePane.setCenter(homeLayout);
        Insets padding = new Insets(100);
        homePane.setPadding(padding);
        homeScene = new Scene(homePane, 800, 600);

        backToHomeButton.setOnAction(e -> {
            stage.setScene(homeScene);
            stage.show();
        });

        stage.setScene(homeScene);
        stage.show();

        courseOverviewButton.setOnAction(e -> {
            Label titleCourseOverView = new Label("Please choose an overview option: ");
            Button averageProgressModule = new Button("Average progress Module");
            Button progressPercentage = new Button("Course progress %");
            Button completedCourses = new Button("Completed courses");

            averageProgressModule.setStyle("-fx-font-size: 10; -fx-background-color: #d2b48c;");
            progressPercentage.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
            completedCourses.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
            titleCourseOverView.setStyle("-fx-font-size: 28;");

            averageProgressModule.setPrefSize(150, 50);
            progressPercentage.setPrefSize(150, 50);
            completedCourses.setPrefSize(150, 50);

            VBox layout = new VBox(10, averageProgressModule, progressPercentage, completedCourses, backToHomeButton);
            layout.setAlignment(Pos.CENTER);

            BorderPane overViewPane = new BorderPane();
            overViewPane.setTop(titleCourseOverView);
            BorderPane.setAlignment(titleCourseOverView, Pos.CENTER);
            titleCourseOverView.setPadding(new Insets(25, 0, 25, 0));

            overViewPane.setCenter(layout);

            Scene courseOverviewScene = new Scene(overViewPane, 800, 600);
            stage.setScene(courseOverviewScene);
            courseOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.show();

            progressPercentage.setOnAction(a -> {
                Stage courseOverviewStage = new Stage(); // Create a new stage
            
                Label titleCourseOverView1 = new Label("Please choose a course: ");
            
                // Retrieve course names
                List<String> courseNames = getCourseNames();
                ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(courseNames));
            
                Button showProgressButton = new Button("Show Average Progress per Module");
                showProgressButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
            
                VBox layout2 = new VBox(10, titleCourseOverView1, courseComboBox, showProgressButton, backToHomeButton);
                layout2.setAlignment(Pos.CENTER);
            
                BorderPane overViewPane2 = new BorderPane();
                overViewPane2.setTop(titleCourseOverView1);
                BorderPane.setAlignment(titleCourseOverView1, Pos.CENTER);
                titleCourseOverView1.setPadding(new Insets(25, 0, 25, 0));
            
                overViewPane.setCenter(layout2);
            
                Scene courseOverviewScene2 = new Scene(overViewPane2, 800, 600);
            
                showProgressButton.setOnAction(event -> {
                    String selectedCourse = courseComboBox.getValue();
                    if (selectedCourse != null) {
                        // Get and display average progress per module for the selected course
                        String progressPerModule = getAverageProgressPerModule(selectedCourse);
                        // Display the result (you can customize this part)
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Average Progress per Module");
                        alert.setHeaderText(null);
                        alert.setContentText(progressPerModule);
                        alert.showAndWait();
                    } else {
                        // Handle case where no course is selected
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select a course.");
                        alert.showAndWait();
                    }
                });
            
                courseOverviewStage.setScene(courseOverviewScene);
                courseOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                courseOverviewStage.show(); // Show the new stage
            });

        });

        

        certificateOverviewButton.setOnAction(e -> {

            Label titleCertificateOverview = new Label("Certificate Overview:");
            Button viewCertificates = new Button("Gender Certificate %");
            Button generateCertificates = new Button("Account certificates");

            viewCertificates.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
            generateCertificates.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
            titleCertificateOverview.setStyle("-fx-font-size: 28;");

            viewCertificates.setPrefSize(150, 50);
            generateCertificates.setPrefSize(150, 50);

            VBox layout = new VBox(10, viewCertificates, generateCertificates, backToHomeButton);
            layout.setAlignment(Pos.CENTER);

            BorderPane certificateOverviewPane = new BorderPane();
            certificateOverviewPane.setTop(titleCertificateOverview);
            BorderPane.setAlignment(titleCertificateOverview, Pos.CENTER);
            titleCertificateOverview.setPadding(new Insets(25, 0, 25, 0));

            certificateOverviewPane.setCenter(layout);

            Scene certificateOverviewScene = new Scene(certificateOverviewPane, 800, 600);
            stage.setScene(certificateOverviewScene);
            certificateOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.show();
        });

        webcastOverview.setOnAction(e -> {

            Label titleWebcastOverview = new Label("Top 3 Most Watched Webcasts:");
            titleWebcastOverview.setStyle("-fx-font-size: 28;");
            titleWebcastOverview.setPadding(new Insets(25, 0, 25, 0));

            String topWebcasts = getTopThreeWatchedWebcastsTitles();
            topWebcasts = topWebcasts.substring(1, topWebcasts.length() - 1);
            String[] webcastTitles = topWebcasts.split(", ", 3);

            String topWebcastPercentages = getTopThreeWatchedWebcastsPercentage();
            topWebcastPercentages = topWebcastPercentages.substring(1, topWebcastPercentages.length() - 1);
            String[] webcastPercentage = topWebcastPercentages.split(", ", 3);

            Label webcastFirstTitleLabel = new Label(
                    "1. " + webcastTitles[0].toString() + " (" + webcastPercentage[0].toString() + "% watched)");
            webcastFirstTitleLabel.setStyle("-fx-font-size: 18;");

            Label webcastSecondTitleLabel = new Label(
                    "2. " + webcastTitles[1].toString() + " (" + webcastPercentage[1].toString() + "% watched)");
            webcastSecondTitleLabel.setStyle("-fx-font-size: 18;");

            Label webcastThirdTitleLabel = new Label(
                    "3. " + webcastTitles[2].toString() + " (" + webcastPercentage[2] + "% watched)");
            webcastThirdTitleLabel.setStyle("-fx-font-size: 18;");

            VBox webcastItem = new VBox(webcastFirstTitleLabel, webcastSecondTitleLabel, webcastThirdTitleLabel);
            webcastItem.setSpacing(15);
            webcastItem.setAlignment(Pos.CENTER);

            backToHomeButton.setPrefSize(150, 50);

            VBox layout = new VBox(10, titleWebcastOverview, webcastItem, backToHomeButton);
            layout.setAlignment(Pos.CENTER);

            BorderPane webcastOverviewPane = new BorderPane();
            webcastOverviewPane.setTop(titleWebcastOverview);
            BorderPane.setAlignment(titleWebcastOverview, Pos.CENTER);

            webcastOverviewPane.setCenter(layout);

            Scene webcastOverviewScene = new Scene(webcastOverviewPane, 800, 600);
            stage.setScene(webcastOverviewScene);
            webcastOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.show();

        });

        homeScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
        return homeScene;

    }

    private List<String> getCourseNames() {
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

    private String getAverageProgressPerModule(String courseName) {
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {

    }
}
