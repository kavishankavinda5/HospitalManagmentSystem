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
import sample.controller.actionTask.ReferenceAction;
import sample.model.Reference;

import java.net.URL;
import java.util.ResourceBundle;

public class ReferenceViewController {


    private Reference selectedRef;
    private boolean isComlaintTableSet =false;
    private boolean isDocSpecSet =false;
    ObservableList<Reference> refComplaintData;


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
        System.out.println(refComplaintData.toString());

//        TableColumn id =new TableColumn("Complaint List");
//        referenceView_table.getColumns().add(id);
//        id.setCellValueFactory(new PropertyValueFactory<Reference,String>("referenceValue"));
//        referenceView_table.setItems(refComplaintData);

        referenceView_dropDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (!isComlaintTableSet){
                    TableColumn id =new TableColumn("Complaint List");
                    referenceView_table.getColumns().add(id);
                    id.setCellValueFactory(new PropertyValueFactory<Reference,String>("referenceValue"));
                    referenceView_table.setItems(refComplaintData);
                    //isComlaintTableSet =true;
                }

            }
        });

        referenceView_dropDown.getItems().addAll(ReferenceAction.referenceTypes);

        referenceView_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Reference reference =new Reference(  referenceView_dropDown.getValue(), referenceView_AVEDreference.getText());
                ReferenceAction.addReference(reference);
                refComplaintData =  FXCollections.observableArrayList(ReferenceAction.getComplaintRefArrayList());
                referenceView_table.setItems(refComplaintData);


            }
        });

        referenceView_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Reference reference =new Reference(referenceView_dropDown.getValue(),referenceView_AVEDreference.getText());
                ReferenceAction.updateOrDeleteReference(reference,null,2);
            }
        });

        referenceView_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Reference oldReference = referenceView_table.getSelectionModel().getSelectedItem();
                System.out.println(oldReference);
                selectedRef =oldReference;
                System.out.println("selected ref :"+selectedRef);
                referenceView_AVEDreference.setText(oldReference.getReferenceValue());
            }
        });



    }


}
