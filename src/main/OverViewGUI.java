package main;

import DatabaseManager.*;
import cursist.CursistController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverViewGUI extends Application {
    private DatabaseManager db;
    private Connection connection;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;
    private BorderPane homePane;
    private OverViewController overViewController = new OverViewController();
    private Button backToOptions;

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
        backToHomeButton.setPrefSize(150, 50);
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
            backToHomeButton.setPrefSize(150, 50);

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

            backToOptions = new Button("< Back");
            backToOptions.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
            backToOptions.setPrefSize(150, 50);
            backToOptions.setOnAction(event -> {
                stage.setScene(courseOverviewScene);
                stage.show();
            });

            progressPercentage.setOnAction(a -> {

                Label genderOverview1 = new Label("Please choose a course: ");
                genderOverview1.setStyle("-fx-font-size: 28; ");
                genderOverview1.setPadding(new Insets(25, 0, 25, 0));

                // Retrieve course names
                List<String> courseNames = overViewController.getCourseNames();
                ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(courseNames));
                courseComboBox.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                courseComboBox.setPrefSize(150, 50);
                courseComboBox.setPromptText("Select course");

                Button showProgressButton = new Button("Show Average Progress per Module");
                showProgressButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                showProgressButton.setPrefSize(150, 50);

                VBox layout2 = new VBox(10, genderOverview1, courseComboBox, showProgressButton, backToOptions);
                layout2.setAlignment(Pos.CENTER);

                BorderPane overViewPane2 = new BorderPane();
                overViewPane2.setTop(genderOverview1);
                BorderPane.setAlignment(genderOverview1, Pos.CENTER);
                genderOverview1.setPadding(new Insets(25, 0, 25, 0));

                overViewPane2.setCenter(layout2);

                Scene courseOverviewScene2 = new Scene(overViewPane2, 800, 600);

                // Set the scene and show the stage
                courseOverviewScene2.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                stage.setScene(courseOverviewScene2);
                stage.show();

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

            });

            averageProgressModule.setOnAction(b -> {

                Label titleCertificateOverview = new Label("Select an emailaddress and course:");
                titleCertificateOverview.setStyle("-fx-font-size: 28;");
                titleCertificateOverview.setPadding(new Insets(25, 0, 25, 0));

                // Retrieve account emails using the CursistController method
                ArrayList<String> accountEmails = cursistController.getAllCursistEmailAddress();
                ComboBox<String> accountComboBox = new ComboBox<>(FXCollections.observableArrayList(accountEmails));
                accountComboBox.setPromptText("Select emailaddress");
                accountComboBox.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                accountComboBox.setPrefSize(150, 50);

                // Retrieve course names
                List<String> courseNames = overViewController.getCourseNames();
                ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(courseNames));
                courseComboBox.setPromptText("Select course");
                courseComboBox.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                courseComboBox.setPrefSize(150, 50);

                Button showProgressButton = new Button("Show Progress per Module (%)");
                showProgressButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                showProgressButton.setPrefSize(150, 50);

                VBox layout3 = new VBox(10, titleCertificateOverview, accountComboBox, courseComboBox,
                        showProgressButton, backToOptions);
                layout3.setAlignment(Pos.CENTER);

                BorderPane certificateOverviewPane = new BorderPane();

                certificateOverviewPane.setTop(titleCertificateOverview);
                BorderPane.setAlignment(titleCertificateOverview, Pos.CENTER);
                titleCertificateOverview.setPadding(new Insets(25, 0, 25, 0));

                certificateOverviewPane.setCenter(layout3);

                Scene certificateOverviewScene = new Scene(certificateOverviewPane, 800, 600);
                certificateOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");

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

                // style the scene

                stage.setScene(certificateOverviewScene);
                stage.show(); // Show the new stage
            });

            completedCourses.setOnAction(event -> {
                Label titleCompletedOverview = new Label("Completed courses");
                titleCompletedOverview.setStyle("-fx-font-size: 28;");
                titleCompletedOverview.setPadding(new Insets(25, 0, 25, 0));

                // Retrieve course names
                List<String> courseNames = overViewController.getCourseNames();
                ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(courseNames));
                courseComboBox.setPromptText("Please select a course");
                courseComboBox.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                courseComboBox.setPrefSize(150, 50);

                Button selectCourseButton = new Button("Show completed courses");
                selectCourseButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                selectCourseButton.setPrefSize(150, 50);

                VBox layout4 = new VBox(10, titleCompletedOverview, courseComboBox, selectCourseButton,
                        backToOptions);
                layout4.setAlignment(Pos.CENTER);

                BorderPane completedOverviewPane = new BorderPane();
                completedOverviewPane.setTop(titleCompletedOverview);
                BorderPane.setAlignment(titleCompletedOverview, Pos.CENTER);
                titleCompletedOverview.setPadding(new Insets(25, 0, 25, 0));

                completedOverviewPane.setCenter(layout4);

                Scene completedOverviewScene = new Scene(completedOverviewPane, 800, 600);

                selectCourseButton.setOnAction(courseEvent -> {
                    String selectedCourse = courseComboBox.getValue();

                    if (selectedCourse != null) {
                        String completionCount = overViewController.getCompletedCourseAccounts(selectedCourse);
                        // Display the result (you can customize this part)
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Completion Count");
                        alert.setHeaderText(null);
                        alert.setContentText(completionCount);
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
                completedOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                stage.setScene(completedOverviewScene);
                stage.show();
            });

        });

        certificateOverviewButton.setOnAction(e -> {

            Label titleCertificateOverview = new Label("Certificate Overview:");
            Button viewCertificates = new Button("Gender Certificate %");
            Button generateCertificates = new Button("Account certificates");
            Button backToPage = new Button("< Back");

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
                genderOverview1.setStyle("-fx-font-size: 24;");
                genderOverview1.setPadding(new Insets(25, 0, 25, 0));

                ChoiceBox<String> genderChoiceBox = new ChoiceBox<>();
                genderChoiceBox.getItems().addAll("Select gender", "Male", "Female", "Other");
                genderChoiceBox.getSelectionModel().selectFirst();
                genderChoiceBox.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                genderChoiceBox.setPrefSize(150, 50);

                Button showProgressButton = new Button("Show Percentage Courses With Certificate");
                showProgressButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                showProgressButton.setPrefSize(150, 50);

                VBox layout2 = new VBox(10, genderOverview1, genderChoiceBox, showProgressButton, backToPage);
                layout2.setAlignment(Pos.CENTER);

                BorderPane overViewPane2 = new BorderPane();
                overViewPane2.setTop(genderOverview1);
                BorderPane.setAlignment(genderOverview1, Pos.CENTER);
                genderOverview1.setPadding(new Insets(25, 0, 25, 0));

                overViewPane2.setCenter(layout2);

                Scene genderOverviewScene = new Scene(overViewPane2, 800, 600);
                genderOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");

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

                Label accountOverview = new Label("Please choose an account: ");
                accountOverview.setStyle("-fx-font-size: 24;");
                accountOverview.setPadding(new Insets(25, 0, 25, 0));

                ArrayList<String> accountEmails = cursistController.getAllCursistEmailAddress();

                ComboBox<String> accountChoiceBox = new ComboBox<>();
                accountChoiceBox.getItems().addAll(accountEmails.toArray(new String[0]));
                accountChoiceBox.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                accountChoiceBox.setPrefSize(150, 50);
                accountChoiceBox.setPromptText("Select Email");

                Button generateButton = new Button("Generate");
                generateButton.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
                generateButton.setPrefSize(150, 50);

                VBox layoutBox = new VBox(10, accountOverview, accountChoiceBox, generateButton, backToPage);

                layoutBox.setPadding(new Insets(25, 0, 25, 0));

                layoutBox.setAlignment(Pos.CENTER);

                generateButton.setOnAction(buttonEvent -> {
                    String selectedAccount = accountChoiceBox.getValue();

                    if (selectedAccount != null) {
                        String completedCertificates = overViewController
                                .getCompletedCertificates(selectedAccount.toString());
                        if (!completedCertificates.isEmpty()) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Certificates achieved");
                            alert.setHeaderText("Certificates achieved");
                            alert.setContentText(completedCertificates);
                            alert.showAndWait();
                            System.out.println(completedCertificates);
                        } else {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText(null);
                            alert.setContentText("This cursist has yet to achieve any certificates.");
                            alert.showAndWait();
                        }

                    } else {
                        // Handle case where no account is selected
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select an account.");
                        alert.showAndWait();

                    }
                });

                Scene certificateOverviewScene = new Scene(layoutBox, 800, 600);
                certificateOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                stage.setScene(certificateOverviewScene);
                stage.show();

            });

            certificateOverviewPane.setCenter(layout);

            Scene certificateOverviewScene = new Scene(certificateOverviewPane, 800, 600);
            stage.setScene(certificateOverviewScene);
            certificateOverviewScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.show();

            backToPage.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
            backToPage.setPrefSize(150, 50);
            backToPage.setOnAction(k -> {
                stage.setScene(certificateOverviewScene);
                stage.show();
            });

        });

        webcastOverview.setOnAction(e ->

        {

            Label titleWebcastOverview = new Label("Webcast Overview:");
            Button viewWebcasts = new Button("Top 3 Watched Webcasts");

            viewWebcasts.setStyle("-fx-font-size: 12; -fx-background-color: #d2b48c;");
            titleWebcastOverview.setStyle("-fx-font-size: 28;");

            viewWebcasts.setPrefSize(150, 50);

            VBox layout = new VBox(10, viewWebcasts, backToHomeButton);
            layout.setAlignment(Pos.CENTER);

            BorderPane webcastOverviewPane = new BorderPane();
            webcastOverviewPane.setTop(titleWebcastOverview);
            BorderPane.setAlignment(titleWebcastOverview, Pos.CENTER);
            titleWebcastOverview.setPadding(new Insets(25, 0, 25, 0));

            viewWebcasts.setOnAction(event -> {
                // Assuming you have a method to retrieve and display the top 3 watched webcasts
                String result = overViewController.getTop3WatchedWebcasts();
                // Display the result
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Top 3 Watched Webcasts");
                alert.setHeaderText(null);
                alert.setContentText(result);
                alert.showAndWait();
            });

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