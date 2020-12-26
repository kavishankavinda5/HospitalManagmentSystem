package sample.model;

import java.sql.Time;
import java.time.LocalDate;

public class Appointment {
    private Patient patient;
    private LocalDate appointmentDate;
    private Time timeModel;
    private  MedicalOfficer medicalOfficer;
    private  AppointmentStatus  appointmentStatus;

    public Appointment() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getTime() {
        return timeModel;
    }

    public void setTime(Time timeModel) {
        this.timeModel = timeModel;
    }

    public MedicalOfficer getMedicalOfficer() {
        return medicalOfficer;
    }

    public void setMedicalOfficer(MedicalOfficer medicalOfficer) {
        this.medicalOfficer = medicalOfficer;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    @Override
    public String toString() {
        return patient.getName()+"~"+patient.getIdCardNumber() + "~" + appointmentDate + "~" + timeModel + "~" + medicalOfficer.getName()+ "~" + medicalOfficer.getIdCardNumber()  + "~" + appointmentStatus;
    }
}
