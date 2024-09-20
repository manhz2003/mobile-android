package com.eaut20210719.thuchanh1609;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button registerButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        // Xử lý khi người dùng nhấn nút Đăng ký
        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (databaseHelper.isUserExists(username)) {
                Toast.makeText(RegisterActivity.this, "Tên tài khoản người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.addUser(username, password);
                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                finish();  // Quay lại LoginActivity
            }
        });
    }
}
