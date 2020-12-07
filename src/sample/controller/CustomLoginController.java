package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.liveClock;

public class CustomLoginController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label userLogin_userLable;

    @FXML
    private JFXTextField userLogin_userName;

    @FXML
    private JFXPasswordField userLogin_userPassword;

    @FXML
    private JFXButton userLogin_SigninButoon;

    @FXML
    private Hyperlink userLogin_forgetPassword;

    @FXML
    private Label loginView_timelable;

    @FXML
    private Label loginView_dateLabel;

    @FXML
    private Label userLogin_diaplayData;

    @FXML
    void initialize() {

        liveClock(loginView_timelable);

    }
    public void setUserLoginLable(String name){
        userLogin_userLable.setText(name+" Login");
    }
}
