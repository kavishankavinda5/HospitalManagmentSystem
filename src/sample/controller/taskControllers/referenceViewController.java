package sample.controller.taskControllers;
import sample.model.UserRoll;
import java.util.ArrayList;

public class referenceViewController {

   public static ArrayList<UserRoll> userRolls = new ArrayList<>();

   public static ArrayList<String> doctorSpeciality = new ArrayList<>();

   public static ArrayList<String> gender = new ArrayList<>();

    public static void setUserRolls(UserRoll userRolls) {
        referenceViewController.userRolls.add(userRolls);
    }

    public static ArrayList<UserRoll> getUserRolls() {
        return userRolls;
    }

    public static void setDoctorSpeciality(String speciality) {
        doctorSpeciality.add(speciality);
    }

    public static ArrayList<String> getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public static ArrayList<String> getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        referenceViewController.gender.add(gender);
    }
}
