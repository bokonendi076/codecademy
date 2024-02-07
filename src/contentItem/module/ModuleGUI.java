package module;

import java.util.ArrayList;

import contentItem.*;
import module.ModuleController;
import course.courseController;
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
import contentItem.contentItemController;

public class ModuleGUI extends contentItem.ContentItem {
    // private ModuleController moduleController;
    // private ListView<String> list;
    // private ObservableList<String> items;
    // private Scene mainScene;
    // private Scene homeScene;
    // private Button backHome;
    // private Button backToCodeCademy;
    // private BorderPane homePane;

    // public scene to get the cursist scene
    // public Scene moduleScene(Stage stage) {
    // moduleController = new ModuleController();
    // GUI gui = new GUI();

    // Button backToHomeButton = new Button("< Home");
    // backHome = new Button("< Home");
    // backHome.setOnAction(k -> {
    // stage.setScene(homeScene);
    // stage.show();
    // });

    // // Create a welcome message for the homepage
    // Button createButton = new Button("Create Module");
    // Button readButton = new Button("All Modules");
    // Label welcomeLabel = new Label("Welcome to Module Manager");
    // Insets welcomeLabelPadding = new Insets(
    // 25);
    // welcomeLabel.setPadding(welcomeLabelPadding);
    // welcomeLabel.setStyle("-fx-font-size: 24;");
    // Button editButton = new Button("Edit Module");
    // backToCodeCademy = new Button("< CodeCademy");
    // backToCodeCademy.setOnAction(l -> {
    // stage.setScene(gui.getHomeScene(stage));
    // stage.show();
    // });

    // // Create layout for the homepage
    // homePane = new BorderPane();
    // BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
    // homePane.setTop(welcomeLabel);
    // VBox homeLayout = new VBox(10, createButton, readButton, editButton,
    // backToCodeCademy);

    // createButton.setPrefSize(150, 50);
    // createButton.setStyle("-fx-font-size: 18;");
    // readButton.setPrefSize(150, 50);
    // readButton.setStyle("-fx-font-size: 18;");
    // editButton.setPrefSize(150, 50);
    // editButton.setStyle("-fx-font-size: 18;");
    // backToCodeCademy.setPrefSize(150, 50);
    // backToCodeCademy.setStyle("-fx-font-size: 18;");

    // homeLayout.setAlignment(Pos.CENTER);
    // homePane.setCenter(homeLayout);
    // Insets padding = new Insets(100);
    // homePane.setPadding(padding);
    // homeScene = new Scene(homePane, 800, 600);

    // // CRUD (read) functionality...
    // readButton.setOnAction(g -> {
    // items = FXCollections.observableArrayList();
    // list = new ListView<>();
    // // arraylist with all course names
    // ArrayList<String> moduleNames = moduleController.getAllModules();

    // items.setAll(moduleNames);
    // list.setItems(items);
    // list.setStyle("-fx-font-size: 24; -fx-alignment: center;");
    // // TODO: fix list padding
    // // list.setPadding(buttonsMenuPadding);

    // BorderPane cursistPage = new BorderPane();

    // cursistPage.setCenter(list);
    // BorderPane.setMargin(list, new Insets(25));

    // Label ModulePageTitle = new Label("Overview all courses");
    // ModulePageTitle.setStyle("-fx-font-size: 30;");
    // BorderPane.setAlignment(ModulePageTitle, Pos.CENTER);
    // cursistPage.setTop(ModulePageTitle);

    // HBox cursistPageButtons = new HBox(backHome);
    // cursistPageButtons.setSpacing(10);
    // Insets cursistPageButtonsPadding = new Insets(0, 15, 0, 15);
    // cursistPageButtons.setPadding(cursistPageButtonsPadding);
    // cursistPageButtons.setAlignment(Pos.CENTER);
    // BorderPane.setMargin(cursistPageButtons, new Insets(0, 0, 25, 0));
    // cursistPage.setBottom(cursistPageButtons);

    // mainScene = new Scene(cursistPage, 800, 600); // Assign mainScene here

    // stage.setTitle("Course overview");
    // stage.setScene(mainScene);
    // stage.show();

    // });

    // return homeScene;
    // }

    private ModuleController ModuleController;
    private contentItemController contentItemController;
    private ListView<String> list;
    private ObservableList<String> items;
    private Scene mainScene;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;

    public Scene ModuleScene(Stage stage) {
        GUI gui = new GUI();
        ModuleController = new ModuleController();

        Button backToHomeButton = new Button("< Home");
        backToHomeButton.setStyle("-fx-background-color: #d2b48c;");
        backHome = new Button("< Home");
        backHome.setOnAction(j -> {
            stage.setScene(homeScene);
            stage.show();
        });

        // Create a welcome message for the homepage
        Button createButton = new Button("Create Module");
        Button readButton = new Button("All Modules");
        Label welcomeLabel = new Label("Welcome to Module management");

        Insets welcomeLabelPadding = new Insets(25);
        welcomeLabel.setPadding(welcomeLabelPadding);
        welcomeLabel.setStyle("-fx-font-size: 24;");
        Button editButton = new Button("Edit Module");
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

        TextField TitleField = new TextField();
        TitleField.setPromptText("Title");

        TextField VersionField = new TextField();
        VersionField.setPromptText("Version");

        TextField contactPersonNameField = new TextField();
        contactPersonNameField.setPromptText("Contact person name");

        TextField contactPersonEmailField = new TextField();
        contactPersonEmailField.setPromptText("contact person email");

        TextField followNumberField = new TextField();
        followNumberField.setPromptText("follow number");

        TextField moduleIdField = new TextField();
        moduleIdField.setPromptText("Module ID");

        contentItemController = new contentItemController();
        ArrayList<Integer> contentItemIDs = contentItemController.getAllContentItems();
        ObservableList<Integer> contentItemIDList = FXCollections.observableArrayList(contentItemIDs);
        ComboBox<Integer> comboBox = new ComboBox(contentItemIDList);
        comboBox.setPromptText("Select a content item id");

        Button addButton = new Button("Add Module");
        addButton.setStyle("-fx-background-color: #d2b48c;");

        addButton.setOnAction(f -> {

            if (TitleField.getText().isBlank()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                return;
            }

            String title = TitleField.getText();
            String version = VersionField.getText();
            String contactPersonName = contactPersonNameField.getText();
            String contactPersonEmail = contactPersonEmailField.getText();
            int followNumber = Integer.valueOf(followNumberField.getText());
            int moduleId = Integer.valueOf(moduleIdField.getText());
            int contentItemId = (int) comboBox.getSelectionModel().getSelectedItem();

            Module newModule = new Module();
            newModule.setTitle(title);
            newModule.setVersion(version);
            newModule.setContactPersonName(contactPersonName);
            newModule.setContactPersonEmail(contactPersonEmail);
            newModule.setFollowNumber(followNumber);
            newModule.setModuleID(moduleId);
            newModule.setContentItemID(contentItemId);

            ModuleController.saveModule(newModule);

            // Clear the input fields after adding a Module
            TitleField.clear();
            VersionField.clear();
            contactPersonNameField.clear();
            contactPersonEmailField.clear();
            followNumberField.clear();
            moduleIdField.clear();
            comboBox.getSelectionModel().clearSelection();
        });

        VBox createFields = new VBox(TitleField, VersionField, contactPersonNameField,
                contactPersonEmailField, followNumberField, moduleIdField, comboBox, addButton);
        createFields.setSpacing(7);

        // CRUD Buttons are created
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update Module");

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
            // arraylist with all Module names
            ArrayList<String> ModuleNames = ModuleController.getAllModules();
            Button infoButton = new Button("More Info");
            infoButton.setStyle("-fx-background-color: #d2b48c;");
            infoButton.setPadding(buttonsMenuPadding);

            Label label = new Label("Module ID ↓");
            label.setStyle("-fx-font-size: 20;");

            items.setAll(ModuleNames);
            list.setItems(items);
            list.setStyle("-fx-font-size: 24; -fx-alignment: center;");
            list.setPadding(buttonsMenuPadding);

            VBox centerBox = new VBox(label, list);
            BorderPane cursistPage = new BorderPane();

            cursistPage.setCenter(centerBox);
            BorderPane.setMargin(centerBox, new Insets(25));

            Label cursistPageTitle = new Label("Overview all Modules");
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

            stage.setTitle("Module overview");
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

            infoButton.setOnAction(j -> {
                String selectedModule = list.getSelectionModel().getSelectedItem();
                Module module = ModuleController.getModuleByTitle(Integer.valueOf(selectedModule));

                String title = module.getTitle();
                String version = module.getVersion();
                String contactPersonName = module.getContactPersonName();
                String contactPersonEmail = module.getContactPersonEmail();
                int followNumber = module.getFollowNumber();
                int contentItemID = module.getContentItemID();
                int moduleID = module.getModuleID();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Module");
                alert.setHeaderText(null);
                alert.setContentText("Title: " + title + "\n" + "Version: " + version + "\n"
                        + "ContactPersonName: " + contactPersonName + "\n" + "ContactPersonEmail: "
                        + contactPersonEmail + "\n" + "FollowNumber: " + followNumber + "\n"
                        + "ContentItemID: " + contentItemID + "\n" + "ModuleID: " + moduleID + "\n");

                alert.showAndWait();
            });

        });

        // Handle delete button action
        deleteButton.setOnAction(h -> {
            String selectedModule = list.getSelectionModel().getSelectedItem();

            if (selectedModule != null) {
                ModuleController.deleteModule(selectedModule);
                items.remove(selectedModule);
            }
        });

        Label mainSceneTitle = new Label("Create a new Module");
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
        stage.setTitle("Module Management");
        stage.setScene(mainScene);
        stage.show();

        stage.setScene(homeScene);
        stage.setTitle("Module Management");
        stage.show();

        // Create button op homepage
        createButton.setOnAction(j -> {
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

        });

        editButton.setOnAction(k -> {
            BorderPane editPane = new BorderPane();
            Label title = new Label("Choose Module to edit");
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
            ArrayList<String> ModuleNames = ModuleController.getAllModules();

            items.setAll(ModuleNames);
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
            TextField updateTitleField = new TextField();
            updateTitleField.setDisable(true);
            TextField updateVerionField = new TextField();
            TextField updateContactPersonName = new TextField();
            TextField updateContactPersonEmail = new TextField();
            TextField updateFollowNumber = new TextField();

            chooseButton.setOnAction(f -> {
                BorderPane editWindow = new BorderPane();
                Label editWindowTitle = new Label("Edit window");
                editWindowTitle.setStyle("-fx-font-size: 30;");
                Button confirmButton = new Button("Confirm");
                confirmButton.setPadding(buttonsMenuPadding);
                confirmButton.setStyle("-fx-background-color: #d2b48c;");

                HBox editButtons = new HBox(backHome, confirmButton);

                String selectedModuleName = list.getSelectionModel().getSelectedItem();
                Module selectedModule = ModuleController.getModuleByTitle(Integer.valueOf(selectedModuleName));

                updateTitleField.setText(selectedModule.getTitle());
                updateVerionField.setText(selectedModule.getVersion());
                updateContactPersonName.setText(selectedModule.getContactPersonName());
                updateContactPersonEmail
                        .setText(String.valueOf(selectedModule.getContactPersonEmail()));
                updateFollowNumber.setText(String.valueOf(selectedModule.getFollowNumber()));

                VBox updateFields = new VBox(updateTitleField, updateVerionField,
                        updateContactPersonName,
                        updateContactPersonEmail, updateFollowNumber);
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
                    selectedModule.setTitle(updateTitleField.getText());
                    selectedModule.setVersion(updateVerionField.getText());
                    selectedModule.setContactPersonName(updateContactPersonName.getText());
                    selectedModule.setContactPersonEmail(updateContactPersonEmail.getText());
                    selectedModule.setFollowNumber(Integer.parseInt(updateFollowNumber.getText()));

                    ModuleController.updateModuleFields(selectedModule);

                    // Add alert pop-up that Module has been edited
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Module edited");
                    alert.setHeaderText(null);
                    alert.setContentText("Module has been edited successfully!");
                    alert.showAndWait();

                });
            });
        });

        homeScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
        return homeScene;
    }

    public ModuleGUI(ModuleController moduleController) {
    }
}
