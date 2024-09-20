package com.eaut20210719.thuchanh30_8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.eaut20210719.thuchanh30_8.controlers.EmployeeController;
import com.eaut20210719.thuchanh30_8.models.Employee;

import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {

    private ListView listView;
    private EmployeeController employeeController;
    private Button btnBack;  // Khai báo nút quay về

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        listView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);  // Gắn ID nút quay về
        employeeController = new EmployeeController(this);
        employeeController.open();

        // Lấy danh sách nhân viên từ cơ sở dữ liệu
        List<Employee> employeeList = employeeController.getAllEmployees();

        // Sử dụng ArrayAdapter để hiển thị dữ liệu
        ArrayAdapter<Employee> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);
        listView.setAdapter(adapter);

        // Xử lý sự kiện khi nhấn vào nút "Quay về"
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Đóng Activity hiện tại
            }
        });
    }

    @Override
    protected void onDestroy() {
        employeeController.close();
        super.onDestroy();
    }
}
