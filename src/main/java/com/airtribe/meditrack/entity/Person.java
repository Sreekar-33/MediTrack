package com.airtribe.meditrack.entity;

public abstract class Person extends MedicalEntity{

    protected String name;
    protected String email;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    protected int age;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Person(int id, String name, String email, int age) {
        super(id);
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String getDetails() {
        return String.format("%s | Name: %s | Age: %d | Email: %s",
                getEntityType(), name, age, email);
    }
}
