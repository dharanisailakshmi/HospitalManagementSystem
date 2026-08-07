package pro;


import java.time.LocalDate;
import java.util.ArrayList;

public class Doctor extends User {
    private int doctorId;
    private String name;
    private String specialty;
    private ArrayList<LocalDate> availability;

    public Doctor(String username, String password, int doctorId, String name, String specialty) {
        super(username, password);
        this.doctorId = doctorId;
        this.name = name;
        this.specialty = specialty;
        this.availability = new ArrayList<>();
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }
    public String getUsername()
    {
    	return username;
    }
    public String getPassword()
    {
    	return password;
    }
    public ArrayList<LocalDate> getAvailability() {
        return availability;
    }

    public void addAvailability(LocalDate date) {
        if (!availability.contains(date)) {
            availability.add(date);
            System.out.println("Availability added for " + date);
        } else {
            System.out.println("Already available on " + date);
        }
    }

    @Override
    public String toString() {
        return "Doctor ID: " + doctorId + ", Name: " + name + ", Specialty: " + specialty;
    }
}
