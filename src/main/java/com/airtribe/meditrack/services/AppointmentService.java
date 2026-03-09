package com.airtribe.meditrack.services;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.ConfigUtil;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService {

    private String filePath = ConfigUtil.getProperty("data.appointment.file");
    private DataStore<Appointment> appointmentStore = new DataStore<>();
    private IdGenerator idGenerator = IdGenerator.getInstance();

    // Create Appointment
    public Appointment createAppointment(Doctor doctor, Patient patient) {

        int id = idGenerator.generateId();

        Appointment appointment = new Appointment(id, doctor, patient);

        appointmentStore.add(id, appointment);

        saveAppointment(appointment);

        return appointment;
    }

    // Get Appointment by ID
    public Appointment getAppointment(int id) throws AppointmentNotFoundException{
        Appointment appointment = appointmentStore.get(id);

        if (appointment == null) {
            throw new AppointmentNotFoundException(
                    "Appointment with ID " + id + " not found."
            );
        }
        return appointment;
    }

    // Cancel Appointment
    public void cancelAppointment(int id) throws AppointmentNotFoundException{
        Appointment appointment = appointmentStore.get(id);

        if (appointment == null) {
            throw new AppointmentNotFoundException(
                    "Cannot cancel. Appointment with ID " + id + " does not exist."
            );
        }

        appointment.cancel();
    }

    // List all appointments
    public Collection<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }

    // Get appointments for a patient
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        return appointmentStore.getAll()
                .stream()
                .filter(a -> a.getPatient().getId() == patientId)
                .collect(Collectors.toList());
    }

    public void saveAppointment(Appointment appointment) {

        String row = appointment.getId() + ","
                + appointment.getPatient().getId() + ","
                + appointment.getDoctor().getId();

        CSVUtil.writeCSV(filePath, row);
    }

    public void loadAppointments(PatientService patientService,
                                 DoctorService doctorService) {

        List<String[]> rows = CSVUtil.readCSV(filePath);

        for (String[] row : rows) {

            int id = Integer.parseInt(row[0]);
            int patientId = Integer.parseInt(row[1]);
            int doctorId = Integer.parseInt(row[2]);

            Patient patient = patientService.searchPatient(patientId);
            Doctor doctor = doctorService.searchById(doctorId);

            Appointment appointment =
                    new Appointment(id, doctor, patient);

            appointmentStore.add(id, appointment);
        }
    }

}
