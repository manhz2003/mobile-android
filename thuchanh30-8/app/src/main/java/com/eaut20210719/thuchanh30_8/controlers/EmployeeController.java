package com.eaut20210719.thuchanh30_8.controlers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eaut20210719.thuchanh30_8.databases.DatabaseHelper;
import com.eaut20210719.thuchanh30_8.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public EmployeeController(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addEmployee(Employee employee) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, employee.getName());
        values.put(DatabaseHelper.COLUMN_BIRTHDATE, employee.getBirthDate());
        values.put(DatabaseHelper.COLUMN_POSITION, employee.getPosition());
        values.put(DatabaseHelper.COLUMN_PHONE, employee.getPhoneNumber());
        values.put(DatabaseHelper.COLUMN_EMAIL, employee.getEmail());
        database.insert(DatabaseHelper.TABLE_EMPLOYEES, null, values);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_EMPLOYEES, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") Employee employee = new Employee(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BIRTHDATE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_POSITION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL))
            );
            employees.add(employee);
            cursor.moveToNext();
        }
        cursor.close();
        return employees;
    }
}
