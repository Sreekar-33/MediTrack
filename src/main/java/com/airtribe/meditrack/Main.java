package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.services.*;
import com.airtribe.meditrack.util.*;
import com.airtribe.meditrack.billingStrategy.*;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService();

        BillingService billingService =
                new BillingService(new StandardBilling());

        if (args.length > 0 && args[0].equals("--loadData")) {

            System.out.println("Loading persisted data...");

            patientService.loadPatients();
            doctorService.loadDoctors();
            appointmentService.loadAppointments(patientService, doctorService);

            System.out.println("Data loaded successfully.");
        }

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
                System.out.println("10. AI Doctor Recommendation");
                System.out.println("11. Exiting Meditrack");

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
                        aiRecommendation(scanner, doctorService);
                        break;

                    case 11:
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

    private static void aiRecommendation(Scanner scanner, DoctorService doctorService) {
        System.out.println("\n--- AI Doctor Recommendation ---");
        String symptoms = readLine(scanner, "Describe your symptoms: ");
        List<Doctor> recommended = AIHelper.recommendDoctors(symptoms, doctorService.getAllDoctors());

        if (recommended.isEmpty()) {
            System.out.println("No matching doctors found. Please add doctors first.");
            return;
        }

        System.out.println("Recommended doctors:");
        for (int i = 0; i < recommended.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + recommended.get(i).getDetails());
        }

        System.out.println("\nWould you like to check slots for any of these doctors?");
        String bookChoice = readLine(scanner, "Enter the doctor number (or press Enter to skip): ");
        if (bookChoice.isEmpty()) return;

        try {
            int doctorNum = Integer.parseInt(bookChoice);
            if (doctorNum < 1 || doctorNum > recommended.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            Doctor selectedDoctor = recommended.get(doctorNum - 1);
            String date = readLine(scanner, "Enter date for slots (yyyy-MM-dd): ");

            List<String> slots = AIHelper.suggestSlots(date);
            System.out.println("Suggested slots for Dr. " + selectedDoctor.getName() + " on " + date + ":");
            for (int i = 0; i < slots.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + slots.get(i));
            }

            System.out.println("\nTo book an appointment, go back to Main Menu -> 3. Manage Appointments -> 1. Create Appointment.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    private static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }


}