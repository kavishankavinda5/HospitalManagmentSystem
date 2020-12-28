package sample.controller.taskControllers;

import java.io.*;
import java.util.ArrayList;

public class SystemDataWriter {

    public void writeDataToFile(String dataLine,String filePath,int deleteItems){
        File file = new File((filePath));

        if (deleteItems ==10){
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataLine);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Data written to appointment File single data");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void writeDataToFile(ArrayList<String> dataList,String filePath,int deleteItems){
        File file = new File(filePath);

        if (deleteItems ==10){
            boolean isDeletd = file.delete();
            try {
               boolean isFileCreted=  file.createNewFile();
                System.out.println("file created : "+isFileCreted);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("file deleted : "+isDeletd);

        }

        try {
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
            for (int i=0;i<dataList.size();i++){
                bufferedWriter.write(dataList.get(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Data written to appointment File Arrray Data data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
