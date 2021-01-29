package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Person;
import main.Routine;
import main.Workout;
import main.db.Database;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;

public class MainController {

    @FXML private Text routineText;
    @FXML private Text lets;
    @FXML private Button routineButton;
    @FXML private Button openFile;
    @FXML private Button next;
    @FXML private Button prev;
    @FXML private Text welcome;
    @FXML private Separator sep1;
    @FXML private Separator sep2;

    private Routine routine;
    private int dayCounter;
    private LinkedList<Workout> workouts;

    private Stage curStage;

    @FXML
    public void generateRoutine() throws Exception {
        //Generate the routine
        Person user = Database.getPerson(LoginController.getUsernameIn());
        try {
            routine = new Routine(user);
        }
        catch (NullPointerException np) {
            //New registered user
            user = Database.getPerson(RegisterController.getUsernameIn());
            routine = new Routine(user);
        }

        //Get workouts
        workouts = routine.getWorkouts();
        dayCounter = 0;

        //Hide/update previous text
        welcome.setVisible(false);
        lets.setText("DAY 1:");
        routineButton.setVisible(false);

        //Show routine buttons
        openFile.setVisible(true);
        sep1.setVisible(true);
        sep2.setVisible(true);
        prev.setVisible(true);
        next.setVisible(true);

        //Show the first day of the workout routine
        routineText.setStyle("-fx-font-family: monospace");
        routineText.setText(workouts.get(0).toString());
    }

    @FXML
    public void next() {
        try {
            routineText.setText(workouts.get(++dayCounter).toString());
            lets.setText("DAY " + (dayCounter + 1) + ":");
        }
        catch (IndexOutOfBoundsException ex) {
            dayCounter--;
        }
    }

    @FXML
    public void prev() {
        try {
            routineText.setText(workouts.get(--dayCounter).toString());
            lets.setText("DAY " + (dayCounter + 1) + ":");
        }
        catch (IndexOutOfBoundsException ex) {
            dayCounter++;
        }
    }

    @FXML
    public void openWorkoutFile() {
        //Open window to save file in .txt format
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save workout routine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(curStage);

        if (file != null) {
            if(!file.getName().contains(".")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            try {
                //Set file type, write to file and open it
                routine.writeToFile(file);
                Desktop.getDesktop().open(file);
                openFile.setVisible(false);
                sep1.setVisible(false);
                sep2.setVisible(false);
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    public void loadMain(Event e) throws Exception {
        //Load the main window
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new URL(
                    utils.URL.formattedURL(this.getClass().getResource("/view/main.fxml").toExternalForm())));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        Pane root = loader.load();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/view/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        curStage = new Stage();
        curStage.setScene(scene);
        curStage.setTitle("Workout Generator - Personal Trainer Software  Version 1.0");
        curStage.setResizable(false);
        curStage.show();
    }
}
