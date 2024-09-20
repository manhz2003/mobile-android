package com.eaut20210719.btkt2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends Activity {

    private EditText etMsv;
    private EditText etHoTen;
    private EditText etNgaySinh;
    private EditText etLop;
    private EditText etDiemC1;
    private EditText etDiemGiuaKy1;
    private EditText etDiemCuoiKy1;
    private EditText etDiemC2;
    private EditText etDiemGiuaKy2;
    private EditText etDiemCuoiKy2;
    private EditText etDiemC3;
    private EditText etDiemGiuaKy3;
    private EditText etDiemCuoiKy3;
    private Button btnSave;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Khởi tạo các EditText và Button
        etMsv = findViewById(R.id.etMsv);
        etHoTen = findViewById(R.id.etHoTen);
        etNgaySinh = findViewById(R.id.etNgaySinh);
        etLop = findViewById(R.id.etLop);
        etDiemC1 = findViewById(R.id.etDiemC1);
        etDiemGiuaKy1 = findViewById(R.id.etDiemGiuaKy1);
        etDiemCuoiKy1 = findViewById(R.id.etDiemCuoiKy1);
        etDiemC2 = findViewById(R.id.etDiemC2);
        etDiemGiuaKy2 = findViewById(R.id.etDiemGiuaKy2);
        etDiemCuoiKy2 = findViewById(R.id.etDiemCuoiKy2);
        etDiemC3 = findViewById(R.id.etDiemC3);
        etDiemGiuaKy3 = findViewById(R.id.etDiemGiuaKy3);
        etDiemCuoiKy3 = findViewById(R.id.etDiemCuoiKy3);
        btnSave = findViewById(R.id.btnSave);
        btnExit = findViewById(R.id.btnExit);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các EditText
                String msv = etMsv.getText().toString();
                String hoTen = etHoTen.getText().toString();
                String ngaySinh =  etNgaySinh.getText().toString();
                String lop = etLop.getText().toString();
                String diemC1Str = etDiemC1.getText().toString();
                String diemGiuaKy1Str = etDiemGiuaKy1.getText().toString();
                String diemCuoiKy1Str = etDiemCuoiKy1.getText().toString();
                String diemC2Str = etDiemC2.getText().toString();
                String diemGiuaKy2Str = etDiemGiuaKy2.getText().toString();
                String diemCuoiKy2Str = etDiemCuoiKy2.getText().toString();
                String diemC3Str = etDiemC3.getText().toString();
                String diemGiuaKy3Str = etDiemGiuaKy3.getText().toString();
                String diemCuoiKy3Str = etDiemCuoiKy3.getText().toString();

                if (msv.isEmpty() || hoTen.isEmpty() || ngaySinh.isEmpty() || lop.isEmpty() ||
                        diemC1Str.isEmpty() || diemGiuaKy1Str.isEmpty() || diemCuoiKy1Str.isEmpty() ||
                        diemC2Str.isEmpty() || diemGiuaKy2Str.isEmpty() || diemCuoiKy2Str.isEmpty() ||
                        diemC3Str.isEmpty() || diemGiuaKy3Str.isEmpty() || diemCuoiKy3Str.isEmpty()) {
                    Toast.makeText(InputActivity.this, "Tất cả các trường không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ngaySinh.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    Toast.makeText(InputActivity.this, "Ngày sinh không hợp lệ (dd/MM/yyyy)", Toast.LENGTH_SHORT).show();
                    return;
                }

                float diemC1, diemGiuaKy1, diemCuoiKy1, diemC2, diemGiuaKy2, diemCuoiKy2;
                float diemC3, diemGiuaKy3, diemCuoiKy3;
                try {
                    diemC1 = Float.parseFloat(diemC1Str);
                    diemGiuaKy1 = Float.parseFloat(diemGiuaKy1Str);
                    diemCuoiKy1 = Float.parseFloat(diemCuoiKy1Str);
                    diemC2 = Float.parseFloat(diemC2Str);
                    diemGiuaKy2 = Float.parseFloat(diemGiuaKy2Str);
                    diemCuoiKy2 = Float.parseFloat(diemCuoiKy2Str);
                    diemC3 = Float.parseFloat(diemC3Str);
                    diemGiuaKy3 = Float.parseFloat(diemGiuaKy3Str);
                    diemCuoiKy3 = Float.parseFloat(diemCuoiKy3Str);
                } catch (NumberFormatException e) {
                    Toast.makeText(InputActivity.this, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (diemC1 < 0 || diemC1 > 10 || diemGiuaKy1 < 0 || diemGiuaKy1 > 10 || diemCuoiKy1 < 0 || diemCuoiKy1 > 10 ||
                        diemC2 < 0 || diemC2 > 10 || diemGiuaKy2 < 0 || diemGiuaKy2 > 10 || diemCuoiKy2 < 0 || diemCuoiKy2 > 10 ||
                        diemC3 < 0 || diemC3 > 10 || diemGiuaKy3 < 0 || diemGiuaKy3 > 10 || diemCuoiKy3 < 0 || diemCuoiKy3 > 10) {
                    Toast.makeText(InputActivity.this, "Điểm phải từ 0 đến 10", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tính điểm trung bình cho các môn học
                float diemTrungBinh1 = (diemC1 * 0.1f) + (diemGiuaKy1 * 0.2f) + (diemCuoiKy1 * 0.7f);
                float diemTrungBinh2 = (diemC2 * 0.1f) + (diemGiuaKy2 * 0.2f) + (diemCuoiKy2 * 0.7f);
                float diemTrungBinh3 = (diemC3 * 0.1f) + (diemGiuaKy3 * 0.2f) + (diemCuoiKy3 * 0.7f);

                // Tạo đối tượng SinhVien
                SinhVien sinhVien = new SinhVien(msv, hoTen, ngaySinh, lop, diemTrungBinh1, diemTrungBinh2, diemTrungBinh3);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("sinhVien", sinhVien);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
