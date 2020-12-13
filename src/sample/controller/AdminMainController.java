package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView adminMain_mainHome;

    @FXML
    private BorderPane adminMain_boarderPane;

    @FXML
    private JFXButton adminMain_viewReference;

    @FXML
    private JFXButton adminMain_viewUser;

    @FXML
    private JFXButton adminMain_viewPostal;

    @FXML
    private JFXButton adminMain_viewAppointment;

    @FXML
    private JFXButton adminMain_viewComplaint;

    @FXML
    private JFXButton adminMain_addReference;

    @FXML
    private JFXButton adminMain_addUser;

    @FXML
    private JFXButton adminMain_generateReport;

    @FXML
    private AnchorPane adminMain_loaderPane;

    @FXML
    private Pane adminMain_logout;

    @FXML
    private JFXButton adminMain_logoutButton;

    @FXML
    void initialize() {

        adminMain_viewUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/user/viewEditDeleteUser");
                    Pane view = Main.getView("taskView/user/viewEditDeleteUser");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        adminMain_addUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println();
                    Pane view = Main.getView("taskView/user/addUser");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        adminMain_addReference.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/reference/addReference");
                    Pane view = Main.getView("taskView/reference/addReference");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        adminMain_viewReference.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/reference/viewEditDeleteReference");
                    Pane view = Main.getView("taskView/reference/viewEditDeleteReference");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        adminMain_viewComplaint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/complaint/viewEditDeleteComplaint");
                    Pane view = Main.getView("taskView/complaint/viewEditDeleteComplaint");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        adminMain_viewAppointment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/appointment/viewEditDeleteAppointment");
                    Pane view = Main.getView("taskView/appointment/viewEditDeleteAppointment");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        adminMain_viewPostal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/postal/viewEditDeletePostal");
                    Pane view = Main.getView("taskView/postal/viewEditDeletePostal");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        adminMain_generateReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/report/generateReport");
                    Pane view = Main.getView("taskView/report/generateReport");
                    setReceptionViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        adminMain_mainHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                adminMain_boarderPane.setCenter(adminMain_loaderPane);
            }
        });


        adminMain_logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                adminMain_logout.getScene().getWindow().hide();
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


    }

    public void setReceptionViewCenter(Pane view){
        adminMain_boarderPane.setCenter(view);

    }



}
