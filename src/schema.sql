-- Run this once in MySQL Workbench / mysql CLI before starting the app.

CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

CREATE TABLE admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE doctors (
    doctor_id INT PRIMARY KEY,          -- admin assigns this manually, so NOT auto_increment
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL
);

CREATE TABLE patients (
    patient_id INT PRIMARY KEY,         -- admin assigns this manually too
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
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,   -- DB generates this now, not idCounter
    doctor_id INT NOT NULL,
    patient_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

-- Seed data equivalent to your old initializeSystem() method.
-- The Java code also has a seedIfEmpty() safety check, but running this
-- once by hand is simplest for a first test.

INSERT INTO admins (username, password) VALUES ('Admin', 'Admin@123');

INSERT INTO doctors (doctor_id, username, password, name, specialty) VALUES
(101, 'drsmith',   'Docpass@1', 'Dr. Smith', 'Cardiology'),
(102, 'drjohnson', 'Docpass@2', 'Dr. Johnson', 'Neurology'),
(103, 'drjohn',    'Docpass@3', 'Dr. John', 'Dermotology'),
(104, 'drPrudhvi', 'Docpass@4', 'Dr. Prudhvi Raj', 'Dental');

INSERT INTO rooms (room_number, is_occupied) VALUES
(1, FALSE), (2, FALSE), (3, FALSE), (4, FALSE), (5, FALSE),
(6, FALSE), (7, FALSE), (8, FALSE), (9, FALSE), (10, FALSE);

-- Patients occupy rooms 1-3 in your original seed data.
INSERT INTO patients (patient_id, username, password, name, age, address, disease, room_number) VALUES
(201, 'john',  'John@123',  'John Doe',   30, '123 Main St',   'Heart Disease', 1),
(202, 'jane',  'jane123',   'Jane Smith', 25, '456 Elm St',    'Migraine',      2),
(203, 'javed', 'Javed@123', 'Javed Don',  29, 'ROAD 1, KPHB',  'Dental Disease', 3);

UPDATE rooms SET is_occupied = TRUE WHERE room_number IN (1, 2, 3);

INSERT INTO appointments (doctor_id, patient_id, appointment_date) VALUES
(101, 201, '2024-12-15'),
(102, 202, '2024-12-16');
-- Note: your original code also had an appointment for doctor 103 / patient 204,
-- but patient 204 was never actually created, so it's left out here.
