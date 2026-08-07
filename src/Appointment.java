package pro;

import java.time.LocalDate;

public class Appointment {

    private int appointmentId;
    private int doctorId;
    private int patientId;
    private LocalDate date;

    
    public Appointment(int doctorId, int patientId, LocalDate date) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
    }

    public Appointment(int appointmentId, int doctorId, int patientId, LocalDate date) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId +
               ", Doctor ID: " + doctorId +
               ", Patient ID: " + patientId +
               ", Date: " + date;
    }
}
