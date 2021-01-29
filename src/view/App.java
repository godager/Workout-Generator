package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Load login window
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(
                utils.URL.formattedURL(this.getClass().getResource("/view/login.fxml").toExternalForm())));
        Pane pane = loader.load();

        //Show login window
        Scene scene = new Scene(pane);
        String css = this.getClass().getResource("/view/login.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Workout Generator - Personal Trainer Software  Version 1.0");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}