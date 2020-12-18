package sample.controller.taskControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import sample.controller.actionTask.UserAction;
import sample.model.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UserViewController {
    private  static  int staffID =0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label userView_userNameLable;

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
    private JFXComboBox<String> userView_marital;

    @FXML
    private JFXTextField userView_userName;

    @FXML
    private JFXTextField userView_allergies;

    @FXML
    private JFXComboBox<BloodGroup> userView_bloodGroup;

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
        userView_userTypeDrop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (userView_userTypeDrop.getValue()==UserRoll.PATIENT){
                    userView_staffID.setDisable(true);
                    userView_staffdoj.setDisable(true);
                    userView_staffEmail.setDisable(true);
                    userView_speciality.setDisable(true);
                    userView_bloodGroup.setDisable(false);
                    userView_userName.setDisable(true);
                    userView_userPassword.setDisable(true);
                    userView_userNameLable.setDisable(true);


                }else if (userView_userTypeDrop.getValue()==UserRoll.ADMIN){
                    userView_bloodGroup.setDisable(true);
                    userView_staffID.setDisable(true);
                    userView_staffdoj.setDisable(true);
                    userView_staffEmail.setDisable(true);
                    userView_speciality.setDisable(true);
                    userView_bloodGroup.setDisable(true);
                    userView_userName.setDisable(true);
                    userView_userPassword.setDisable(true);
                    userView_allergies.setDisable(true);
                    userView_userNameLable.setDisable(true);
                    userView_speciality.setDisable(true);
                }
                else if (userView_userTypeDrop.getValue()==UserRoll.RECEPTIONIST){
                    userView_staffID.setDisable(false);
                    userView_staffdoj.setDisable(false);
                    userView_staffEmail.setDisable(false);
                    userView_speciality.setDisable(true);
                }else {
                    userView_speciality.setDisable(false);
                }
            }
        });


        //set the derop down wit the data taken by the reference module
        userView_userTypeDrop.getItems().addAll(ReferenceViewController.getUserRolls());
        userView_speciality.getItems().addAll(ReferenceViewController.getDoctorSpeciality());
        userView_gender.getItems().addAll(ReferenceViewController.getGender());
        userView_bloodGroup.getItems().addAll(ReferenceViewController.getBloogGroup());
        userView_marital.getItems().addAll(ReferenceViewController.getMaritalStatus());

        userView_addUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                userView_dob.setConverter(new StringConverter<LocalDate>() {
                    String pattern = "yyyy-MM-dd";
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                    {
                        userView_dob.setPromptText(pattern.toLowerCase());
                    }

                    @Override public String toString(LocalDate date) {
                        if (date != null) {
                            return dateFormatter.format(date);
                        } else {
                            return "";
                        }
                    }

                    @Override public LocalDate fromString(String string) {
                        if (string != null && !string.isEmpty()) {
                            return LocalDate.parse(string, dateFormatter);
                        } else {
                            return null;
                        }
                    }
                });


                switch (userView_userTypeDrop.getValue()){
                    case PATIENT:
                        Patient patient = new Patient();
                        patient.setUserRoll(userView_userTypeDrop.getValue());
                        patient.setName(userView_name.getText());
                        patient.setGender(userView_gender.getValue());
                        patient.setMaritalStatus(userView_marital.getValue());
                        patient.setDob(userView_dob.getValue());
                        patient.setPhoneNumber(userView_phoneNum.getText());
                        patient.setIdCardNumber(userView_NIC.getText());
                        patient.setUserName(userView_NIC.getText());
                        patient.setUserPassword(userView_NIC.getText());
                        patient.setBloodGroup(userView_bloodGroup.getValue());
                        patient.setAllergies(userView_allergies.getText());

                        UserAction.addPatient(patient,UserRoll.ADMIN);

                    case RECEPTIONIST:
                        Receptionist receptionist = new Receptionist();
                        receptionist.setUserRoll(userView_userTypeDrop.getValue());
                        receptionist.setName(userView_name.getText()); ;
                        receptionist.setGender(userView_gender.getValue());
                        receptionist.setMaritalStatus(userView_marital.getValue());
                        receptionist.setDob(userView_dob.getValue()); ;
                        receptionist.setPhoneNumber(userView_phoneNum.getText());
                        receptionist.setIdCardNumber(userView_NIC.getText());
                        receptionist.setUserName(userView_NIC.getText());
                        receptionist.setUserPassword(userView_NIC.getText());
                        receptionist.setStaffID(getStaffId());
                        receptionist.setStaffEmailAddress(userView_staffEmail.getText());

                        UserAction.addReceptionist(receptionist,UserRoll.ADMIN);


                    case ADMIN:
                        Admin admin = new Admin();
                        admin.setUserRoll(userView_userTypeDrop.getValue());
                        admin.setName(userView_name.getText()); ;
                        admin.setGender(userView_gender.getValue());
                        admin.setMaritalStatus(userView_marital.getValue());
                        admin.setDob(userView_dob.getValue()); ;
                        admin.setPhoneNumber(userView_phoneNum.getText());
                        admin.setIdCardNumber(userView_NIC.getText());
                        admin.setUserName(userView_NIC.getText());
                        admin.setUserPassword(userView_NIC.getText());


                        UserAction.addAdmin(admin,UserRoll.ADMIN);




                }
            }
        });

        userView_searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String serachID = userView_nicSearch.getText();
                Patient patient =UserAction.searchPatient(serachID,UserAction.patientDataFilePath);
                displayUserdata(patient);
            }
        });
    }

    public void displayUserdata(Patient patient){
        userView_userTypeDrop.setValue(patient.getUserRoll());
        userView_name.setText(patient.getUserName());
        userView_gender.setValue(patient.getGender());
        userView_marital.setValue(patient.getMaritalStatus());
        userView_dob.setValue(patient.getDob());
        userView_phoneNum.setText(patient.getPhoneNumber());
        userView_NIC.setText(patient.getIdCardNumber());
        userView_userName.setText(patient.getUserName());
        userView_NIC.setText(patient.getIdCardNumber());
        userView_bloodGroup.setValue(patient.getBloodGroup());
        userView_allergies.setText(patient.getAllergies());
    }

    public static int  getStaffId(){
        staffID ++;
        return  staffID;
    }
}
