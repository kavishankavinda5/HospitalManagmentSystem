package sample.model;

import java.time.LocalDate;

public abstract class Employee extends User{
    private int staffID;
    private String staffEmailAddress;
    private LocalDate dateOfJoining;
    private String photoPath;

    public int getStaffID() {
        return staffID;
    }

    public String getStaffEmailAddress() {
        return staffEmailAddress;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public String getStaffPhoto() {
        return photoPath;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setStaffEmailAddress(String staffEmailAddress) {
        this.staffEmailAddress = staffEmailAddress;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public void setStaffPhoto(String photoPath) {
        this.photoPath = photoPath;
    }


    @Override


    public String toString() {
        return super.toString()+"~"+staffID +"~" + staffEmailAddress + "~" + dateOfJoining ;
    }
}
