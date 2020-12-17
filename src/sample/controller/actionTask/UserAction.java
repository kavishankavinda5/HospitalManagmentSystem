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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static void setVariable(int lineNumber, String data) throws IOException {
        Path path = Paths.get("data.txt");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(lineNumber - 1, data);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public static Patient searchPatient(String seachTerm, String filepath){
        boolean found = false;
        Patient patient1 = new Patient();
        String id =seachTerm;



        try{
            String userRoll = null;
            String name=null;
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
                userName =scanner.next();
                password=scanner.next();
                blood =scanner.next();
                allergy =scanner.next();

                if (idcardNumber.equals(seachTerm)){
                    found = true;
                }
            }
            if (found){
                patient1.setUserRoll(getUserRoll(userRoll));
                patient1.setName(name);
                patient1.setGender(gender);
                patient1.setMaritalStatus(marital);
                patient1.setDob(getLocalDatefromString(dob));
                patient1.setPhoneNumber(phonenumber);
                patient1.setIdCardNumber(idcardNumber);
                patient1.setUserName(userName);
                patient1.setUserPassword(password);
                patient1.setBloodGroup(getBloodGroup(blood));
                patient1.setAllergies(allergy);

                System.out.println(patient1.toString());


            }else {
                System.out.println("record not found");
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

return patient1;

    }

    //get UserRoll enum object with the String type input of the name
    public static UserRoll getUserRoll(String name){
        UserRoll userRoll = null;
        switch (name){
            case "PATIENT":
                userRoll = UserRoll.PATIENT;
                break;

            case  "ADMIN":
                userRoll = UserRoll.ADMIN;
                break;

            case "RECEPTIONIST":
                userRoll = UserRoll.RECEPTIONIST;
                break;

            case "MEDICALOFFICER":
                userRoll = UserRoll.MEDICALOFFICER;
                break;

        }

        return  userRoll;
    }

    public static LocalDate getLocalDatefromString(String string) {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }


    public static BloodGroup getBloodGroup(String name){
        BloodGroup blooGroup = null;
        switch (name){
            case "A_POSITIVE":
                blooGroup = BloodGroup.A_POSITIVE;
                break;

            case  "A_NEGATIVE":
                blooGroup = BloodGroup.A_NEGATIVE;
                break;

            case "AB_POSITIVE":
                blooGroup = BloodGroup.AB_POSITIVE;
                break;

            case "AB_NEGATIVE":
                blooGroup = BloodGroup.AB_NEGATIVE;
                break;
            case "B_POSITIVE":
                blooGroup =BloodGroup.B_POSITIVE;
                break;
            case "B_NEGATIVE":
                blooGroup =BloodGroup.B_NEGATIVE;
                break;

        }

        return  blooGroup;
    }

    public static void addReceptionist(Receptionist receptionist,UserRoll roll){

        if (roll.equals(UserRoll.ADMIN)){
            saveReceptionist(receptionist);
        }
    }

    private static  void  saveReceptionist(Receptionist receptionist){
        File receptionFile = new File("src/sample/fileStorage/userData/receptionistData.txt");

        try (FileWriter fileWriter = new FileWriter(receptionFile, true)) {

            BufferedWriter receptionBufferedWriter = new BufferedWriter(fileWriter);

            receptionBufferedWriter.write(receptionist.getUserRoll().toString() + ",");
            receptionBufferedWriter.write(receptionist.getName() + ",");
            receptionBufferedWriter.write(receptionist.getGender() + ",");
            receptionBufferedWriter.write(receptionist.getMaritalStatus()+",");
            receptionBufferedWriter.write(receptionist.getDob() + ",");
            receptionBufferedWriter.write(receptionist.getPhoneNumber()+ ",");
            receptionBufferedWriter.write(receptionist.getIdCardNumber() + ",");
            receptionBufferedWriter.write(receptionist.getUserName()+",");
            receptionBufferedWriter.write(receptionist.getUserPassword() + ",");
            receptionBufferedWriter.write(receptionist.getStaffID()+",");
            receptionBufferedWriter.write(receptionist.getStaffEmailAddress());
            receptionBufferedWriter.newLine();
            receptionBufferedWriter.close();
            fileWriter.close();
            System.out.println("Receptionist saved: " + receptionFile.getPath()+" patient saved");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void addMedicalOfficer(MedicalOfficer medicalOfficer,UserRoll userRoll){

    }

}



