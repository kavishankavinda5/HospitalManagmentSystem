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
import sample.model.Admin;
import sample.model.Patient;
import sample.model.Receptionist;

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

    private void openDashBoard(String fileName){
        userLogin_SigninButoon.getScene().getWindow().hide();
        Stage dashBoardStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/dashBoards/"+fileName+".fxml"));

        try {
            loader.load();

        }catch (IOException e){
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        dashBoardStage.setScene(new Scene(root));

        switch (fileName){
            case "patientMainView":
                Patient patientDetails = UserAction.searchPatient(userLogin_userName.getText(),userLogin_userName.getText(),userLogin_userPassword.getText());
                PatientViewController patientViewController = loader.getController();
                patientViewController.setCurrentpatient(patientDetails);
                break;

            case "receptionistMainView":
                //Receptionist receptionistDetails =UserAction.searchReceptionRecord()
                break;
            case "adminMainView":
                Admin adminDetails = UserAction.searchAdmin(userLogin_userName.getText(),userLogin_userPassword.getText());
                AdminMainController adminMainController = loader.getController();
                adminMainController.setCurrentAdmin(adminDetails);
                break;
            case "medicalOfficerView":
                break;
            default:
                break;
        }
        dashBoardStage.show();
    }

}
