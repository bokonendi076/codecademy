package enrollment;

import main.*;
import DatabaseManager.*;
import enrollment.*;
import course.*;
import cursist.*;
import java.time.LocalDate;
import java.util.ArrayList;
import course.courseController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EnrollmentGUI extends Application {
    private EnrollmentController enrollmentController;
    private ListView<String> list;
    private ListView<String> courseNamesList;
    private ListView<String> enrollmentDateList;
    private ObservableList<String> items;
    private ObservableList<String> courseNamesItems;
    private ObservableList<String> enrollmentDateItems;
    private Scene mainScene;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;
    private course.courseController courseController;
    private cursist.CursistController cursistController;

    public Scene enrollmentScene(Stage stage) {
        GUI gui = new GUI();
        enrollmentController = new EnrollmentController();

        Button backToHomeButton = new Button("< Home");
        backToHomeButton.setStyle("-fx-background-color: #d2b48c;");
        backHome = new Button("< Home");
        backHome.setOnAction(j -> {
            stage.setScene(homeScene);
            stage.show();
        });

        // Create a welcome message for the homepage
        Button createButton = new Button("Add enrollment");
        Button readButton = new Button("View all enrollments");

        Label welcomeLabel = new Label("Welcome to enrollment management");

        Insets welcomeLabelPadding = new Insets(25);
        welcomeLabel.setPadding(welcomeLabelPadding);
        welcomeLabel.setStyle("-fx-font-size: 24;");
        backToCodeCademy = new Button("< CodeCademy");

        backToCodeCademy.setOnAction(l -> {
            stage.setScene(gui.getHomeScene(stage));
            stage.show();
        });
        backToCodeCademy.setPrefSize(150, 50);
        backToCodeCademy.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");

        // Create layout for the homepage
        BorderPane homePane = new BorderPane();
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
        homePane.setTop(welcomeLabel);
        VBox homeLayout = new VBox(10, createButton, readButton, backToCodeCademy);

        createButton.setPrefSize(150, 50);
        createButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        readButton.setPrefSize(150, 50);
        readButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");

        homeLayout.setAlignment(Pos.CENTER);
        homePane.setCenter(homeLayout);
        Insets padding = new Insets(100);
        homePane.setPadding(padding);
        homeScene = new Scene(homePane, 800, 600);

        // Label for Enrollment Date
        Label enrollmentDateLabel = new Label("Enrollment Date:");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Enrollment Date");
        datePicker.setEditable(false);
        HBox datePickerBox = new HBox(enrollmentDateLabel, datePicker);
        datePickerBox.setAlignment(Pos.CENTER);
        datePickerBox.setSpacing(5);

        // Label for Course Name
        Label addCourseNameLabel = new Label("Course Name:");
        courseController = new courseController();
        ArrayList<String> courseNames = courseController.getAllCourses();
        ObservableList<String> options = FXCollections.observableArrayList(courseNames);
        ComboBox courseNameBox = new ComboBox<>(options);
        courseNameBox.setPromptText("Select course name");
        HBox courseNameBoxWrapper = new HBox(addCourseNameLabel, courseNameBox);
        courseNameBoxWrapper.setAlignment(Pos.CENTER);
        courseNameBoxWrapper.setSpacing(5);

        // Label for Cursist Email
        Label cursistEmailLabel = new Label("Cursist Email:");
        cursistController = new CursistController();
        ArrayList<String> cursistEmails = cursistController.getAllCursistEmailAddress();
        ObservableList<String> cursistOptions = FXCollections.observableArrayList(cursistEmails);
        ComboBox cursistEmailBox = new ComboBox<>(cursistOptions);
        cursistEmailBox.setPromptText("Select email address");
        HBox cursistEmailBoxWrapper = new HBox(cursistEmailLabel, cursistEmailBox);
        cursistEmailBoxWrapper.setAlignment(Pos.CENTER);
        cursistEmailBoxWrapper.setSpacing(5);

        Button addButton = new Button("Register enrollment Item");
        addButton.setStyle("-fx-background-color: #d2b48c;");

        addButton.setOnAction(f -> {

            // check if input fields are empty
            if (datePicker.getValue() == null || courseNameBox.getValue() == null
                    || cursistEmailBox.getValue() == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all the fields.");
                alert.showAndWait();

            } else {

                // validate the input of date to not be in the future
                if (datePicker.getValue().isAfter(LocalDate.now())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Enrollment date cannot be in the future.");
                    alert.showAndWait();
                    return;
                }

                LocalDate enrollmentDate = datePicker.getValue();
                String courseName = courseNameBox.getValue().toString();
                String cursistEmail = cursistEmailBox.getValue().toString();

                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentDate(enrollmentDate);
                enrollment.setCourseName(courseName);
                enrollment.setCursistEmailAddress(cursistEmail);

                // Add the enrollment to the database
                enrollmentController.addEnrollment(enrollment);

                // Show alert after adding a content item
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Enrollment registered successfully.");
                alert.showAndWait();

                // Clear input fields
                datePicker.setValue(null);
                courseNameBox.setValue(null);
                cursistEmailBox.setValue(null);

            }
        });

        VBox createFields = new VBox(datePickerBox, courseNameBoxWrapper, cursistEmailBoxWrapper, addButton);
        createFields.setSpacing(7);
        createFields.setAlignment(Pos.CENTER);

        // CRUD Buttons are created
        Button deleteButton = new Button("Delete");

        // Create layout for buttons
        HBox buttonsMenu = new HBox(addButton, backToHomeButton);

        Insets buttonsMenuPadding = new Insets(10);
        addButton.setPadding(buttonsMenuPadding);
        deleteButton.setPadding(buttonsMenuPadding);
        deleteButton.setStyle("-fx-background-color: #d2b48c;");

        backHome.setPadding(buttonsMenuPadding);
        backHome.setStyle("-fx-background-color: #d2b48c;");

        buttonsMenu.setAlignment(Pos.CENTER);
        buttonsMenu.setSpacing(5);

        VBox vboxesCombined = new VBox(createFields, buttonsMenu);
        vboxesCombined.setSpacing(10);

        // Initialize main pane
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(vboxesCombined);

        // Initialize list and items
        list = new ListView<>();
        items = FXCollections.observableArrayList();

        courseNamesList = new ListView<>();
        courseNamesItems = FXCollections.observableArrayList();

        enrollmentDateList = new ListView<>();
        enrollmentDateItems = FXCollections.observableArrayList();

        // CRUD (read) functionality...
        readButton.setOnAction(g -> {
            // arraylist with all watchedContent
            ArrayList<String> enrollments = enrollmentController.getAllEnrollments();
            Button showEnrollments = new Button("Show all enrollments");
            showEnrollments.setStyle("-fx-background-color: #d2b48c;");
            showEnrollments.setPrefSize(150, 40);

            Button infoButton = new Button("More Info");
            infoButton.setPadding(buttonsMenuPadding);
            infoButton.setStyle("-fx-background-color: #d2b48c;");

            items.clear(); // Clear previous items
            courseNamesItems.clear();
            enrollmentDateItems.clear();

            for (String enrollmentDetails : enrollments) {
                items.add(enrollmentDetails); // Add enrollment details directly
            }

            list.setItems(items);
            list.setStyle("-fx-font-size: 16; -fx-alignment: center;");
            list.setPadding(buttonsMenuPadding);

            BorderPane cursistPage = new BorderPane();

            cursistPage.setCenter(list);
            BorderPane.setMargin(list, new Insets(25));

            Label cursistPageTitle = new Label("Overview all enrollments");

            cursistPageTitle.setStyle("-fx-font-size: 30;");
            BorderPane.setAlignment(cursistPageTitle, Pos.CENTER);
            cursistPage.setTop(cursistPageTitle);

            HBox cursistPageButtons = new HBox(backHome, showEnrollments);
            cursistPageButtons.setSpacing(10);
            Insets cursistPageButtonsPadding = new Insets(0, 15, 0, 15);
            cursistPageButtons.setPadding(cursistPageButtonsPadding);
            cursistPageButtons.setAlignment(Pos.CENTER);
            BorderPane.setMargin(cursistPageButtons, new Insets(0, 0, 25, 0));
            cursistPage.setBottom(cursistPageButtons);

            mainScene = new Scene(cursistPage, 800, 600); // Assign mainScene here

            stage.setTitle("Enrollments overview");
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

            infoButton.setOnAction(h -> {
                String selectedEnrollment = list.getSelectionModel().getSelectedItem();

                Enrollment enrollment = enrollmentController.getEnrollmentByName(selectedEnrollment);

                if (enrollment != null) {
                    String enrollmentDate = String.valueOf(enrollment.getEnrollmentDate());
                    String courseName = enrollment.getCourseName();
                    String cursistEmail = enrollment.getCursistEmailAddress();

                    String info = "Enrollment Date: " + enrollmentDate + "\nCourse Name: " + courseName
                            + "\nCursist Email: " + cursistEmail;

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Enrollment Info");
                    alert.setHeaderText(null);
                    alert.setContentText(info);
                    alert.showAndWait();
                }
            });

            showEnrollments.setOnAction(h -> {
                String selectedEmail = list.getSelectionModel().getSelectedItem();

                Label info = new Label(
                        "These are all the enrollments for the cursist with the email: " + selectedEmail);
                info.setStyle("-fx-font-size: 16; -fx-alignment: center;");

                Button backButtonOverview = new Button("< Back");
                backButtonOverview.setStyle("-fx-background-color: #d2b48c;");
                backButtonOverview.setPadding(buttonsMenuPadding);
                backButtonOverview.setPrefSize(100, 40);

                backButtonOverview.setOnAction(l -> {
                    stage.setScene(mainScene);
                    stage.show();
                });

                ArrayList<String> enrollmentDates = enrollmentController.getAllEnrollmentDatesByEmail(selectedEmail);
                ObservableList<String> enrollmentDateObservableList = FXCollections
                        .observableArrayList(enrollmentDates);

                enrollmentDateItems.clear();
                enrollmentDateItems.addAll(enrollmentDateObservableList);
                enrollmentDateList.setItems(enrollmentDateItems);
                enrollmentDateList.setStyle("-fx-font-size: 16; -fx-alignment: center;");
                enrollmentDateList.setPadding(buttonsMenuPadding);

                ArrayList<String> courses = enrollmentController.getAllEnrollmentsByEmail(selectedEmail);
                ObservableList<String> coursesObservableList = FXCollections
                        .observableArrayList(courses);

                courseNamesItems.clear();
                courseNamesItems.addAll(coursesObservableList);
                courseNamesList.setItems(courseNamesItems);
                courseNamesList.setStyle("-fx-font-size: 16; -fx-alignment: center;");
                courseNamesList.setPadding(buttonsMenuPadding);

                HBox vBox2 = new HBox(courseNamesList, enrollmentDateList);
                vBox2.setSpacing(10);
                vBox2.setPadding(buttonsMenuPadding);
                vBox2.setAlignment(Pos.CENTER); // Align the HBox content to center horizontally

                HBox buttonsEnrollemnt = new HBox(backButtonOverview, deleteButton);
                buttonsEnrollemnt.setSpacing(10);
                buttonsEnrollemnt.setPadding(buttonsMenuPadding);
                buttonsEnrollemnt.setAlignment(Pos.CENTER); // Align the HBox content to center horizontally

                VBox vBox = new VBox(info, vBox2, buttonsEnrollemnt);
                vBox.setSpacing(10);
                vBox.setPadding(buttonsMenuPadding);
                vBox.setAlignment(Pos.CENTER); // Align the VBox content to center horizontally and vertically

                BorderPane cursistPage2 = new BorderPane();
                cursistPage2.setCenter(vBox);

                Scene showEnrollmentsScene = new Scene(cursistPage2, 800, 600); // Assign mainScene here

                stage.setTitle("Enrollments overview");
                showEnrollmentsScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                stage.setScene(showEnrollmentsScene);
                stage.show();

                // Handle delete button action
                deleteButton.setOnAction(m ->

                {
                    String selectedEnrollment = courseNamesList.getSelectionModel().getSelectedItem();

                    if (selectedEnrollment != null) {
                        // Haal het geselecteerde inschrijvingsobject op uit de lijst
                        Enrollment selectedEnrollmentObject = enrollmentController
                                .getEnrollmentByName(selectedEnrollment);

                        if (selectedEnrollmentObject != null) {
                            // Roep de deleteEnrollment-methode aan met de vereiste parameters
                            enrollmentController.deleteEnrollment(selectedEnrollmentObject.getCourseName(),
                                    selectedEnrollmentObject.getCursistEmailAddress(),
                                    selectedEnrollmentObject.getEnrollmentDate());

                            // Verwijder het geselecteerde item uit de lijst
                            courseNamesList.getItems().remove(selectedEnrollmentObject.getCourseName());

                            // Verwijder het geselecteerde item uit de ObservableList
                            enrollmentDateObservableList.remove(selectedEnrollmentObject.getEnrollmentDate());

                            refreshContent();

                        }
                    }
                });

            });

        });

        Label mainSceneTitle = new Label("Register new Enrollment");
        mainSceneTitle.setStyle("-fx-font-size: 30;");
        Insets mainSceneTitlePadding = new Insets(0, 0, 25,
                0);
        mainSceneTitle.setPadding(mainSceneTitlePadding);
        mainPane.setTop(mainSceneTitle);
        BorderPane.setAlignment(mainSceneTitle, Pos.CENTER);
        Insets mainPanePadding = new Insets(
                25);
        mainPane.setPadding(mainPanePadding);
        backToHomeButton.setPadding(buttonsMenuPadding);

        // mainPane.setBottom(backToHomeButton);
        backToHomeButton.setOnAction(i -> {
            stage.setScene(homeScene);
            stage.show();
        });

        Scene mainScene = new Scene(mainPane, 800,
                600);
        stage.setTitle("Enrollment management");
        stage.setScene(mainScene);
        stage.show();

        stage.setScene(homeScene);
        stage.setTitle("Enrollment management");
        stage.show();
        // Create button op homepage
        createButton.setOnAction(j -> {
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

        });

        homeScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
        return homeScene;
    }

    public EnrollmentGUI(EnrollmentController enrollmentController) {
        this.enrollmentController = enrollmentController;
    }

    @Override
    public void start(Stage arg0) throws Exception {

    }

    private void refreshContent() {
        // Perform UI updates on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Clear the list
            list.getItems().clear();
            courseNamesList.getItems().clear();
            enrollmentDateItems.clear();

            // Add the updated list
            list.getItems().addAll(enrollmentController.getAllEnrollments());
            courseNamesList.getItems().addAll(enrollmentController.getAllEnrollmentCourseNames());
            enrollmentDateList.getItems().addAll(enrollmentController.getAllEnrollmentDates());

            // Refresh the UI
            list.refresh();
            courseNamesList.refresh();
            enrollmentDateList.refresh();
        });
    }

}
