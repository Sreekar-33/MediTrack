package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.Specialization;

public class Doctor extends Person {
    private Specialization specialization;

    public Doctor(int id, String name, String email, int age, Specialization specialization) {
        super(id, name, email, age);
        this.specialization = specialization;
    }

    public Specialization getSpecialization() {
        return specialization;
    }
}
