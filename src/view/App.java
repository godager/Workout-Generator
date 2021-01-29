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
        loader.setLocation(new URL(formattedURL()));
        Pane pane = loader.load();

        //Show login window
        Scene scene = new Scene(pane);
        String css = this.getClass().getResource("/view/login.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setTitle("WORKOUT GENERATOR - Personal Trainer Software  Version 1.0");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private String formattedURL() {
        String path = this.getClass().getResource("/view/login.fxml").toExternalForm();
        path = path.replace('%', ' ');
        path = path.replaceAll("20", "");
        String sub = path.substring(15);
        String finalPath = "file:///C://Users//" + sub;
        finalPath = finalPath.replace("out/production/WorkoutGenerator/", "src/");
        return finalPath;
    }

    public static void main(String[] args) {
        launch(args);
    }
}