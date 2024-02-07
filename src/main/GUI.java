package main;

import main.OverViewGUI;
import course.*;
import cursist.*;
import contentItem.*;
import watchedContent.*;
import webcast.Webcast;
import webcast.WebcastGUI;
import enrollment.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import module.ModuleController;
import module.ModuleGUI;

public class GUI extends Application {
        private WatchedContentController watchedContentController;
        private contentItemController contentItemController;
        private courseController courseController;
        private ModuleController moduleController;
        private Scene codeCademyHomeScene;
        private Button cursistManagementButton;
        private Button courseManagementButton;
        private CursistController cursistController;
        private EnrollmentController enrollmentController;

        @Override
        public void start(Stage stage) {
                // Start the home scene
                stage.setScene(getHomeScene(stage));
                stage.show();

        };

        // public scene to get the codecademy home scene
        public Scene getHomeScene(Stage stage) {
                BorderPane codeCademyPane = new BorderPane();
                Label codeCademyTitle = new Label("CodeCademy");
                Label welcomeMessage = new Label("Welcome to CodeCademy!" + "\n" + "What would you like to do?" + "\n" +
                                "Please select an option from the menu.");

                // All buttons
                cursistManagementButton = new Button("Cursist manager");
                courseManagementButton = new Button("Course manager");
                Button webcastManagementButton = new Button("Webcast manager");
                Button contentItemManagementButton = new Button("Content item manager");
                Button watchedContentManagementButton = new Button("Watched content");
                Button moduleManagementButton = new Button("Module manager");
                Button enrollmentManagementButton = new Button("Enrollment manager");
                Button certificateManagementButton = new Button("Certificate manager");
                Button overViewButton = new Button("Overview options");

                certificateManagementButton.setDisable(true);

                cursistManagementButton.setOnMouseEntered(event -> cursistManagementButton.setCursor(Cursor.HAND));
                cursistManagementButton.setOnMouseExited(event -> cursistManagementButton.setCursor(Cursor.DEFAULT));
                courseManagementButton.setOnMouseEntered(event -> courseManagementButton.setCursor(Cursor.HAND));
                courseManagementButton.setOnMouseExited(event -> courseManagementButton.setCursor(Cursor.DEFAULT));
                webcastManagementButton.setOnMouseEntered(event -> webcastManagementButton.setCursor(Cursor.HAND));
                webcastManagementButton.setOnMouseExited(event -> webcastManagementButton.setCursor(Cursor.DEFAULT));
                contentItemManagementButton
                                .setOnMouseEntered(event -> contentItemManagementButton.setCursor(Cursor.HAND));
                contentItemManagementButton
                                .setOnMouseExited(event -> contentItemManagementButton.setCursor(Cursor.DEFAULT));
                watchedContentManagementButton
                                .setOnMouseEntered(event -> watchedContentManagementButton.setCursor(Cursor.HAND));
                watchedContentManagementButton
                                .setOnMouseExited(event -> watchedContentManagementButton.setCursor(Cursor.DEFAULT));
                moduleManagementButton.setOnMouseEntered(event -> moduleManagementButton.setCursor(Cursor.HAND));
                moduleManagementButton.setOnMouseExited(event -> moduleManagementButton.setCursor(Cursor.DEFAULT));
                enrollmentManagementButton
                                .setOnMouseEntered(event -> enrollmentManagementButton.setCursor(Cursor.HAND));
                enrollmentManagementButton
                                .setOnMouseExited(event -> enrollmentManagementButton.setCursor(Cursor.DEFAULT));
                certificateManagementButton
                                .setOnMouseEntered(event -> certificateManagementButton.setCursor(Cursor.HAND));
                certificateManagementButton
                                .setOnMouseExited(event -> certificateManagementButton.setCursor(Cursor.DEFAULT));

                overViewButton.setOnMouseEntered(event -> overViewButton.setCursor(Cursor.HAND));
                overViewButton.setOnMouseExited(event -> overViewButton.setCursor(Cursor.DEFAULT));

                cursistManagementButton.setPrefSize(150, 50);
                cursistManagementButton.setStyle(
                                "-fx-font-size: 10;  -fx-background-radius: 5;");
                courseManagementButton.setPrefSize(150, 50);
                courseManagementButton.setStyle(
                                "-fx-font-size: 10 ; -fx-background-radius: 5;");
                webcastManagementButton.setPrefSize(150, 50);
                webcastManagementButton.setStyle(
                                "-fx-font-size: 10; -fx-background-radius: 5;");
                contentItemManagementButton.setPrefSize(150, 50);
                contentItemManagementButton.setStyle(
                                "-fx-font-size: 10; -fx-background-radius: 5;");
                watchedContentManagementButton.setPrefSize(150, 50);
                watchedContentManagementButton.setStyle(
                                "-fx-font-size: 10; -fx-background-radius: 5;");
                moduleManagementButton.setPrefSize(150, 50);
                moduleManagementButton.setStyle(
                                "-fx-font-size: 10; -fx-background-radius: 5;");
                enrollmentManagementButton.setPrefSize(150, 50);
                enrollmentManagementButton.setStyle(
                                "-fx-font-size: 10; -fx-background-radius: 5;");
                certificateManagementButton.setPrefSize(150, 50);
                certificateManagementButton.setStyle(
                                "-fx-font-size: 10; -fx-background-radius: 5;");

                overViewButton.setPrefSize(150, 50);
                overViewButton.setStyle(
                                "-fx-font-size: 10; -fx-background-radius: 5;");

                // All buttons in box
                VBox leftButtonBox = new VBox(cursistManagementButton, courseManagementButton, webcastManagementButton,
                                contentItemManagementButton, overViewButton);
                VBox rightButtonBox = new VBox(watchedContentManagementButton, moduleManagementButton,
                                enrollmentManagementButton, certificateManagementButton);
                HBox buttonBox = new HBox(leftButtonBox, rightButtonBox);

                leftButtonBox.setSpacing(15);
                rightButtonBox.setSpacing(15);
                buttonBox.setSpacing(70);

                // Set styles
                codeCademyTitle.setStyle(
                                "-fx-font-size: 30; -fx-padding: 10; -fx-text-fill: #333; -fx-font-weight: bold;");
                welcomeMessage.setStyle("-fx-font-size: 20; -fx-padding: 10;  -fx-text-fill: #333;");

                VBox vbox = new VBox(codeCademyTitle, welcomeMessage);
                vbox.setAlignment(Pos.CENTER);

                codeCademyPane.setStyle(
                                "-fx-background-color: #f2f2f2; -fx-padding: 10; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #ccc; -fx-border-width: 1;");
                codeCademyPane.setTop(vbox);
                codeCademyPane.setCenter(buttonBox);
                buttonBox.setAlignment(Pos.CENTER);

                Label label = new Label(
                                "Yessin Boukrach || 2206857  \nYasir Kelloulou || 2212394 \nAmin Ahmidout || 2224569");
                HBox labelBox = new HBox(label);
                codeCademyPane.setBottom(labelBox);
                labelBox.setAlignment(Pos.CENTER);

                label.setStyle("-fx-font-size: 20; -fx-padding: 10; -fx-text-fill: #333;");
                label.setOpacity(0.3);

                BorderPane.setAlignment(codeCademyTitle, Pos.CENTER);
                BorderPane.setMargin(codeCademyTitle, new Insets(10));

                // Create the homepage scene
                codeCademyHomeScene = new Scene(codeCademyPane, 800, 600);
                stage.setScene(codeCademyHomeScene);
                stage.setTitle("CodeCademy");
                stage.show();

                // Cursist Manager Scene
                cursistManagementButton.setOnAction(j -> {
                        CursistGUI cursistGui = new CursistGUI(cursistController);
                        stage.setScene(cursistGui.cursistScene(stage));
                        stage.show();
                });

                // Course Manager Scene
                courseManagementButton.setOnAction(e -> {
                        CourseGUI courseGUI = new CourseGUI(courseController);
                        stage.setScene(courseGUI.courseScene(stage));
                        stage.show();
                });

                // // Webcast Manager Scene
                webcastManagementButton.setOnAction(e -> {
                        WebcastGUI webcastGUI = new WebcastGUI(new Webcast());
                        stage.setScene(webcastGUI.webcastScene(stage));
                        stage.show();
                });

                // Content Item Manager Scene
                contentItemManagementButton.setOnAction(e -> {
                        contentItemGUI contentItemGUI = new contentItemGUI(contentItemController);
                        stage.setScene(contentItemGUI.contentItemScene(stage));
                        stage.show();
                });

                // Watched Content Manager Scene
                watchedContentManagementButton.setOnAction(e -> {
                        WatchedContentGUI watchedContentGUI = new WatchedContentGUI(watchedContentController);
                        stage.setScene(watchedContentGUI.watchedContentScene(stage));
                        stage.show();
                });

                // Module Manager Scene
                moduleManagementButton.setOnAction(e -> {
                        ModuleGUI moduleGUI = new ModuleGUI(moduleController);
                        stage.setScene(moduleGUI.ModuleScene(stage));
                        stage.show();
                });

                // Enrollment Manager Scene
                enrollmentManagementButton.setOnAction(e -> {
                        EnrollmentGUI enrollmentGUI = new EnrollmentGUI(enrollmentController);
                        stage.setScene(enrollmentGUI.enrollmentScene(stage));
                        stage.show();
                });

                // Overview Scene
                overViewButton.setOnAction(e -> {
                        OverViewGUI overviewGUI = new OverViewGUI();
                        stage.setScene(overviewGUI.OverViewScene(stage));
                        stage.show();
                });

                String buttonStyle = "-fx-background-color: #d2b48c;";
                cursistManagementButton.setStyle(buttonStyle);
                courseManagementButton.setStyle(buttonStyle);
                webcastManagementButton.setStyle(buttonStyle);
                contentItemManagementButton.setStyle(buttonStyle);
                watchedContentManagementButton.setStyle(buttonStyle);
                moduleManagementButton.setStyle(buttonStyle);
                enrollmentManagementButton.setStyle(buttonStyle);
                certificateManagementButton.setStyle(buttonStyle);
                overViewButton.setStyle(buttonStyle);

                codeCademyHomeScene.getRoot().setStyle("-fx-background-color: #f5f5dc;");

                return codeCademyHomeScene;
        }

        public GUI() {
        }

}
