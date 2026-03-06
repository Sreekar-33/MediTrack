package com.airtribe.meditrack.services;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService {
    private DataStore<Appointment> appointmentStore = new DataStore<>();
    private IdGenerator idGenerator = IdGenerator.getInstance();

    // Create Appointment
    public Appointment createAppointment(Doctor doctor, Patient patient) {

        int id = idGenerator.generateId();

        Appointment appointment = new Appointment(id, doctor, patient);

        appointmentStore.add(id, appointment);

        return appointment;
    }

    // Get Appointment by ID
    public Optional<Appointment> getAppointment(int id) {
        return Optional.ofNullable(appointmentStore.get(id));
    }

    // Cancel Appointment
    public boolean cancelAppointment(int id) {
        Appointment appointment = appointmentStore.get(id);
        if (appointment == null)
            return false;
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return true;
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

}
