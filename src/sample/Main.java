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
import org.jetbrains.annotations.NotNull;
import sample.controller.MultipleFXMLLoader;
import sample.controller.actionTask.UserAction;
import sample.controller.taskControllers.referenceViewController;
import sample.model.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainLoginWindow.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        referenceViewController.setUserRolls(UserRoll.ADMIN);
        referenceViewController.setUserRolls(UserRoll.RECEPTIONIST);
        referenceViewController.setUserRolls(UserRoll.PATIENT);
        referenceViewController.setUserRolls(UserRoll.MEDICALOFFICER);

        referenceViewController.setDoctorSpeciality("BoneSpeciality");
        referenceViewController.setDoctorSpeciality("BrainSpeciality");
        referenceViewController.setDoctorSpeciality("Kidney Speciality");

        referenceViewController.setGender("Male");
        referenceViewController.setGender("Female");

        referenceViewController.setMaritalStatus("Married");
        referenceViewController.setMaritalStatus("Unmarried");

        referenceViewController.setBloogGroup(BloodGroup.A_POSITIVE);
        referenceViewController.setBloogGroup(BloodGroup.A_NEGATIVE);
        referenceViewController.setBloogGroup(BloodGroup.AB_NEGATIVE);
        referenceViewController.setBloogGroup(BloodGroup.AB_POSITIVE);


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
