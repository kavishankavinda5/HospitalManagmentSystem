package sample.controller.actionTask;

import org.jetbrains.annotations.NotNull;
import sample.model.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class UserAction {

    // scerate key and salt for the user login data encryption
    private static final String secretKey = "boooooooooom!!!!";
    private static final String salt = "ssshhhhhhhhhhh!!!!";
    private static Scanner scanner;

    public static String patientloginData = "src/sample/fileStorage/loginData/patientLoginData.txt";
    public static String adminloginData = "src/sample/fileStorage/loginData/adminLoginData.txt";
    public static String receptionLoginData = "src/sample/fileStorage/loginData/receptionLoginData.txt";
    public static String medicalLoginData = "src/sample/fileStorage/loginData/medicalofficerLoginData.txt";

    public static String patientDataFilePath = "src/sample/fileStorage/moduleData/userData/patientData.txt";
    public static String receptionistFilePath = "src/sample/fileStorage/moduleData/userData/receptionistData.txt";
    public static String medicalOfficerFilePath = "src/sample/fileStorage/moduleData/userData/medicalOfficerData.txt";
    public static String adminFilePath = "src/sample/fileStorage/moduleData/userData/adminData.txt";


    /* =============================================================================================================
       LOGIN_USER Action tasks
      ============================================================================================================= */

    // verify user login credentials
    // parameters UserName , User Password , UserType (reception,admin.doctor,patient)
    public static int verifyLogin(String userName, String password, String userType) {

        int feedback = 0;

        //load respective user dta from the selectedfiles from the local storage
        ArrayList<LoginUser> loginData = loadUserLoginData(userType);

        for (int i = 0; i < loginData.size(); i++) {
            String saveduserName = loginData.get(i).getUserName().trim();
            String savedUserPassword = loginData.get(i).getUserPassword().trim();

            boolean a = (saveduserName.equals(encrypt(userName, secretKey)));
            boolean b = (savedUserPassword.equals(encrypt(password, secretKey)));

            if (a && b) {
                feedback = 1;
            }
        }
        return feedback;
    }

    //load user data from the file storage(from text files stored in the local storage)
    public static @NotNull
    ArrayList<LoginUser> loadUserLoginData(String userType) {

        ArrayList<LoginUser> userData = new ArrayList<>();

        File file = new File("src/sample/fileStorage/loginData/" + userType + "LoginData.txt");

        if (file != null) {
            try (FileReader fileReader = new FileReader(file)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                LineNumberReader lineNumberReader = new LineNumberReader(bufferedReader);
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {

                    System.out.println(lineNumberReader.getLineNumber());
                    List<String> tempList = Arrays.asList(line.split("[~\n]"));
                    LoginUser loginUser = new LoginUser();
                    loginUser.setUserName(tempList.get(0).trim());
                    loginUser.setUserPassword(tempList.get(1).trim());
                    userData.add(loginUser);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return userData;
    }

    public static String encryptUserData(String userDataEncrypt){
        return encrypt(userDataEncrypt,secretKey);
    }

    public static String decryptUserData(String userDataDecrypt){
         String decrypteUserData =decrypt(userDataDecrypt,secretKey);

         return decrypteUserData;
    }

    private static String encrypt(String strToEncrypt, String secret) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    private static String decrypt(String strToDecrypt, String secret) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;

    }


    /* =============================================================================================================
       PATIENT Action tasks
      =============================================================================================================*/

    //write a method for add patient data
    public static void  addPatient(Patient user, UserRoll roll) {

        if (roll.equals(UserRoll.ADMIN) || roll.equals(UserRoll.RECEPTIONIST)) {
            savePatient(user);
            JOptionPane.showMessageDialog(null,"Record Add Successfully");
        }

    }

    //write a method for save patient data
    private static void savePatient(Patient user) {
        File file = new File(patientDataFilePath);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(user.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("file path : " + file.getPath() + " patient saved");
            addUserLoginData(patientloginData, new LoginUser(user.getUserName(), user.getUserPassword()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    //write a method for update patient data
    public static void updatePatientRecord(UserRoll userRoll,Patient patientRecord,String searchedID,LoginUser loginUser){
        if (userRoll.equals(UserRoll.RECEPTIONIST) || userRoll.equals(UserRoll.ADMIN)){

            Object[] options = { "OK", "CANCEL" };
            Toolkit.getDefaultToolkit().beep();
            int selectedValue = JOptionPane.showOptionDialog(null, "Are You Sure Update This Record"+"\nClick OK to continue", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);

            if (selectedValue == JOptionPane.WHEN_FOCUSED) {
                editPatientRecord(patientDataFilePath,patientRecord,searchedID,loginUser);
            }

        }else {

            System.out.println("acces denied(cannot update)");
        }
    }

    //write a method for edit patient data
    private static void editPatientRecord(String filePath,Patient patientEdit,String searchPetientid,LoginUser loginUser){

        ArrayList<String> tempPatientList =new ArrayList<>();

        File file = new File(filePath);
        boolean found = false;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split("~"));

                if (!tempList.get(6).equals(searchPetientid)) {
                    tempPatientList.add(line);
                } else {
                    found = true;
                    line = patientEdit.toString();
                    tempPatientList.add(line);
                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found) {
                try {

                    File fileNew = new File(patientDataFilePath);
                    if(file.exists()){

                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    for (int i = 0; i < tempPatientList.size(); i++) {
                        newbufferedWriter.write(tempPatientList.get(i));
                        newbufferedWriter.newLine();
                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    JOptionPane.showMessageDialog(null,"Update Successfully");
                    System.out.println("patient edited  success");
                    System.out.println(tempPatientList.toString());
                    updateUserLoginData(patientloginData,loginUser);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,"Record Not Found"+"\nPlease Check ID Number in Search Box", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //write a method for search patient
    public static Patient searchPatient(String seachTerm,String userName,String userpassword){
        Patient foundPatient = searchPatientRecord(seachTerm,userName,userpassword);
        System.out.println("return Patient : "+foundPatient.toString());
        return foundPatient;
    }

    //write a method for search patient data
    private static Patient searchPatientRecord(String searchTerm, String userName,String password){

        boolean found = false;
        Patient searchedPatient = new Patient();
        List<String> temp = new ArrayList<>();

        File patientFile = new File(patientDataFilePath);
        if (patientFile != null) {
            try (FileReader fileReader = new FileReader(patientFile)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    List<String> tempList= Arrays.asList(line.split("~"));

                    if ((tempList.get(6).equals(searchTerm) ||
                            tempList.get(8).equals(encryptUserData(userName)) && tempList.get(9).equals(encryptUserData(password))) && !found) {
                        found = true;

                        temp =tempList;
                        System.out.println("Patient Record found in patientdata.txt");
                    }
                }
                    if (found){
                        System.out.println("patient record found :"+temp.toString());
                        searchedPatient.setUserRoll(getUserRoll(temp.get(0)));
                        searchedPatient.setName(temp.get(1));
                        searchedPatient.setGender(getGender(temp.get(2)));
                        searchedPatient.setMaritalStatus(temp.get(3));
                        searchedPatient.setDob(getLocalDatefromString(temp.get(4)));
                        searchedPatient.setPhoneNumber(temp.get(5));
                        searchedPatient.setIdCardNumber(temp.get(6));
                        searchedPatient.setAddress(temp.get(7));
                        searchedPatient.setUserName(temp.get(8));
                        searchedPatient.setUserPassword(temp.get(9));
                        searchedPatient.setBloodGroup(getBloodGroup(temp.get(10)));
                        searchedPatient.setAllergies(temp.get(11));

                        JOptionPane.showMessageDialog(null,"Record Found");
                        System.out.println("search found :"+searchedPatient);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Record Not Found");
                    }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return searchedPatient;
    }

    public static  ArrayList<Patient> getAllPatients(){
        ArrayList<Patient> allPatientRecords = new ArrayList<>();
        File newFile = new File(patientDataFilePath);
        String patientRecord = null;
        try (FileReader patientReader = new FileReader(newFile)){

            BufferedReader patientBufferedRedaer = new BufferedReader(patientReader);

            while ((patientRecord = patientBufferedRedaer.readLine()) != null) {
                List<String> tenpPatientRec = Arrays.asList(patientRecord.split("~"));
                Patient newPatient = getPatientFromList(tenpPatientRec);
                allPatientRecords.add(newPatient);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allPatientRecords;
    }

    private static Patient getPatientFromList(List<String> tenpPatientRec) {
        Patient returnPatient = new Patient();

        returnPatient.setUserRoll(getUserRoll(tenpPatientRec.get(0)));
        returnPatient.setName(tenpPatientRec.get(1));
        returnPatient.setGender(getGender(tenpPatientRec.get(2)));
        returnPatient.setMaritalStatus(tenpPatientRec.get(3));
        returnPatient.setDob(getLocalDatefromString(tenpPatientRec.get(4)));
        returnPatient.setPhoneNumber(tenpPatientRec.get(5));
        returnPatient.setIdCardNumber(tenpPatientRec.get(6));
        returnPatient.setAddress(tenpPatientRec.get(7));
        returnPatient.setUserName(tenpPatientRec.get(8));
        returnPatient.setUserPassword(tenpPatientRec.get(9));
        returnPatient.setBloodGroup(getBloodGroup(tenpPatientRec.get(10)));
        returnPatient.setAllergies(tenpPatientRec.get(11));

        return returnPatient;
    }


    /* =============================================================================================================
       RECEPTIONIST Action tasks
      =============================================================================================================
    */

    //write a method for add receptionist data
    public static void addReceptionist(Receptionist receptionist,UserRoll roll){

        if (roll.equals(UserRoll.ADMIN)){
            saveReceptionist(receptionist);
            JOptionPane.showMessageDialog(null,"Record Add Successfully");
        }
    }

    //write a method for save receptionist data
    private static  void  saveReceptionist(Receptionist receptionist){
        File receptionFile = new File(receptionistFilePath);

        try (FileWriter fileWriter = new FileWriter(receptionFile, true)) {

            BufferedWriter receptionBufferedWriter = new BufferedWriter(fileWriter);

            receptionBufferedWriter.write(receptionist.toString());
            receptionBufferedWriter.close();
            fileWriter.close();
            System.out.println("Receptionist saved: " + receptionFile.getPath()+" patient saved");
            addUserLoginData(receptionLoginData,new LoginUser(receptionist.getUserName(), receptionist.getUserPassword()));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    //write a method for update receptionist data
    public static void updateReceptionRecord(UserRoll userRoll,Receptionist receptionist,String searchedID,LoginUser loginUser){
        if (userRoll.equals(UserRoll.RECEPTIONIST) || userRoll.equals(UserRoll.ADMIN)){

            Object[] options = { "OK", "CANCEL" };
            Toolkit.getDefaultToolkit().beep();
            int selectedValue = JOptionPane.showOptionDialog(null, "Are You Sure Update This Record"+"\nClick OK to continue", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);

            if (selectedValue == JOptionPane.WHEN_FOCUSED) {
                editReceptionRecord(receptionistFilePath,receptionist,searchedID,loginUser);
            }

        }else {
            System.out.println("acces denied(cannot update)");
        }
    }

    //write a method for edit receptionist data
    private static void editReceptionRecord(String filePath,Receptionist receptionist,String searchPetientid,LoginUser loginUser){

        ArrayList<String> tempPatientList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split("~"));
                if(!tempList.get(6).equals(searchPetientid)){
                    tempPatientList.add(line);
                }else {
                    found = true;
                    String newLine = receptionist.toString();
                    line =newLine;
                    tempPatientList.add(line);

                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found == true){
                try {

                    File fileNew = new File(receptionistFilePath);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    newbufferedWriter.write("");
                    for (int i=0;i<tempPatientList.size();i++){
                        newbufferedWriter.write(tempPatientList.get(i));
                        newbufferedWriter.newLine();

                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    JOptionPane.showMessageDialog(null,"Update Successfully");
                    System.out.println("Receptionist edited  success");
                    System.out.println(tempPatientList.toString());
                    updateUserLoginData(receptionLoginData,loginUser);
                    System.out.println("receptionist login data updated");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,"Record Not Found"+"\nPlease Check ID Number in Search Box", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //write a method for search receptionist data
    public static Receptionist searchReceptionRecord(String seachTerm,String userName,String userPass){

        Receptionist foundReception = searchReceptionist(seachTerm,userName,userPass);
        System.out.println("return Patient :"+foundReception.toString());
        return foundReception;
    }

    //write a method for search receptionist
    private static Receptionist searchReceptionist(String searchTerm, String userName,String userPassword){

        boolean found = false;
        Receptionist searchedReceptionist = new Receptionist();
        List<String> temp = new ArrayList<>();


        File patientFile = new File(receptionistFilePath);
        if (patientFile != null) {
            try (FileReader fileReader = new FileReader(patientFile)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);


                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    List<String> tempList= Arrays.asList(line.split("~"));

                    if ((tempList.get(6).equals(searchTerm) ||
                            tempList.get(1).equals(encryptUserData(userName)) && tempList.get(9).equals(encryptUserData(userPassword))) && !found) {
                        found = true;
                        temp =tempList;
                        System.out.println("search Receptionist  found ");
                    }
                }
                if (found){
                    System.out.println("ReceptionRecord  found :"+temp.toString());
                    searchedReceptionist.setUserRoll(getUserRoll(temp.get(0)));
                    searchedReceptionist.setName(temp.get(1));
                    searchedReceptionist.setGender(getGender(temp.get(2)));
                    searchedReceptionist.setMaritalStatus(temp.get(3));
                    searchedReceptionist.setDob(getLocalDatefromString(temp.get(4)));
                    searchedReceptionist.setPhoneNumber(temp.get(5));
                    searchedReceptionist.setIdCardNumber(temp.get(6));
                    searchedReceptionist.setAddress(temp.get(7));
                    searchedReceptionist.setUserName(temp.get(8));
                    searchedReceptionist.setUserPassword(temp.get(9));
                    searchedReceptionist.setStaffID(Integer.parseInt(temp.get(10)));
                    searchedReceptionist.setStaffEmailAddress(temp.get(11));
                    searchedReceptionist.setDateOfJoining(getLocalDatefromString(temp.get(12)));

                    JOptionPane.showMessageDialog(null,"Record Found");
                    System.out.println("search found :"+searchedReceptionist);
                }
                else {
                    JOptionPane.showMessageDialog(null,"Record Not Found");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return searchedReceptionist;
    }

    public static  ArrayList<Receptionist> getAllReceptionist(){
        ArrayList<Receptionist> allReceptionRecords = new ArrayList<>();
        File newFile = new File(receptionistFilePath);
        String receptionRecord = null;
        try (FileReader receptionReader = new FileReader(newFile)){
            BufferedReader receptionBufferedRedaer = new BufferedReader(receptionReader);

            while ((receptionRecord = receptionBufferedRedaer.readLine()) != null) {
                List<String> tempReceptionList = Arrays.asList(receptionRecord.split("~"));
                Receptionist newReception = getReceptionistFromList(tempReceptionList);
                allReceptionRecords.add(newReception);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allReceptionRecords;
    }

    private static Receptionist getReceptionistFromList(List<String> tempReceptionRec) {
        Receptionist returnReceptionist = new Receptionist();

        returnReceptionist.setUserRoll(getUserRoll(tempReceptionRec.get(0)));
        returnReceptionist.setName(tempReceptionRec.get(1));
        returnReceptionist.setGender(getGender(tempReceptionRec.get(2)));
        returnReceptionist.setMaritalStatus(tempReceptionRec.get(3));
        returnReceptionist.setDob(getLocalDatefromString(tempReceptionRec.get(4)));
        returnReceptionist.setPhoneNumber(tempReceptionRec.get(5));
        returnReceptionist.setIdCardNumber(tempReceptionRec.get(6));
        returnReceptionist.setAddress(tempReceptionRec.get(7));
        returnReceptionist.setUserName(tempReceptionRec.get(8));
        returnReceptionist.setUserPassword(tempReceptionRec.get(9));
        returnReceptionist.setStaffID(Integer.parseInt(tempReceptionRec.get(10)));
        returnReceptionist.setStaffEmailAddress(tempReceptionRec.get(11));
        returnReceptionist.setDateOfJoining(getLocalDatefromString(tempReceptionRec.get(12)));


        return returnReceptionist;
    }


    /* =============================================================================================================
       ADMIN Action tasks
      =============================================================================================================*/

    //write a method for add admin data
    public static void addAdmin (Admin admin,UserRoll userRoll){

        if (userRoll.equals(UserRoll.ADMIN)) {
            saveAdmin(admin);
            JOptionPane.showMessageDialog(null,"Record Add Successfully");
        }
        else {
            System.out.println("cannot save");}
    }

    //write a method for save admin data
    private static void saveAdmin(Admin admin) {
        File file = new File(adminFilePath);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            BufferedWriter adminBufferedWriter = new BufferedWriter(fileWriter);

            adminBufferedWriter.write(admin.toString());
            adminBufferedWriter.newLine();
            adminBufferedWriter.close();
            fileWriter.close();

            System.out.println("file path : " + file.getPath() + " admin saved");
            addUserLoginData(adminloginData,new LoginUser(admin.getUserName(), admin.getUserPassword()));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    //write a method for update admin data
    public static void updateAdmin(UserRoll userRoll,Admin adminRecord,String searchedID,LoginUser loginUser){
        if (userRoll.equals(UserRoll.ADMIN)){

            Object[] options = { "OK", "CANCEL" };
            Toolkit.getDefaultToolkit().beep();
            int selectedValue = JOptionPane.showOptionDialog(null, "Are You Sure Update This Record"+"\nClick OK to continue", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);

            if (selectedValue == JOptionPane.WHEN_FOCUSED) {
                editAdminRecord(adminFilePath,adminRecord,searchedID,loginUser);
            }

        }else {
            System.out.println("acces denied(cannot update)");
        }
    }

    //write a method for edit admin data
    private static void editAdminRecord(String filePath,Admin adminEdit,String searchAdminRec,LoginUser loginUser){

        ArrayList<String> tempAdminList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader adminfileReader = new FileReader(file);
            BufferedReader adminbufferedReader = new BufferedReader(adminfileReader);
            String line =null;
            while ((line = adminbufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split("~"));
                if(!tempList.get(6).equals(searchAdminRec)){
                    tempAdminList.add(line);
                }else {
                    found = true;
                    String newLine = adminEdit.toString();
                    line =newLine;
                    tempAdminList.add(line);

                }
            }

            adminbufferedReader.close();
            adminfileReader.close();

            if (found){
                try {

                    File fileNew = new File(adminFilePath);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    newbufferedWriter.write("");
                    for (int i=0;i<tempAdminList.size();i++){
                        newbufferedWriter.write(tempAdminList.get(i));
                        newbufferedWriter.newLine();

                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    JOptionPane.showMessageDialog(null,"Update Successfully");
                    System.out.println("Admin edited  success");
                    System.out.println(tempAdminList.toString());

                    updateUserLoginData(adminloginData,loginUser);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,"Record Not Found"+"\nPlease Check ID Number in Search Box", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //write a method for search admin
    public static Admin searchAdmin(String seachTerm,String userPassword){

        Admin foundAdmin = searchAdminRecord(seachTerm,userPassword);
        System.out.println("return Patient :"+foundAdmin.toString());
        return foundAdmin;
    }

    //write a method for search admin data
    private static Admin searchAdminRecord(String searchTerm, String pass){

        boolean found = false;
        Admin searchedAdmin = new Admin();
        List<String> temp = new ArrayList<>();

        File adminFile = new File(adminFilePath);
        if (adminFile != null) {
            try (FileReader fileReader = new FileReader(adminFile)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    List<String> tempList= Arrays.asList(line.split("~"));

                    if ((tempList.get(6).equals(searchTerm) ||
                            tempList.get(1).equals(searchTerm) && tempList.get(9).equals(pass)) && !found) {
                        found = true;
                        temp =tempList;
                        System.out.println("search Admin found ");
                    }
                }
                if (found){
                    System.out.println("Admin record found :"+temp.toString());
                    searchedAdmin.setUserRoll(getUserRoll(temp.get(0)));
                    searchedAdmin.setName(temp.get(1));
                    searchedAdmin.setGender(getGender(temp.get(2)));
                    searchedAdmin.setMaritalStatus(temp.get(3));
                    searchedAdmin.setDob(getLocalDatefromString(temp.get(4)));
                    searchedAdmin.setPhoneNumber(temp.get(5));
                    searchedAdmin.setIdCardNumber(temp.get(6));
                    searchedAdmin.setAddress(temp.get(7));
                    searchedAdmin.setUserName(temp.get(8));
                    searchedAdmin.setUserPassword(temp.get(9));

                    if(pass==null){
                        JOptionPane.showMessageDialog(null,"Record Found");
                    }

                    System.out.println("search found :"+searchedAdmin);
                }
                else {
                    if(pass==null){
                        JOptionPane.showMessageDialog(null,"Record Not Found");
                    }

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return searchedAdmin;
    }

    public static ArrayList<Admin> getAllAdmin(){
        ArrayList<Admin> allAdminRecords =new ArrayList<>();
        File newFile = new File(adminFilePath);
        String adminRecord = null;
        try (FileReader adminFileReader = new FileReader(newFile)){
            BufferedReader adminBufferedRedaer = new BufferedReader(adminFileReader);

            while ((adminRecord = adminBufferedRedaer.readLine()) != null) {
                List<String> tempAdminList = Arrays.asList(adminRecord.split("~"));
                System.out.println(tempAdminList.toString());
                Admin newAdmin = getAdminFromList(tempAdminList);
                allAdminRecords.add(newAdmin);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allAdminRecords;
    }

    private static Admin getAdminFromList(List<String> tempAdminRec) {
        Admin returnAdmiin =new Admin();

        returnAdmiin.setUserRoll(getUserRoll(tempAdminRec.get(0)));
        returnAdmiin.setName(tempAdminRec.get(1));
        returnAdmiin.setGender(getGender(tempAdminRec.get(2)));
        returnAdmiin.setMaritalStatus(tempAdminRec.get(3));
        returnAdmiin.setDob(getLocalDatefromString(tempAdminRec.get(4)));
        returnAdmiin.setPhoneNumber(tempAdminRec.get(5));
        returnAdmiin.setIdCardNumber(tempAdminRec.get(6));
        returnAdmiin.setAddress(tempAdminRec.get(7));
        returnAdmiin.setUserName(tempAdminRec.get(8));
        returnAdmiin.setUserPassword(tempAdminRec.get(9));
        return returnAdmiin;
    }

    /* =============================================================================================================
       MEDICALOFFICER Action tasks
      =============================================================================================================*/

    //write a method for add medicalofficer data
    public static void addMedicalOfficer(MedicalOfficer medicalOfficer,UserRoll userRoll){

        if (userRoll.equals(UserRoll.ADMIN)) {
            saveMedicalOfficer(medicalOfficer);
            JOptionPane.showMessageDialog(null,"Record Add Successfully");
        }

    }

    //write a method for save medicalofficer data
    private static void saveMedicalOfficer(MedicalOfficer medicalOfficer) {
        File file = new File(medicalOfficerFilePath);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(medicalOfficer.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("file path : " + file.getPath()+" medicalOfficer saved");
            addUserLoginData(medicalLoginData,new LoginUser(medicalOfficer.getUserName(), medicalOfficer.getUserPassword()));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    //Write a method for update medicalofficer data record
    public static void updateMedicalOfficerRecord(UserRoll userRoll,MedicalOfficer medicalOfficerRecord,String searchedID,LoginUser loginUser){
        if (userRoll.equals(UserRoll.ADMIN)){

            Object[] options = { "OK", "CANCEL" };
            Toolkit.getDefaultToolkit().beep();
            int selectedValue = JOptionPane.showOptionDialog(null, "Are You Sure Update This Record"+"\nClick OK to continue", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);

            if (selectedValue == JOptionPane.WHEN_FOCUSED) {
                editMedicalOfficerRecord(medicalOfficerFilePath,medicalOfficerRecord,searchedID,loginUser);
            }

        }
        else{
            System.out.println("Access denied(Cannot update)");
        }
    }

    //Write a method foe edit medicalofficer data record
    private static void editMedicalOfficerRecord(String filePath,MedicalOfficer medicalOfficer,String searchMedicalOfficerId,LoginUser loginUser){

        ArrayList<String> tempMedicalOfficerList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split("~"));
                if(!tempList.get(6).equals(searchMedicalOfficerId)){
                    tempMedicalOfficerList.add(line);
                }else {
                    found = true;
                    String newLine = medicalOfficer.toString();
                    line =newLine;
                    tempMedicalOfficerList.add(line);

                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found ){
                try {

                    File fileNew = new File(medicalOfficerFilePath);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    newbufferedWriter.write("");
                    for (int i=0;i<tempMedicalOfficerList.size();i++){
                        newbufferedWriter.write(tempMedicalOfficerList.get(i));
                        newbufferedWriter.newLine();

                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    JOptionPane.showMessageDialog(null,"Update Successfully");
                    System.out.println("Medical Officer Edited Success");
                    System.out.println(tempMedicalOfficerList.toString());

                    updateUserLoginData(medicalLoginData,loginUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,"Record Not Found"+"\nPlease Check ID Number in Search Box", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Write a method for Search medicalofficer data record
    public static MedicalOfficer searchMedicalOfficer(String seachTerm, String filepath) {
        boolean found = false;
        MedicalOfficer searchedMedicalOfficer = new MedicalOfficer();

        try {
            String userRoll = null;
            String name = null, address = null;
            String gender = null, marital = null, dob = null, phonenumber = null, idcardNumber = null, userName = null, password = null;
            String staffId =null,dateOFJoin=null;
            String staffEmailAddress = null, speciality = null;
            scanner = new Scanner(new File(filepath));
            scanner.useDelimiter("[~\n]");

            while (scanner.hasNext() && !found) {

                userRoll = scanner.next();
                name = scanner.next();
                gender = scanner.next();
                marital = scanner.next();
                dob = scanner.next();
                phonenumber = scanner.next();
                idcardNumber = scanner.next();
                address = scanner.next();
                userName = scanner.next();
                password = scanner.next();
                staffId = scanner.next();
                staffEmailAddress = scanner.next();
                dateOFJoin =scanner.next();
                speciality = scanner.next();


                if (idcardNumber.equals(seachTerm)) {
                    found = true;
                }
            }
            if (found){
                searchedMedicalOfficer.setUserRoll(getUserRoll(userRoll));
                searchedMedicalOfficer.setName(name);
                searchedMedicalOfficer.setGender(getGender(gender));
                searchedMedicalOfficer.setMaritalStatus(marital);
                searchedMedicalOfficer.setDob(getLocalDatefromString(dob));
                searchedMedicalOfficer.setPhoneNumber(phonenumber);
                searchedMedicalOfficer.setIdCardNumber(idcardNumber);
                searchedMedicalOfficer.setAddress(address);
                searchedMedicalOfficer.setUserName(userName);
                searchedMedicalOfficer.setUserPassword(password);
                searchedMedicalOfficer.setStaffID(Integer.parseInt(staffId));
                searchedMedicalOfficer.setStaffEmailAddress(staffEmailAddress);
                searchedMedicalOfficer.setDateOfJoining(getLocalDatefromString(dateOFJoin));
                searchedMedicalOfficer.setSpeciality(speciality);

                System.out.println(searchedMedicalOfficer.toString());

                JOptionPane.showMessageDialog(null,"Record Found");

            }
            else {
                JOptionPane.showMessageDialog(null,"Record Not Found");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return searchedMedicalOfficer;

    }

    public static ArrayList<MedicalOfficer> getAllMedicalOfficer(){
        ArrayList<MedicalOfficer> allMedicalOfficerRecords =new ArrayList<>();
        File newFile = new File(medicalOfficerFilePath);
        String adminRecord = null;
        try (FileReader adminFileReader = new FileReader(newFile)){
            BufferedReader adminBufferedRedaer = new BufferedReader(adminFileReader);

            while ((adminRecord = adminBufferedRedaer.readLine()) != null) {
                List<String> tempMedicList = Arrays.asList(adminRecord.split("~"));
                System.out.println(tempMedicList.toString());
                MedicalOfficer medicalOfficer = getMedicalOfficer(tempMedicList);
                allMedicalOfficerRecords.add(medicalOfficer);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allMedicalOfficerRecords;
    }

    private static MedicalOfficer getMedicalOfficer(List<String> tempMedicList) {
        MedicalOfficer returnMedicalOfficer =new MedicalOfficer();

        returnMedicalOfficer.setUserRoll(getUserRoll(tempMedicList.get(0)));
        returnMedicalOfficer.setName(tempMedicList.get(1));
        returnMedicalOfficer.setGender(getGender(tempMedicList.get(2)));
        returnMedicalOfficer.setMaritalStatus(tempMedicList.get(3));
        returnMedicalOfficer.setDob(getLocalDatefromString(tempMedicList.get(4)));
        returnMedicalOfficer.setPhoneNumber(tempMedicList.get(5));
        returnMedicalOfficer.setIdCardNumber(tempMedicList.get(6));
        returnMedicalOfficer.setAddress(tempMedicList.get(7));
        returnMedicalOfficer.setUserName(tempMedicList.get(8));
        returnMedicalOfficer.setUserPassword(tempMedicList.get(9));
        returnMedicalOfficer.setStaffID(Integer.parseInt(tempMedicList.get(10)));
        returnMedicalOfficer.setStaffEmailAddress(tempMedicList.get(11));
        returnMedicalOfficer.setDateOfJoining(getLocalDatefromString(tempMedicList.get(12)));
        returnMedicalOfficer.setSpeciality(tempMedicList.get(13));
        return returnMedicalOfficer;
    }

    public static ArrayList<MedicalOfficer> getMedicalOfficerBySpeciality(String speciality){
        ArrayList<MedicalOfficer> medicalOfficerBySpec = new ArrayList<>();
        ArrayList<MedicalOfficer> tempMedicalOfficer = getAllMedicalOfficer();
        for (int i=0;i<tempMedicalOfficer.size();i++){
            if (tempMedicalOfficer.get(i).getSpeciality().trim().equals(speciality.trim())){
                MedicalOfficer medicalOfficer = tempMedicalOfficer.get(i);
                medicalOfficer.setUserPassword(null);
                medicalOfficerBySpec.add(medicalOfficer);
                System.out.println("Medical officer found by speciality");
            }else {
                System.out.println("Medical officer not found by speciality");
            }
        }

        return medicalOfficerBySpec;
    }



    /* =============================================================================================================
       Common User  Action tasks
      =============================================================================================================*/

    //write a method for delete user data
    public static void deleteUserRecord(UserRoll taskUserRoll, String searchTerm, UserRoll currentUserRoll,LoginUser loginUser){
        if (taskUserRoll.equals(UserRoll.RECEPTIONIST)){
            Object[] options = { "OK", "CANCEL" };
            Toolkit.getDefaultToolkit().beep();
            int selectedValue = JOptionPane.showOptionDialog(null, "Are You Sure Delete This Record"+"\nClick OK to continue", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);

            if (selectedValue == JOptionPane.WHEN_FOCUSED) {
                removeUserRecord(patientDataFilePath,searchTerm,loginUser,patientloginData);
            }

        }
        else if (taskUserRoll.equals(UserRoll.ADMIN)){

            Object[] options = { "OK", "CANCEL" };
            Toolkit.getDefaultToolkit().beep();
            int selectedValue = JOptionPane.showOptionDialog(null, "Are You Sure Delete This Record"+"\nClick OK to continue", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);

            if (selectedValue == JOptionPane.WHEN_FOCUSED) {
                switch (currentUserRoll){
                    case ADMIN:
                        removeUserRecord(adminFilePath,searchTerm,loginUser,adminloginData);

                        break;
                    case RECEPTIONIST:
                        removeUserRecord(receptionistFilePath,searchTerm,loginUser,receptionLoginData);

                        break;
                    case PATIENT:
                        removeUserRecord(patientDataFilePath,searchTerm,loginUser,patientloginData);

                        break;
                    case MEDICALOFFICER:
                        removeUserRecord(medicalOfficerFilePath,searchTerm,loginUser,medicalLoginData);

                        break;
                    default:
                        break;
                }
            }

        }
    }

    //write a method for remove user data
    private static void removeUserRecord(String filePath, String serachTerm,LoginUser loginUser,String loginDataPath){
        ArrayList<String> tempPatientList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split("~"));
                if(!tempList.get(6).equals(serachTerm)){
                    tempPatientList.add(line);
                }else {
                    found = true;
                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found){
                try {

                    File fileNew = new File(filePath);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    newbufferedWriter.write("");
                    for (int i=0;i<tempPatientList.size();i++){
                        newbufferedWriter.write(tempPatientList.get(i));
                        newbufferedWriter.newLine();

                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    JOptionPane.showMessageDialog(null,"Delete Successfully");
                    System.out.println("User deleted success");
                    System.out.println(tempPatientList.toString());
                    deleteUserLoginData(loginDataPath,loginUser);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,"Record Not Found"+"\nPlease Check ID Number in Search Box", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BloodGroup getBloodGroup (String name){
                BloodGroup blooGroup = null;
                switch (name) {
                    case "A_POSITIVE":
                        blooGroup = BloodGroup.A_POSITIVE;
                        break;

                    case "A_NEGATIVE":
                        blooGroup = BloodGroup.A_NEGATIVE;
                        break;

                    case "AB_POSITIVE":
                        blooGroup = BloodGroup.AB_POSITIVE;
                        break;

                    case "AB_NEGATIVE":
                        blooGroup = BloodGroup.AB_NEGATIVE;
                        break;
                    case "B_POSITIVE":
                        blooGroup = BloodGroup.B_POSITIVE;
                        break;
                    case "B_NEGATIVE":
                        blooGroup = BloodGroup.B_NEGATIVE;
                        break;
                    case "O_POSITIVE":
                        blooGroup = BloodGroup.O_POSITIVE;
                        break;
                    case "O_NEGATIVE":
                        blooGroup = BloodGroup.O_NEGATIVE;
                        break;


        }

        return blooGroup;
    }

    public static Gender getGender(String gender){
                Gender userGender=null;
                switch (gender){
                    case "MALE":
                        userGender =Gender.MALE;
                        break;
                    case "FEMALE":
                        userGender =Gender.FEMALE;
                        break;
                    case "OTHER":
                        userGender = Gender.OTHER;
                    default:
                        break;
                }
                return userGender;
            }

    public static LocalDate getLocalDatefromString (String string){
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }

    public static String getStringfromLocalDate (LocalDate date){
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

    //get UserRoll enum object with the String type input of the name
    public static UserRoll getUserRoll (String name){
                UserRoll userRoll = null;
                switch (name) {
                    case "PATIENT":
                        userRoll = UserRoll.PATIENT;
                        break;

            case "ADMIN":
                userRoll = UserRoll.ADMIN;
                break;

            case "RECEPTIONIST":
                userRoll = UserRoll.RECEPTIONIST;
                break;

            case "MEDICALOFFICER":
                userRoll = UserRoll.MEDICALOFFICER;
                break;

        }

        return userRoll;
    }



    /* =============================================================================================================
       User Login Action  tasks
      =============================================================================================================*/

    private static void addUserLoginData(String fileName, LoginUser user) {
        File addLoginFile = new File(fileName);

        try (FileWriter userLoginWriter = new FileWriter(addLoginFile, true)) {
            BufferedWriter userLoginBufferedWriter = new BufferedWriter(userLoginWriter);
            userLoginBufferedWriter.write(user.toString());
            userLoginBufferedWriter.newLine();
            userLoginBufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("user Login data added success");
        }
    }

    private static void updateUserLoginData(String fileName, LoginUser user){
        ArrayList<String> tempLoginList =new ArrayList<>();
        File file = new File(fileName);
        boolean found =false;
        try{
            FileReader loginfileReader = new FileReader(file);
            BufferedReader loginbufferedReader = new BufferedReader(loginfileReader);
            String line =null;
            while ((line = loginbufferedReader.readLine()) != null) {
                List<String> tempLoginUserList = Arrays.asList(line.split("~"));
                if((!tempLoginUserList.get(0).equals(user.getUserName()) )&&
                        (!tempLoginUserList.get(1).equals(user.getUserPassword()) )){
                         tempLoginList.add(line);
                }else {
                    found = true;
                    String newLine = user.toString();
                    line =newLine;
                    tempLoginList.add(line);

                }
            }

            loginbufferedReader.close();
            loginfileReader.close();

            if (found == true){
                try {

                    File fileNew = new File(fileName);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    newbufferedWriter.write("");
                    for (int i=0;i<tempLoginList.size();i++){
                        newbufferedWriter.write(tempLoginList.get(i));
                        newbufferedWriter.newLine();

                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    System.out.println("Admin edited  success");
                    System.out.println(tempLoginList.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUserLoginData(String fileName, LoginUser user){
        ArrayList<String> tempLoginList =new ArrayList<>();
        File file = new File(fileName);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempLoginUserList = Arrays.asList(line.split("~"));
                if((!tempLoginUserList.get(0).equals(user.getUserName()) )&&
                        (!tempLoginUserList.get(1).equals(user.getUserPassword()) )){
                    tempLoginList.add(line);
                }else {
                    found = true;

                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found){
                try {

                    File fileNew = new File(fileName);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    newbufferedWriter.write("");
                    for (int i=0;i<tempLoginList.size();i++){
                        newbufferedWriter.write(tempLoginList.get(i));
                        newbufferedWriter.newLine();

                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    System.out.println("Userlogin record deleted success");
                    System.out.println(tempLoginList.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
