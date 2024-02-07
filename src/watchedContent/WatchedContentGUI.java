package watchedContent;

import main.*;
import cursist.*;
import java.time.LocalDate;
import java.util.ArrayList;

import contentItem.*;
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

public class WatchedContentGUI extends Application {
    private WatchedContentController watchedContentController;
    private ListView<String> list;
    private ObservableList<String> items;
    private Scene mainScene;
    private Scene homeScene;
    private Button backHome;
    private Button backToCodeCademy;
    private contentItem.contentItemController contentItemController;
    private cursist.CursistController cursistController;

    public Scene watchedContentScene(Stage stage) {
        GUI gui = new GUI();
        watchedContentController = new WatchedContentController();

        Button backToHomeButton = new Button("< Home");
        backToHomeButton.setStyle("-fx-background-color: #d2b48c;");
        backHome = new Button("< Home");
        backHome.setOnAction(j -> {
            stage.setScene(homeScene);
            stage.show();
        });

        // Create a welcome message for the homepage
        Button createButton = new Button("Add % watched");
        Button readButton = new Button("View all % watched");
        Label welcomeLabel = new Label("Welcome to Watched Content management");

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

        // Create layout for the watched content creation

        contentItemController = new contentItem.contentItemController();
        ArrayList<Integer> contentItems = contentItemController.getAllContentItems();
        ObservableList<Integer> contentItemsList = FXCollections.observableArrayList(contentItems);
        ComboBox contentItemIDBox = new ComboBox<>(contentItemsList);
        contentItemIDBox.setPromptText("Choose contentitemId");

        cursistController = new CursistController();
        ArrayList<Integer> cursists = cursistController.getAllCursistIDs();
        ObservableList<Integer> cursistsList = FXCollections.observableArrayList(cursists);
        ComboBox cursistIDBox = new ComboBox<>(cursistsList);
        cursistIDBox.setPromptText("Choose cursistId");

        Label percentageSpinnerLabel = new Label("%");
        percentageSpinnerLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        Spinner<Integer> spinner = new Spinner<>();
        // Set the value factory to restrict values between 0 and 100
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 100, 0);
        spinner.setValueFactory(valueFactory);

        Button addButton = new Button("Add Content Item");
        addButton.setStyle("-fx-background-color: #d2b48c;");

        addButton.setOnAction(f -> {

            int contentItemID = Integer.parseInt(contentItemIDBox.getValue().toString());
            int cursistID = Integer.parseInt(cursistIDBox.getValue().toString());
            double percentageWatched = spinner.getValue();

            WatchedContent watchedContent = new WatchedContent();
            watchedContent.setContentItemID(contentItemID);
            watchedContent.setCursistID(cursistID);
            watchedContent.setPercentageWatched(percentageWatched);

            watchedContentController.addPercentage(contentItemID, cursistID, percentageWatched);

            // Show alert after adding a contentitem
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Watched % added successfully.");
            alert.showAndWait();

            // Clear the input fields after adding a contentitem
            contentItemIDBox.setValue("");
            cursistIDBox.setValue("");
            spinner.getValueFactory().setValue(0);

        });

        HBox spinnerBox = new HBox(spinner, percentageSpinnerLabel);

        VBox createFields = new VBox(contentItemIDBox, cursistIDBox, spinnerBox, addButton);
        createFields.setSpacing(7);

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
            ArrayList<String> watchedContent = watchedContentController.getAllWatchedContent();
            Button infoButton = new Button("More Info");
            infoButton.setStyle("-fx-background-color: #d2b48c;");
            infoButton.setPadding(buttonsMenuPadding);

            Label label = new Label("Content item ID ↓");
            label.setStyle("-fx-font-size: 20;");

            items.setAll(watchedContent);
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

            mainScene = new Scene(cursistPage, 800, 600); // Assign mainScene here

            stage.setTitle("Content overview");
            mainScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");
            stage.setScene(mainScene);
            stage.show();

            infoButton.setOnAction(h -> {
                String selectedContent = String.valueOf(list.getSelectionModel().getSelectedItem());
                WatchedContent rawContent = watchedContentController
                        .getWatchedContentById(Integer.valueOf(selectedContent));
                WatchedContent finalContent = watchedContentController.getWatchedContent(rawContent.getContentItemID(),
                        rawContent.getCursistID());

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Content");
                alert.setHeaderText(null);
                alert.setContentText("ContentItemID: " + finalContent.getContentItemID() + "\nCursistID: "
                        + finalContent.getCursistID() + "\nPercentageWatched: " + finalContent.getPercentageWatched()
                        + "%" + "\n");

                alert.showAndWait();

            });

        });

        // Handle delete button action
        deleteButton.setOnAction(h -> {
            int selectedContent = list.getSelectionModel().getSelectedIndex();

            if (selectedContent != -1) {
                watchedContentController.deleteWatchedContent(selectedContent);
                items.remove(selectedContent);
            }
        });

        Label mainSceneTitle = new Label("Add new watched item");
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
        stage.setTitle("Watched Content Management");
        stage.setScene(mainScene);
        stage.show();

        stage.setScene(homeScene);
        stage.setTitle("Watched Content Management");
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

    public WatchedContentGUI(WatchedContentController watchedContentController) {
        this.watchedContentController = watchedContentController;
    }

    @Override
    public void start(Stage arg0) throws Exception {

    }

}
