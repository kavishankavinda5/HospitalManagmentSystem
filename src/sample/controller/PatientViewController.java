package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientViewController {

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
    void initialize() {
        assert patientView != null : "fx:id=\"mainReceptionView\" was not injected: check your FXML file 'patientMainView.fxml'.";
        assert patientView_home != null : "fx:id=\"patientView_home\" was not injected: check your FXML file 'patientMainView.fxml'.";
        assert patientView_appointment != null : "fx:id=\"receptionView_appointment\" was not injected: check your FXML file 'patientMainView.fxml'.";
        assert patientView_complaint != null : "fx:id=\"patientView_complaint\" was not injected: check your FXML file 'patientMainView.fxml'.";
        assert patientMain_logout != null : "fx:id=\"receptionMain_logout\" was not injected: check your FXML file 'patientMainView.fxml'.";
        assert patientView_logoutButton != null : "fx:id=\"patientView_logoutButton\" was not injected: check your FXML file 'patientMainView.fxml'.";
        assert patientMain_backIcon != null : "fx:id=\"adminMain_backIcon\" was not injected: check your FXML file 'patientMainView.fxml'.";
        assert patientView_homePane != null : "fx:id=\"receptionView_homePane\" was not injected: check your FXML file 'patientMainView.fxml'.";
        assert patientView_userName != null : "fx:id=\"patientView_userName\" was not injected: check your FXML file 'patientMainView.fxml'.";


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

}
