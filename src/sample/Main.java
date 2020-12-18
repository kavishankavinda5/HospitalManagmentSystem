package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.controller.MultipleFXMLLoader;
import sample.controller.actionTask.UserAction;
import sample.controller.taskControllers.ReferenceViewController;
import sample.model.*;

import java.time.LocalTime;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainLoginWindow.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        ReferenceViewController.setUserRolls(UserRoll.ADMIN);
        ReferenceViewController.setUserRolls(UserRoll.RECEPTIONIST);
        ReferenceViewController.setUserRolls(UserRoll.PATIENT);
        ReferenceViewController.setUserRolls(UserRoll.MEDICALOFFICER);

        ReferenceViewController.setDoctorSpeciality("BoneSpeciality");
        ReferenceViewController.setDoctorSpeciality("BrainSpeciality");
        ReferenceViewController.setDoctorSpeciality("Kidney Speciality");

        ReferenceViewController.setGender("Male");
        ReferenceViewController.setGender("Female");

        ReferenceViewController.setMaritalStatus("Married");
        ReferenceViewController.setMaritalStatus("Unmarried");

        ReferenceViewController.setBloogGroup(BloodGroup.A_POSITIVE);
        ReferenceViewController.setBloogGroup(BloodGroup.A_NEGATIVE);
        ReferenceViewController.setBloogGroup(BloodGroup.AB_NEGATIVE);
        ReferenceViewController.setBloogGroup(BloodGroup.AB_POSITIVE);
        ReferenceViewController.setBloogGroup(BloodGroup.B_NEGATIVE);
        ReferenceViewController.setBloogGroup(BloodGroup.B_POSITIVE);
        ReferenceViewController.setBloogGroup(BloodGroup.O_NEGATIVE);
        ReferenceViewController.setBloogGroup(BloodGroup.O_POSITIVE);

        ReferenceViewController.setPostalTypes(PostalType.DISPATCH);
        ReferenceViewController.setPostalTypes(PostalType.RECEIVED);


      launch(args);
    }

    public static void liveClock(Label dispalyLable) {
        //live clock
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            dispalyLable.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    public static Pane getView(String fileName) {
        //receptionView_task.setVisible(false);
        System.out.println("you clicked " + fileName);
        MultipleFXMLLoader newFXML = new MultipleFXMLLoader();
        Pane viewCurrent = newFXML.getPage(fileName);

        return viewCurrent;
    }

}
