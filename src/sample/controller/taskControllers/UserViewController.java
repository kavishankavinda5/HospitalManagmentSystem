package sample.controller.taskControllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.StringLengthValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import sample.controller.actionTask.ReferenceAction;
import sample.controller.actionTask.UserAction;
import sample.model.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    private  static  int staffID =0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label userView_userNameLable;

    @FXML
    private TableView<Admin> userView_userTable;

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
    private JFXComboBox<Gender> userView_gender;

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
    public void initialize(URL url, ResourceBundle rb) {

//      ObservableList<Admin> adminData = FXCollections.observableArrayList(UserAction.getAllAdmin());
//      System.out.println(adminData.toString());
//
//        TableColumn id =new TableColumn("NIC_Number");
//        userView_userTable.getColumns().add(id);
//        id.setCellValueFactory(new PropertyValueFactory<Admin,String>("idCardNumber"));
//
//        userView_userTable.setItems(adminData);

        //Validate User Inputs
        validateInitialize();

        //set the drop down wit the data taken by the reference module
        userView_userTypeDrop.getItems().addAll(ReferenceAction.getUserRolls());
        userView_speciality.getItems().addAll(ReferenceAction.getDocSpecialityStringArray());
        userView_gender.getItems().addAll(ReferenceAction.getGender());
        userView_bloodGroup.getItems().addAll(ReferenceAction.getBloogGroup());
        userView_marital.getItems().addAll(ReferenceAction.getMaritalStatus());

        userView_userTypeDrop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switch (userView_userTypeDrop.getValue()){
                    case ADMIN:
                        setViewForAdmin();
                        break;
                    case PATIENT:
                        setViewForPatient();
                        break;
                    case RECEPTIONIST:
                        setViewForReception();
                        break;
                    case MEDICALOFFICER:
                        setViewForMedicalOfficer();
                        break;

                    default:
                        break;
                }
            }
        });

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

                if(checkInputs()) {

                    switch (userView_userTypeDrop.getValue()) {

                        case PATIENT:

                            if (userView_bloodGroup.getSelectionModel().getSelectedIndex() < 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Blood Group is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (userView_allergies.getText().length() <= 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Allergies is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                if (validateInputs()) {
                                    UserAction.addPatient(getPatient(), UserRoll.ADMIN);
                                }
                                else {
                                    Toolkit.getDefaultToolkit().beep();
                                    JOptionPane.showMessageDialog(null, "Invalid Data", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            break;


                        case RECEPTIONIST:

                            if (userView_staffEmail.getText().length() <= 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Staff Email is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (userView_staffdoj.getValue()==null){
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Join Date is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                if (validateInputs()) {
                                    UserAction.addReceptionist(getReceptionist(), UserRoll.ADMIN);
                                }
                                else {
                                    Toolkit.getDefaultToolkit().beep();
                                    JOptionPane.showMessageDialog(null, "Invalid Data", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            break;

                        case ADMIN:

                            if (validateInputs()) {
                                UserAction.addAdmin(getAdmin(), UserRoll.ADMIN);
                            }
                            else {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Invalid Data", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }

                            break;

                        case MEDICALOFFICER:

                            if (userView_staffEmail.getText().length() <= 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Staff Email is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (userView_staffdoj.getValue()==null){
                                    Toolkit.getDefaultToolkit().beep();
                                    JOptionPane.showMessageDialog(null, "Join Date is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            else if (userView_speciality.getSelectionModel().getSelectedIndex() < 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Speciality is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                if (validateInputs()) {
                                    UserAction.addMedicalOfficer(getMedicalOfficer(), UserRoll.ADMIN);
                                }
                                else {
                                    Toolkit.getDefaultToolkit().beep();
                                    JOptionPane.showMessageDialog(null, "Invalid Data", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            break;

                    }


                }

            }
        });

        userView_searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String serachID = userView_searchField.getText();

                if (userView_userTypeDrop.getSelectionModel().getSelectedIndex() < 0) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "User Type is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else if (userView_searchField.getText().length() <= 0) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Search ID is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else {

                    switch (userView_userTypeDrop.getValue()) {

                        case RECEPTIONIST:
                            Receptionist receptionist = UserAction.searchReceptionRecord(serachID, null, null);
                            displayReceptionistData(receptionist);
                            break;

                        case MEDICALOFFICER:
                            MedicalOfficer medicalOfficer = UserAction.searchMedicalOfficer(serachID, UserAction.medicalOfficerFilePath);
                            displayMedicalOfficerData(medicalOfficer);
                            break;

                        case PATIENT:

                            Patient patient = UserAction.searchPatient(serachID, null, null);
                            displayPatientData(patient);
                            break;

                        case ADMIN:
                            Admin admin = UserAction.searchAdmin(serachID, null);
                            displayAdminData(admin);
                            break;

                    }

                }
            }
        });

        userView_deleteUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (userView_userTypeDrop.getSelectionModel().getSelectedIndex() < 0) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "User Type is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else if (userView_searchField.getText().length() <= 0) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Search ID is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    UserAction.deleteUserRecord(UserRoll.ADMIN, userView_searchField.getText(), userView_userTypeDrop.getValue(), getLoginUser());

                }

            }
        });

        userView_updateUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (userView_userTypeDrop.getSelectionModel().getSelectedIndex() < 0) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "User Type is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else if (userView_searchField.getText().length() <= 0) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Search ID is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else if(checkInputs()) {

                    switch (userView_userTypeDrop.getValue()) {

                        case PATIENT:

                            if (userView_bloodGroup.getSelectionModel().getSelectedIndex() < 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Blood Group is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (userView_allergies.getText().length() <= 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Allergies is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                if (validateInputs()) {
                                    UserAction.updatePatientRecord(UserRoll.ADMIN, getPatient(), userView_searchField.getText(), getLoginUser());
                                }
                                else {
                                    Toolkit.getDefaultToolkit().beep();
                                    JOptionPane.showMessageDialog(null, "Invalid Data", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            break;


                        case RECEPTIONIST:

                            if (userView_staffEmail.getText().length() <= 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Staff Email is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (userView_staffdoj.getValue()==null){
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Join Date is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                if (validateInputs()) {
                                    UserAction.updateReceptionRecord(UserRoll.ADMIN, getReceptionist(), userView_searchField.getText(), getLoginUser());
                                }
                                else {
                                    Toolkit.getDefaultToolkit().beep();
                                    JOptionPane.showMessageDialog(null, "Invalid Data", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            break;

                        case ADMIN:

                            if (validateInputs()) {
                                UserAction.updateAdmin(UserRoll.ADMIN, getAdmin(), userView_searchField.getText(), getLoginUser());
                            }
                            else {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Invalid Data", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }

                            break;

                        case MEDICALOFFICER:

                            if (userView_staffEmail.getText().length() <= 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Staff Email is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (userView_staffdoj.getValue()==null){
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Join Date is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (userView_speciality.getSelectionModel().getSelectedIndex() < 0) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(null, "Speciality is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                if (validateInputs()) {
                                    UserAction.updateMedicalOfficerRecord(UserRoll.ADMIN, getMedicalOfficer(), userView_searchField.getText(), getLoginUser());
                                }
                                else {
                                    Toolkit.getDefaultToolkit().beep();
                                    JOptionPane.showMessageDialog(null, "Invalid Data", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            break;
                    }
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

    private LoginUser getLoginUser() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserName(UserAction.encryptUserData(userView_userName.getText()));
        loginUser.setUserPassword(UserAction.encryptUserData(userView_userPassword.getText()));

        return loginUser;
    }

    public void displayPatientData(Patient patient){
        userView_userTypeDrop.setValue(patient.getUserRoll());
        userView_name.setText(patient.getName());
        userView_gender.setValue(patient.getGender());
        userView_marital.setValue(patient.getMaritalStatus());
        userView_dob.setValue(patient.getDob());
        userView_phoneNum.setText(patient.getPhoneNumber());
        userView_NIC.setText(patient.getIdCardNumber());
        userView_address.setText(patient.getAddress());
        userView_userName.setText(UserAction.decryptUserData(patient.getUserName()));
        userView_userPassword.setText(UserAction.decryptUserData(patient.getUserPassword()));
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
        userView_userName.setText(UserAction.decryptUserData(medicalOfficer.getUserName()));
        userView_userPassword.setText(UserAction.decryptUserData(medicalOfficer.getUserPassword()));
        userView_staffID.setText(String.valueOf(medicalOfficer.getStaffID()));
        userView_staffEmail.setText(medicalOfficer.getStaffEmailAddress());
        userView_staffdoj.setValue(medicalOfficer.getDateOfJoining());
        userView_speciality.setValue(medicalOfficer.getSpeciality().trim());

    }

    public void displayAdminData(Admin admin){
        userView_userTypeDrop.setValue(admin.getUserRoll());
        userView_name.setText(admin.getName());
        userView_gender.setValue(admin.getGender());
        userView_marital.setValue(admin.getMaritalStatus());
        userView_dob.setValue(admin.getDob());
        userView_phoneNum.setText(admin.getPhoneNumber());
        userView_NIC.setText(admin.getIdCardNumber());
        userView_address.setText(admin.getAddress());
        userView_userName.setText(UserAction.decryptUserData(admin.getUserName()));
        userView_userPassword.setText(UserAction.decryptUserData(admin.getUserPassword()));
    }

    public void displayReceptionistData(Receptionist receptionist){
        userView_userTypeDrop.setValue(receptionist.getUserRoll());
        userView_name.setText(receptionist.getName());
        userView_gender.setValue(receptionist.getGender());
        userView_marital.setValue(receptionist.getMaritalStatus());
        userView_dob.setValue(receptionist.getDob());
        userView_phoneNum.setText(receptionist.getPhoneNumber());
        userView_NIC.setText(receptionist.getIdCardNumber());
        userView_address.setText(receptionist.getAddress());
        userView_userName.setText(UserAction.decryptUserData(receptionist.getUserName()));
        userView_userPassword.setText(UserAction.decryptUserData(receptionist.getUserPassword()));
        userView_staffdoj.setValue(receptionist.getDateOfJoining());
        userView_staffEmail.setText(receptionist.getStaffEmailAddress());
        userView_staffID.setText(String.valueOf(receptionist.getStaffID()));
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
        patient.setUserName(UserAction.encryptUserData(userView_userName.getText()));
        patient.setUserPassword(UserAction.encryptUserData(userView_userPassword.getText()));
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
        receptionist.setUserName(UserAction.encryptUserData(userView_userName.getText()));
        receptionist.setUserPassword(UserAction.encryptUserData(userView_userPassword.getText()));
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
        admin.setUserName(UserAction.encryptUserData(userView_userName.getText()).trim());
        admin.setUserPassword(UserAction.encryptUserData(userView_userPassword.getText()).trim());

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
        medicalOfficer.setUserName(UserAction.encryptUserData(userView_userName.getText()));
        medicalOfficer.setUserPassword(UserAction.encryptUserData(userView_userPassword.getText()));
        medicalOfficer.setStaffID(getStaffId());
        medicalOfficer.setStaffEmailAddress(userView_staffEmail.getText());
        medicalOfficer.setSpeciality(userView_speciality.getValue());
        medicalOfficer.setDateOfJoining(userView_staffdoj.getValue());

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
        userView_staffID.clear();
        userView_userTypeDrop.getItems();


    }

    public static int  getStaffId(){
        staffID ++;
        return  staffID;
    }

    public void setViewForAdmin(){
        userView_name.setDisable(false);
        userView_gender.setDisable(false);
        userView_marital.setDisable(false);
        userView_dob.setDisable(false);
        userView_phoneNum.setDisable(false);
        userView_NIC.setDisable(false);
        userView_address.setDisable(false);
        userView_userName.setDisable(false);
        userView_userPassword.setDisable(false);
        userView_bloodGroup.setDisable(true);
        userView_allergies.setDisable(true);
        userView_staffID.setDisable(true);
        userView_staffEmail.setDisable(true);
        userView_staffdoj.setDisable(true);
        userView_speciality.setDisable(true);
    }

    public void setViewForReception(){
        userView_name.setDisable(false);
        userView_gender.setDisable(false);
        userView_marital.setDisable(false);
        userView_dob.setDisable(false);
        userView_phoneNum.setDisable(false);
        userView_NIC.setDisable(false);
        userView_address.setDisable(false);
        userView_userName.setDisable(false);
        userView_userPassword.setDisable(false);
        userView_bloodGroup.setDisable(true);
        userView_allergies.setDisable(true);
        userView_staffID.setDisable(true);
        userView_staffEmail.setDisable(false);
        userView_staffdoj.setDisable(false);
        userView_speciality.setDisable(true);
    }

    public void setViewForPatient(){
        userView_name.setDisable(false);
        userView_gender.setDisable(false);
        userView_marital.setDisable(false);
        userView_dob.setDisable(false);
        userView_phoneNum.setDisable(false);
        userView_NIC.setDisable(false);
        userView_address.setDisable(false);
        userView_userName.setDisable(false);
        userView_userPassword.setDisable(false);
        userView_bloodGroup.setDisable(false);
        userView_allergies.setDisable(false);
        userView_staffID.setDisable(true);
        userView_staffEmail.setDisable(true);
        userView_staffdoj.setDisable(true);
        userView_speciality.setDisable(true);
    }

    public void setViewForMedicalOfficer(){
        userView_name.setDisable(false);
        userView_gender.setDisable(false);
        userView_marital.setDisable(false);
        userView_dob.setDisable(false);
        userView_phoneNum.setDisable(false);
        userView_NIC.setDisable(false);
        userView_address.setDisable(false);
        userView_userName.setDisable(false);
        userView_userPassword.setDisable(false);
        userView_bloodGroup.setDisable(true);
        userView_allergies.setDisable(true);
        userView_staffID.setDisable(true);
        userView_staffEmail.setDisable(false);
        userView_staffdoj.setDisable(false);
        userView_speciality.setDisable(false);
    }


    public void validateInitialize(){

        //Check Input Field Of Name is text
        RegexValidator regexValidator = new RegexValidator();
        regexValidator.setRegexPattern("[A-Za-z\\s]+");
        regexValidator.setMessage("Only Text");
        userView_name.getValidators().add(regexValidator);
        userView_name.focusedProperty().addListener((o, oldValue, newValue) -> {
            if(!newValue)  userView_name.validate();
        });

        //Check Input Field Of Phone Number is number
        NumberValidator numbValid = new NumberValidator();
        numbValid.setMessage("Only Number");
        userView_phoneNum.getValidators().add(numbValid);
        userView_phoneNum.focusedProperty().addListener((o, oldVal,newVal)->{
            if(!newVal) userView_phoneNum.validate();
        });

        //Check Length Of Phone Number
        StringLengthValidator lengthValidatorNumb= new StringLengthValidator(10);
        userView_phoneNum.getValidators().add(lengthValidatorNumb);
        userView_phoneNum.focusedProperty().addListener((o, oldValue, newValue) -> {
            if(!newValue) userView_phoneNum.validate();
        });

    }

    public Boolean validateInputs(){

        Boolean dataInputs = false;

        //Check Input Field Of Name is text
        RegexValidator regexValidator = new RegexValidator();
        regexValidator.setRegexPattern("[A-Za-z\\s]+");
        regexValidator.setMessage("Only Text");
        userView_name.getValidators().add(regexValidator);
        userView_name.focusedProperty().addListener((o, oldValue, newValue) -> {
            if(!newValue)  userView_name.validate();
        });

        //Check Input Field Of Phone Number is number
        NumberValidator numbValid = new NumberValidator();
        numbValid.setMessage("Only Number");
        userView_phoneNum.getValidators().add(numbValid);
        userView_phoneNum.focusedProperty().addListener((o, oldVal,newVal)->{
            if(!newVal) userView_phoneNum.validate();
        });

        //Check Length Of Phone Number
        StringLengthValidator lengthValidatorNumb= new StringLengthValidator(10);
        userView_phoneNum.getValidators().add(lengthValidatorNumb);
        userView_phoneNum.focusedProperty().addListener((o, oldValue, newValue) -> {
            if(!newValue) userView_phoneNum.validate();
        });

        if ((userView_name.validate() && userView_phoneNum.validate())){
            dataInputs = true;
        }
            return dataInputs;
    }

    public Boolean checkInputs(){

        Boolean allCheck =false;

        if (userView_userTypeDrop.getSelectionModel().getSelectedIndex() < 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "User Type is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_name.getText().length() <= 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Name is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_gender.getSelectionModel().getSelectedIndex() < 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Gender is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_marital.getSelectionModel().getSelectedIndex() < 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Marital is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_dob.getValue()==null){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Date of Birth is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_phoneNum.getText().length() <= 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Phone Number is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_NIC.getText().length() <= 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "NIC is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_address.getText().length() <= 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Address is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_userName.getText().length() <= 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "User Name is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else if (userView_userPassword.getText().length() <= 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Password is Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else{
            allCheck = true;
        }
        return allCheck;
    }

}
