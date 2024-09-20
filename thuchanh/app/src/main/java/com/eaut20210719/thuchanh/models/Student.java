package com.eaut20210719.thuchanh.models;

public class Student {
    private String studentId;
    private String fullName;
    private double math;
    private double physics;
    private double chemistry;
    private double literature;
    private double english;
    private double average;

    public Student(String studentId, String fullName, double math, double physics, double chemistry, double literature, double english, double average) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.math = math;
        this.physics = physics;
        this.chemistry = chemistry;
        this.literature = literature;
        this.english = english;
        this.average = average;
    }

    // Getters and Setters for all fields
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getMath() {
        return math;
    }

    public void setMath(double math) {
        this.math = math;
    }

    public double getPhysics() {
        return physics;
    }

    public void setPhysics(double physics) {
        this.physics = physics;
    }

    public double getChemistry() {
        return chemistry;
    }

    public void setChemistry(double chemistry) {
        this.chemistry = chemistry;
    }

    public double getLiterature() {
        return literature;
    }

    public void setLiterature(double literature) {
        this.literature = literature;
    }

    public double getEnglish() {
        return english;
    }

    public void setEnglish(double english) {
        this.english = english;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
