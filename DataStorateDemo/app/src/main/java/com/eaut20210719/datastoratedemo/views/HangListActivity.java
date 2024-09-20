package com.eaut20210719.datastoratedemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.eaut20210719.datastoratedemo.Adapter.HangAdapter;
import com.eaut20210719.datastoratedemo.R;
import com.eaut20210719.datastoratedemo.dao.HangDAO;
import com.eaut20210719.datastoratedemo.models.Hang;

import java.util.List;

public class HangListActivity extends AppCompatActivity {

    private ListView listView;
    private HangDAO hangDAO;
    private HangAdapter hangAdapter;
    private Button btnBackToManageHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_list);

        listView = findViewById(R.id.listViewHang);
        btnBackToManageHang = findViewById(R.id.btnBackToManageHang);
        hangDAO = new HangDAO(this);

        loadHangList(); // Tải dữ liệu ban đầu

        // Xử lý sự kiện nhấn nút quay lại
        btnBackToManageHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Quay lại màn hình trước đó
            }
        });

        // Xử lý sự kiện nhấn vào một mục trong danh sách
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hang selectedHang = hangAdapter.getItem(position);
                if (selectedHang != null) {
                    Intent intent = new Intent(HangListActivity.this, HangActivity.class);
                    intent.putExtra("hang_id", selectedHang.getId()); // Truyền ID của hàng hóa đã chọn
                    startActivityForResult(intent, REQUEST_CODE_ADD_HANG);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_HANG && resultCode == RESULT_OK) {
            loadHangList(); // Làm mới danh sách hàng
        }
    }

    private void loadHangList() {
        List<Hang> hangList = hangDAO.getAllHang();
        Log.d("HangListActivity", "Size of hangList: " + hangList.size());
        hangAdapter = new HangAdapter(this, hangList);
        listView.setAdapter(hangAdapter);
    }


    private static final int REQUEST_CODE_ADD_HANG = 1;
}
