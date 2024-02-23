package main;

import DatabaseManager.*;
import cursist.Cursist;
import cursist.CursistController;
import certificate.Certificate;
import certificate.CertificateController;
import contentItem.*;
import course.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;
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

public class OverViewGUI extends Application {
    private DatabaseManager db;
    private CursistController cursistController;
    private Connection connection;
    private ListView<String> list;
    private ObservableList<String> items;
    private Scene mainScene;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;
    private BorderPane homePane;
    private String firstTitleWebcast;
    private String secondTitleWebcast;
    private String thirdTitleWebcast;
    private Button viewCertificates;
    private Certificate certificate;
    private CertificateController certificateController = new CertificateController();
    private OverViewController overViewController = new OverViewController();

    // Constructor
    public OverViewGUI() {
    }

    GUI gui = new GUI();

    // public scene to get the cursist scene
    public Scene OverViewScene(Stage stage) {
        db = new DatabaseManager();
        connection = db.getConnection();
        CursistController cursistController = new CursistController();

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

                Label titleCourseOverView1 = new Label("Please choose a course: ");

                // Retrieve course names
                List<String> courseNames = overViewController.getCourseNames();
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
                        String progressPerModule = overViewController.getAverageProgressPerModule(selectedCourse);

                        // Display the result
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

                stage.setScene(courseOverviewScene);
                stage.show(); // Show the new stage
            });

            averageProgressModule.setOnAction(b -> {
                Stage certificateOverviewStage = new Stage(); // Create a new stage

                Label titleCertificateOverview = new Label("Select an account and course:");

                // Retrieve account emails using the CursistController method
                ArrayList<String> accountEmails = cursistController.getAllCursistEmailAddress();
                ComboBox<String> accountComboBox = new ComboBox<>(FXCollections.observableArrayList(accountEmails));

                // Retrieve course names
                List<String> courseNames = overViewController.getCourseNames();
                ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(courseNames));

                Button showProgressButton = new Button("Show Progress per Module (%)");
                showProgressButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");

                VBox layout3 = new VBox(10, titleCertificateOverview, accountComboBox, courseComboBox,
                        showProgressButton, backToHomeButton);
                layout3.setAlignment(Pos.CENTER);

                BorderPane certificateOverviewPane = new BorderPane();
                certificateOverviewPane.setTop(titleCertificateOverview);
                BorderPane.setAlignment(titleCertificateOverview, Pos.CENTER);
                titleCertificateOverview.setPadding(new Insets(25, 0, 25, 0));

                certificateOverviewPane.setCenter(layout3);

                Scene certificateOverviewScene = new Scene(certificateOverviewPane, 800, 600);

                showProgressButton.setOnAction(event -> {
                    String selectedAccount = accountComboBox.getValue();
                    String selectedCourse = courseComboBox.getValue();

                    if (selectedAccount != null && selectedCourse != null) {
                        // Get and display progress per module as percentage for the selected account
                        // and course
                        String progressPerModule = overViewController.getProgressPerModule(selectedAccount,
                                selectedCourse);
                        // Display the result (you can customize this part)
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Progress per Module (%)");
                        alert.setHeaderText(null);
                        alert.setContentText(progressPerModule);
                        alert.showAndWait();
                    } else {
                        // Handle case where account or course is not selected
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select an account and a course.");
                        alert.showAndWait();
                    }
                });

                certificateOverviewStage.setScene(certificateOverviewScene);
                certificateOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                certificateOverviewStage.show(); // Show the new stage
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

            // generateCertificates.setOnAction(event -> {

            // Cursist selectedCursist = getCursistByName();
            // Course selectedCourse = ; // implementeer deze methode volgens je GUI

            // if (selectedCursist != null && selectedCourse != null) {
            // int selectedCursistID = selectedCursist.getcursistID();
            // int selectedCourseID = selectedCourse.getCourseId();

            // Map<String, Double> progressMap =
            // cursistController.getProgressPercentageByModule(selectedCursistID,
            // selectedCourseID);

            // // Toon de resultaten
            // for (Map.Entry<String, Double> entry : progressMap.entrySet()) {
            // String moduleName = entry.getKey();
            // double percentage = entry.getValue();

            // System.out.println("Module: " + moduleName + ", Progress: " + percentage +
            // "%");
            // // Hier kun je de resultaten weergeven in je GUI of een andere actie
            // ondernemen.
            // }
            // } else {
            // // Handel het geval af waarin geen cursist of cursus is geselecteerd
            // Alert alert = new Alert(AlertType.WARNING);
            // alert.setTitle("Warning");
            // alert.setHeaderText(null);
            // alert.setContentText("Please select a cursist and a course.");
            // alert.showAndWait();
            // }
            // });

            viewCertificates.setOnAction(z -> {
                // Maak een ComboBox met geslachtsopties
                ObservableList<String> genderOptions = FXCollections.observableArrayList("Male", "Female", "Other");
                ComboBox<String> genderComboBox = new ComboBox<>(genderOptions);

                // Toon een dialoogvenster met de ComboBox en wacht op gebruikersselectie
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Select Gender");
                dialog.setHeaderText("Please select a gender:");

                // Voeg de ComboBox toe aan het dialoogvenster
                dialog.getDialogPane().setContent(genderComboBox);

                // Voeg OK en Annuleren knoppen toe aan het dialoogvenster
                ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

                // Wacht op gebruikersselectie
                Optional<String> result = dialog.showAndWait();

                // Controleer of de gebruiker OK heeft gekozen en voer de actie uit
                if (result.isPresent() && result.get().equals("OK")) {
                    // Krijg het geselecteerde geslacht uit de ComboBox
                    String selectedGender = genderComboBox.getValue();

                    // Vervang CertificateControllerObject met de daadwerkelijke instantie van
                    // CertificateController
                    double certificatePercentage = certificateController
                            .getCertificatePercentageByGender(selectedGender);

                    // Toon het resultaat
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Certificate Percentage");
                    alert.setHeaderText("Percentage of certificates completed for " + selectedGender);
                    alert.setContentText(String.format("%.2f%%", certificatePercentage));
                    alert.showAndWait();
                }
            });

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

            String topWebcasts = overViewController.getTopThreeWatchedWebcastsTitles();
            topWebcasts = topWebcasts.substring(1, topWebcasts.length() - 1);
            String[] webcastTitles = topWebcasts.split(", ", 3);

            String topWebcastPercentages = overViewController.getTopThreeWatchedWebcastsPercentage();
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
    
                boolean hasRows = rs.next();  // Check if there are any rows
    
                if (!hasRows) {
                    result.append("This user has no progress in this course");
                } else {
                    do {
                        String moduleTitle = rs.getString("ModuleTitle");
                        double averageProgress = rs.getDouble("PercentageWatched");
    
                        result.append(moduleTitle).append(": ").append(String.format("%.2f%%", averageProgress)).append("\n");
                    } while (rs.next());
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