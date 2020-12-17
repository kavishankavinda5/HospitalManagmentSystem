package sample.model;

import java.sql.Time;
import java.time.LocalDate;

public class Appointment {
    private Patient patient;
    private LocalDate appointmentDate;
    private Time time;
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
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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
}
