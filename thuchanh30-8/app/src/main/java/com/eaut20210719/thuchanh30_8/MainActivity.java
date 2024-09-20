package com.eaut20210719.thuchanh30_8;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.eaut20210719.thuchanh30_8.controlers.EmployeeController;
import com.eaut20210719.thuchanh30_8.models.Employee;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtBirthDate, edtPosition, edtPhoneNumber, edtEmail;
    private EmployeeController employeeController;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        edtPosition = findViewById(R.id.edtPosition);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtEmail = findViewById(R.id.edtEmail);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnShowList = findViewById(R.id.btnShowList);

        employeeController = new EmployeeController(this);
        employeeController.open();

        // Thêm TextWatcher cho các trường nhập liệu
        edtName.addTextChangedListener(new ValidationTextWatcher(edtName));
        edtBirthDate.addTextChangedListener(new ValidationTextWatcher(edtBirthDate));
        edtPosition.addTextChangedListener(new ValidationTextWatcher(edtPosition));
        edtPhoneNumber.addTextChangedListener(new ValidationTextWatcher(edtPhoneNumber));
        edtEmail.addTextChangedListener(new ValidationTextWatcher(edtEmail));

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String birthDate = edtBirthDate.getText().toString();
            String position = edtPosition.getText().toString();
            String phoneNumber = edtPhoneNumber.getText().toString();
            String email = edtEmail.getText().toString();

            if (validateInput(name, birthDate, position, phoneNumber, email)) {
                Employee employee = new Employee(name, birthDate, position, phoneNumber, email);
                employeeController.addEmployee(employee);
                Toast.makeText(MainActivity.this, "Thêm nhân viên thành công !", Toast.LENGTH_SHORT).show();
                edtName.setText("");
                edtBirthDate.setText("");
                edtPosition.setText("");
                edtPhoneNumber.setText("");
                edtEmail.setText("");
            }
        });

        btnShowList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EmployeeListActivity.class);
            startActivity(intent);
        });

    }

    private boolean validateInput(String name, String birthDate, String position, String phoneNumber, String email) {
        boolean valid = true;

        if (name.length() < 2) {
            edtName.setError("Tên cần từ 2 ký tự trở lên");
            valid = false;
        }
        if (!birthDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            edtBirthDate.setError("Ngày sinh không đúng định dạng");
            valid = false;
        }
        if (position.isEmpty()) {
            edtPosition.setError("Chức vụ không được bỏ trống");
            valid = false;
        }
        if (!phoneNumber.matches("\\d{10}")) {
            edtPhoneNumber.setError("Số điện thoại gồm 10 số");
            valid = false;
        }
        if (!email.contains("@")) {
            edtEmail.setError("Email phải chứa @");
            valid = false;
        }

        return valid;
    }

    @Override
    protected void onDestroy() {
        employeeController.close();
        super.onDestroy();
    }

    private class ValidationTextWatcher implements TextWatcher {

        private final EditText editText;

        private ValidationTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();

            if (editText == edtName) {
                if (text.length() < 2) {
                    editText.setError("Tên cần từ 2 ký tự trở lên");
                }
            } else if (editText == edtBirthDate) {
                if (!text.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    editText.setError("Ngày sinh không đúng định dạng");
                }
            } else if (editText == edtPosition) {
                if (text.isEmpty()) {
                    editText.setError("Chức vụ không được bỏ trống");
                }
            } else if (editText == edtPhoneNumber) {
                if (!text.matches("\\d{10}")) {
                    editText.setError("Số điện thoại gồm 10 số");
                }
            } else if (editText == edtEmail) {
                if (!text.contains("@")) {
                    editText.setError("Email phải chứa @");
                }
            }
        }
    }
}
