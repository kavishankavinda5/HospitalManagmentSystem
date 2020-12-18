package sample.model;

import java.time.LocalDate;

//create a Postal class

public abstract class Postal {

    //Instance variable

    private PostalType postalType;
    private String referenceNo;
    private String toName;
    private String fromName;
    private String note;
    private LocalDate date;

//getters and setters

    public void setPostalType(PostalType postalType) {
        this.postalType = postalType;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PostalType getPostalType() {
        return postalType;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public String getToName() {
        return toName;
    }

    public String getFromName() {
        return fromName;
    }

    public String getNote() {
        return note;
    }

    public LocalDate getDate() {
        return date;
    }

//toString method
    @Override
    public String toString() {
        return "Postal{" +
                "postalType=" + postalType +
                ", referenceNo='" + referenceNo + '\'' +
                ", toName='" + toName + '\'' +
                ", fromName='" + fromName + '\'' +
                ", note='" + note + '\'' +
                ", date=" + date +
                '}';
    }
}
