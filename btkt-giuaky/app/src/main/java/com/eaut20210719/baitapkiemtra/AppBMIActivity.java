package com.eaut20210719.baitapkiemtra;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AppBMIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbmi);  // Ensure the layout is correctly named

        EditText nameInput = findViewById(R.id.nameInput);
        EditText ageInput = findViewById(R.id.ageInput);
        EditText heightInput = findViewById(R.id.heightInput);
        EditText weightInput = findViewById(R.id.weightInput);
        EditText genderInput = findViewById(R.id.genderInput);
        TextView resultText = findViewById(R.id.resultText);
        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(view -> {
            if (nameInput.getText().toString().isEmpty() || ageInput.getText().toString().isEmpty() ||
                    heightInput.getText().toString().isEmpty() || weightInput.getText().toString().isEmpty() ||
                    genderInput.getText().toString().isEmpty()) {
                showToast("Vui lòng nhập đầy đủ thông tin.");
            } else {
                try {
                    double height = Double.parseDouble(heightInput.getText().toString()) / 100;
                    double weight = Double.parseDouble(weightInput.getText().toString());
                    double bmi = weight / (height * height);
                    String healthStatus = getHealthStatus(bmi);
                    String result = "Tên: " + nameInput.getText().toString() +
                            "\nTuổi: " + ageInput.getText().toString() +
                            "\nGiới tính: " + genderInput.getText().toString() +
                            "\nBMI: " + String.format("%.2f", bmi) +
                            "\nTình trạng sức khỏe: " + healthStatus;
                    updateResultText(resultText, result, bmi);
                } catch (NumberFormatException e) {
                    showToast("Dữ liệu nhập không hợp lệ. Vui lòng nhập số.");
                }
            }
        });
    }

    private String getHealthStatus(double bmi) {
        if (bmi < 16) return "Gầy độ 3";
        else if (bmi < 16.99) return "Gầy độ 2";
        else if (bmi < 18.5) return "Gầy độ 1";
        else if (bmi < 25) return "Bình thường";
        else if (bmi < 30) return "Thừa cân";
        else if (bmi < 35) return "Béo phì độ I";
        else if (bmi < 40) return "Béo phì độ II";
        else return "Béo phì độ III";
    }

    private void updateResultText(TextView textView, String text, double bmi) {
        int backgroundColor;
        int textColor = Color.WHITE;

        if (bmi < 18.5) {
            backgroundColor = Color.BLUE;
        } else if (bmi < 25) {
            backgroundColor = Color.GREEN;
        } else if (bmi < 30) {
            backgroundColor = Color.YELLOW;
            textColor = Color.BLACK;
        } else {
            backgroundColor = Color.RED;
        }

        textView.setTextColor(textColor);
        textView.setBackgroundColor(backgroundColor);
        textView.setText(text);
    }


    private void showToast(String message) {
        Toast toast = new Toast(getApplicationContext());
        View toastView = LayoutInflater.from(AppBMIActivity.this).inflate(R.layout.custom_toast, null);
        TextView text = toastView.findViewById(R.id.toast_text);
        text.setText(message);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
