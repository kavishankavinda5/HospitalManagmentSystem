package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.liveClock;

public class CustomLoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label customLogin_invalidMessage;

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

        userLogin_SigninButoon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                switch (userLogin_userName.getText()){
                    case "admin":
                        openDashBoard("adminMainView");
                        break;
                    case "reception":
                        openDashBoard("receptionistMainView");
                        break;
                    case "patient":
                        openDashBoard("patientMainView");
                        break;
                    case "doctor":
                        openDashBoard("medicalOfficerView");
                        break;

                    default:
                        customLogin_invalidMessage.setText("Invalid User Input !!!");

                }



            }
        });

    }
    public void setUserLoginLable(String name){
        userLogin_userLable.setText(name+" Login");
    }

    public void openDashBoard(String fileName){
        userLogin_SigninButoon.getScene().getWindow().hide();
        Stage receptionStage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/sample/view/dashBoards/"+fileName+".fxml"));
        try {
            loader.load();


        }catch (IOException e){
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        receptionStage.setScene(new Scene(root));


        receptionStage.show();
    }


}
