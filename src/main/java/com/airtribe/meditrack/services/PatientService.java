package com.airtribe.meditrack.services;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.ConfigUtil;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientService implements Searchable<Patient> {
    private String filePath = ConfigUtil.getProperty("data.patient.file");

    private DataStore<Patient> patientStore = new DataStore<>();
    private IdGenerator idGenerator = IdGenerator.getInstance();

    // Create Patient
    public Patient createPatient(String name, String email, int age) {

        int id = idGenerator.generateId();
        Patient patient = new Patient(id, name, email, age);
        patientStore.add(id, patient);
        savePatient(patient);
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

    // search by ID
    public Patient searchPatient(int id) {

        return patientStore.get(id);

    }

    // search by name
    public Collection<Patient> searchPatient(String name) {

        return patientStore.getAll()
                .stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // search by age
    public Collection<Patient> searchPatient(int age, boolean byAge) {

        return patientStore.getAll()
                .stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toList());
    }

    public void savePatient(Patient patient) {

        String row = patient.getId() + ","
                + patient.getName() + ","
                + patient.getEmail() + ","
                + patient.getAge();

        CSVUtil.writeCSV(filePath, row);
    }

    public void loadPatients() {

        List<String[]> rows = CSVUtil.readCSV(filePath);

        for (String[] row : rows) {

            int id = Integer.parseInt(row[0]);
            String name = row[1];
            String email = row[2];
            int age = Integer.parseInt(row[3]);

            Patient patient = new Patient(id, name, email, age);

            patientStore.add(id, patient);
        }
    }

}