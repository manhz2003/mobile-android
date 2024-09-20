package com.eaut20210719.thuchanh30_8.models;

public class Employee {
    private String name;
    private String birthDate;
    private String position;
    private String phoneNumber;
    private String email;

    // Constructor
    public Employee(String name, String birthDate, String position, String phoneNumber, String email) {
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " - " + position + "-" + birthDate + " - " + phoneNumber + " - " + email;
    }
}
