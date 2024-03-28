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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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

        // Create layout for the enrollment creation
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Enrollment Date");

        // Combobox for available courses
        courseController = new courseController();
        ArrayList<String> courseNames = courseController.getAllCourses();
        ObservableList<String> options = FXCollections.observableArrayList(courseNames);
        ComboBox courseNameBox = new ComboBox<>(options);
        courseNameBox.setPromptText("Select course name");

        // Combobox for available cursists
        cursistController = new CursistController();
        ArrayList<String> cursistEmails = cursistController.getAllCursistEmailAddress();
        ObservableList<String> cursistOptions = FXCollections.observableArrayList(cursistEmails);
        ComboBox cursistEmailBox = new ComboBox<>(cursistOptions);
        cursistEmailBox.setPromptText("Select emailaddress");

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

                LocalDate enrollmentDate = datePicker.getValue();
                String courseName = courseNameBox.getValue().toString();
                String cursistEmail = cursistEmailBox.getValue().toString();

                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentDate(enrollmentDate);
                enrollment.setCourseName(courseName);
                enrollment.setCursistEmailAddress(cursistEmail);

                // Add the enrollment to the database
                enrollmentController.addEnrollment(enrollment);

                // Show alert after adding a contentitem
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Enrollment registered successfully.");
                alert.showAndWait();

                // Clear the input fields after adding a contentitem
                datePicker.setValue(null);
                courseNameBox.getSelectionModel().clearSelection();
                cursistEmailBox.getSelectionModel().clearSelection();

                courseNameBox.setPromptText("Select course name");
                cursistEmailBox.setPromptText("Select emailaddress");
            }
        });

        VBox createFields = new VBox(datePicker, courseNameBox, cursistEmailBox, addButton);
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
            ArrayList<String> enrollmentCourseNames = enrollmentController.getAllEnrollmentCourseNames();
            ArrayList<String> enrollmentDates = enrollmentController.getAllEnrollmentDates();

            Button infoButton = new Button("More Info");
            infoButton.setPadding(buttonsMenuPadding);
            infoButton.setStyle("-fx-background-color: #d2b48c;");

            Label emailLabel = new Label("EmailAddress ↓");
            Label courseNameLabel = new Label("Coursename ↓");
            HBox listHbox = new HBox(emailLabel, courseNameLabel);

            emailLabel.setStyle("-fx-font-size: 20; -fx-padding: 0 12 0 0;");
            courseNameLabel.setStyle("-fx-font-size: 20");

            items.clear(); // Clear previous items
            courseNamesItems.clear();
            enrollmentDateItems.clear();

            for (String enrollmentDetails : enrollments) {
                items.add(enrollmentDetails); // Add enrollment details directly
            }

            for (String enrollmentDetailsCourseNames : enrollmentCourseNames) {
                courseNamesItems.add(enrollmentDetailsCourseNames); // Add enrollment details directly
            }

            for (String enrollmentDetailsDates : enrollmentDates) {
                enrollmentDateItems.add(enrollmentDetailsDates); // Add enrollment details directly
            }

            list.setItems(items);
            list.setStyle("-fx-font-size: 16; -fx-alignment: center;");
            list.setPadding(buttonsMenuPadding);

            courseNamesList.setItems(courseNamesItems);
            courseNamesList.setStyle("-fx-font-size: 16; -fx-alignment: center;");
            courseNamesList.setPadding(buttonsMenuPadding);

            enrollmentDateList.setItems(enrollmentDateItems);
            enrollmentDateList.setStyle("-fx-font-size: 16; -fx-alignment: center;");
            enrollmentDateList.setPadding(buttonsMenuPadding);

            HBox listHbox2 = new HBox(list, courseNamesList, enrollmentDateList);
            listHbox2.setSpacing(0);
            listHbox2.setPadding(buttonsMenuPadding);
            listHbox2.setAlignment(Pos.CENTER);

            VBox centerBox = new VBox(listHbox2);
            BorderPane cursistPage = new BorderPane();

            cursistPage.setCenter(centerBox);
            BorderPane.setMargin(centerBox, new Insets(25));

            Label cursistPageTitle = new Label("Overview all enrollments");

            cursistPageTitle.setStyle("-fx-font-size: 30;");
            BorderPane.setAlignment(cursistPageTitle, Pos.CENTER);
            cursistPage.setTop(cursistPageTitle);

            HBox cursistPageButtons = new HBox(deleteButton, backHome, infoButton);
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

        });

        // Handle delete button action
        deleteButton.setOnAction(h -> {
            String selectedEnrollment = list.getSelectionModel().getSelectedItem();

            if (selectedEnrollment != null) {
                // Haal het geselecteerde inschrijvingsobject op uit de lijst
                Enrollment selectedEnrollmentObject = enrollmentController.getEnrollmentByName(selectedEnrollment);

                if (selectedEnrollmentObject != null) {
                    // Roep de deleteEnrollment-methode aan met de vereiste parameters
                    enrollmentController.deleteEnrollment(selectedEnrollmentObject.getCourseName(),
                            selectedEnrollmentObject.getCursistEmailAddress(),
                            selectedEnrollmentObject.getEnrollmentDate());

                    // Verwijder het geselecteerde item uit de lijst
                    items.remove(selectedEnrollment);
                    courseNamesItems.remove(selectedEnrollment);

                    // Verwijder het geselecteerde item uit de list
                    list.getItems().remove(selectedEnrollment);
                    courseNamesList.getItems().remove(selectedEnrollment);

                    refreshContent();
                }
            }
        });

        Label mainSceneTitle = new Label("Register new Enrollment");
        mainSceneTitle.setStyle("-fx-font-size: 30;");
        Insets mainSceneTitlePadding = new Insets(0, 0, 25, 0);
        mainSceneTitle.setPadding(mainSceneTitlePadding);
        mainPane.setTop(mainSceneTitle);
        BorderPane.setAlignment(mainSceneTitle, Pos.CENTER);
        Insets mainPanePadding = new Insets(25);
        mainPane.setPadding(mainPanePadding);
        backToHomeButton.setPadding(buttonsMenuPadding);

        // mainPane.setBottom(backToHomeButton);
        backToHomeButton.setOnAction(i -> {
            stage.setScene(homeScene);
            stage.show();
        });

        Scene mainScene = new Scene(mainPane, 800, 600);
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
