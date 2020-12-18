package sample.controller.taskControllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.StringConverter;
import sample.controller.actionTask.PostalAction;

import sample.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static sample.model.PostalType.*;

public class PostalViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Label postalView_addressLable;

    @FXML
    private Label postalView_fromAddressLable;

    @FXML
    private TableView<?> postalView_userTable;

    @FXML
    private JFXButton postalView_addPostal;

    @FXML
    private JFXButton postalView_updatePostal;

    @FXML
    private JFXButton postalView_deletePostal;

    @FXML
    private JFXButton postalView_viewAll;

    @FXML
    private JFXButton postalView_reset;

    @FXML
    private JFXComboBox<PostalType> postalView_type;

    @FXML
    private JFXTextField postalView_refecenceNo;

    @FXML
    private JFXTextField postalView_toName;

    @FXML
    private JFXTextField postalView_fromName;

    @FXML
    private com.jfoenix.controls.JFXTextArea postalView_note;

    @FXML
    private com.jfoenix.controls.JFXTextArea  postalView_address;

    @FXML
    private com.jfoenix.controls.JFXTextArea  postalView_fromAddress;

    @FXML
    private DatePicker postalView_date;


    @FXML
    void initialize() {

        postalView_type.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (postalView_type.getValue() == RECEIVED) {
                    postalView_address.setDisable(true);

                } else {
                    postalView_fromAddress.setDisable(true);
                }

            }

        });

        //set the derop down wit the data taken by the reference module
       postalView_type.getItems().addAll(ReferenceViewController.getPostalTypes());


        postalView_addPostal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                postalView_date.setConverter(new StringConverter<LocalDate>() {
                    final String pattern = "yyyy-MM-dd";
                    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                    {
                        postalView_date.setPromptText(pattern.toLowerCase());
                    }

                    @Override
                    public String toString(LocalDate date) {
                        if (date != null) {
                            return dateFormatter.format(date);
                        } else {
                            return "";
                        }
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        if (string != null && !string.isEmpty()) {
                            return LocalDate.parse(string, dateFormatter);
                        } else {
                            return null;
                        }
                    }
                });


                switch (postalView_type.getValue()) {

                    case RECEIVED:
                        ReceivedPostal receivedPostal = new ReceivedPostal();
                        receivedPostal.setPostalType(postalView_type.getValue());
                        receivedPostal.setReferenceNo(postalView_refecenceNo.getText());
                        receivedPostal.setToName(postalView_toName.getText());
                        receivedPostal.setFromName(postalView_fromName.getText());
                        receivedPostal.setNote(postalView_note.getText());
                        receivedPostal.setFromAddress(postalView_fromAddress.getText());
                        receivedPostal.setDate(postalView_date.getValue());

                        PostalAction.addReceived(receivedPostal, UserRoll.RECEPTIONIST);

                    case DISPATCH:
                        DispatchPostal dispatchPostal = new DispatchPostal();
                        dispatchPostal.setPostalType(postalView_type.getValue());
                        dispatchPostal.setReferenceNo(postalView_refecenceNo.getText());
                        dispatchPostal.setToName(postalView_toName.getText());
                        dispatchPostal.setFromName(postalView_fromName.getText());
                        dispatchPostal.setNote(postalView_note.getText());
                        dispatchPostal.setAddress(postalView_address.getText());
                        dispatchPostal.setDate(postalView_date.getValue());


                        PostalAction.addDispatch(dispatchPostal, UserRoll.RECEPTIONIST);
                }


            }});

    }
}