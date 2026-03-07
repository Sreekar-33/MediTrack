package com.airtribe.meditrack.services;


import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class DoctorService implements Searchable<Doctor> {

    private DataStore<Doctor> doctorStore = new DataStore<>();
    private IdGenerator idGenerator = IdGenerator.getInstance();

    public Doctor createDoctor(String name, String email, int age, Specialization specialization, double consultationFee) {

        int id = idGenerator.generateId();

        Doctor doctor = new Doctor(id, name, email, age, specialization,consultationFee);

        doctorStore.add(id, doctor);

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
}
