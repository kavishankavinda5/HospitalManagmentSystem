package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionMainViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private BorderPane mainReceptionView;

    @FXML
    private JFXButton receptionView_viewVisitor;

    @FXML
    private JFXButton receptionView_viewPatient;

    @FXML
    private JFXButton receptionView_viewPostal;

    @FXML
    private JFXButton receptionView_viewAppointment;

    @FXML
    private JFXButton receptionView_viewComplaint;

    @FXML
    private ImageView receptionView_home;

    @FXML
    private JFXButton receptionView_addVisitor;

    @FXML
    private JFXButton receptionView_addComplaint;

    @FXML
    private JFXButton receptionView_addPostal;

    @FXML
    private JFXButton receptionView_addAppointment;

    @FXML
    private AnchorPane receptionView_homePane;

    @FXML
    void initialize() {
        receptionView_viewPatient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/patient/viewEditDeletePatient");
                    Pane view = getView("taskView/patient/viewEditDeletePatient");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        receptionView_viewVisitor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               try {
                   System.out.println("taskView/visitor/viewEditDeleteVisitor");
                   Pane view = getView("taskView/visitor/viewEditDeleteVisitor");
                   setReceptionViewCenter(view);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });

        receptionView_viewPostal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("you clicked receptionView_viewPostal view");
                try {
                    Pane view = getView("taskView/postal/viewEditDeletePostal");
                    setReceptionViewCenter(view);
                } catch (Exception e){
                e.printStackTrace();
            }

            }
        });

        receptionView_viewAppointment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/appointment/viewEditDeleteAppointment");
                    Pane view = getView("taskView/appointment/viewEditDeleteAppointment");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        receptionView_home.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                setReceptionViewCenter(receptionView_homePane);
            }
        });

        receptionView_viewComplaint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/complaint/viewEditDeleteComplaint");
                    Pane view = getView("taskView/complaint/viewEditDeleteComplaint");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        receptionView_addVisitor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/visitor/addVisitor");
                    Pane view = getView("taskView/visitor/addVisitor");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        receptionView_addAppointment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    System.out.println("taskView/appointment/addAppointment");
                    Pane view = getView("taskView/appointment/addAppointment");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        receptionView_addComplaint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/complaint/addComplaint");
                    Pane view = getView("taskView/complaint/addComplaint");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        receptionView_addPostal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/postal/addPostal");
                    Pane view = getView("taskView/postal/addPostal");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public Pane getView(String fileName){
        //receptionView_task.setVisible(false);
        System.out.println("you clicked receptionView//patient");
        MultipleFXMLLoader newFXML = new MultipleFXMLLoader();
        Pane view = newFXML.getPage(fileName);

        return view;
    }


    public void setReceptionViewCenter(Pane view){
        mainReceptionView.setCenter(view);

    }

    public void setReceptionViewLeft(Pane view){
        mainReceptionView.setLeft(view);
    }

}
