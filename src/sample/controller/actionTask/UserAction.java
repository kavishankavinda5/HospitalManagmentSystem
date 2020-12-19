package sample.controller.actionTask;

import org.jetbrains.annotations.NotNull;
import sample.model.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserAction {

    // scerate key and salt for the user login data encryption
    private static String secretKey = "boooooooooom!!!!";
    private static String salt = "ssshhhhhhhhhhh!!!!";
    private  static Scanner scanner;
    public static  String patientDataFilePath = "src/sample/fileStorage/moduleData/userData/patientData.txt";
    public static  String receptionistFilePath = "src/sample/fileStorage/moduleData/userData/receptionistData.txt";
    public static String medicalOfficerFilePath = "src/sample/fileStorage/moduleData/userData/medicalOfficerData.txt";
    public static String adminFilePath = "src/sample/fileStorage/moduleData/userData/adminData.txt";

    /* =============================================================================================================
       LOGIN_USER Action tasks
      =============================================================================================================
    */

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
                    List<String> tempList = Arrays.asList(line.split(","));
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

    public static String encrypt(String strToEncrypt, String secret) {
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

    public static String decrypt(String strToDecrypt, String secret) {
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
      =============================================================================================================
    */

    public static void addPatient(Patient user, UserRoll roll) {

        if (roll.equals(UserRoll.ADMIN) || roll.equals(UserRoll.RECEPTIONIST)) {
            savePatient(user);
        }

    }

    private static void savePatient(Patient user) {
        File file = new File(patientDataFilePath);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(user.getUserRoll().toString() + ",");
            bufferedWriter.write(user.getName() + ",");
            bufferedWriter.write(user.getGender() + ",");
            bufferedWriter.write(user.getMaritalStatus()+",");
            bufferedWriter.write(user.getDob() + ",");
            bufferedWriter.write(user.getPhoneNumber()+ ",");
            bufferedWriter.write(user.getIdCardNumber() + ",");
            bufferedWriter.write(user.getAddress()+ ",");
            bufferedWriter.write(user.getUserName()+",");
            bufferedWriter.write(user.getUserPassword() + ",");
            bufferedWriter.write(user.getBloodGroup() + ",");
            bufferedWriter.write(user.getAllergies());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("file path : " + file.getPath()+" patient saved");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static void deleteUserRecord(UserRoll taskUserRoll, String searchTerm, UserRoll currentUserRoll){
        if (taskUserRoll.equals(UserRoll.RECEPTIONIST)){
            removeUserRecord(patientDataFilePath,searchTerm);

        }else if (taskUserRoll.equals(UserRoll.ADMIN)){
           switch (currentUserRoll){
               case ADMIN:
                   removeUserRecord(adminFilePath,searchTerm);
                   break;
               case RECEPTIONIST:
                   removeUserRecord(receptionistFilePath,searchTerm);
                   break;
               case PATIENT:
                   removeUserRecord(patientDataFilePath,searchTerm);
                   break;
               case MEDICALOFFICER:
                   removeUserRecord(medicalOfficerFilePath,searchTerm);
                   break;
               default:
                   break;
           }
        }else {

        }
    }

    private static void removeUserRecord(String filePath, String serachTerm){
        ArrayList<String> tempPatientList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split(","));
                if(!tempList.get(6).equals(serachTerm)){
                    tempPatientList.add(line);
                }else {
                    found = true;

                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found == true){
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
                    System.out.println("patient deleted success");
                    System.out.println(tempPatientList.toString());

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

    public static void updatePatientRecord(UserRoll userRoll,Patient patientRecord,String searchedID){
        if (userRoll.equals(UserRoll.RECEPTIONIST) || userRoll.equals(UserRoll.ADMIN)){
            editPatientRecord(patientDataFilePath,patientRecord,searchedID);
        }else {
            System.out.println("acces denied(cannot update)");
        }
    }

    private static void editPatientRecord(String filePath,Patient patientEdit,String searchPetientid){

        ArrayList<String> tempPatientList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split(","));
                if(!tempList.get(6).equals(searchPetientid)){
                    tempPatientList.add(line);
                }else {
                    found = true;
                    String newLine = getStringPatient(patientEdit);
                    line =newLine;
                    tempPatientList.add(line);

                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found == true){
                try {

                    File fileNew = new File(patientDataFilePath);
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
                    System.out.println("patient edited  success");
                    System.out.println(tempPatientList.toString());

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

    public static Patient searchPatient(String seachTerm, String filepath){
        boolean found = false;
        Patient searchedPatient = new Patient();
        String id =seachTerm;



        try{
            String userRoll = null;
            String name=null,address =null;
            String gender=null,marital=null,dob=null,phonenumber=null,idcardNumber=null,userName=null,password=null,blood=null,allergy =null;
            scanner = new Scanner(new File(filepath));
            scanner.useDelimiter("[,\n]");

            while (scanner.hasNext() && !found){

                userRoll= scanner.next();
                name =scanner.next();
                gender =scanner.next();
                marital =scanner.next();
                dob =scanner.next();
                phonenumber = scanner.next();
                idcardNumber = scanner.next();
                address = scanner.next();
                userName =scanner.next();
                password=scanner.next();
                blood =scanner.next();
                allergy =scanner.next();

                if (idcardNumber.equals(seachTerm)){
                    found = true;
                }
            }
            if (found){
                searchedPatient.setUserRoll(getUserRoll(userRoll));
                searchedPatient.setName(name);
                searchedPatient.setGender(gender);
                searchedPatient.setMaritalStatus(marital);
                searchedPatient.setDob(getLocalDatefromString(dob));
                searchedPatient.setPhoneNumber(phonenumber);
                searchedPatient.setIdCardNumber(idcardNumber);
                searchedPatient.setAddress(address);
                searchedPatient.setUserName(userName);
                searchedPatient.setUserPassword(password);
                searchedPatient.setBloodGroup(getBloodGroup(blood));
                searchedPatient.setAllergies(allergy);

                System.out.println(searchedPatient.toString());


            }else {
                System.out.println("record not found");
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

return searchedPatient;

    }

    /* =============================================================================================================
       RECEPTIONIST Action tasks
      =============================================================================================================
    */

    public static void addReceptionist(Receptionist receptionist,UserRoll roll){

        if (roll.equals(UserRoll.ADMIN)){
            saveReceptionist(receptionist);
        }
    }

    private static  void  saveReceptionist(Receptionist receptionist){
        File receptionFile = new File(receptionistFilePath);

        try (FileWriter fileWriter = new FileWriter(receptionFile, true)) {

            BufferedWriter receptionBufferedWriter = new BufferedWriter(fileWriter);

            receptionBufferedWriter.write(receptionist.getUserRoll().toString() + ",");
            receptionBufferedWriter.write(receptionist.getName() + ",");
            receptionBufferedWriter.write(receptionist.getGender() + ",");
            receptionBufferedWriter.write(receptionist.getMaritalStatus()+",");
            receptionBufferedWriter.write(receptionist.getDob() + ",");
            receptionBufferedWriter.write(receptionist.getPhoneNumber()+ ",");
            receptionBufferedWriter.write(receptionist.getIdCardNumber() + ",");
            receptionBufferedWriter.write(receptionist.getAddress()+",");
            receptionBufferedWriter.write(receptionist.getUserName()+",");
            receptionBufferedWriter.write(receptionist.getUserPassword() + ",");
            receptionBufferedWriter.write(receptionist.getStaffID()+",");
            receptionBufferedWriter.write(receptionist.getStaffEmailAddress()+" ");
            receptionBufferedWriter.newLine();
            receptionBufferedWriter.close();
            fileWriter.close();
            System.out.println("Receptionist saved: " + receptionFile.getPath()+" patient saved");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }



    /* =============================================================================================================
       ADMIN Action tasks
      =============================================================================================================
    */

    //write a method for add admin data
    public static void addAdmin (Admin admin,UserRoll userRoll){

    if (userRoll.equals(UserRoll.ADMIN)) {
        saveAdmin(admin);
    }
    else {
        System.out.println("cannot save");}
}

    //write a method for save admin data
    private static void saveAdmin(Admin admin) {
        File file = new File(adminFilePath);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            BufferedWriter adminBufferedWriter = new BufferedWriter(fileWriter);
            adminBufferedWriter.write(admin.getUserRoll().toString() + ",");
            adminBufferedWriter.write(admin.getName() + ",");
            adminBufferedWriter.write(admin.getGender() + ",");
            adminBufferedWriter.write(admin.getMaritalStatus() + ",");
            adminBufferedWriter.write(admin.getDob() + ",");
            adminBufferedWriter.write(admin.getPhoneNumber() + ",");
            adminBufferedWriter.write(admin.getIdCardNumber() + ",");
            adminBufferedWriter.write(admin.getAddress()+",");
            adminBufferedWriter.write(admin.getUserName() + ",");
            adminBufferedWriter.write(admin.getUserPassword() + ",");

            adminBufferedWriter.newLine();
            adminBufferedWriter.close();
            fileWriter.close();
            System.out.println("file path : " + file.getPath() + " admin saved");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


    /* =============================================================================================================
       MEDICALOFFICER Action tasks
      =============================================================================================================
    */

    //write a method for add medicalofficer data
    public static void addMedicalOfficer(MedicalOfficer medicalOfficer,UserRoll userRoll){

         if (userRoll.equals(UserRoll.ADMIN)) {
             saveMedicalOfficer(medicalOfficer);
         }

     }

    //write a method for save medicalofficer data
    private static void saveMedicalOfficer(MedicalOfficer medicalOfficer) {
        File file = new File(medicalOfficerFilePath);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(medicalOfficer.getUserRoll().toString() + ",");
            bufferedWriter.write(medicalOfficer.getName() + ",");
            bufferedWriter.write(medicalOfficer.getGender() + ",");
            bufferedWriter.write(medicalOfficer.getMaritalStatus()+",");
            bufferedWriter.write(medicalOfficer.getDob() + ",");
            bufferedWriter.write(medicalOfficer.getPhoneNumber()+ ",");
            bufferedWriter.write(medicalOfficer.getIdCardNumber() + ",");
            bufferedWriter.write(medicalOfficer.getAddress()  + ",");
            bufferedWriter.write(medicalOfficer.getUserName()+",");
            bufferedWriter.write(medicalOfficer.getUserPassword() + ",");
            bufferedWriter.write(medicalOfficer.getStaffID() + ",");
            bufferedWriter.write(medicalOfficer.getStaffEmailAddress() + ",");
            bufferedWriter.write(medicalOfficer.getSpeciality());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("file path : " + file.getPath()+" medicalOfficer saved");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    //Write a method for delete medicalofficer data record
    public static void deleteMedicalOfficerRcord(UserRoll userRoll,String searchTerm){
        if (userRoll.equals(UserRoll.ADMIN)){
            removeMedicalOfficerRecord(medicalOfficerFilePath,searchTerm);
        }else {
            System.out.println("Access denied");
        }
    }

    //Write a method for update medicalofficer data record
    public static void updateMedicalOfficerRecord(UserRoll userRoll,MedicalOfficer medicalOfficerRecord,String searchedID){
        if (userRoll.equals(UserRoll.ADMIN)){
            editMedicalOfficerRecord(medicalOfficerFilePath,medicalOfficerRecord,searchedID);
        }else {
            System.out.println("Access denied(Cannot update)");
        }
    }

    //Write a method for remove medicalofficer data record
    private static void removeMedicalOfficerRecord(String filePath,String serachTerm){
        ArrayList<String> tempMedicalOfficertList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split(","));
                if(!tempList.get(6).equals(serachTerm)){
                    tempMedicalOfficertList.add(line);
                }else {
                    found = true;

                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found == true){
                try {

                    File fileNew = new File(medicalOfficerFilePath);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    newbufferedWriter.write("");
                    for (int i=0;i<tempMedicalOfficertList.size();i++){
                        newbufferedWriter.write(tempMedicalOfficertList.get(i));
                        newbufferedWriter.newLine();

                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    System.out.println("Medical Officer deleted success");
                    System.out.println(tempMedicalOfficertList.toString());

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

    //Write a method foe edit medicalofficer data record
    private static void editMedicalOfficerRecord(String filePath,MedicalOfficer medicalOfficer,String searchMedicalOfficerId){

        ArrayList<String> tempMedicalOfficerList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split(","));
                if(!tempList.get(6).equals(searchMedicalOfficerId)){
                    tempMedicalOfficerList.add(line);
                }else {
                    found = true;
                    String newLine = getStringMedicalOfficer(medicalOfficer);
                    line =newLine;
                    tempMedicalOfficerList.add(line);

                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found == true){
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
                    System.out.println("Medical Officer Edited Success");
                    System.out.println(tempMedicalOfficerList.toString());

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

    //Write a method for Search medicalofficer data record
    public static MedicalOfficer searchMedicalOfficer(String seachTerm, String filepath) {
        boolean found = false;
        MedicalOfficer searchedMedicalOfficer = new MedicalOfficer();
        String id = seachTerm;


        try {
            String userRoll = null;
            String name = null, address = null;
            String gender = null, marital = null, dob = null, phonenumber = null, idcardNumber = null, userName = null, password = null;
            int staffId = 0;
            String staffEmailAddress = null, speciality = null;
            scanner = new Scanner(new File(filepath));
            scanner.useDelimiter("[,\n]");

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
                staffId = scanner.nextInt();
                staffEmailAddress = scanner.next();
                speciality = scanner.next();

                if (idcardNumber.equals(seachTerm)) {
                    found = true;
                }
            }
            if (found){
                searchedMedicalOfficer.setUserRoll(getUserRoll(userRoll));
                searchedMedicalOfficer.setName(name);
                searchedMedicalOfficer.setGender(gender);
                searchedMedicalOfficer.setMaritalStatus(marital);
                searchedMedicalOfficer.setDob(getLocalDatefromString(dob));
                searchedMedicalOfficer.setPhoneNumber(phonenumber);
                searchedMedicalOfficer.setIdCardNumber(idcardNumber);
                searchedMedicalOfficer.setAddress(address);
                searchedMedicalOfficer.setUserName(userName);
                searchedMedicalOfficer.setUserPassword(password);
                searchedMedicalOfficer.setStaffID(staffId);
                searchedMedicalOfficer.setStaffEmailAddress(staffEmailAddress);
                searchedMedicalOfficer.setSpeciality(speciality);


                System.out.println(searchedMedicalOfficer.toString());


            }else {
                System.out.println("record not found");
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return searchedMedicalOfficer;

    }


    /* =============================================================================================================
       Common User  Action tasks
      =============================================================================================================
    */

            private static String getStringPatient (Patient patientEdit){
                String editPatient = patientEdit.getUserRoll() + ","
                        + patientEdit.getName() + ","
                        + patientEdit.getGender() + ","
                        + patientEdit.getMaritalStatus() + ","
                        + patientEdit.getDob() + ","
                        + patientEdit.getPhoneNumber() + ","
                        + patientEdit.getIdCardNumber() + ","
                        + patientEdit.getAddress() + ","
                        + patientEdit.getUserName() + ","
                        + patientEdit.getUserPassword() + ","
                        + patientEdit.getBloodGroup() + ","
                        + patientEdit.getAllergies();

                return editPatient;

            }

            private static String getStringMedicalOfficer(MedicalOfficer medicalOfficerEdit) {

                String editMedicalOfficer = medicalOfficerEdit.getUserRoll() + ","
                        + medicalOfficerEdit.getName() + ","
                        + medicalOfficerEdit.getGender() + ","
                        + medicalOfficerEdit.getMaritalStatus() + ","
                        + medicalOfficerEdit.getDob() + ","
                        + medicalOfficerEdit.getPhoneNumber() + ","
                        + medicalOfficerEdit.getIdCardNumber() + ","
                        + medicalOfficerEdit.getAddress() + ","
                        + medicalOfficerEdit.getUserName() + ","
                        + medicalOfficerEdit.getUserPassword() + ","
                        + medicalOfficerEdit.getStaffID() + ","
                        + medicalOfficerEdit.getStaffEmailAddress() + ","
                        + medicalOfficerEdit.getSpeciality();

                return editMedicalOfficer;

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


        }
