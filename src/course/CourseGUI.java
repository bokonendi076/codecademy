package course;

import main.*;
import java.util.ArrayList;
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

public class CourseGUI extends Application {
    private courseController courseController;
    private ListView<String> list;
    private ObservableList<String> items;
    private Scene mainScene;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;

    public Scene courseScene(Stage stage) {
        GUI gui = new GUI();
        courseController = new courseController();

        Button backToHomeButton = new Button("< Home");
        backToHomeButton.setStyle("-fx-background-color: #d2b48c;");
        backHome = new Button("< Home");
        backHome.setOnAction(j -> {
            stage.setScene(homeScene);
            stage.show();
        });

        // Create a welcome message for the homepage
        Button createButton = new Button("Create Course");
        Button readButton = new Button("All Courses");
        Label welcomeLabel = new Label("Welcome to course management");

        Insets welcomeLabelPadding = new Insets(25);
        welcomeLabel.setPadding(welcomeLabelPadding);
        welcomeLabel.setStyle("-fx-font-size: 24;");
        Button editButton = new Button("Edit Course");
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
        VBox homeLayout = new VBox(10, createButton, readButton, editButton, backToCodeCademy);

        createButton.setPrefSize(150, 50);
        createButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        readButton.setPrefSize(150, 50);
        readButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        editButton.setPrefSize(150, 50);
        editButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");

        homeLayout.setAlignment(Pos.CENTER);
        homePane.setCenter(homeLayout);
        Insets padding = new Insets(100);
        homePane.setPadding(padding);
        homeScene = new Scene(homePane, 800, 600);

        TextField createNaamField = new TextField();
        createNaamField.setPromptText("Naam");

        TextField createSubjectField = new TextField();
        createSubjectField.setPromptText("Subject");

        TextField createIntroductionText = new TextField();
        createIntroductionText.setPromptText("Introduction Text");

        // create choicebox for difficulty level
        ComboBox<String> difficultyLevelComboBox = new ComboBox<>();
        difficultyLevelComboBox.getItems().addAll("Select difficulty", "Beginner", "Intermediate", "Expert");
        difficultyLevelComboBox.getSelectionModel().selectFirst();

        // set a label next to the spinner that says "Difficulty Level"
        Label difficultyLevelLabel = new Label("Difficulty Level:");
        HBox difficultyLevelHBox = new HBox(difficultyLevelLabel, difficultyLevelComboBox);
        difficultyLevelHBox.setSpacing(10);

        Button addButton = new Button("Add Course");
        addButton.setStyle("-fx-background-color: #d2b48c;");

        addButton.setOnAction(f -> {

            if (createNaamField.getText().isBlank()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                return;
            }

            String naam = createNaamField.getText();
            String subject = createSubjectField.getText();
            String introductionText = createIntroductionText.getText();
            String difficultyLevel = difficultyLevelComboBox.getValue();

            Course newCourse = new Course();
            newCourse.setName(naam);
            newCourse.setSubject(subject);
            newCourse.setIntroductionText(introductionText);
            newCourse.setDifficultyLevel(difficultyLevel);

            courseController.saveCourse(newCourse);

            // Show alert after adding a course
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Course added successfully.");
            alert.showAndWait();

            // Clear the input fields after adding a course
            createNaamField.clear();
            createSubjectField.clear();
            createIntroductionText.clear();
            difficultyLevelComboBox.getValue();
        });

        VBox createFields = new VBox(createNaamField, createSubjectField, createIntroductionText,
                difficultyLevelHBox, addButton);
        createFields.setSpacing(7);

        // CRUD Buttons are created
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update Course");

        // Create layout for buttons
        HBox buttonsMenu = new HBox(addButton, backToHomeButton);

        Insets buttonsMenuPadding = new Insets(10);
        addButton.setPadding(buttonsMenuPadding);
        deleteButton.setPadding(buttonsMenuPadding);
        deleteButton.setStyle("-fx-background-color: #d2b48c;");
        updateButton.setPadding(buttonsMenuPadding);
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
            // arraylist with all course names
            ArrayList<String> courseNames = courseController.getAllCourses();
            Button infoButton = new Button("More Info");
            infoButton.setPadding(buttonsMenuPadding);
            infoButton.setStyle("-fx-background-color: #d2b48c;");

            Label label = new Label("Course name ↓");
            label.setStyle("-fx-font-size: 20;");

            items.setAll(courseNames);
            list.setItems(items);
            list.setStyle("-fx-font-size: 24; -fx-alignment: center;");
            list.setPadding(buttonsMenuPadding);

            VBox centerBox = new VBox(label, list);
            BorderPane cursistPage = new BorderPane();

            cursistPage.setCenter(centerBox);
            BorderPane.setMargin(centerBox, new Insets(25));

            Label cursistPageTitle = new Label("Overview all courses");
            cursistPageTitle.setStyle("-fx-font-size: 30;");
            BorderPane.setAlignment(cursistPageTitle, Pos.CENTER);
            cursistPage.setTop(cursistPageTitle);

            HBox cursistPageButtons = new HBox(backHome, deleteButton, infoButton);
            cursistPageButtons.setSpacing(10);
            Insets cursistPageButtonsPadding = new Insets(0, 15, 0, 15);
            cursistPageButtons.setPadding(cursistPageButtonsPadding);
            cursistPageButtons.setAlignment(Pos.CENTER);
            BorderPane.setMargin(cursistPageButtons, new Insets(0, 0, 25, 0));
            cursistPage.setBottom(cursistPageButtons);

            mainScene = new Scene(cursistPage, 800, 600); // Assign mainScene here

            stage.setTitle("Course overview");
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

            infoButton.setOnAction(h -> {
                String selectedCourse = list.getSelectionModel().getSelectedItem();
                Course course = courseController.getCourseByName(selectedCourse);

                if (selectedCourse != null) {
                    int completedCursistCount = courseController.getCompletedCursistCount(selectedCourse);

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Course Details");
                    alert.setHeaderText(null);
                    alert.setContentText("Course Name: " + course.getName() + "\n"
                            + "Subject: " + course.getSubject() + "\n"
                            + "Introduction Text: " + course.getIntroductionText() + "\n"
                            + "Difficulty Level: " + course.getDifficultyLevel() + "\n"
                            + "Completed Cursist Count: " + completedCursistCount);
                    alert.showAndWait();
                }
            });

        });

        // Handle delete button action
        deleteButton.setOnAction(h -> {
            String selectedCourse = list.getSelectionModel().getSelectedItem();

            if (selectedCourse != null) {
                courseController.deleteCourse(selectedCourse);
                items.remove(selectedCourse);
            }
        });

        Label mainSceneTitle = new Label("Create a new course");
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
        stage.setTitle("Course Management");
        stage.setScene(mainScene);
        stage.show();

        stage.setScene(homeScene);
        stage.setTitle("Course Management");
        stage.show();
        // Create button op homepage
        createButton.setOnAction(j -> {
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

        });

        editButton.setOnAction(k -> {
            BorderPane editPane = new BorderPane();
            Label title = new Label("Choose course to edit");
            BorderPane.setAlignment(title, Pos.TOP_CENTER);
            title.setStyle("-fx-font-size: 30;");
            Button chooseButton = new Button("Edit");
            chooseButton.setPadding(buttonsMenuPadding);
            chooseButton.setStyle("-fx-background-color: #d2b48c;");

            // Use the class-level backHome variable
            HBox buttonsEdit = new HBox(backHome, chooseButton);
            buttonsEdit.setSpacing(15);

            Insets buttonsEditPadding = new Insets(0, 15, 0, 15);
            buttonsEdit.setPadding(buttonsEditPadding);
            ArrayList<String> courseNames = courseController.getAllCourses();

            items.setAll(courseNames);
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
            TextField updateNameField = new TextField();
            updateNameField.setDisable(true);
            TextField updateSubjectField = new TextField();
            TextField updateIntroductionText = new TextField();

            ComboBox<String> updateDifficultyLevel = new ComboBox<>();
            updateDifficultyLevel.getItems().addAll("Select difficulty", "Beginner", "Intermediate", "Expert");
            updateDifficultyLevel.getSelectionModel().selectFirst();

            chooseButton.setOnAction(f -> {
                // check if a course is selected
                if (list.getSelectionModel().getSelectedIndex() == -1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No course selected");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select a course to edit");
                    alert.showAndWait();
                } else {

                    BorderPane editWindow = new BorderPane();
                    Label editWindowTitle = new Label("Edit window");
                    editWindowTitle.setStyle("-fx-font-size: 30;");
                    Button confirmButton = new Button("Confirm");
                    confirmButton.setPadding(buttonsMenuPadding);
                    confirmButton.setStyle("-fx-background-color: #d2b48c;");

                    // make seperate button that goes back to the list of courses
                    Button backButton = new Button("< Back to list");
                    backButton.setPadding(buttonsMenuPadding);
                    backButton.setStyle("-fx-background-color: #d2b48c;");
                    backButton.setOnAction(g -> {
                        stage.setScene(updateScene);
                        stage.show();
                    });

                    HBox editButtons = new HBox(backButton, confirmButton);

                    String selectedCourseName = list.getSelectionModel().getSelectedItem();
                    Course selectedCourse = courseController.getCourseByName(selectedCourseName);

                    // Create labels
                    Label nameLabel = new Label("Name:");
                    Label subjectLabel = new Label("Subject:");
                    Label introductionLabel = new Label("Introduction:");
                    Label difficLabel = new Label("Difficulty Level:");

                    // Set text for each field based on selectedCourse
                    updateNameField.setText(selectedCourse.getName());
                    updateSubjectField.setText(selectedCourse.getSubject());
                    updateIntroductionText.setText(selectedCourse.getIntroductionText());
                    updateDifficultyLevel.setValue(String.valueOf(selectedCourse.getDifficultyLevel()));

                    // Create HBox for each label and corresponding field
                    HBox nameBox = new HBox(nameLabel, updateNameField);
                    HBox subjectBox = new HBox(subjectLabel, updateSubjectField);
                    HBox introductionBox = new HBox(introductionLabel, updateIntroductionText);
                    HBox difficultyLevelBox = new HBox(difficLabel, updateDifficultyLevel);

                    // Set spacing between elements in each HBox
                    nameBox.setSpacing(10);
                    subjectBox.setSpacing(10);
                    introductionBox.setSpacing(10);
                    difficultyLevelBox.setSpacing(10);

                    // Create VBox to hold all HBox elements
                    VBox updateFields = new VBox(nameBox, subjectBox, introductionBox, difficultyLevelBox);

                    // Set spacing between VBox children
                    updateFields.setSpacing(15);

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
                        selectedCourse.setName(updateNameField.getText());
                        selectedCourse.setSubject(updateSubjectField.getText());
                        selectedCourse.setIntroductionText(updateIntroductionText.getText());
                        selectedCourse.setDifficultyLevel((updateDifficultyLevel.getValue()));

                        courseController.updateCourseFields(selectedCourse);

                        // Add alert pop-up that course has been edited
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Course edited");
                        alert.setHeaderText(null);
                        alert.setContentText("Course has been edited successfully!");
                        alert.showAndWait();

                    });
                }
            });

        });

        homeScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
        return homeScene;
    }

    public CourseGUI(courseController courseController) {
    }

    @Override
    public void start(Stage arg0) throws Exception {

    }

}
