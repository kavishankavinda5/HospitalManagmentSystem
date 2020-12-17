package sample.controller.taskControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.getView;

public class ReportGenController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private BorderPane reportGenarator_boarderPane;

    @FXML
    private AnchorPane reportGenarator_topTask;


    @FXML
    private ComboBox<String> reportGenarator_reportType;

    @FXML
    void initialize() {
        reportGenarator_reportType.getItems().add("UserLog Report");
        reportGenarator_reportType.getItems().add("Appointment Report");
        reportGenarator_reportType.getItems().add("Patientlogin Report");


        reportGenarator_reportType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switch (reportGenarator_reportType.getValue()){
                    case "UserLog Report":
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(Main.class.getResource("/sample/view/taskView/extra_report_view/reportsUserLog.fxml"));
                            loader.getController();
                            reportGenarator_boarderPane.setCenter(loader.load());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case "Appointment Report":

                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(Main.class.getResource("/sample/view/taskView/extra_report_view/reportsAppointment.fxml"));
                            loader.getController();
                            reportGenarator_boarderPane.setCenter(loader.load());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case "Patientlogin Report" :

                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(Main.class.getResource("/sample/view/taskView/extra_report_view/reportsPatientlogin.fxml"));
                            loader.getController();
                            reportGenarator_boarderPane.setCenter(loader.load());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                }
            }
        });

    }
}
