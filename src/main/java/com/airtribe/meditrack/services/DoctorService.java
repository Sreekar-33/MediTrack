package com.airtribe.meditrack.services;


import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.ConfigUtil;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorService implements Searchable<Doctor> {
    private String filePath = ConfigUtil.getProperty("data.doctor.file");

    private DataStore<Doctor> doctorStore = new DataStore<>();
    private IdGenerator idGenerator = IdGenerator.getInstance();

    public Doctor createDoctor(String name, String email, int age, Specialization specialization, double consultationFee) {

        int id = idGenerator.generateId();

        Doctor doctor = new Doctor(id, name, email, age, specialization,consultationFee);

        doctorStore.add(id, doctor);

        saveDoctor(doctor);

        return doctor;
    }

    @Override
    public Doctor searchById(int id) {
        return doctorStore.get(id);
    }

    @Override
    public Collection<Doctor> searchByName(String name) {

        return doctorStore.getAll()
                .stream()
                .filter(d -> d.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Doctor> searchByAge(int age) {

        return doctorStore.getAll()
                .stream()
                .filter(d-> d.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }

    public void deleteDoctor(int doctorId){
        doctorStore.remove(doctorId);
    }

    public void saveDoctor(Doctor doctor) {

        String row = doctor.getId() + ","
                + doctor.getName() + ","
                + doctor.getEmail() + ","
                + doctor.getAge() + ","
                + doctor.getSpecialization()
                + doctor.getConsultationFee();

        CSVUtil.writeCSV("data/doctors.csv", row);
    }

    public void loadDoctors() {

        List<String[]> rows = CSVUtil.readCSV("data/doctors.csv");

        for (String[] row : rows) {

            int id = Integer.parseInt(row[0]);
            String name = row[1];
            String email = row[2];
            int age = Integer.parseInt(row[3]);
            Specialization spec = Specialization.valueOf(row[4]);
            double fees = Double.parseDouble(row[5]);

            Doctor doctor = new Doctor(id, name, email, age, spec, fees);

            doctorStore.add(id, doctor);

        }
    }
}
