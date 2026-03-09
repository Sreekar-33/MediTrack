package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.Specialization;

public class Doctor extends Person {
    private Specialization specialization;
    private double consultationFee;

    public Doctor(int id, String name, String email, int age, Specialization specialization, double consultationFee) {
        super(id, name, email, age);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    public Specialization getSpecialization() {
        return specialization;
    }
    public double getConsultationFee(){
        return consultationFee;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" | Specialization: %s | Fee: %.2f",
                specialization.name(), consultationFee);
    }
    @Override
    public String getEntityType(){
        return "Doctor";
    }


    
}
