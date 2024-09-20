package com.eaut20210719.thuchanh;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eaut20210719.thuchanh.controllers.AverageCalculatorController;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextStudentId, editTextFullName, editTextMath, editTextPhysics, editTextChemistry, editTextLiterature, editTextEnglish;
    private TextView textViewResult;
    private Button buttonCalculate;
    private AverageCalculatorController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextStudentId = findViewById(R.id.editTextStudentId);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextMath = findViewById(R.id.editTextMath);
        editTextPhysics = findViewById(R.id.editTextPhysics);
        editTextChemistry = findViewById(R.id.editTextChemistry);
        editTextLiterature = findViewById(R.id.editTextLiterature);
        editTextEnglish = findViewById(R.id.editTextEnglish);
        textViewResult = findViewById(R.id.textViewResult);
        buttonCalculate = findViewById(R.id.buttonCalculate);

        // Initialize controller
        controller = new AverageCalculatorController(this);

        // Set click listener for button
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input data
                String studentId = editTextStudentId.getText().toString().trim();
                String fullName = editTextFullName.getText().toString().trim();
                String mathStr = editTextMath.getText().toString().trim();
                String physicsStr = editTextPhysics.getText().toString().trim();
                String chemistryStr = editTextChemistry.getText().toString().trim();
                String literatureStr = editTextLiterature.getText().toString().trim();
                String englishStr = editTextEnglish.getText().toString().trim();

                // Check if all fields are filled
                if (studentId.isEmpty() || fullName.isEmpty() || mathStr.isEmpty() || physicsStr.isEmpty() || chemistryStr.isEmpty() || literatureStr.isEmpty() || englishStr.isEmpty()) {
                    showCustomToast("Vui lòng nhập đầy đủ thông tin điểm.");
                    return;
                }

                try {
                    // Parse scores
                    double math = Double.parseDouble(mathStr);
                    double physics = Double.parseDouble(physicsStr);
                    double chemistry = Double.parseDouble(chemistryStr);
                    double literature = Double.parseDouble(literatureStr);
                    double english = Double.parseDouble(englishStr);

                    // Check if scores are valid
                    if (math < 0 || math > 10 || physics < 0 || physics > 10 || chemistry < 0 || chemistry > 10 || literature < 0 || literature > 10 || english < 0 || english > 10) {
                        showCustomToast("Điểm không hợp lệ! Vui lòng nhập điểm từ 0 đến 10.");
                        return;
                    }

                    // Calculate and save average
                    double average = (math + physics + chemistry + literature + english) / 5;
                    controller.saveStudentData(studentId, fullName, math, physics, chemistry, literature, english, average);

                    // Display result
                    textViewResult.setText(String.format("Điểm trung bình: %.2f", average));

                    // Show Snackbar with detail option
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Đã tính điểm thành công!", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Xem chi tiết", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String detailMessage = String.format("Toán: %.2f\nLý: %.2f\nHóa: %.2f\nVăn: %.2f\nAnh: %.2f",
                                    math, physics, chemistry, literature, english);
                            showCustomToast(detailMessage);  // Gọi phương thức tùy chỉnh ở trên
                        }
                    });
                    snackbar.setActionTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
                    snackbar.show();

                    // Hide Snackbar after 4 seconds
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            snackbar.dismiss();
                        }
                    }, 4000); // Thời gian hiển thị 4 giây

                } catch (NumberFormatException e) {
                    // Handle invalid input
                    showCustomToast("Điểm không hợp lệ! Vui lòng nhập điểm từ 0 đến 10.");
                }
            }
        });
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_container));

        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        ImageView icon = layout.findViewById(R.id.toast_icon);
        icon.setImageResource(R.drawable.ic_info);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG); // Use long duration
        toast.setView(layout);
        toast.show();

        // Use a Handler to make the toast display longer
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 4000);
    }
}
