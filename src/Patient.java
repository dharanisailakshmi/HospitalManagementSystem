package pro;

public class Patient extends User {
    private int patientId;
    private String name;
    private int age;
    private String address;
    private String disease;
    private int roomNumber;

    public Patient(String username, String password, int patientId, String name, int age, String address, String disease, int roomNumber) {
        super(username, password);
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.address = address;
        this.disease = disease;
        this.roomNumber = roomNumber;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId + ", Name: " + name + ", Age: " + age +
               ", Address: " + address + ", Disease: " + disease + ", Room Number: " + roomNumber;
    }
}
