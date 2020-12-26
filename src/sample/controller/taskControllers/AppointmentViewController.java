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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.controller.actionTask.ReferenceAction;
import sample.controller.actionTask.UserAction;
import sample.model.Appointment;
import sample.model.MedicalOfficer;
import sample.model.Patient;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppointmentViewController {

    Appointment currentAppointment;
    boolean isDocTableSet =false;
    boolean isPatientTableSet =false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> appointmentView_mainTable;

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
    private JFXComboBox<?> appointmentView_status;

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
    private TableView<?> appointmentView_patientTable;

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
    private JFXButton appointmentView_doctorselectButton;

    @FXML
    void initialize() {

        appointmentView_doctorSpecDrop.getItems().addAll(ReferenceAction.getDocSpecialityStringArray());

        currentAppointment =new Appointment();

        appointmentView_patientSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String patientID =appointmentView_patientID.getText();
                Patient patient = UserAction.searchPatient(patientID,null,null);
                appointmentView_patientShow.setText("Name : "+patient.getName()+"\n ID : "+patient.getIdCardNumber());
                currentAppointment.setPatient(patient);

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


    }

}
