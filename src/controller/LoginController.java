package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.db.DBConnection;
import main.db.Database;
import utils.Password;

import java.sql.SQLException;

public class LoginController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Text error;
    @FXML private AnchorPane ap;
    private static String usernameIn;

    //Calling methods from events in the login window
    @FXML
    public void loginClick(Event e) throws Exception {
        usernameIn = username.getText();

        DBConnection.connect();
        try {
            boolean loginOK = Database.login(username.getText(), password.getText());
            if (loginOK) {
                //Close register window
                Stage primaryStage = (Stage) ap.getScene().getWindow();
                primaryStage.hide();

                //Initialize main controller
                MainController mc = new MainController();
                mc.loadMain(e);
            }
            else {
                error.setText("Wrong password");
            }
        }
        catch (SQLException ex) {
            error.setText("Username does not exist");
        }
        finally {
            DBConnection.close();
        }
    }

    @FXML
    public void registerClick(Event e) {
        //Close login window
        Stage primaryStage = (Stage) ap.getScene().getWindow();
        primaryStage.hide();

        //Initialize register controller
        RegisterController rc = new RegisterController();
        rc.loadRegister(e);
    }

    public static String getUsernameIn() {
        return usernameIn;
    }
}
