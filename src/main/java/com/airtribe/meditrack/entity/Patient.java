package com.airtribe.meditrack.entity;

public class Patient extends Person implements Cloneable {

    public Patient(int id, String name, String email, int age) {
        super(id, name, email, age);
    }

    /*
    * just doing it for assignment purpose, i don't really understand why we need
    * patient to be cloned, two different patients will have two different attributes
    * and we can use single patient objects for many appointments.
     */


    @Override
    public Patient clone() {

        try {
            return (Patient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getEntityType() {
        return "Patient";
    }
}