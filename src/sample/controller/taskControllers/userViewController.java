package sample.controller.taskControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sample.model.User;
import sample.model.UserRoll;

import java.net.URL;
import java.util.ResourceBundle;

public class userViewController {
    ObservableList<String> userType;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> userView_userTable;

    @FXML
    private JFXButton userView_addUser;

    @FXML
    private JFXButton userView_updateUser;

    @FXML
    private JFXButton userView_deleteUser;

    @FXML
    private JFXButton userView_viewAll;

    @FXML
    private JFXButton userView_reset;

    @FXML
    private JFXTextField userView_name;

    @FXML
    private JFXTextField userView_phoneNum;

    @FXML
    private JFXTextField userView_NIC;

    @FXML
    private DatePicker userView_dob;

    @FXML
    private JFXTextField userView_addressLine01;

    @FXML
    private JFXTextField userView_addressLine02;

    @FXML
    private JFXComboBox<?> userView_marital;

    @FXML
    private JFXTextField userView_userName;

    @FXML
    private JFXTextField userView_allergies;

    @FXML
    private JFXComboBox<?> userView_bloodGroup;

    @FXML
    private JFXComboBox<String> userView_gender;

    @FXML
    private JFXTextField userView_staffID;

    @FXML
    private JFXTextField userView_staffEmail;

    @FXML
    private DatePicker userView_staffdoj;

    @FXML
    private JFXTextField userView_nicSearch;

    @FXML
    private JFXButton userView_searchButton;

    @FXML
    private JFXComboBox<String> userView_speciality;

    @FXML
    private JFXComboBox<UserRoll> userView_userTypeDrop;

    @FXML
    private JFXPasswordField userView_userPassword;

    @FXML
    void initialize() {

        userView_userTypeDrop.getItems().addAll(referenceViewController.getUserRolls());
        userView_speciality.getItems().addAll(referenceViewController.getDoctorSpeciality());
        userView_gender.getItems().addAll(referenceViewController.getGender());
        
        userView_addUser.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent actionEvent) {
       //User user =
    }
       });
    }
}
