package sample.model;

import java.util.Date;

public abstract class User {
    private UserRoll userRoll;
    private String userName;
    private String name;
    private String gender;
    private Long phoneNumber;
    private String idCardNumber;
    private Date dob;
    private String address;
    private String maritalStatus;

    public UserRoll getUserRoll() {
        return userRoll;
    }

    public void setUserRoll(UserRoll userRoll) {
        this.userRoll = userRoll;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public Date getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
