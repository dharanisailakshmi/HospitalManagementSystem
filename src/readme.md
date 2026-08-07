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

Run this in MySQL Workbench, phpMyAdmin, or the `mysql` CLI before starting the app. It creates the `hospital` database, all five tables, and the same seed data your app inserts automatically on first run.

```sql
CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

CREATE TABLE admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE doctors (
    doctor_id INT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL
);

CREATE TABLE patients (
    patient_id INT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    address VARCHAR(200),
    disease VARCHAR(200),
    room_number INT
);

CREATE TABLE rooms (
    room_number INT PRIMARY KEY,
    is_occupied BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE appointments (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id INT NOT NULL,
    patient_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

INSERT INTO admins (username, password) VALUES ('Admin', 'Admin@123');

INSERT INTO doctors (doctor_id, username, password, name, specialty) VALUES
(101, 'drsmith',   'Docpass@1', 'Dr. Smith', 'Cardiology'),
(102, 'drjohnson', 'Docpass@2', 'Dr. Johnson', 'Neurology'),
(103, 'drjohn',    'Docpass@3', 'Dr. John', 'Dermotology'),
(104, 'drPrudhvi', 'Docpass@4', 'Dr. Prudhvi Raj', 'Dental');

INSERT INTO rooms (room_number, is_occupied) VALUES
(1, FALSE), (2, FALSE), (3, FALSE), (4, FALSE), (5, FALSE),
(6, FALSE), (7, FALSE), (8, FALSE), (9, FALSE), (10, FALSE);

INSERT INTO patients (patient_id, username, password, name, age, address, disease, room_number) VALUES
(201, 'john',  'John@123',  'John Doe',   30, '123 Main St',   'Heart Disease', 1),
(202, 'jane',  'jane123',   'Jane Smith', 25, '456 Elm St',    'Migraine',      2),
(203, 'javed', 'Javed@123', 'Javed Don',  29, 'ROAD 1, KPHB',  'Dental Disease', 3);

UPDATE rooms SET is_occupied = TRUE WHERE room_number IN (1, 2, 3);

INSERT INTO appointments (doctor_id, patient_id, appointment_date) VALUES
(101, 201, '2024-12-15'),
(102, 202, '2024-12-16');
```

> Note: the seed `INSERT`s above are optional — the app's `seedIfEmpty()` method inserts the same default data automatically the first time it runs against an empty database. Running them by hand is just a quick way to confirm your MySQL connection works before touching the Java code.

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

| Role | Username | Password |
|---|---|---|
| Admin | `Admin` | `Admin@123` |
| Doctor | `drsmith` | `Docpass@1` |
| Patient | `john` | `John@123` |

## Notes

- New doctor/patient passwords require at least 6 characters, one uppercase letter, one number, and one special character.
- The app checks the database on startup and only inserts the default seed data (admin, doctors, rooms, patients) if the tables are empty, so it's safe to run more than once.
