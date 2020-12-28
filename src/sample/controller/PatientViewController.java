package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;
import sample.controller.actionTask.ReferenceAction;
import sample.model.BloodGroup;
import sample.model.Gender;
import sample.model.Patient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientViewController {

    private Patient currentpatient;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane patientView;

    @FXML
    private ImageView patientView_home;

    @FXML
    private JFXButton patientView_appointment;

    @FXML
    private JFXButton patientView_complaint;

    @FXML
    private Pane patientMain_logout;

    @FXML
    private JFXButton patientView_logoutButton;

    @FXML
    private ImageView patientMain_backIcon;

    @FXML
    private AnchorPane patientView_homePane;

    @FXML
    private Label patientView_userName;

    @FXML
    private JFXTextField patientView_name;

    @FXML
    private JFXTextField patientView_telephone;

    @FXML
    private JFXTextField patientView_idNumber;

    @FXML
    private JFXTextArea patientView_address;

    @FXML
    private JFXTextArea patientView_allergies;

    @FXML
    private JFXComboBox<Gender> patientView_gender;
    @FXML
    private JFXComboBox<String> patientView_maritalStatus;

    @FXML
    private JFXComboBox<BloodGroup> patientView_bloodGroup;

    @FXML
    private DatePicker patientView_dob;

    @FXML
    void initialize() {

        patientView_gender.getItems().addAll(ReferenceAction.getGender());
        patientView_bloodGroup.getItems().addAll(ReferenceAction.getBloogGroup());
        patientView_maritalStatus.getItems().addAll(ReferenceAction.getMaritalStatus());

        patientView_appointment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/appointmentView");
                    Pane view = Main.getView("taskView/appointmentView");
                    setPatientViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        patientView_complaint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/complaintView");
                    Pane view = Main.getView("taskView/complaintView");
                    setPatientViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        patientView_logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Object[] options = { "OK", "CANCEL" };
                Toolkit.getDefaultToolkit().beep();
                int selectedValue = JOptionPane.showOptionDialog(null, "Are You Sure LogOut"+"\nClick OK to continue", "Warning",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);

                if (selectedValue == JOptionPane.WHEN_FOCUSED) {

                    patientMain_logout.getScene().getWindow().hide();
                    Stage detailsStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();

                    loader.setLocation(getClass().getResource("/sample/view/mainLoginWindow.fxml"));
                    try {
                        loader.load();

                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    Parent root = loader.getRoot();
                    detailsStage.setScene(new Scene(root));
                    detailsStage.show();

                }


            }
        });

        patientView_home.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                patientView.setCenter(patientView_homePane);
            }
        });

    }


    public void setPatientViewCenter(Pane view){
        patientView.setCenter(view);

    }

    public Patient getCurrentpatient() {
        return currentpatient;
    }

    public void setCurrentpatient(Patient currentpatient) {
        this.currentpatient = currentpatient;
        this.patientView_name.setText(currentpatient.getName());
        this.patientView_telephone.setText(currentpatient.getPhoneNumber());
        this.patientView_idNumber.setText(currentpatient.getIdCardNumber());
        this.patientView_gender.setValue(currentpatient.getGender());
        this.patientView_allergies.setText(currentpatient.getAllergies());
        this.patientView_bloodGroup.setValue(currentpatient.getBloodGroup());
        this.patientView_maritalStatus.setValue(currentpatient.getMaritalStatus());
        this.patientView_address.setText(currentpatient.getAddress());
        this.patientView_dob.setValue(currentpatient.getDob());

        System.out.println( "Patient in dashboard"+currentpatient.toString());


    }


}
