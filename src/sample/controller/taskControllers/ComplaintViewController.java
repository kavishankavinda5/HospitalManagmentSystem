package sample.controller.taskControllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import sample.controller.actionTask.ReferenceAction;

import java.net.URL;
import java.util.ResourceBundle;

public class ComplaintViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane vedComplaintView;

    @FXML
    private JFXComboBox<String> complaintView_complaintDropDown;

    @FXML
    void initialize() {
       //   complaintView_complaintDropDown.getItems().addAll(ReferenceAction.getComp);
    }
}
