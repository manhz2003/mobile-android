package com.eaut20210719.btkt2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private SinhVienAdapter sinhVienAdapter;
    private List<SinhVien> sinhVienList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sinhVienList = new ArrayList<>();
        sinhVienAdapter = new SinhVienAdapter(sinhVienList);
        recyclerView.setAdapter(sinhVienAdapter);

        Button btnAdd = findViewById(R.id.btnNhapThongTin);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            SinhVien sinhVien = data.getParcelableExtra("sinhVien");
            if (sinhVien != null) {
                sinhVienList.add(sinhVien);
                sinhVienAdapter.notifyDataSetChanged();
            }
        }
    }


}
