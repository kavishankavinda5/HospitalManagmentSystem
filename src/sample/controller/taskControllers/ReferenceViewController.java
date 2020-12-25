package sample.controller.taskControllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.controller.actionTask.ReferenceAction;
import sample.model.Reference;

import java.net.URL;
import java.util.ResourceBundle;

public class ReferenceViewController {

    private static Reference selectedRef;
    private boolean isTableSet =false;

    ObservableList<Reference> refComplaintData;
    ObservableList<Reference> refDoctorSpecility;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox<String> referenceView_dropDown;

    @FXML
    private JFXTextField referenceView_AVEDreference;

    @FXML
    private JFXButton referenceView_add;

    @FXML
    private JFXButton referenceView_update;

    @FXML
    private JFXButton referenceView_delete;

    @FXML
    private JFXButton referenceView_reset;

    @FXML
    private TableView<Reference> referenceView_table;

    @FXML
    void initialize() {

        refComplaintData = FXCollections.observableArrayList(ReferenceAction.getComplaintRefArrayList());
        refDoctorSpecility = FXCollections.observableArrayList(ReferenceAction.getDoctorSpecialityArray());
        System.out.println(refComplaintData.toString());

        referenceView_dropDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (referenceView_dropDown.getValue() =="Complaint Types"){
                    getReferenceTable(refComplaintData);
                }
                if (referenceView_dropDown.getValue() =="Doctor Speciality"){
                    getReferenceTable(refDoctorSpecility);
                }

            }
        });

        referenceView_dropDown.getItems().addAll(ReferenceAction.referenceTypes);

        referenceView_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            Reference reference =new Reference(Main.getReferenceID(),referenceView_dropDown.getValue(), referenceView_AVEDreference.getText());
            ReferenceAction.addReference(reference);
            loadReferenceViewData();
            }
        });

        referenceView_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               Reference reference =new Reference(selectedRef.getRefenceID(), referenceView_dropDown.getValue(),referenceView_AVEDreference.getText());
               ReferenceAction.updateOrDeleteReference(reference,3);
               loadReferenceViewData();
            }
        });

        referenceView_update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String referenceValue =referenceView_AVEDreference.getText();
                Reference updateReference =new Reference(selectedRef.getRefenceID(), referenceView_dropDown.getValue(),referenceValue);
                System.out.println("reference in update : "+ updateReference);
                ReferenceAction.updateOrDeleteReference(updateReference,1);
                loadReferenceViewData();

            }
        });

        referenceView_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedRef = referenceView_table.getSelectionModel().getSelectedItem();
                System.out.println(selectedRef);
                System.out.println("selected ref :"+selectedRef);
                referenceView_AVEDreference.setText(selectedRef.getReferenceValue());
            }
        });



    }


    public void getReferenceTable(ObservableList<Reference> refData){

        if (!isTableSet){
            TableColumn id =new TableColumn("refID");
            TableColumn refValue =new TableColumn("Reference value");
            referenceView_table.getColumns().addAll(id,refValue);
            id.setCellValueFactory(new PropertyValueFactory<Reference,String>("refenceID"));
            refValue.setCellValueFactory(new PropertyValueFactory<Reference,String>("referenceValue"));
            isTableSet =true;
        }
        referenceView_table.setItems(refData);

    }

    public void loadReferenceViewData(){
        refComplaintData =  FXCollections.observableArrayList(ReferenceAction.getComplaintRefArrayList());
        refDoctorSpecility = FXCollections.observableArrayList(ReferenceAction.getDoctorSpecialityArray());
        if (referenceView_dropDown.getValue() =="Complaint Types"){
            referenceView_table.setItems(refComplaintData);
        }else {
            referenceView_table.setItems(refDoctorSpecility);
        }
    }

}
