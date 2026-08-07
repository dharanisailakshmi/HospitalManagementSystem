package pro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
import java.time.DayOfWeek;

class HospitalManagementSystem
{
 
    private AdminDAO adminDAO = new AdminDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private RoomDAO roomDAO = new RoomDAO();

    private Scanner scanner = new Scanner(System.in);

    public HospitalManagementSystem() {
    
        seedIfEmpty();
    }

    private void seedIfEmpty() {
        if (adminDAO.countAdmins() == 0) {
            adminDAO.insert(new Admin("Admin", "Admin@123"));
        }

        if (doctorDAO.countDoctors() == 0) {
            doctorDAO.insert(new Doctor("drsmith", "Docpass@1", 101, "Dr. Smith", "Cardiology"));
            doctorDAO.insert(new Doctor("drjohnson", "Docpass@2", 102, "Dr. Johnson", "Neurology"));
            doctorDAO.insert(new Doctor("drjohn", "Docpass@3", 103, "Dr. John", "Dermotology"));
            doctorDAO.insert(new Doctor("drPrudhvi", "Docpass@4", 104, "Dr. Prudhvi Raj", "Dental"));
        }

        if (roomDAO.countRooms() == 0) {
            for (int i = 1; i <= 10; i++) {
                roomDAO.insertRoom(i);
            }
        }

        if (patientDAO.countPatients() == 0) {
            Room availableRoom = roomDAO.getFirstAvailableRoom();
            if (availableRoom != null) {
                roomDAO.assignRoom(availableRoom.getRoomNumber());
                patientDAO.insert(new Patient("john", "John@123", 201, "John Doe", 30, "123 Main St", "Heart Disease", availableRoom.getRoomNumber()));
            }

            availableRoom = roomDAO.getFirstAvailableRoom();
            if (availableRoom != null) {
                roomDAO.assignRoom(availableRoom.getRoomNumber());
                patientDAO.insert(new Patient("jane", "jane123", 202, "Jane Smith", 25, "456 Elm St", "Migraine", availableRoom.getRoomNumber()));
            }

            availableRoom = roomDAO.getFirstAvailableRoom();
            if (availableRoom != null) {
                roomDAO.assignRoom(availableRoom.getRoomNumber());
                patientDAO.insert(new Patient("javed", "Javed@123", 203, "Javed Don", 29, "ROAD 1, KPHB", "Dental Disease", availableRoom.getRoomNumber()));
            }

            appointmentDAO.insert(new Appointment(101, 201, LocalDate.of(2024, 12, 15)));
            appointmentDAO.insert(new Appointment(102, 202, LocalDate.of(2024, 12, 16)));
        }
    }

 
    public void start() {
        while (true) {
            showMainMenu();
        }
    }

    private void showMainMenu() {
        System.out.println("\n---- Hospital Management System ----");
        System.out.println("1. Admin Login");
        System.out.println("2. Doctor Login");
        System.out.println("3. Patient Login");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = getIntegerInput();

        switch (choice) {
            case 1:
                adminLogin();
                break;
            case 2:
                doctorLogin();
                break;
            case 3:
                patientLogin();
                break;
            case 4:
                System.out.println("Exiting system...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    /* ************************************************************** Admin login process *********************************************************** */
    private void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine().trim();

        Admin loggedInAdmin = adminDAO.findByCredentials(username, password);

        if (loggedInAdmin != null) {
            System.out.println("\033[93mAdmin login successful! Welcome, \033[0m" + username + ".");
            adminMenu();
            return;
        }

        System.out.println("Invalid Admin credentials. Please try again.");
    }

    /* **************************************************************  Admin menu ******************************************************************* */
    private void adminMenu() {
        while (true) {
            System.out.println("\n---- Admin Menu ----");
            System.out.println("1. Add Doctor");
            System.out.println("2. Patient Registration");
            System.out.println("3. View Doctors");
            System.out.println("4. View Patients");
            System.out.println("5. View Room Vacancies");
            System.out.println("6.  Logout");
            System.out.print("Enter your choice: ");
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    registerPatient();
                    break;
                case 3:
                    viewDoctors();
                    break;
                case 4:
                    viewPatients();
                    break;
                case 5:
                    viewRoomVacancies();
                    break;
                case 6:
                    System.out.println("Logging out from Admin account...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /* ******************************************************************* Admin: Add Doctor ************************************************************* */
    private void addDoctor()
    {
        String username;
        while (true) {
            System.out.print("Enter Doctor Username: ");
            username = scanner.nextLine().trim();
            if (isValidUsername(username)) {
                break;
            } else {
                System.out.println("Invalid username. Please use only alphanumeric characters and underscores.");
            }
        }

        String password;
        while (true) {
            System.out.print("Enter Doctor Password: ");
            password = scanner.nextLine().trim();
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println("Invalid password. Password must be at least 6 characters long,atleast contain one special character,captial letter and number");
            }
        }

        System.out.print("Enter Doctor ID: ");
        int doctorId = getIntegerInput();

        String name;
        while (true) {
            System.out.print("Enter Doctor Name: ");
            name = scanner.nextLine().trim();
            if (isValidName(name)) {
                break;
            } else {
                System.out.println("Invalid name. Please use only letters and spaces.");
            }
        }

        System.out.print("Enter Doctor Specialty: ");
        String specialty = scanner.nextLine().trim();

        
        if (doctorDAO.findById(doctorId) != null) {
            System.out.println("Doctor with ID " + doctorId + " already exists.");
            return;
        }

        if (doctorDAO.findByUsername(username) != null) {
            System.out.println("Doctor with Username " + username + " already exists.");
            return;
        }

        Doctor newDoctor = new Doctor(username, password, doctorId, name, specialty);
        doctorDAO.insert(newDoctor); 
        System.out.println("\033[93mDoctor added successfully!\033[0m");
    }

    /* *********************************** Admin: Patient Registration *********************************** */
    private void registerPatient() {
        if (roomDAO.getFirstAvailableRoom() == null) {
            System.out.println("No room vacancies available. Cannot register new patient.");
            return;
        }

        String username;
        while (true) {
            System.out.print("Enter Patient Username: ");
            username = scanner.nextLine().trim();
            if (isValidUsername(username)) {
                break;
            } else {
                System.out.println("Invalid username. Please use only alphanumeric characters and underscores.");
            }
        }

        String password;
        while (true) {
            System.out.print("Enter Patient Password: ");
            password = scanner.nextLine().trim();
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println("Invalid password. Password must be at least 6 characters long,atleast contain one special character,captial letter and number");
            }
        }

        System.out.print("Enter Patient ID: ");
        int patientId = getIntegerInput();

        String name;
        while (true) {
            System.out.print("Enter Patient Name: ");
            name = scanner.nextLine().trim();
            if (isValidName(name)) {
                break;
            } else {
                System.out.println("Invalid name. Please use only letters and spaces.");
            }
        }

        System.out.print("Enter Patient Age: ");
        int age = getIntegerInput();

        System.out.print("Enter Patient Address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Enter Patient Disease: ");
        String disease = scanner.nextLine().trim();

       
        if (patientDAO.findById(patientId) != null) {
            System.out.println("Patient with ID " + patientId + " already exists.");
            return;
        }

        if (patientDAO.findByUsername(username) != null) {
            System.out.println("Patient with Username " + username + " already exists.");
            return;
        }

        Room assignedRoom = roomDAO.getFirstAvailableRoom();

        if (assignedRoom == null) {
            System.out.println("No room vacancies available. Cannot register new patient.");
            return;
        }

        roomDAO.assignRoom(assignedRoom.getRoomNumber()); 
        Patient newPatient = new Patient(username, password, patientId, name, age, address, disease, assignedRoom.getRoomNumber());
        patientDAO.insert(newPatient); 
        System.out.println("Patient registered successfully! Assigned Room Number: " + assignedRoom.getRoomNumber());
    }

    /* ******************************************** Admin: View Doctors ****************************************** */
    private void viewDoctors()
    {
        ArrayList<Doctor> doctors = doctorDAO.getAll();
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
            return;
        }

        System.out.println("\n---- List of Doctors ----");
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    /* ********************************************** Admin: View Patients **************************************** */
    private void viewPatients()
    {
        ArrayList<Patient> patients = patientDAO.getAll(); 
        if (patients.isEmpty()) {
            System.out.println("No patients available.");
            return;
        }

        System.out.println("\n---- List of Patients ----");
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    /* ************************************************** Admin: View Room Vacancies ********************************* */
    private void viewRoomVacancies()
    {
        ArrayList<Room> vacantRooms = roomDAO.getVacantRooms(); 
        System.out.println("\n---- Room Vacancies ----");

        if (vacantRooms.isEmpty()) {
            System.out.println("No room vacancies available.");
            return;
        }

        for (Room room : vacantRooms) {
            System.out.println("Room Number: " + room.getRoomNumber());
        }
    }

    /* ************************************** Doctor login process *************************************** */
    private void doctorLogin() {
        System.out.print("Enter Doctor Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Doctor Password: ");
        String password = scanner.nextLine().trim();

       
        Doctor loggedInDoctor = doctorDAO.findByCredentials(username, password);

        if (loggedInDoctor != null) {
            System.out.println("\033[93mDoctor login successful! Welcome, \033[0m" + loggedInDoctor.getName() + ".");
            doctorMenu(loggedInDoctor);
            return;
        }

        System.out.println("Invalid Doctor credentials. Please try again.");
    }

    /* ***************************************** Doctor menu ********************************************** */
    private void doctorMenu(Doctor doctor)
    {
        while (true) {
            System.out.println("\n---- Doctor Menu ----");
            System.out.println("1. View Appointments");
            System.out.println("2. Logout");
            System.out.print("Enter your choice: ");
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    viewDoctorAppointments(doctor);
                    break;
                case 2:
                    System.out.println("Logging out from Doctor account...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /* ****************************************** Doctor: View Appointments **************************************** */
    private void viewDoctorAppointments(Doctor doctor)
    {
        ArrayList<Appointment> myAppointments = appointmentDAO.getByDoctorId(doctor.getDoctorId()); // CHANGED
        System.out.println("\n---- Your Appointments ----");

        if (myAppointments.isEmpty()) {
            System.out.println("No appointments assigned to you.");
            return;
        }

        for (Appointment appointment : myAppointments) {
            System.out.println(appointment);
        }
    }

    /* ********************************************************* Patient login process *************************************************************** */
    private void patientLogin() 
    {
        System.out.print("Enter Patient Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Patient Password: ");
        String password = scanner.nextLine().trim();

        Patient loggedInPatient = patientDAO.findByCredentials(username, password);

        if (loggedInPatient != null) 
        {
            System.out.println("\033[93mPatient login successful! Welcome, \033[0m" + loggedInPatient.getName() + ".");
            patientMenu(loggedInPatient);
            return;
        }

        System.out.println("Invalid Patient credentials. Please try again.");
    }

    /* ************************************************************************************** Patient menu ***************************************************************** */
    private void patientMenu(Patient patient) {
        while (true) {
            System.out.println("\n---- Patient Menu ----");
            System.out.println("1. View Appointments");
            System.out.println("2. Book Appointment");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    viewPatientAppointments(patient);
                    break;
                case 2:
                    bookAppointment(patient);
                    break;
                case 3:
                    System.out.println("Logging out from Patient account...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /* *************************************************************************** Patient: View Appointments ********************************************************** */
    private void viewPatientAppointments(Patient patient)
    {
        System.out.print("Enter Month (1-12): ");
        int month = getIntegerInput();
        System.out.print("Enter Year (e.g., 2024): ");
        int year = getIntegerInput();

       
        ArrayList<Appointment> filtered = appointmentDAO.getByPatientAndMonth(patient.getPatientId(), month, year);

        System.out.println("\n---- Your Appointments for " + month + "/" + year + " ----");
        if (filtered.isEmpty()) 
        {
            System.out.println("No appointments found for the specified month and year.");
            return;
        }

        for (Appointment appointment : filtered) 
        {
            System.out.println(appointment);
        }
    }

    /* ***************************************************************** Patient: Book Appointment ************************************************ */
    private void bookAppointment(Patient patient) {
        System.out.println("\n---- Book Appointment ----");
        System.out.println("Available Specialties:");
        ArrayList<String> specialties = doctorDAO.getUniqueSpecialties();

        for (int i = 0; i < specialties.size(); i++) {
            System.out.println((i + 1) + ". " + specialties.get(i));
        }

        System.out.print("Select Specialty by number: ");
        int specialtyChoice = getIntegerInput();

        if (specialtyChoice < 1 || specialtyChoice > specialties.size()) {
            System.out.println("Invalid specialty choice. Booking canceled.");
            return;
        }

        String selectedSpecialty = specialties.get(specialtyChoice - 1);
        ArrayList<Doctor> specializedDoctors = doctorDAO.findBySpecialty(selectedSpecialty);

        if (specializedDoctors.isEmpty()) {
            System.out.println("No doctors available with specialty: " + selectedSpecialty);
            return;
        }

        System.out.println("\n---- Available Doctors ----");
        for (int i = 0; i < specializedDoctors.size(); i++) {
            System.out.println((i + 1) + ". " + specializedDoctors.get(i));
        }

        System.out.print("Select Doctor by number: ");
        int doctorChoice = getIntegerInput();

        if (doctorChoice < 1 || doctorChoice > specializedDoctors.size()) {
            System.out.println("Invalid doctor choice. Booking canceled.");
            return;
        }

        Doctor selectedDoctor = specializedDoctors.get(doctorChoice - 1);

        scanner.nextLine(); 
        System.out.println("Enter patient problem: ");
        String problem = scanner.nextLine();
        patient.setDisease(problem);                    
        patientDAO.updateDisease(patient.getPatientId(), problem); 

        System.out.println("Enter year for appointment: ");
        int year = scanner.nextInt();
        System.out.println("Enter Month for appointment: ");
        int month = scanner.nextInt();

        LocalDate date1 = LocalDate.of(year, month, 1);
        System.out.println("Su Mo Tu We Th Fr Sa");

        int dayOfWeek = date1.getDayOfWeek().getValue() % 7;
        for (int i = 0; i < dayOfWeek; i++) {
            System.out.print("   ");
        }

        while (date1.getMonthValue() == month) {
            System.out.printf("%2d ", date1.getDayOfMonth());
            if (date1.getDayOfWeek().getValue() % 7 == 6) {
                System.out.println();
            }
            date1 = date1.plusDays(1);
        }
        System.out.println();

        scanner.nextLine(); 
        System.out.print("Enter Appointment Date (DD): ");
        String dateInput = scanner.nextLine().trim();

        String formattedMonth = String.format("%02d", month);
        String formattedDay = String.format("%02d", Integer.parseInt(dateInput));

        dateInput = year + "-" + formattedMonth + "-" + formattedDay;

        LocalDate date;
        try {
            date = LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Booking canceled.");
            return;
        }

        if (date.isBefore(LocalDate.now())) {
            System.out.println("You cannot book an appointment in the past. Please select a future date.");
            return;
        }

        DayOfWeek dayOfWeek1 = date.getDayOfWeek();
        if (dayOfWeek1 == DayOfWeek.SUNDAY) {
            System.out.println("Sorry, doctors are not available on Sunday. Try with another date.");
            return;
        }

       
        if (appointmentDAO.existsForPatientOnDate(patient.getPatientId(), date)) {
            System.out.println("You already have an appointment on this date. Booking canceled.");
            return;
        }

        Appointment newAppointment = new Appointment(selectedDoctor.getDoctorId(), patient.getPatientId(), date);
        appointmentDAO.insert(newAppointment); 
        System.out.println("\033[93mAppointment booked successfully! Appointment ID: \033[0m" + newAppointment.getAppointmentId());
    }

    
    private int getIntegerInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    public static boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }

    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]+$");
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 6) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasSpecialChar = false;
        boolean hasNumber = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }

            if (hasUpperCase && hasSpecialChar && hasNumber) {
                return true;
            }
        }

        return false;
    }

   
    public static void main(String[] args)
    {
        HospitalManagementSystem system = new HospitalManagementSystem();
        system.start();
    }
}
