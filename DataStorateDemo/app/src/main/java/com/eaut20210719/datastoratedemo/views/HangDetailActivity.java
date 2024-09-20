package com.eaut20210719.datastoratedemo.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eaut20210719.datastoratedemo.R;
import com.eaut20210719.datastoratedemo.dao.HangDAO;
import com.eaut20210719.datastoratedemo.models.Hang;

public class HangDetailActivity extends AppCompatActivity {

    private EditText edtTenHang, edtSoLuong, edtGia;
    private Button btnUpdate, btnDelete;
    private HangDAO hangDAO;
    private int hangId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_list);

        edtTenHang = findViewById(R.id.etTenHang);
        edtSoLuong = findViewById(R.id.etSoLuong);
        edtGia = findViewById(R.id.etGia);
        btnUpdate = findViewById(R.id.btnUpdateHang);
        btnDelete = findViewById(R.id.btnDeleteHang);

        hangDAO = new HangDAO(this);

        // Nhận ID từ Intent
        hangId = getIntent().getIntExtra("hang_id", -1);
        if (hangId != -1) {
            Hang hang = hangDAO.getAllHang().stream().filter(h -> h.getId() == hangId).findFirst().orElse(null);
            if (hang != null) {
                edtTenHang.setText(hang.getTenHang());
                edtSoLuong.setText(String.valueOf(hang.getSoLuong()));
                edtGia.setText(String.valueOf(hang.getGia()));
            }
        }

        // Xử lý sự kiện nhấn nút cập nhật
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hang hang = new Hang(hangId, edtTenHang.getText().toString(), Integer.parseInt(edtSoLuong.getText().toString()), Double.parseDouble(edtGia.getText().toString()));
                int result = hangDAO.updateHang(hang);
                if (result > 0) {
                    Toast.makeText(HangDetailActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(HangDetailActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện nhấn nút xóa
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = hangDAO.deleteHang(hangId);
                if (result > 0) {
                    Toast.makeText(HangDetailActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(HangDetailActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
