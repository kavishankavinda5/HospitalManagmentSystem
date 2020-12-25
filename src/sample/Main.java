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
import sample.controller.SystemDataReader;
import sample.controller.actionTask.ReferenceAction;
import sample.controller.actionTask.UserAction;
import sample.model.*;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    private static int referenceID ;
    private static int staffID ;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainLoginWindow.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {

        ReferenceAction.loadReference();
        ReferenceAction.setPostalTypes(PostalType.DISPATCH);
        ReferenceAction.setPostalTypes(PostalType.RECEIVED);

        System.out.println(UserAction.getMedicalOfficerBySpeciality("brain").toString());

        loadSystemData();
        launch(args);
        saveSystemData();
        System.out.println("system closing");
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

    public static void loadSystemData(){

        SystemDataReader systemDataReader =new SystemDataReader();
        ArrayList<String> systemData = systemDataReader.getTempDataArray("src/sample/fileStorage/systemData.txt");
        for (int i=0;i<systemData.size();i++){
            String line =systemData.get(i);
            List<String> temSystemDataList = Arrays.asList(line.split("~"));
            switch (temSystemDataList.get(0)){
                case "staffID":
                    staffID = Integer.parseInt(temSystemDataList.get(1));
                    System.out.println("staff ID : "+staffID);
                    break;
                case "referenceID":
                    referenceID =Integer.parseInt(temSystemDataList.get(1));
                    System.out.println("reference ID : "+referenceID);
                    break;
                default:
                    break;

            }
        }

    }

    public static void saveSystemData(){
        File file =new File("src/sample/fileStorage/systemData.txt");
        file.delete();

        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("staffID~"+staffID);
            bufferedWriter.newLine();
            bufferedWriter.write("referenceID~"+referenceID);

            bufferedWriter.close();
            fileWriter.close();
            System.out.println("system data saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getStaffID(){
        staffID ++;
        return staffID;
    }

    public static int getReferenceID(){
        referenceID++;
        return referenceID;
    }


}
