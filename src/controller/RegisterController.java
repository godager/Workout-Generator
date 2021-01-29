package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.db.DBConnection;
import main.db.Database;
import utils.Password;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    //Calling methods from events in the register window
    @FXML private TextField name;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Slider slider;
    @FXML private ToggleGroup group;
    @FXML private RadioButton muscleGrowth;
    @FXML private RadioButton maxStrength;
    @FXML private RadioButton weightLoss;
    @FXML private RadioButton xSkiing;
    @FXML private Spinner spinner;
    @FXML private AnchorPane ap;
    @FXML private Text nameError;
    @FXML private Text usernameError;
    @FXML private Text passwordError;

    private String nameIn;
    private static String usernameIn;
    private String passwordIn;
    private int expIn;
    private String goalIn;
    private int timesPerWeekIn;

    public static String getUsernameIn() {
        return usernameIn;
    }

    @FXML
    public void letsGo(Event e) throws Exception {
        //Get input parameters
        nameIn = name.getText();
        usernameIn = username.getText();
        passwordIn = password.getText();
        expIn = (int) slider.getValue();
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        goalIn = goalInDBFormat(selectedRadioButton.getText());
        timesPerWeekIn = (int) spinner.getValue();

        //Check if user has entered a name
        if (name.equals("")) {
            nameError.setText("Please enter a name");
            return;
        }
        else {
            nameError.setText("");
        }

        //Check password requirements
        if (!Password.passwordValid(passwordIn)) {
            passwordError.setText("Password must be at least 8 characters");
            return;
        }
        else {
            passwordError.setText("");
        }

        //Check if username already exists
        DBConnection.connect();
        if (Database.usernameExists(usernameIn)) {
            usernameError.setText("USERNAME ALREADY IN USE");
            return;
        }
        else {
            usernameError.setText("");
        }

        //Check if input is past limit
        if (inputLengthTooLong()) return;

        //All input is OK -> add user to database with input parameters
        Database.addPerson(nameIn, expIn, goalIn, usernameIn, passwordIn, timesPerWeekIn);
        DBConnection.close();

        //Close register window
        Stage primaryStage = (Stage) ap.getScene().getWindow();
        primaryStage.hide();

        //Initialize main controller
        MainController mc = new MainController();
        mc.loadMain(e);
    }

    @FXML
    public void loadRegister(Event e) {
        //Load the register window
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new URL("file:///C://Users//sindr/Documents/Hobby projects/Code PT/src/view/register.fxml"));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Display window
        Scene scene = new Scene(pane);
        String css = this.getClass().getResource("/view/register.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage regStage = new Stage();
        regStage.setScene(scene);
        regStage.setTitle("SWEAT GAINS - Personal Trainer Software  Version 1.0");
        regStage.setResizable(false);
        regStage.show();
    }

    private boolean inputLengthTooLong() {
        boolean tooLong = false;
        if(nameIn.length() > 30) {
            nameError.setText("Name must be less than 30 chars");
            tooLong = true;
        }
        if(usernameIn.length() > 20) {
            usernameError.setText("Username must be less than 20 chars");
            tooLong = true;
        }
        if(passwordIn.length() > 30) {
            passwordError.setText("Password must be less than 30 chars");
            tooLong = true;
        }
        return tooLong;
    }


    //Converts input goal to data name format
    private String goalInDBFormat (String s) {
        if (s.equals("Muscle growth")) return "muscleGrowth";
        if (s.equals("Max strength")) return "maxStrength";
        if (s.equals("Weight loss")) return "weightLoss";
        if (s.equals("Cross country skiing")) return "xSkiing";
        return null;
    }

    //Initializes toggle group after the scene is loaded
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        group = new ToggleGroup();
        this.muscleGrowth.setToggleGroup(group);
        maxStrength.setToggleGroup(group);
        weightLoss.setToggleGroup(group);
        xSkiing.setToggleGroup(group);
    }
}