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
import sample.Main;
import sample.controller.actionTask.UserAction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.liveClock;

public class CustomLoginController {

    private String userRoll;

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

                //get data from the view and store it int variables
                String inputUserName = userLogin_userName.getText().trim();
                String inputPassword = userLogin_userPassword.getText().trim();

                int i = UserAction.verifyLogin(inputUserName,inputPassword, userRoll.toLowerCase().trim());
               // System.out.println(i);

                if (i == 1){
                    switch (userRoll){
                        case "Admin":
                            openDashBoard("adminMainView");
                            break;
                        case "Reception":
                            openDashBoard("receptionistMainView");
                            break;
                        case "Patient":
                            openDashBoard("patientMainView");
                            break;
                        case "MedicalOfficer":
                            openDashBoard("medicalOfficerView");
                            break;

                        default:
                            customLogin_invalidMessage.setText("Invalid User Input !!!");

                    }
                }
                else {
                    customLogin_invalidMessage.setText("Invalid User Input !!!");
                }
            }
        });

    }
    public void setUserLoginLable(String name){
        userLogin_userLable.setText(name+" Login");
        this.userRoll = name;
    }

    public void openDashBoard(String fileName){
        userLogin_SigninButoon.getScene().getWindow().hide();
        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/sample/view/dashBoards/"+fileName+".fxml"));
        try {
            loader.load();


        }catch (IOException e){
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }


}
