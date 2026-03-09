package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.AppointmentStatus;

public class Appointment extends MedicalEntity implements Cloneable {


    private Doctor doctor;
    private Patient patient;
    private AppointmentStatus status;


    public Appointment(int id, Doctor doctor, Patient patient) {
        super(id);
        this.doctor = doctor;
        this.patient = patient;
        this.status = AppointmentStatus.PENDING;
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus appointmentStatus){
        this.status = appointmentStatus;
    }


    @Override
    public Appointment clone() {

        try {
            // deep copying patient because Ap1.patient and Ap2.patient should be different.
            // shallow copying doctor because both appointment will have same doctor object.
            Appointment cloned = (Appointment) super.clone();
            cloned.patient = patient.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getEntityType() {
        return "Appointment";
    }

    @Override
    public String getDetails() {
        return String.format("Appointment [%s] | Patient: %s | Doctor: Dr. %s | Status: %s",
                getId(), patient.getName(), doctor.getName(), status.name());
    }
}
