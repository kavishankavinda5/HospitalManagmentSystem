package sample.model;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.Date;

public abstract class Employee extends User{
    private int staffID;
    private String staffEmailAddress;
    private LocalDate dateOfJoining;
    private Image staffPhoto;

    public int getStaffID() {
        return staffID;
    }

    public String getStaffEmailAddress() {
        return staffEmailAddress;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public Image getStaffPhoto() {
        return staffPhoto;
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

    public void setStaffPhoto(Image staffPhoto) {
        this.staffPhoto = staffPhoto;
    }
}
