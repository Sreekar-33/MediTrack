package com.airtribe.meditrack.test;


import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.services.AppointmentService;
import com.airtribe.meditrack.services.DoctorService;
import com.airtribe.meditrack.services.PatientService;
import com.airtribe.meditrack.enums.*;

import java.util.Collection;

public class TestRunner {

    public static void main(String[] args) {

        System.out.println("----- Running MediTrack Manual Tests -----");

        testPatientService();
        testDoctorService();
        testAppointmentService();

        System.out.println("----- Tests Completed -----");
    }

    private static void testPatientService() {

        System.out.println("\nTesting PatientService");

        PatientService patientService = new PatientService();

        Patient p1 = patientService.createPatient("John", "john@mail.com", 30);
        Patient p2 = patientService.createPatient("Alice", "alice@mail.com", 25);

        System.out.println("Created Patients:");
        System.out.println(p1.getName());
        System.out.println(p2.getName());

        Collection<Patient> result = patientService.searchByName("John");

        System.out.println("Search by name 'John': " + result.size());

        Collection<Patient> ageSearch = patientService.searchByAge(30);

        System.out.println("Search by age 30: " + ageSearch.size());
    }

    private static void testDoctorService() {

        System.out.println("\nTesting DoctorService");

        DoctorService doctorService = new DoctorService();

        Doctor d1 = doctorService.createDoctor(
                "Dr Smith",
                "smith@mail.com",
                45,
                Specialization.CARDIOLOGIST,
                400
        );

        Doctor d2 = doctorService.createDoctor(
                "Dr Lee",
                "lee@mail.com",
                40,
                Specialization.DERMATOLOGIST,
                500
        );

        System.out.println("Created Doctors:");
        System.out.println(d1.getName());
        System.out.println(d2.getName());

        Collection<Doctor> result = doctorService.searchByAge(45);

        System.out.println("Search doctor age 45: " + result.size());
    }

    private static void testAppointmentService() {

        System.out.println("\nTesting AppointmentService");

        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService();

        Patient p = patientService.createPatient("Bob", "bob@mail.com", 35);

        Doctor d = doctorService.createDoctor(
                "Dr Adams",
                "adams@mail.com",
                50,
                Specialization.NEUROLOGIST,
                1000
        );

        Appointment ap = appointmentService.createAppointment(d, p);

        System.out.println("Appointment created with ID: " + ap.getAppointmentId());

        try {

            appointmentService.getAppointment(999);

        } catch (AppointmentNotFoundException e) {

            System.out.println("Exception caught successfully: " + e.getMessage());

        }
    }

}