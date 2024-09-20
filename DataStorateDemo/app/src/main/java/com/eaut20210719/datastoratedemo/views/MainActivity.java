package com.eaut20210719.datastoratedemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.eaut20210719.datastoratedemo.R;

public class MainActivity extends AppCompatActivity {

    private Button btnManageHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnManageHang = findViewById(R.id.btnManageHang);

        btnManageHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HangActivity.class);
                startActivity(intent);
            }
        });
    }
}
