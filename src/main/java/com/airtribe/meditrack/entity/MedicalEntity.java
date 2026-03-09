package com.airtribe.meditrack.entity;

import java.io.Serializable;

public abstract class MedicalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Integer id;

    public MedicalEntity() {}

    public MedicalEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getEntityType();

    public abstract String getDetails();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MedicalEntity that = (MedicalEntity) obj;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getEntityType() + " [" + id + "]";
    }
}