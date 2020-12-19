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

    }
}
