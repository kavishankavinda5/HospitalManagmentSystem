package sample.controller.taskControllers;


import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentViewController {

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
    private JFXComboBox<?> appointmentView_doctorSpecDrop;

    @FXML
    private TableView<?> appointmentView_docTable;

    @FXML
    private Pane appointmentView_DocDetails;

    @FXML
    private JFXButton appointmentView_selectDoc;

    @FXML
    private JFXButton appointmentView_addPatientBy;

    @FXML
    private JFXButton appointmentView_addDoctorBy;

    @FXML
    void initialize() {

        appointmentView_mainTabPane.setVisible(false);
    }

}
