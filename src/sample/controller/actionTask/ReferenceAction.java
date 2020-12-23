package sample.controller.actionTask;

import sample.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReferenceAction {


    public static ArrayList<String> referenceTypes =new ArrayList<>();
    public static final String referenceFile = "src/sample/fileStorage/moduleData/referenceData.txt";

    //Constant Value arrays

    public static ArrayList<UserRoll> userRolls =new ArrayList<>();
    public static ArrayList<Gender> gender = new ArrayList<>();
    public static ArrayList<BloodGroup> bloogGroup = new ArrayList<>();
    public static ArrayList<String> maritalStatus = new ArrayList<>();
    public static ArrayList<PostalType>postalTypes=new ArrayList<>();

    //Changable type Arrays
    public static ArrayList<Reference> complaintRefArrayList = new ArrayList<>();
    public static ArrayList<String> doctorSpeciality = new ArrayList<>();


/*
=======================================================================================================================
=============   Complaint Type and Speciallity type Reference
=======================================================================================================================
 */

    public static void addReference(Reference reference){
    File newRef = new File(referenceFile);

    if(reference.getReferenceType().equals(referenceTypes.get(0))){
        complaintRefArrayList.add(reference);
    }else {
        doctorSpeciality.add(reference.getReferenceValue());
    }

    try(FileWriter fileWriter = new FileWriter(newRef,true)) {
        BufferedWriter bufferedWriter= new BufferedWriter(fileWriter);
        bufferedWriter.write(reference.toString());
        bufferedWriter.newLine();
        bufferedWriter.close();
        fileWriter.close();
        System.out.println("Ref Added "+reference.toString());

    } catch (IOException e) {
        e.printStackTrace();
    }
}

//update or delete ref record input 1 for edid and for any other integer detele
    //to delete reference only the seletected reference should be pass
    public static void updateOrDeleteReference(Reference newReference,Reference oldReferenceValue,int operation){

        File file = new File(referenceFile);
        ArrayList<String> tempRefeArrya = new ArrayList<>();
        boolean found=false;

        try (FileReader fileReader = new FileReader(file)){
            BufferedReader bufferedReader =new BufferedReader(fileReader);
            String line=null;

            while ((line=bufferedReader.readLine()) != null && !found){
                if (!(line.equals(oldReferenceValue.toString()))){
                    tempRefeArrya.add(line);
                }else {
                    found =true;
                    if (operation ==1){
                        line=newReference.toString();
                        tempRefeArrya.add(line);
                    }
                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found){
                if(file.exists()){
                    file.delete();
                }
                file.createNewFile();
                writeListToRefFile(referenceFile,tempRefeArrya);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeListToRefFile(String fileName,ArrayList<String> RefArray) {
        File fileNew = new File(fileName);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileNew);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < RefArray.size(); i++) {
                bufferedWriter.write(RefArray.get(i));
                bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();


                System.out.println("Reference record successfully Updated");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /*
=======================================================================================================================
=============  ********************************************************************************************************
=======================================================================================================================
 */
    public static ArrayList<BloodGroup> getBloogGroup() {
        return bloogGroup;
    }

    public static void loadReference(){

        setGender();
        setUserRolls();
        setMaritalStatus();
        setBloogGroup();
        loadSavedReference();
        setReferenceTypes();

    }

    public static ArrayList<Reference> getComplaintRefArrayList() {
        return complaintRefArrayList;
    }

    private static void loadSavedReference(){

        ArrayList<Reference> allSavedReference =new ArrayList<>();

        File refFile = new File(referenceFile);
        try (FileReader fileReader = new FileReader(refFile)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line =bufferedReader.readLine() )!=null){
                List<String> temrefObj = Arrays.asList(line.split("~"));
                Reference reference = new Reference(temrefObj.get(0),temrefObj.get(1));
                allSavedReference.add(reference);
            }

            for (int i=0;i<allSavedReference.size();i++){
                if (allSavedReference.get(i).getReferenceType().equals("Complaint Types")){
                    System.out.println(allSavedReference.toString());
                    Reference reference = new Reference("Complaint Types",allSavedReference.get(i).getReferenceValue());
                    complaintRefArrayList.add(reference);
                }else {
                    doctorSpeciality.add(allSavedReference.get(i).getReferenceValue());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void setUserRolls() {
        userRolls.add(UserRoll.ADMIN);
        userRolls.add(UserRoll.PATIENT);
        userRolls.add(UserRoll.RECEPTIONIST);
        userRolls.add(UserRoll.MEDICALOFFICER);
    }

    public static ArrayList<UserRoll> getUserRolls() {
        return userRolls;
    }

    public static ArrayList<String> getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public static ArrayList<Gender> getGender() {
        return gender;
    }

    public static void setGender() {

        gender.add(Gender.MALE);
        gender.add(Gender.FEMALE);
        gender.add(Gender.OTHER);
    }

    public static void setBloogGroup() {
        bloogGroup.add(BloodGroup.A_POSITIVE);
        bloogGroup.add(BloodGroup.A_NEGATIVE);
        bloogGroup.add(BloodGroup.AB_NEGATIVE);
        bloogGroup.add(BloodGroup.AB_POSITIVE);
        bloogGroup.add(BloodGroup.B_NEGATIVE);
        bloogGroup.add(BloodGroup.B_POSITIVE);
        bloogGroup.add(BloodGroup.O_NEGATIVE);
        bloogGroup.add(BloodGroup.O_POSITIVE);
    }

    public static void setMaritalStatus() {
        maritalStatus.add("Married");
        maritalStatus.add("Unmarried");
    }

    public static ArrayList<String> getMaritalStatus() {
        return maritalStatus;
    }

    public static ArrayList<PostalType> getPostalTypes() {
        return postalTypes;
    }

    public static void setPostalTypes(PostalType postalTypes) {
        ReferenceAction.postalTypes.add(postalTypes);
    }

    public static void setReferenceTypes(){
        referenceTypes.add("Complaint Types");
        referenceTypes.add("Doctor Speciality");
    }
}
