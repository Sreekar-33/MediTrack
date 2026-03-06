package com.airtribe.meditrack.services;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;


import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientService implements Searchable<Patient> {

    private DataStore<Patient> patientStore = new DataStore<>();
    private IdGenerator idGenerator = IdGenerator.getInstance();

    // Create Patient
    public Patient createPatient(String name, String email, int age) {

        int id = idGenerator.generateId();
        Patient patient = new Patient(id, name, email, age);
        patientStore.add(id, patient);
        return patient;
    }

    @Override
    public Patient searchById(int id) {
        return patientStore.get(id);
    }

    @Override
    public Collection<Patient> searchByName(String name) {

        return patientStore.getAll()
                .stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Patient> searchByAge(int age) {

        return patientStore.getAll()
                .stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toList());
    }

    // Update Patient
    public boolean updatePatientEmail(int id, String newEmail) {
        Patient patient = patientStore.get(id);
        if (patient == null)
            return false;
        patient.setEmail(newEmail);
        return true;
    }

    // Delete Patient
    public boolean deletePatient(int id) {
        Patient patient = patientStore.get(id);
        if (patient == null)
            return false;
        patientStore.remove(id);
        return true;
    }

    // List All Patients
    public Collection<Patient> getAllPatients() {
        return patientStore.getAll();
    }

}