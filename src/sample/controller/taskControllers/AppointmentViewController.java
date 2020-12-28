package sample.controller.taskControllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.controller.actionTask.AppointmentAction;
import sample.controller.actionTask.ReferenceAction;
import sample.controller.actionTask.UserAction;
import sample.model.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppointmentViewController {

    Patient selectedPatient;
    MedicalOfficer seletedDoctor;
    Appointment currentAppointment;
    boolean isDocTableSet =false;
    boolean isMainTablePending =false;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Appointment> appointmentView_mainTable;


    @FXML
    private JFXButton appointmentView_addAppointment;

    @FXML
    private JFXButton appointmentView_updateAppoin;

    @FXML
    private JFXButton appointmentView_deleteAppoin;

    @FXML
    private JFXButton appointmentView_viewAllAppoin;

    @FXML
    private JFXButton appointmentView_viewPending;

    @FXML
    private AnchorPane appointmentView_mainPane;

    @FXML
    private JFXTextField appointmentView_patient;

    @FXML
    private JFXTextField appointmentView_doctor;

    @FXML
    private JFXTextArea appointmentView_symtoms;

    @FXML
    private JFXComboBox<AppointmentStatus> appointmentView_status;

    @FXML
    private DatePicker appointmentView_APdate;

    @FXML
    private TextField appointmentView_searchID;

    @FXML
    private JFXButton appointmentView_searchButton;

    @FXML
    private JFXTextField appointmentView_timeHour;

    @FXML
    private JFXTextField appointmentView_timeMinute;

    @FXML
    private TabPane appointmentView_mainTabPane;

    @FXML
    private Tab appointmentView_patientTab;

    @FXML
    private Pane appointmentView_patientDetails;

    @FXML
    private JFXButton appointmentView_selectPatient;

    @FXML
    private JFXTextField appointmentView_patientID;

    @FXML
    private JFXButton appointmentView_patientSearch;

    @FXML
    private Tab appointmentView_doctorTab;

    @FXML
    private Tab appointmentView_apDetailsTab;

    @FXML
    private JFXComboBox<String> appointmentView_doctorSpecDrop;

    @FXML
    private TableView<MedicalOfficer> appointmentView_docTable;

    @FXML
    private Pane appointmentView_DocDetails;

    @FXML
    private JFXButton appointmentView_selectDoc;

    @FXML
    private JFXButton appointmentView_addPatientBy;

    @FXML
    private JFXButton appointmentView_addDoctorBy;

    @FXML
    private Label appointmentView_patientShow;

    @FXML
    private Label appointmentView_doctorShow;

    @FXML
    private JFXButton appointmentView_doctorselectButton;

    @FXML
    private JFXButton appointmentView_viewApproved;

    @FXML
    private JFXButton appointmentView_viewCompleted;

    @FXML
    private JFXButton appointmentView_resetViewBtn;

    @FXML
    private Label appointmentView_apIDLabel;

    @FXML
    private TextArea appointmentView_appointmentDetails;



    @FXML
    void initialize() {

        currentAppointment =new Appointment();

        appointmentView_status.getItems().addAll(ReferenceAction.apointmentStatus);

        appointmentView_doctorSpecDrop.getItems().addAll(ReferenceAction.getDocSpecialityStringArray());

        appointmentView_patientSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String patientID =appointmentView_patientID.getText();
                Patient patient = UserAction.searchPatient(patientID,null,null);
                appointmentView_patientShow.setText(
                        "Name : "+patient.getName()+
                         "\nID : " +patient.getIdCardNumber()
                        +"\nContact Number : "+patient.getPhoneNumber());
                selectedPatient =patient;

            }
        });

        appointmentView_selectPatient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                appointmentView_patient.setText(selectedPatient.getName());
                currentAppointment.setPatient(selectedPatient);
                System.out.println("selected patient : "+selectedPatient.toString());
            }
        });

        appointmentView_doctorselectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String docSpectArea = appointmentView_doctorSpecDrop.getValue();
                ArrayList<MedicalOfficer> medicBySpec = UserAction.getMedicalOfficerBySpeciality(docSpectArea);

                ObservableList<MedicalOfficer> medicalOfficerBySpec = FXCollections.observableArrayList(medicBySpec);

                if (!isDocTableSet){
                    isDocTableSet =true;
                    TableColumn id =new TableColumn("Doctor Name");
                    appointmentView_docTable.getColumns().addAll(id);
                    id.setCellValueFactory(new PropertyValueFactory<MedicalOfficer,String>("name"));
                }
                appointmentView_docTable.setItems(medicalOfficerBySpec);



            }
        });

        appointmentView_docTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MedicalOfficer selectedOfficer = appointmentView_docTable.getSelectionModel().getSelectedItem();
                appointmentView_doctorShow.setText("Name : "+selectedOfficer.getName()+"\n"+
                       "Spectiality :"+ selectedOfficer.getSpeciality());
                seletedDoctor =selectedOfficer;
                System.out.println("selected medicalOfficer"+selectedOfficer.toString());
            }
        });

        appointmentView_selectDoc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentAppointment.setMedicalOfficer(seletedDoctor);
                appointmentView_doctor.setText(seletedDoctor.getName());
            }
        });

        appointmentView_addAppointment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(getInitialAppointment().toString());
                currentAppointment.setAppointmentStatus(AppointmentStatus.PENDING);
                AppointmentAction.addAppointment(getInitialAppointment());
            }
        });

        appointmentView_resetViewBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetDisplay();
            }
        });

        appointmentView_searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int apID =Integer.parseInt(appointmentView_searchID.getText());
                Appointment foundAppointment =AppointmentAction.searchAppointmentByID(apID);
                displayAppointmentDetailTab(foundAppointment);
                setCurrentAppointment(foundAppointment);
                displayAppointment(foundAppointment);
            }
        });

        appointmentView_viewPending.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Appointment> pendingAppointments = AppointmentAction.getAppointmentByStatus(1);
                ObservableList<Appointment> pendingAp = FXCollections.observableArrayList(pendingAppointments);
                setMainTable();
                appointmentView_mainTable.setItems(pendingAp);
            }
        });

        appointmentView_viewApproved.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Appointment> approvedAppointments = AppointmentAction.getAppointmentByStatus(2);
                ObservableList<Appointment> approvedAp = FXCollections.observableArrayList(approvedAppointments);
                setMainTable();
                appointmentView_mainTable.setItems(approvedAp);
            }
        });

        appointmentView_viewCompleted.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Appointment> completedAppointments = AppointmentAction.getAppointmentByStatus(3);
                ObservableList<Appointment> completedAp = FXCollections.observableArrayList(completedAppointments);
                setMainTable();
                appointmentView_mainTable.setItems(completedAp);
            }
        });

        appointmentView_mainTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Appointment selectedOppintment = appointmentView_mainTable.getSelectionModel().getSelectedItem();
                displayAppointment(selectedOppintment);
                displayAppointmentDetailTab(selectedOppintment);

            }
        });

        appointmentView_updateAppoin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AppointmentAction.updateAppointment(getCurrentAppointment());
            }
        });

        appointmentView_deleteAppoin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AppointmentAction.deleteAppointment(getCurrentAppointment());
            }
        });

        appointmentView_viewAllAppoin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Appointment> allAp = AppointmentAction.getAppointmentArrayList();
                ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(allAp);
                setMainTable();
                appointmentView_mainTable.setItems(allAppointments);

            }
        });

        appointmentView_addPatientBy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                appointmentView_mainTabPane.getSelectionModel().select(appointmentView_patientTab);
            }
        });

        appointmentView_addDoctorBy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                appointmentView_mainTabPane.getSelectionModel().select(appointmentView_doctorTab);
            }
        });



    }

    public Appointment getInitialAppointment(){
        Appointment appointment=new Appointment();
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment.setAppointmentID(Main.getAppointmentID());
        appointment.setPatient(this.currentAppointment.getPatient());
        appointment.setMedicalOfficer(this.currentAppointment.getMedicalOfficer());
        appointment.setAppointmentDate(appointmentView_APdate.getValue());
        appointment.setTime(new AppointmentTime(appointmentView_timeHour.getText(),appointmentView_timeMinute.getText()));
        appointment.setSymtomes(appointmentView_symtoms.getText());

        return appointment;
    }

    public Appointment getCurrentAppointment(){
        Appointment appointment=new Appointment();
        appointment.setAppointmentStatus(appointmentView_status.getValue());
        appointment.setAppointmentID(Integer.parseInt(appointmentView_apIDLabel.getText()));
        appointment.setPatient(currentAppointment.getPatient());
        appointment.setMedicalOfficer(currentAppointment.getMedicalOfficer());
        appointment.setAppointmentDate(appointmentView_APdate.getValue());
        appointment.setTime(new AppointmentTime(appointmentView_timeHour.getText(),appointmentView_timeMinute.getText()));
        appointment.setSymtomes(appointmentView_symtoms.getText());

        return appointment;
    }

    public void displayAppointment(Appointment appointment){

        appointmentView_APdate.setValue(appointment.getAppointmentDate());
        appointmentView_timeHour.setText(appointment.getTime().getHours());
        appointmentView_timeMinute.setText(appointment.getTime().getMinutes());
        appointmentView_status.setValue(appointment.getAppointmentStatus());
        appointmentView_patient.setText(appointment.getPatient().getName());
        appointmentView_doctor.setText(appointment.getMedicalOfficer().getName());
        appointmentView_symtoms.setText(appointment.getSymtomes());
        appointmentView_apIDLabel.setText(String.valueOf(appointment.getAppointmentID()));
    }

    public void resetDisplay(){
        appointmentView_APdate.setValue(null);
        appointmentView_timeHour.setText(null);
        appointmentView_timeMinute.setText(null);
        appointmentView_status.setValue(null);
        appointmentView_patient.setText(null);
        appointmentView_doctor.setText(null);
        appointmentView_symtoms.setText(null);
        appointmentView_doctorSpecDrop.setValue(null);
        appointmentView_docTable.setItems(null);
        appointmentView_doctorShow.setText(null);
        appointmentView_patientShow.setText(null);
        appointmentView_patientID.setText(null);
        appointmentView_searchID.setText(null);
        appointmentView_appointmentDetails.setText(null);
        appointmentView_apIDLabel.setText(null);
        appointmentView_mainTable.setItems(null);
        appointmentView_mainTabPane.getSelectionModel().select(appointmentView_apDetailsTab);


    }

    public void setCurrentAppointment(Appointment currentAppointment) {
        this.currentAppointment = currentAppointment;
    }

    public void displayAppointmentDetailTab(Appointment appointment){
        appointmentView_mainTabPane.getSelectionModel().select(appointmentView_apDetailsTab);
        appointmentView_appointmentDetails.setText(
                "----Patient Details ---- \n"+
                        "Name : "+appointment.getPatient().getName()+
                        "\nContact Number : "+appointment.getPatient().getPhoneNumber()+
                        "\nAppointment Date : "+appointment.getAppointmentDate()+
                        "\nAppointment Time : "+appointment.getTime()+
                        "\n"+
                        "\n----Medical Officer Details----  \n"+
                        "Name : "+appointment.getMedicalOfficer().getName()+
                        "\nSpeciality : "+appointment.getMedicalOfficer().getSpeciality()
        );
    }

    public void setMainTable(){

        if (!isMainTablePending){
            isMainTablePending =true;
            TableColumn id =new TableColumn("AppointmentID");
            TableColumn status =new TableColumn("Appointment Status");
            TableColumn ApDate =new TableColumn("Appointment Date");

            appointmentView_mainTable.getColumns().addAll(id,status,ApDate);
            id.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("appointmentID"));
            status.setCellValueFactory(new PropertyValueFactory<Appointment,AppointmentStatus>("appointmentStatus"));
            ApDate.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDate>("appointmentDate"));

        }
    }

}
