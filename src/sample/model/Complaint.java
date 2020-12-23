package sample.model;

public class Complaint {
    private String complaintType;
    private String complaintBy;
    private String phoneNumber;
    private String description;
    private String note;
    private String actiontaken;

    public Complaint() {
    }

    public Complaint(String complaintType, String complaintBy, String phoneNumber, String description, String note, String actiontaken) {
        this.complaintType = complaintType;
        this.complaintBy = complaintBy;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.note = note;
        this.actiontaken = actiontaken;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintBy() {
        return complaintBy;
    }

    public void setComplaintBy(String complaintBy) {
        this.complaintBy = complaintBy;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActiontaken() {
        return actiontaken;
    }

    public void setActiontaken(String actiontaken) {
        this.actiontaken = actiontaken;
    }

    @Override
    public String toString() {
        return complaintType+","+
                complaintBy+","+
                phoneNumber+","+
                description+","+
                note+","+
                actiontaken;
    }
}
