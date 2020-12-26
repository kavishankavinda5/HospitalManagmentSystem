package sample.controller.actionTask;

import sample.model.DispatchPostal;
import sample.model.PostalType;
import sample.model.ReceivedPostal;
import sample.model.UserRoll;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//create a postalAction class

public class PostalAction {
   //received postal data recoded in receivedData.txt file
    public static  String receivedFilePath="src/sample/fileStorage/moduleData/postalData/receivedData.txt";

    //dispatch postal data recoded in dispatch .txt file
    public static  String dispatchFilePath="src/sample/fileStorage/moduleData/postalData/dispatchData.txt";

    //get PostalType enum object with the String type input of the pName
    public static PostalType getPostalType(String pName)
    {
        PostalType postalType = null;
        switch (pName){
            case "RECEIVED":
                postalType = PostalType.RECEIVED;
                break;

            case  "DISPATCH":
                postalType = PostalType.DISPATCH;
                break;
        }
        return  postalType;
    }

    //write a method for add received postal
    public static void addReceived(ReceivedPostal postal, UserRoll roll) {
        if (roll.equals(UserRoll.RECEPTIONIST)) {
            saveReceived(postal);
        }


    }

    //write a method for save received postal
    private static void saveReceived(ReceivedPostal receivedPostal) {
        File file = new File(receivedFilePath);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            BufferedWriter receivedBufferedWriter = new BufferedWriter(fileWriter);
            receivedBufferedWriter.write(receivedPostal.getPostalType().toString() + ",");
            receivedBufferedWriter.write(receivedPostal.getReferenceNo() + ",");
            receivedBufferedWriter.write(receivedPostal.getToName() + ",");
            receivedBufferedWriter.write(receivedPostal.getFromName() + ",");
            receivedBufferedWriter.write(receivedPostal.getNote() + ",");
            receivedBufferedWriter.write(receivedPostal.getFromAddress() + ",");
            receivedBufferedWriter.write(receivedPostal.getDate()+" ");

            receivedBufferedWriter.newLine();
            receivedBufferedWriter.close();
            fileWriter.close();
            System.out.println("file path : " + file.getPath() + " received postal saved");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    //write a method for add dispatch postal
    public static void addDispatch(DispatchPostal dispatchPostal, UserRoll roll) {

        if (roll.equals(UserRoll.RECEPTIONIST)) {
            saveDispatch(dispatchPostal);
        }


    }

    //write a method for save Dispatch postal
    private static void saveDispatch(DispatchPostal dispatchPostal) {
        File file = new File(dispatchFilePath);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            BufferedWriter dispatchPostalBufferedWriter = new BufferedWriter(fileWriter);
            dispatchPostalBufferedWriter.write(dispatchPostal.getPostalType().toString() + ",");
            dispatchPostalBufferedWriter.write(dispatchPostal.getReferenceNo() + ",");
            dispatchPostalBufferedWriter.write(dispatchPostal.getToName() + ",");
            dispatchPostalBufferedWriter.write(dispatchPostal.getFromName() + ",");
            dispatchPostalBufferedWriter.write(dispatchPostal.getNote() + ",");
            dispatchPostalBufferedWriter.write(dispatchPostal.getAddress()+ ",");
            dispatchPostalBufferedWriter.write(dispatchPostal.getDate()+" ");;

            dispatchPostalBufferedWriter.newLine();
            dispatchPostalBufferedWriter.close();
            fileWriter.close();
            System.out.println("file path : " + file.getPath() + " Dispatch postal saved");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }



    }

}
