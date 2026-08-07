# Hospital Management System

A console-based Hospital Management System built in Java, demonstrating core Object-Oriented Programming principles alongside JDBC for persistent data storage in MySQL.

## Overview

The system supports three types of users — **Admin**, **Doctor**, and **Patient** — each with their own login and menu of actions. All data (users, appointments, room assignments) is stored in a MySQL database, so information persists between runs of the program.

## Features

**Admin**
- Add new doctors
- Register new patients (with automatic room assignment)
- View all doctors
- View all patients
- View room vacancies

**Doctor**
- Log in with username/password
- View all appointments assigned to them

**Patient**
- Log in with username/password
- View appointments filtered by month and year
- Book a new appointment by specialty and doctor, with date validation (no past dates, no Sundays, no double-booking on the same date)

## OOP Concepts Used

- **Abstraction** — `User` is an abstract class defining shared behavior for all account types
- **Inheritance** — `Admin`, `Doctor`, and `Patient` all extend `User`
- **Encapsulation** — fields are private/protected with controlled access through getters and setters
- **Polymorphism** — shared `User` reference type used across authentication logic

## Tech Stack

- Java (JDK 17+)
- JDBC (`mysql-connector-j`)
- MySQL

## Project Structure

```
pro/
├── User.java                     # Abstract base class
├── Admin.java                    # Admin user
├── Doctor.java                   # Doctor user
├── Patient.java                  # Patient user
├── Appointment.java              # Appointment model
├── Room.java                     # Room model
├── DBConnection.java             # JDBC connection handler
├── AdminDAO.java                 # Admin database operations
├── DoctorDAO.java                # Doctor database operations
├── PatientDAO.java               # Patient database operations
├── AppointmentDAO.java           # Appointment database operations
├── RoomDAO.java                  # Room database operations
├── HospitalManagementSystem.java # Main class — menus and program flow
```
## Database Schema

The project uses a MySQL database named `hospital`.

The complete database structure, including:
- Tables (`admins`, `doctors`, `patients`, `rooms`, `appointments`)
- Primary keys and foreign key relationships
- Default seed data

is provided separately in the **`schema.sql`** file.

Before running the application:
1. Open MySQL Workbench or any MySQL client.
2. Execute the `schema.sql` file to create the database and tables.
3. Update the MySQL credentials in `DBConnection.java`.
4. Run the application.

## Setup

1. **Install MySQL** and make sure it's running.
2. **Create the database** — copy the SQL from the [Database Schema](#database-schema) section above and run it in MySQL Workbench, phpMyAdmin, or the `mysql` command line.
3. **Add the MySQL JDBC driver** to your classpath (via Maven or manually):
   ```xml
   <dependency>
       <groupId>com.mysql</groupId>
       <artifactId>mysql-connector-j</artifactId>
       <version>8.3.0</version>
   </dependency>
   ```
4. **Set your database credentials** — open `DBConnection.java` and edit these three lines near the top to match your own local MySQL setup:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/hospital";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
   ```
   Change `hospital` if you named your database something else, and update `USER`/`PASSWORD` to your own MySQL login.
5. **Compile and run:**
   ```bash
   javac pro/*.java
   java pro.HospitalManagementSystem
   ```

## Default Login Credentials (seeded automatically on first run)

| Role    |  Username |   Password  |
|---------|-----------|-------------|
| Admin   | `Admin`   | `Admin@123` |
| Doctor  | `drsmith` | `Docpass@1` |
| Patient | `john`    | `John@123`  | 

## Notes

- New doctor/patient passwords require at least 6 characters, one uppercase letter, one number, and one special character.
- The app checks the database on startup and only inserts the default seed data (admin, doctors, rooms, patients) if the tables are empty, so it's safe to run more than once.
