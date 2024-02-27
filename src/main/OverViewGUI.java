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

                Label genderOverview1 = new Label("Please choose a course: ");

                // Retrieve course names
                List<String> courseNames = overViewController.getCourseNames();
                ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(courseNames));

                Button showProgressButton = new Button("Show Average Progress per Module");
                showProgressButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");

                VBox layout2 = new VBox(10, genderOverview1, courseComboBox, showProgressButton, backToHomeButton);
                layout2.setAlignment(Pos.CENTER);

                BorderPane overViewPane2 = new BorderPane();
                overViewPane2.setTop(genderOverview1);
                BorderPane.setAlignment(genderOverview1, Pos.CENTER);
                genderOverview1.setPadding(new Insets(25, 0, 25, 0));

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

            completedCourses.setOnAction(event -> {
                // Create a new stage for course selection
                Stage courseSelectionStage = new Stage();
                courseSelectionStage.setTitle("Select a Course");

                // Retrieve course names
                List<String> courseNames = overViewController.getCourseNames();
                ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(courseNames));

                Button selectCourseButton = new Button("Select Course");
                selectCourseButton.setOnAction(courseEvent -> {
                    String selectedCourse = courseComboBox.getValue();

                    if (selectedCourse != null) {
                        // Close the course selection stage
                        courseSelectionStage.close();

                        // Get and display the count of participants who have completed the selected
                        // course
                        String completionCount = overViewController.getCompletedCourseAccounts(selectedCourse);
                        // Display the result (you can customize this part)
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Completion Count");
                        alert.setHeaderText(null);
                        alert.setContentText(
                                "The number of participants who completed the course is: " + completionCount);
                        alert.showAndWait();
                    } else {
                        // Handle case where the course is not selected
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select a course.");
                        alert.showAndWait();
                    }
                });

                VBox courseSelectionLayout = new VBox(10, courseComboBox, selectCourseButton);
                courseSelectionLayout.setAlignment(Pos.CENTER);
                Scene courseSelectionScene = new Scene(courseSelectionLayout, 300, 150);

                courseSelectionStage.setScene(courseSelectionScene);
                courseSelectionStage.show();
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

            viewCertificates.setOnAction(event -> {
                Label genderOverview1 = new Label("Please choose a gender: ");

                // Assuming you have a method to retrieve genders, replace getCourses() with the
                // correct method
                ChoiceBox<String> genderChoiceBox = new ChoiceBox<>();
                genderChoiceBox.getItems().addAll("Select gender", "Male", "Female", "Other");
                genderChoiceBox.getSelectionModel().selectFirst();

                Button showProgressButton = new Button("Show Percentage Courses With Certificate");
                showProgressButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");

                VBox layout2 = new VBox(10, genderOverview1, genderChoiceBox, showProgressButton, backToHomeButton);
                layout2.setAlignment(Pos.CENTER);

                BorderPane overViewPane2 = new BorderPane();
                overViewPane2.setTop(genderOverview1);
                BorderPane.setAlignment(genderOverview1, Pos.CENTER);
                genderOverview1.setPadding(new Insets(25, 0, 25, 0));

                overViewPane2.setCenter(layout2);

                Scene genderOverviewScene = new Scene(overViewPane2, 800, 600);

                showProgressButton.setOnAction(buttonEvent -> {
                    String selectedGender = genderChoiceBox.getValue();

                    if (selectedGender != null) {
                        // Get and display the percentage of courses with certificates for the selected
                        // gender
                        String result = overViewController.calculatePercentageCoursesWithCertificate(selectedGender);

                        // Display the result
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Percentage of Courses with Certificates");
                        alert.setHeaderText(null);
                        alert.setContentText(result);
                        alert.showAndWait();
                    } else {
                        // Handle case where no gender is selected
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select a gender.");
                        alert.showAndWait();
                    }
                });

                stage.setScene(genderOverviewScene);
                stage.show(); // Show the new stage
            });

            generateCertificates.setOnAction(event -> {

                overViewController.getCompletedCertificates();

                BorderPane generateCertificatePane = new BorderPane();

                Scene certificateOverviewScene = new Scene(generateCertificatePane, 800, 600);

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

            String topWebcasts = overViewController.getTop3WatchedWebcasts();
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

   

    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {

    }
}