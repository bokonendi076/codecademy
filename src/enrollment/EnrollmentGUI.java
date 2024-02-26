package enrollment;

import main.*;
import DatabaseManager.*;
import enrollment.*;
import course.*;
import cursist.*;
import java.time.LocalDate;
import java.util.ArrayList;

import contentItem.ContentItem;
import contentItem.contentItemController;
import course.courseController;
import javafx.application.Application;
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
    private ObservableList<String> items;
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
        Button updateButton = new Button("Edit enrollment");

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
        VBox homeLayout = new VBox(10, createButton, readButton, updateButton, backToCodeCademy);

        createButton.setPrefSize(150, 50);
        createButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        readButton.setPrefSize(150, 50);
        readButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        updateButton.setPrefSize(150, 50);
        updateButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");

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
        courseNameBox.setPromptText("Choose course name");

        // Combobox for available cursists
        cursistController = new CursistController();
        ArrayList<String> cursistEmails = cursistController.getAllCursistEmailAddress();
        ObservableList<String> cursistOptions = FXCollections.observableArrayList(cursistEmails);
        ComboBox cursistEmailBox = new ComboBox<>(cursistOptions);
        cursistEmailBox.setPromptText("Choose your email");

        Button addButton = new Button("Register enrollment Item");
        addButton.setStyle("-fx-background-color: #d2b48c;");

        addButton.setOnAction(f -> {

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
            courseNameBox.setValue(null);
            cursistEmailBox.setValue(null);

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

        // CRUD (read) functionality...
        readButton.setOnAction(g -> {
            // arraylist with all watchedContent
            ArrayList<String> enrollments = enrollmentController.getAllEnrollments();
            Button infoButton = new Button("More Info");
            infoButton.setPadding(buttonsMenuPadding);
            infoButton.setStyle("-fx-background-color: #d2b48c;");

            Label label = new Label("EmailAddress ↓");
            label.setStyle("-fx-font-size: 20;");

            items.setAll(enrollments);
            list.setItems(items);
            list.setStyle("-fx-font-size: 24; -fx-alignment: center;");
            list.setPadding(buttonsMenuPadding);

            VBox centerBox = new VBox(label, list);
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

                if (!selectedEnrollment.isEmpty()) {

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
            Enrollment selectedEnrollmentObject = new Enrollment();
            selectedEnrollmentObject.setCourseName(selectedEnrollmentObject.getCourseName());
            selectedEnrollmentObject.setCursistEmailAddress(selectedEnrollmentObject.getCursistEmailAddress());

            if (!selectedEnrollment.isEmpty()) {
                enrollmentController.deleteEnrollment(selectedEnrollmentObject.getCourseName(),
                        selectedEnrollmentObject.getCursistEmailAddress());
                items.remove(selectedEnrollment);
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

        updateButton.setOnAction(e -> {
            BorderPane editPane = new BorderPane();
            Label title = new Label("Choose enrollment to edit");
            BorderPane.setAlignment(title, Pos.TOP_CENTER);
            title.setStyle("-fx-font-size: 30;");
            Button chooseButton = new Button("Edit");
            chooseButton.setPadding(buttonsMenuPadding);
            chooseButton.setStyle("-fx-background-color: #d2b48c;");

            // Use the class-level backHome variable
            HBox buttonsEdit = new HBox(chooseButton, backHome);
            buttonsEdit.setSpacing(15);

            Insets buttonsEditPadding = new Insets(0, 15, 0, 15);
            buttonsEdit.setPadding(buttonsEditPadding);
            ArrayList<String> cursistEmailList = enrollmentController.getAllEnrollments();

            items.setAll(cursistEmailList);
            list.setItems(items);

            editPane.setBottom(buttonsEdit);
            buttonsEdit.setAlignment(Pos.CENTER);
            buttonsEdit.setAlignment(Pos.CENTER);
            BorderPane.setMargin(buttonsEdit, new Insets(0, 0, 25, 0));
            editPane.setTop(title);
            editPane.setCenter(list);
            list.setStyle("-fx-font-size: 24; -fx-alignment: center;");
            BorderPane.setMargin(list, new Insets(25));

            Scene updateScene = new Scene(editPane, 800, 600);
            updateScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(updateScene);
            stage.show();
            TextField updateNaamField = new TextField();
            TextField updateEmailField = new TextField();
            updateEmailField.setDisable(true);

            TextField updateBirthDateField = new TextField();
            TextField updateAddressField = new TextField();
            TextField updateCityField = new TextField();
            TextField updateCountryField = new TextField();

            chooseButton.setOnAction(f -> {
                BorderPane editWindow = new BorderPane();
                Label editWindowTitle = new Label("Edit window");
                editWindowTitle.setStyle("-fx-font-size: 30;");
                Button confirmButton = new Button("Confirm");
                confirmButton.setPadding(buttonsMenuPadding);
                confirmButton.setStyle("-fx-background-color: #d2b48c;");

                HBox editButtons = new HBox(backHome, confirmButton);

                String selectedCursistName = list.getSelectionModel().getSelectedItem();
                Cursist selectedCursist = cursistController.getCursistByName(selectedCursistName);

                updateNaamField.setText(selectedCursist.getName());
                updateEmailField.setText(selectedCursist.getEmailAddress());
                updateBirthDateField.setText(selectedCursist.getBirthDate().toString());
                updateAddressField.setText(selectedCursist.getAddress());
                updateCityField.setText(selectedCursist.getCity());
                updateCountryField.setText(selectedCursist.getCountry());

                VBox updateFields = new VBox(updateNaamField, updateEmailField,
                        updateBirthDateField,
                        updateAddressField, updateCityField, updateCountryField);
                updateFields.setSpacing(7);

                editWindow.setTop(editWindowTitle);
                BorderPane.setAlignment(editWindowTitle, Pos.CENTER);
                editWindow.setCenter(updateFields);
                editWindow.setBottom(editButtons);
                editButtons.setAlignment(Pos.CENTER);
                editButtons.setSpacing(15);
                BorderPane.setMargin(editButtons, new Insets(0, 0, 25, 0));
                BorderPane.setMargin(updateFields, new Insets(25));

                Scene confirmEdit = new Scene(editWindow, 800, 600);
                confirmEdit.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                stage.setScene(confirmEdit);
                stage.show();

                confirmButton.setOnAction(g -> {
                    // Get the selected cursist
                    selectedCursist.setEmailAddress(updateEmailField.getText());
                    selectedCursist.setName(updateNaamField.getText());
                    selectedCursist.setCity(updateCityField.getText());
                    selectedCursist.setCountry(updateCountryField.getText());
                    selectedCursist.setAddress(updateAddressField.getText());

                    cursistController.updateCursistFields(selectedCursist);

                    Alert alert = new Alert(AlertType.INFORMATION);

                    // Set the title and header text
                    alert.setTitle("Confirmed");
                    alert.setHeaderText(null);

                    // Set the content text
                    alert.setContentText("Edits have been confirmed!");

                    // Show the alert
                    alert.showAndWait();

                    // Clear all textfield
                    // updateNaamField.clear();
                    // updateEmailField.clear();
                    // updateBirthDateField.clear();
                    // updateAddressField.clear();
                    // updateCityField.clear();
                    // updateCountryField.clear();

                    // // set prompt text back for textfields
                    // updateNaamField.setPromptText("Name");
                    // updateEmailField.setPromptText("EmailAddress");
                    // updateBirthDateField.setPromptText("BirthDate");
                    // updateAddressField.setPromptText("Address");
                    // updateCityField.setPromptText("City");
                    // updateCountryField.setPromptText("Country");

                    // // set the textfield to disable
                    // updateNaamField.setDisable(true);
                    // updateEmailField.setDisable(true);
                    // updateBirthDateField.setDisable(true);
                    // updateAddressField.setDisable(true);
                    // updateCityField.setDisable(true);
                    // updateCountryField.setDisable(true);

                });

            });
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

}
