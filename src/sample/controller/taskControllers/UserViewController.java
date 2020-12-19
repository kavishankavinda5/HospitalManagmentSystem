package sample.controller.taskControllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private JFXTextArea userView_address;

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
    private TextField userView_searchField;

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
                switch (userView_userTypeDrop.getValue()){
                    case ADMIN:
                        userView_name.setDisable(false);
                        userView_gender.setDisable(false);
                        userView_marital.setDisable(false);
                        userView_dob.setDisable(false);
                        userView_phoneNum.setDisable(false);
                        userView_NIC.setDisable(false);
                        userView_address.setDisable(false);
                        userView_userName.setDisable(false);
                        userView_userPassword.setDisable(true);
                        userView_bloodGroup.setDisable(true);
                        userView_allergies.setDisable(true);
                        userView_staffID.setDisable(true);
                        userView_staffEmail.setDisable(true);
                        userView_staffdoj.setDisable(true);
                        userView_speciality.setDisable(true);
                        break;
                    default:
                        break;
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
                        UserAction.addPatient(getPatient(),UserRoll.ADMIN);
                        break;

                    case RECEPTIONIST:

                        UserAction.addReceptionist(getReceptionist(),UserRoll.ADMIN);
                        break;

                    case ADMIN:

                        UserAction.addAdmin(getAdmin(),UserRoll.ADMIN);
                        break;

                    case MEDICALOFFICER:

                        UserAction.addMedicalOfficer(getMedicalOfficer(),UserRoll.ADMIN);
                        break;

                }
            }
        });

        userView_searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String serachID = userView_searchField.getText();

                switch (userView_userTypeDrop.getValue()){
                    case RECEPTIONIST:
                        break;

                    case MEDICALOFFICER:
                        MedicalOfficer medicalOfficer =UserAction.searchMedicalOfficer(serachID,UserAction.medicalOfficerFilePath);
                        displayMedicalOfficerData(medicalOfficer);
                        break;

                    case PATIENT:

                        Patient patient =UserAction.searchPatient(serachID,UserAction.patientDataFilePath);
                        displayUserdata(patient);
                        break;

                    case ADMIN:
                        break;


                }

            }
        });

        userView_deleteUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                switch (userView_userTypeDrop.getValue()){
                    case ADMIN:
                        break;
                    case PATIENT:
                        UserAction.deleteUserRecord(UserRoll.ADMIN,userView_searchField.getText(),userView_userTypeDrop.getValue());
                        break;
                    case MEDICALOFFICER:
                        UserAction.deleteMedicalOfficerRcord(UserRoll.ADMIN,userView_searchField.getText());
                        break;
                    case RECEPTIONIST:
                        break;
                }

            }
        });


        userView_updateUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switch (userView_userTypeDrop.getValue()){
                    case ADMIN:
                        break;
                    case PATIENT:
                        UserAction.updatePatientRecord(UserRoll.ADMIN,getPatient(),userView_searchField.getText());
                        break;
                    case MEDICALOFFICER:
                        UserAction.updateMedicalOfficerRecord(UserRoll.ADMIN,getMedicalOfficer(),userView_searchField.getText());
                        break;
                    case RECEPTIONIST:
                        break;
                }

            }
        });

        userView_reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetDisplay();
            }
        });
    }

    public void displayUserdata(Patient patient){
        userView_userTypeDrop.setValue(patient.getUserRoll());
        userView_name.setText(patient.getName());
        userView_gender.setValue(patient.getGender());
        userView_marital.setValue(patient.getMaritalStatus());
        userView_dob.setValue(patient.getDob());
        userView_phoneNum.setText(patient.getPhoneNumber());
        userView_NIC.setText(patient.getIdCardNumber());
        userView_address.setText(patient.getAddress());
        userView_userName.setText(patient.getUserName());
        userView_userPassword.setText(patient.getIdCardNumber());
        userView_bloodGroup.setValue(patient.getBloodGroup());
        userView_allergies.setText(patient.getAllergies());
    }

    public void displayMedicalOfficerData(MedicalOfficer medicalOfficer){
        userView_userTypeDrop.setValue(medicalOfficer.getUserRoll());
        userView_name.setText(medicalOfficer.getName());
        userView_gender.setValue(medicalOfficer.getGender());
        userView_marital.setValue(medicalOfficer.getMaritalStatus());
        userView_dob.setValue(medicalOfficer.getDob());
        userView_phoneNum.setText(medicalOfficer.getPhoneNumber());
        userView_NIC.setText(medicalOfficer.getIdCardNumber());
        userView_address.setText(medicalOfficer.getAddress());
        userView_userName.setText(medicalOfficer.getUserName());
        userView_userPassword.setText(medicalOfficer.getIdCardNumber());
        userView_staffID.setText(String.valueOf(medicalOfficer.getStaffID()));
        userView_staffEmail.setText(medicalOfficer.getStaffEmailAddress());
        userView_speciality.setValue(medicalOfficer.getSpeciality());
    }

    public Patient getPatient(){
        Patient patient = new Patient();
        patient.setUserRoll(userView_userTypeDrop.getValue());
        patient.setName(userView_name.getText());
        patient.setGender(userView_gender.getValue());
        patient.setMaritalStatus(userView_marital.getValue());
        patient.setDob(userView_dob.getValue());
        patient.setPhoneNumber(userView_phoneNum.getText());
        patient.setIdCardNumber(userView_NIC.getText());
        patient.setAddress(userView_address.getText());
        patient.setUserName(userView_NIC.getText());
        patient.setUserPassword(userView_NIC.getText());
        patient.setBloodGroup(userView_bloodGroup.getValue());
        patient.setAllergies(userView_allergies.getText());

        return patient;
    }

    public Receptionist getReceptionist(){
        Receptionist receptionist = new Receptionist();
        receptionist.setUserRoll(userView_userTypeDrop.getValue());
        receptionist.setName(userView_name.getText()); ;
        receptionist.setGender(userView_gender.getValue());
        receptionist.setMaritalStatus(userView_marital.getValue());
        receptionist.setDob(userView_dob.getValue()); ;
        receptionist.setPhoneNumber(userView_phoneNum.getText());
        receptionist.setIdCardNumber(userView_NIC.getText());
        receptionist.setAddress(userView_address.getText());
        receptionist.setUserName(userView_NIC.getText());
        receptionist.setUserPassword(userView_NIC.getText());
        receptionist.setStaffID(getStaffId());
        receptionist.setStaffEmailAddress(userView_staffEmail.getText());

        return receptionist;
    }

    public Admin getAdmin(){
        Admin admin = new Admin();
        admin.setUserRoll(userView_userTypeDrop.getValue());
        admin.setName(userView_name.getText()); ;
        admin.setGender(userView_gender.getValue());
        admin.setMaritalStatus(userView_marital.getValue());
        admin.setDob(userView_dob.getValue()); ;
        admin.setPhoneNumber(userView_phoneNum.getText());
        admin.setAddress(userView_address.getText());
        admin.setIdCardNumber(userView_NIC.getText());
        admin.setUserName(userView_NIC.getText());
        admin.setUserPassword(userView_NIC.getText());

        return admin;

    }

    public  MedicalOfficer getMedicalOfficer(){

        MedicalOfficer medicalOfficer = new MedicalOfficer();

        medicalOfficer.setUserRoll(userView_userTypeDrop.getValue());
        medicalOfficer.setName(userView_name.getText()); ;
        medicalOfficer.setGender(userView_gender.getValue());
        medicalOfficer.setMaritalStatus(userView_marital.getValue());
        medicalOfficer.setDob(userView_dob.getValue()); ;
        medicalOfficer.setPhoneNumber(userView_phoneNum.getText());
        medicalOfficer.setIdCardNumber(userView_NIC.getText());
        medicalOfficer.setAddress(userView_address.getText());
        medicalOfficer.setUserName(userView_NIC.getText());
        medicalOfficer.setUserPassword(userView_NIC.getText());
        medicalOfficer.setStaffID(getStaffId());
        medicalOfficer.setStaffEmailAddress(userView_staffEmail.getText());
        medicalOfficer.setSpeciality(userView_speciality.getValue());

       return medicalOfficer;

    }

    public void resetDisplay(){
        userView_name.clear();
        userView_gender.setValue(null);
        userView_marital.setValue(null);
        userView_phoneNum.clear();
        userView_userPassword.clear();
        userView_NIC.clear();
        userView_userName.clear();
        userView_address.clear();
        userView_allergies.clear();
        userView_dob.setValue(null);
        userView_staffdoj.setValue(null);
        userView_gender.setValue(null);
        userView_speciality.setValue(null);
        userView_bloodGroup.setValue(null);
        userView_staffEmail.clear();
        userView_searchField.clear();
        userView_userTypeDrop.setValue(null);

    }

    public static int  getStaffId(){
        staffID ++;
        return  staffID;
    }
}
