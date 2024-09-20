package com.eaut20210719.datastoratedemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eaut20210719.datastoratedemo.R;
import com.eaut20210719.datastoratedemo.dao.HangDAO;
import com.eaut20210719.datastoratedemo.models.Hang;

public class HangActivity extends AppCompatActivity {

    private EditText etTenHang, etSoLuong, etGia;
    private Button btnAddHang, btnUpdateHang, btnDeleteHang, btnViewAllHang;
    private HangDAO hangDAO;
    private int currentHangId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang);

        etTenHang = findViewById(R.id.etTenHang);
        etSoLuong = findViewById(R.id.etSoLuong);
        etGia = findViewById(R.id.etGia);
        btnAddHang = findViewById(R.id.btnAddHang);
        btnUpdateHang = findViewById(R.id.btnUpdateHang);
        btnDeleteHang = findViewById(R.id.btnDeleteHang);
        btnViewAllHang = findViewById(R.id.btnViewAllHang);

        hangDAO = new HangDAO(this);

        Intent intent = getIntent();
        currentHangId = intent.getIntExtra("hang_id", -1);

        if (currentHangId != -1) {
            Hang hang = hangDAO.getAllHang().stream()
                    .filter(h -> h.getId() == currentHangId)
                    .findFirst()
                    .orElse(null);

            if (hang != null) {
                etTenHang.setText(hang.getTenHang());
                etSoLuong.setText(String.valueOf(hang.getSoLuong()));
                etGia.setText(String.valueOf(hang.getGia()));
            }
        }

        btnAddHang.setOnClickListener(v -> addHang());
        btnUpdateHang.setOnClickListener(v -> updateHang());
        btnDeleteHang.setOnClickListener(v -> deleteHang());
        btnViewAllHang.setOnClickListener(v -> viewAllHang());
    }


    private void clearFields() {
        etTenHang.setText("");
        etSoLuong.setText("");
        etGia.setText("");
    }

    private void addHang() {
        String tenHang = etTenHang.getText().toString().trim();
        String soLuongStr = etSoLuong.getText().toString().trim();
        String giaStr = etGia.getText().toString().trim();

        if (tenHang.isEmpty() || soLuongStr.isEmpty() || giaStr.isEmpty()) {
            // Kiểm tra đầu vào rỗng
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        int soLuong;
        double gia;

        try {
            soLuong = Integer.parseInt(soLuongStr);
            gia = Double.parseDouble(giaStr);

            if (soLuong <= 0 || gia <= 0) {
                // Kiểm tra số lượng và giá không âm hoặc bằng 0
                Toast.makeText(this, "Số lượng và giá phải lớn hơn 0.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            // Kiểm tra định dạng số
            Toast.makeText(this, "Số lượng và giá phải là số hợp lệ.", Toast.LENGTH_SHORT).show();
            return;
        }

        Hang hang = new Hang();
        hang.setTenHang(tenHang);
        hang.setSoLuong(soLuong);
        hang.setGia(gia);

        hangDAO.insertHang(hang);
        Toast.makeText(this, "Thêm hàng thành công!", Toast.LENGTH_SHORT).show();
        clearFields(); // Xóa các trường nhập sau khi thêm thành công
    }

    private void updateHang() {
        if (currentHangId != -1) {
            String tenHang = etTenHang.getText().toString().trim();
            String soLuongStr = etSoLuong.getText().toString().trim();
            String giaStr = etGia.getText().toString().trim();

            if (tenHang.isEmpty() || soLuongStr.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                return;
            }

            int soLuong;
            double gia;

            try {
                soLuong = Integer.parseInt(soLuongStr);
                gia = Double.parseDouble(giaStr);

                if (soLuong <= 0 || gia <= 0) {
                    Toast.makeText(this, "Số lượng và giá phải lớn hơn 0.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số lượng và giá phải là số hợp lệ.", Toast.LENGTH_SHORT).show();
                return;
            }

            Hang hang = new Hang();
            hang.setId(currentHangId);
            hang.setTenHang(tenHang);
            hang.setSoLuong(soLuong);
            hang.setGia(gia);

            hangDAO.updateHang(hang);
            Toast.makeText(this, "Cập nhật hàng thành công!", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteHang() {
        if (currentHangId != -1) {
            hangDAO.deleteHang(currentHangId);
            setResult(RESULT_OK);
            finish();
        }
    }

    private void viewAllHang() {
        Intent intent = new Intent(this, HangListActivity.class);
        startActivity(intent);
    }
}
