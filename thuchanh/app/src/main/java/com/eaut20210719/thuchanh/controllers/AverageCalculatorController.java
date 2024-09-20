package com.eaut20210719.thuchanh.controllers;

import android.content.Context;
import android.widget.Toast;

import com.eaut20210719.thuchanh.models.Student;
import com.eaut20210719.thuchanh.databases.DatabaseHelper;

public class AverageCalculatorController {

    private Context context;
    private DatabaseHelper databaseHelper;

    public AverageCalculatorController(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void saveStudentData(String studentId, String fullName, double math, double physics, double chemistry, double literature, double english, double average) {
        // Create a Student object
        Student student = new Student(studentId, fullName, math, physics, chemistry, literature, english, average);

        // Save the student data to the database
        long result = databaseHelper.addStudent(student);

        if (result == -1) {
            Toast.makeText(context, "Lưu thông tin thất bại.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Lưu thông tin thành công.", Toast.LENGTH_SHORT).show();
        }
    }
}
