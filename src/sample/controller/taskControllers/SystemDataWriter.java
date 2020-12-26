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
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataLine);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void writeDataToFile(ArrayList<String> dataList,String filePath,int deleteItems){
        File file = new File(filePath);

        if (deleteItems ==10){
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
            for (int i=0;i<dataList.size();i++){
                bufferedWriter.write(dataList.get(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
