package contentItem;

import main.*;

import java.time.LocalDate;
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

public class contentItemGUI extends Application {
    private contentItemController contentItemController;
    private ListView<String> list;
    private ObservableList<String> items;
    private Scene mainScene;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;

    public Scene contentItemScene(Stage stage) {
        // Create a new GUI instance
        GUI gui = new GUI();
        contentItemController = new contentItemController();

        // Create a button to go back to the homepage
        Button backToHomeButton = new Button("< Home");
        backToHomeButton.setStyle("-fx-background-color: #d2b48c;");
        backHome = new Button("< Home");
        backHome.setOnAction(j -> {
            stage.setScene(homeScene);
            stage.show();
        });

        // Create a welcome message for the homepage
        Button createButton = new Button("Create Content");
        Button readButton = new Button("All Content");
        Label welcomeLabel = new Label("Welcome to Content Item management");

        // Add padding to the welcome message
        Insets welcomeLabelPadding = new Insets(25);
        welcomeLabel.setPadding(welcomeLabelPadding);
        welcomeLabel.setStyle("-fx-font-size: 24;");
        Button editButton = new Button("Edit Content");
        backToCodeCademy = new Button("< CodeCademy");

        // Add action to the backToCodeCademy button
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

        // Set the size and style of the buttons
        createButton.setPrefSize(150, 50);
        createButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        readButton.setPrefSize(150, 50);
        readButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");
        editButton.setPrefSize(150, 50);
        editButton.setStyle("-fx-font-size: 18; -fx-background-color: #d2b48c;");

        // Set the layout and scene for the homepage
        homeLayout.setAlignment(Pos.CENTER);
        homePane.setCenter(homeLayout);
        Insets padding = new Insets(100);
        homePane.setPadding(padding);
        homeScene = new Scene(homePane, 800, 600);

        // Create forms for adding content items
        TextField createIdField = new TextField();
        createIdField.setPromptText("Content Item ID");

        DatePicker createDateField = new DatePicker();
        createDateField.setPromptText("Publication Date");

        TextField createStatusField = new TextField();
        createStatusField.setPromptText("Status");

        // Create buttons for adding content items
        Button addButton = new Button("Add Content Item");
        addButton.setStyle("-fx-background-color: #d2b48c;");

        // Add action to the add button
        addButton.setOnAction(f -> {

            // Validate input
            if (createIdField.getText().isBlank()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("ContentItem ID cannot be empty.");
                alert.showAndWait();
                return;
            }
            // Add content item
            int contentItemID = Integer.parseInt(createIdField.getText());
            LocalDate date = createDateField.getValue();
            String status = createStatusField.getText();

            ContentItem contentItem = new ContentItem();
            contentItem.setContentItemID(contentItemID);
            contentItem.setPublicationDate(date);
            contentItem.setStatus(status);

            contentItemController.addContentItem(contentItem);

            // Show alert after adding a contentitem
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Content Item added successfully.");
            alert.showAndWait();

            // Clear the input fields after adding a contentitem
            createIdField.clear();
            createDateField.setValue(null);
            createStatusField.clear();

        });

        VBox createFields = new VBox(createIdField, createDateField, createStatusField, addButton);
        createFields.setSpacing(7);

        // CRUD Buttons are created
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update Content Item");

        // Create layout for buttons
        HBox buttonsMenu = new HBox(addButton, backToHomeButton);

        // Set the size and style of the buttons
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
            // arraylist with all contentItems
            ArrayList contentItems = contentItemController.getAllContentItems();
            Button infoButton = new Button("More Info");
            infoButton.setStyle("-fx-background-color: #d2b48c;");
            infoButton.setPadding(buttonsMenuPadding);

            // Create layout for cursist overview
            Label label = new Label("Content item ID ↓");
            label.setStyle("-fx-font-size: 20;");

            items.setAll(contentItems);
            list.setItems(items);
            list.setStyle("-fx-font-size: 24; -fx-alignment: center;");
            list.setPadding(buttonsMenuPadding);

            VBox centerBox = new VBox(label, list);
            BorderPane cursistPage = new BorderPane();

            cursistPage.setCenter(centerBox);
            BorderPane.setMargin(centerBox, new Insets(25));

            Label cursistPageTitle = new Label("Overview all content");
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

            // Create mainScene
            mainScene = new Scene(cursistPage, 800, 600); // Assign mainScene here

            // Set title and style
            stage.setTitle("Content overview");
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

            // Handle info button action
            infoButton.setOnAction(h -> {
                // Get selected content
                String selectedContent = String.valueOf(list.getSelectionModel().getSelectedItem());
                ContentItem selectedContentItem = contentItemController
                        .getContentItemByID(Integer.valueOf(selectedContent));

                String contentID = String.valueOf(selectedContentItem.getContentItemID());
                String publicationDate = String.valueOf(selectedContentItem.getPublicationDate());
                String status = String.valueOf(selectedContentItem.getStatus());

                // Show alert
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Content Item");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Content ID: " + contentID + "\nPublication Date: " + publicationDate + "\nStatus: " + status);

                alert.showAndWait();

            });

        });

        // Handle delete button action
        deleteButton.setOnAction(h -> {
            // Get selected content
            int selectedContent = list.getSelectionModel().getSelectedIndex();

            // Delete selected content
            if (selectedContent != -1) {
                contentItemController.deleteContentItem(selectedContent);
                items.remove(selectedContent);
            }
        });

        // Create mainScene
        Label mainSceneTitle = new Label("Create new content item");
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

        // Create mainScene
        Scene mainScene = new Scene(mainPane, 800, 600);
        stage.setTitle("Content Management");
        stage.setScene(mainScene);
        stage.show();

        // Create a button to go back to the homepage
        createButton.setOnAction(j -> {
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

        });

        // Handle edit button action
        editButton.setOnAction(k -> {
            // Create layout for the homepage
            BorderPane editPane = new BorderPane();
            Label title = new Label("Choose content item to edit");
            BorderPane.setAlignment(title, Pos.TOP_CENTER);
            title.setStyle("-fx-font-size: 30;");
            Button chooseButton = new Button("Edit");
            chooseButton.setPadding(buttonsMenuPadding);
            chooseButton.setStyle("-fx-background-color: #d2b48c;");

            // Use the class-level backHome variable
            HBox buttonsEdit = new HBox(chooseButton, backHome);
            buttonsEdit.setSpacing(15);

            // Create list
            Insets buttonsEditPadding = new Insets(0, 15, 0, 15);
            buttonsEdit.setPadding(buttonsEditPadding);
            ArrayList contentItems = contentItemController.getAllContentItems();

            items.setAll(contentItems);
            list.setItems(items);

            editPane.setBottom(buttonsEdit);
            buttonsEdit.setAlignment(Pos.CENTER);
            buttonsEdit.setAlignment(Pos.CENTER);
            BorderPane.setMargin(buttonsEdit, new Insets(0, 0, 25, 0));
            editPane.setTop(title);
            editPane.setCenter(list);
            list.setStyle("-fx-font-size: 24; -fx-alignment: center;");
            BorderPane.setMargin(list, new Insets(25));

            // Create mainScene
            Scene updateScene = new Scene(editPane, 800, 600);
            updateScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(updateScene);
            stage.show();
            TextField updateContentID = new TextField();
            DatePicker updateDate = new DatePicker();
            TextField updateStatus = new TextField();

            // Handle edit button action
            chooseButton.setOnAction(f -> {
                // Create layout for the homepage
                BorderPane editWindow = new BorderPane();
                Label editWindowTitle = new Label("Edit window");
                editWindowTitle.setStyle("-fx-font-size: 30;");
                Button confirmButton = new Button("Confirm");
                confirmButton.setPadding(buttonsMenuPadding);
                confirmButton.setStyle("-fx-background-color: #d2b48c;");

                HBox editButtons = new HBox(backHome, confirmButton);

                String selectedContentItem = list.getSelectionModel().getSelectedItem();
                ContentItem selectedContent = contentItemController
                        .getContentItemByID(Integer.parseInt(selectedContentItem));

                updateContentID.setText(String.valueOf(selectedContent.getContentItemID()));
                updateDate.setValue(selectedContent.getPublicationDate());
                updateStatus.setText(selectedContent.getStatus());

                VBox updateFields = new VBox(updateContentID, updateDate, updateStatus);
                updateFields.setSpacing(7);

                editWindow.setTop(editWindowTitle);
                BorderPane.setAlignment(editWindowTitle, Pos.CENTER);
                editWindow.setCenter(updateFields);
                editWindow.setBottom(editButtons);
                editButtons.setAlignment(Pos.CENTER);
                editButtons.setSpacing(15);
                BorderPane.setMargin(editButtons, new Insets(0, 0, 25, 0));
                BorderPane.setMargin(updateFields, new Insets(25));

                // Create mainScene
                Scene confirmEdit = new Scene(editWindow, 800, 600);
                confirmEdit.getRoot().setStyle("-fx-background-color: #f5f5dc;");
                stage.setScene(confirmEdit);
                stage.show();

                // Handle confirm button action
                confirmButton.setOnAction(g -> {
                    // Update content
                    selectedContent.setContentItemID(Integer.parseInt(updateContentID.getText()));
                    selectedContent.setPublicationDate(updateDate.getValue());
                    selectedContent.setStatus(updateStatus.getText());

                    contentItemController.updateContentItemFields(selectedContent);

                    // Add alert pop-up that content has been edited
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Content edited");
                    alert.setHeaderText(null);
                    alert.setContentText("Content has been edited successfully!");
                    alert.showAndWait();

                });
            });
        });

        homeScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
        return homeScene;
    }

    // Constructor
    public contentItemGUI(contentItemController contentItemController) {
        this.contentItemController = contentItemController;
    }

    // Main method
    @Override
    public void start(Stage arg0) throws Exception {

    }

}
