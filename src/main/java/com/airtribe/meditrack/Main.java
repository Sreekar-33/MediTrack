package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.services.*;
import com.airtribe.meditrack.util.*;
import com.airtribe.meditrack.billingStrategy.*;

import java.util.Collection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService();

        BillingService billingService =
                new BillingService(new StandardBilling());

        while (true) {

            try {

                System.out.println("\n====== MediTrack Menu ======");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. Delete Patient");
                System.out.println("4. Add Doctor");
                System.out.println("5. View Doctor");
                System.out.println("6. delete doctor");
                System.out.println("7. Create Appointment");
                System.out.println("8. View Appointment");
                System.out.println("9. Generate Bill");
                System.out.println("10. Exiting Meditrack");

                System.out.print("Enter choice: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        addPatient(scanner, patientService);
                        break;

                    case 2:
                        viewPatients(patientService);
                        break;

                    case 3:
                        deletePatient(scanner, patientService);
                        break;

                    case 4:
                        addDoctor(scanner, doctorService);
                        break;

                    case 5:
                        viewDoctors(doctorService);
                        break;

                    case 6:
                        deleteDoctor(scanner, doctorService);
                        break;

                    case 7:
                        createAppointment(scanner, patientService, doctorService, appointmentService);
                        break;

                    case 8:
                        viewAppointments(appointmentService);
                        break;

                    case 9:
                        generateBill(scanner, appointmentService, billingService);
                        break;

                    case 10:
                        System.out.println("Exiting MediTrack...");
                        return;

                    default:
                        System.out.println("Invalid menu option.");
                }

            }
            catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a number.");

            }
            catch (Exception e) {

                System.out.println("Unexpected error: " + e.getMessage());

            }
        }
    }


    private static void deletePatient(Scanner scanner, PatientService service) {

        try {

            System.out.print("Enter Patient ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            service.deletePatient(id);

            System.out.println("Patient deleted successfully.");

        }
        catch (Exception e) {

            System.out.println("Error deleting patient: " + e.getMessage());
        }
    }

    private static void viewPatients(PatientService service) {

        try {

            Collection<Patient> patients = service.getAllPatients();

            if (patients.isEmpty()) {
                System.out.println("No patients found.");
                return;
            }

            System.out.println("\nPatient List:");

            for (Patient p : patients) {

                System.out.println("ID: " + p.getId()
                        + " | Name: " + p.getName()
                        + " | Age: " + p.getAge()
                        + " | Email: " + p.getEmail());
            }

        }
        catch (Exception e) {

            System.out.println("Error fetching patients: " + e.getMessage());
        }
    }

    private static void deleteDoctor(Scanner scanner, DoctorService service) {

        try {

            System.out.print("Enter Doctor ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            service.deleteDoctor(id);

            System.out.println("Doctor deleted successfully.");

        }
        catch (Exception e) {

            System.out.println("Error deleting doctor: " + e.getMessage());
        }
    }

    private static void viewDoctors(DoctorService service) {

        try {

            Collection<Doctor> doctors = service.getAllDoctors();

            if (doctors.isEmpty()) {
                System.out.println("No doctors found.");
                return;
            }

            System.out.println("\nDoctor List:");

            for (Doctor d : doctors) {

                System.out.println("ID: " + d.getId()
                        + " | Name: Dr. " + d.getName()
                        + " | Age: " + d.getAge()
                        + " | Specialization: " + d.getSpecialization());
            }

        }
        catch (Exception e) {

            System.out.println("Error fetching doctors: " + e.getMessage());
        }
    }

    private static void viewAppointments(AppointmentService service) {

        try {

            Collection<Appointment> appointments = service.getAllAppointments();

            if (appointments.isEmpty()) {
                System.out.println("No appointments found.");
                return;
            }

            System.out.println("\nAppointments:");

            for (Appointment ap : appointments) {

                System.out.println("Appointment ID: " + ap.getId()
                        + " | Patient: " + ap.getPatient().getName()
                        + " | Doctor: Dr. " + ap.getDoctor().getName()
                        + " | Status: " + ap.getStatus());
            }

        }
        catch (Exception e) {

            System.out.println("Error fetching appointments: " + e.getMessage());
        }
    }

    private static void addPatient(Scanner scanner, PatientService service) {

        try {

            System.out.print("Enter patient name: ");
            String name = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());

            Validation.validateAge(age);
            Validation.validateEmail(email);

            Patient patient = service.createPatient(name, email, age);

            System.out.println("Patient created with ID: " + patient.getId());

        }
        catch (InvalidDataException e) {

            System.out.println("Invalid patient data: " + e.getMessage());

        }
        catch (NumberFormatException e) {

            System.out.println("Age must be a number.");

        }
    }

    private static void addDoctor(Scanner scanner, DoctorService service) {

        try {

            System.out.print("Enter doctor name: ");
            String name = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter specialization (CARDIOLOGIST/DERMATOLOGIST/NEUROLOGIST): ");
            String spec = scanner.nextLine();

            System.out.println("Enter Consultation fees: ");
            int fees = Integer.parseInt(scanner.nextLine());

            Validation.validateEmail(email);
            Validation.validateAge(age);

            Doctor doctor = service.createDoctor(
                    name,
                    email,
                    age,
                    Specialization.valueOf(spec.toUpperCase()),
                    fees
            );

            System.out.println("Doctor created with ID: " + doctor.getId());

        }
        catch (InvalidDataException e){
            System.out.println("Invalid data entered");
        }
        catch (IllegalArgumentException e) {

            System.out.println("Invalid specialization entered.");

        }
    }

    private static void createAppointment(Scanner scanner,
                                          PatientService patientService,
                                          DoctorService doctorService,
                                          AppointmentService appointmentService) {

        try {

            System.out.print("Enter patient ID: ");
            int patientId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter doctor ID: ");
            int doctorId = Integer.parseInt(scanner.nextLine());

            Patient patient = patientService.searchPatient(patientId);
            Doctor doctor = doctorService.searchById(doctorId);

            Appointment appointment = appointmentService.createAppointment(doctor, patient);

            System.out.println("Appointment created with ID: " + appointment.getId());

        }
        catch (NumberFormatException e) {

            System.out.println("IDs must be numbers.");

        }
    }

    private static void searchPatient(Scanner scanner, PatientService service) {

        try {

            System.out.println("Search by: 1.ID 2.Name 3.Age");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {

                case 1:

                    System.out.print("Enter ID: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.println(service.searchPatient(id));
                    break;

                case 2:

                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    System.out.println(service.searchPatient(name));
                    break;

                case 3:

                    System.out.print("Enter age: ");
                    int age = Integer.parseInt(scanner.nextLine());

                    System.out.println(service.searchPatient(age, true));
                    break;

                default:
                    System.out.println("Invalid search option.");
            }

        }
        catch (NumberFormatException e) {

            System.out.println("Invalid input format.");

        }
    }

    private static void generateBill(Scanner scanner,
                                     AppointmentService appointmentService,
                                     BillingService billingService) {

        try {

            System.out.print("Enter appointment ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Appointment appointment = appointmentService.getAppointment(id);

            BillSummary summary = billingService.generateBill(appointment);

            System.out.println(summary);

        }
        catch (AppointmentNotFoundException e) {

            System.out.println("Cannot generate bill: " + e.getMessage());

        }
        catch (NumberFormatException e) {

            System.out.println("Invalid appointment ID.");

        }
    }


}