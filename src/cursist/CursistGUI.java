package cursist;

import DatabaseManager.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import main.GUI;

public class CursistGUI extends Application {
    private DatabaseManager db;
    private Connection connection;
    private CursistController cursistController;
    private ListView<String> list;
    private ObservableList<String> items;
    private Scene mainScene;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;
    private BorderPane homePane;

    // Constructor
    public CursistGUI(CursistController cursistController) {
        this.cursistController = cursistController;
    }

    GUI gui = new GUI();

    // public scene to get the cursist scene
    public Scene cursistScene(Stage stage) {
        db = new DatabaseManager();
        connection = db.getConnection();
        cursistController = new CursistController();

        Button backToHomeButton = new Button("< Home");
        backToHomeButton.setStyle("-fx-background-color: #d2b48c;");
        backHome = new Button("< Home");
        backHome.setStyle("-fx-background-color: #d2b48c;");
        backHome.setOnAction(k -> {
            stage.setScene(homeScene);
            stage.show();
        });

        // Create a welcome message for the homepage
        Button createButton = new Button("Create Cursist");
        Button readButton = new Button("All Cursists");
        Label welcomeLabel = new Label("Welcome to Cursist Manager");
        Insets welcomeLabelPadding = new Insets(
                25);
        welcomeLabel.setPadding(welcomeLabelPadding);
        welcomeLabel.setStyle("-fx-font-size: 24;");
        Button editButton = new Button("Edit Cursist");
        backToCodeCademy = new Button("< CodeCademy");

        backToCodeCademy.setOnAction(l -> {
            stage.setScene(gui.getHomeScene(stage));
            stage.show();
        });

        // Create layout for the homepage
        homePane = new BorderPane();
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
        homePane.setTop(welcomeLabel);
        VBox homeLayout = new VBox(10, createButton, readButton, editButton, backToCodeCademy);

        createButton.setPrefSize(150, 50);
        createButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        readButton.setPrefSize(150, 50);
        readButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        editButton.setPrefSize(150, 50);
        editButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        backToCodeCademy.setPrefSize(150, 50);
        backToCodeCademy.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");

        homeLayout.setAlignment(Pos.CENTER);
        homePane.setCenter(homeLayout);
        Insets padding = new Insets(100);
        homePane.setPadding(padding);
        homeScene = new Scene(homePane, 800, 600);

        TextField createNaamField = new TextField();
        createNaamField.setPromptText("Name");

        TextField createEmailField = new TextField();
        createEmailField.setPromptText("Email");

        TextField createBirthDateField = new TextField();
        createBirthDateField.setPromptText("Birthdate");

        ChoiceBox<String> genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.getItems().addAll("Select gender", "Male", "Female", "Other");
        genderChoiceBox.getSelectionModel().selectFirst();

        TextField createAddressField = new TextField();
        createAddressField.setPromptText("Address");

        TextField createCityField = new TextField();
        createCityField.setPromptText("City");

        TextField createCountryField = new TextField();
        createCountryField.setPromptText("Country");

        Button addButton = new Button("Add Cursist ");
        addButton.setStyle("-fx-background-color: #d2b48c;");

        addButton.setOnAction(f -> {
            try {
                String naam = createNaamField.getText();
                String email = createEmailField.getText();
                String birthDateText = createBirthDateField.getText();

                if (email.isEmpty()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Email cannot be empty.");
                    alert.showAndWait();
                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate birthDate = LocalDate.parse(birthDateText, formatter);

                String gender = genderChoiceBox.getValue(); // Get the selected gender
                String address = createAddressField.getText();
                String city = createCityField.getText();
                String country = createCountryField.getText();

                Cursist nieuweCursist = new Cursist();
                nieuweCursist.setName(naam);
                nieuweCursist.setEmailAddress(email);
                nieuweCursist.setBirthDate(birthDate);
                nieuweCursist.setSex(gender);
                nieuweCursist.setAddress(address);
                nieuweCursist.setCity(city);
                nieuweCursist.setCountry(country);

                cursistController.saveCursist(nieuweCursist);

                // Add alert pop-up that cursist has been added
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cursist added");
                alert.setHeaderText(null);
                alert.setContentText("Cursist added successfully.");
                alert.showAndWait();

                // Clear the input fields after adding a cursist
                createNaamField.clear();
                createEmailField.clear();
                createBirthDateField.clear();
                genderChoiceBox.getSelectionModel().selectFirst();
                createAddressField.clear();
                createCityField.clear();
                createCountryField.clear();

            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add cursist. Please check birthdate input");
                alert.showAndWait();
                e.printStackTrace();
            }

        });

        VBox createFields = new VBox(createNaamField, createEmailField, createBirthDateField, genderChoiceBox,
                createAddressField,
                createCityField, createCountryField);
        createFields.setSpacing(7);

        // CRUD Buttons are created
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update Cursist");

        // Create layout for the homepage
        HBox buttonsMenu = new HBox(addButton, backToHomeButton);

        Insets buttonsMenuPadding = new Insets(
                10);
        addButton.setPadding(buttonsMenuPadding);
        deleteButton.setPadding(buttonsMenuPadding);
        deleteButton.setStyle("-fx-background-color: #d2b48c;");
        updateButton.setPadding(buttonsMenuPadding);
        updateButton.setStyle("-fx-background-color: #d2b48c;");
        backHome.setPadding(buttonsMenuPadding);

        buttonsMenu.setAlignment(Pos.CENTER);
        buttonsMenu.setSpacing(5);

        VBox vboxesCombined = new VBox(createFields, buttonsMenu);
        vboxesCombined.setSpacing(10);

        // Main pane is created
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(vboxesCombined);

        // Initialize list and items
        list = new ListView<>();
        items = FXCollections.observableArrayList();

        // CRUD (read) functionality...
        readButton.setOnAction(e -> {
            // arraylist of cursist names
            ArrayList<String> cursistNames = cursistController.getAllCursist();

            items.setAll(cursistNames);
            list.setItems(items);
            list.setStyle("-fx-font-size: 24; -fx-alignment: center;");

            BorderPane cursistPage = new BorderPane();

            cursistPage.setCenter(list);
            BorderPane.setMargin(list, new Insets(25));

            Label cursistPageTitle = new Label("Overview all cursists");
            cursistPageTitle.setStyle("-fx-font-size: 30;");
            BorderPane.setAlignment(cursistPageTitle, Pos.CENTER);
            cursistPage.setTop(cursistPageTitle);

            // Create a button voor info
            Button infoButton = new Button("More Info");
            // style the button
            infoButton.setStyle("-fx-background-color: #d2b48c;");
            infoButton.setPadding(buttonsMenuPadding);

            HBox cursistPageButtons = new HBox(deleteButton, backHome, infoButton);
            cursistPageButtons.setSpacing(10);
            Insets cursistPageButtonsPadding = new Insets(0, 15, 0, 15);
            cursistPageButtons.setPadding(cursistPageButtonsPadding);
            cursistPage.setBottom(cursistPageButtons);
            cursistPageButtons.setAlignment(Pos.CENTER);
            BorderPane.setMargin(cursistPageButtons, new Insets(0, 0, 25, 0));

            mainScene = new Scene(cursistPage, 800, 600); // Assign mainScene here
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

            // Handle info button action
            infoButton.setOnAction(f -> {
                String selectedCursist = list.getSelectionModel().getSelectedItem();
                Cursist selectedCursistForInfo = cursistController.getCursistByName(selectedCursist);

                Label cursistInfoTitle = new Label("Cursist info:");
                cursistInfoTitle.setStyle(
                        "-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #d2b48c; -fx-alignment: center; -fx-background-color: #f5f5dc; -fx-padding: 10px;");

                Label cursistInfoName = new Label("Name: " + selectedCursistForInfo.getName());
                Label cursistInfoEmail = new Label("Email: " + selectedCursistForInfo.getEmailAddress());
                Label cursistInfoBirthdate = new Label("Birthdate: " + selectedCursistForInfo.getBirthDate());
                Label cursistInfoGender = new Label("Gender: " + selectedCursistForInfo.getSex());
                Label cursistInfoAddress = new Label("Address: " + selectedCursistForInfo.getAddress());
                Label cursistInfoCity = new Label("City: " + selectedCursistForInfo.getCity());
                Label cursistInfoCountry = new Label("Country: " + selectedCursistForInfo.getCountry());
                Label cursistID = new Label(
                        "Cursist ID: " + cursistController.getCursistID(selectedCursistForInfo.getEmailAddress()));

                // Create VBox for info
                VBox vboxInfoTitle = new VBox(cursistInfoTitle);
                vboxInfoTitle.setStyle(
                        "-fx-font-size: 24; -fx-alignment: center; -fx-padding: 10px;");

                VBox vboxInfo = new VBox(cursistInfoName, cursistInfoEmail, cursistInfoBirthdate,
                        cursistInfoGender,
                        cursistInfoAddress, cursistInfoCity, cursistInfoCountry, cursistID, backHome);
                vboxInfo.setStyle(
                        "-fx-font-size: 24; -fx-alignment: center; -fx-padding: 10px; -fx-border-color: #d2b48c; -fx-border-width: 2px;");

                VBox vboxInfoBackhome = new VBox(backHome);
                vboxInfoBackhome.setStyle(
                        "-fx-font-size: 24; -fx-alignment: center; -fx-padding: 10px;");

                BorderPane infoCursistPane = new BorderPane();
                infoCursistPane.setTop(vboxInfoTitle);
                infoCursistPane.setCenter(vboxInfo);
                infoCursistPane.setBottom(vboxInfoBackhome);

                Scene infoCursistScene = new Scene(infoCursistPane, 800, 600);
                infoCursistScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                stage.setScene(infoCursistScene);
                stage.show();
            });

        });

        // Handle delete button action
        deleteButton.setOnAction(e -> {
            String selectedCursist = list.getSelectionModel().getSelectedItem();

            if (selectedCursist != null) {
                cursistController.deleteCursist(selectedCursist);
                items.remove(selectedCursist);
            }
        });

        Label mainSceneTitle = new Label("Create a new cursist");
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

        backToHomeButton.setOnAction(e -> {
            stage.setScene(homeScene);
            stage.show();
        });

        Scene mainScene = new Scene(mainPane, 800, 600);
        stage.setScene(mainScene);
        stage.show();

        stage.setScene(homeScene);
        stage.show();

        // Create button on homepage
        createButton.setOnAction(e -> {
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

        });

        editButton.setOnAction(e -> {
            BorderPane editPane = new BorderPane();
            Label title = new Label("Choose cursist to edit");
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
            ArrayList<String> cursistNames = cursistController.getAllCursist();

            items.setAll(cursistNames);
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
                genderChoiceBox.getSelectionModel().selectFirst();
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
