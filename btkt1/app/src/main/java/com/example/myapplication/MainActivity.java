package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber1;
    private EditText editTextNumber2;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        textViewResult = findViewById(R.id.textViewResult);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation("add");
            }
        });

        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation("subtract");
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation("multiply");
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation("divide");
            }
        });
    }

    private void performOperation(String operation) {
        String num1 = editTextNumber1.getText().toString();
        String num2 = editTextNumber2.getText().toString();

        if (num1.isEmpty() || num2.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập cả hai số", Toast.LENGTH_SHORT).show();
            return;
        }

        int a = Integer.parseInt(num1);
        int b = Integer.parseInt(num2);
        Calc calc = new Calc(a, b);
        int result = 0;

        try {
            switch (operation) {
                case "add":
                    result = calc.add();
                    break;
                case "subtract":
                    result = calc.subtract();
                    break;
                case "multiply":
                    result = calc.multiply();
                    break;
                case "divide":
                    result = calc.divide();
                    break;
            }
            textViewResult.setText("Kết quả: " + result);
        } catch (ArithmeticException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
