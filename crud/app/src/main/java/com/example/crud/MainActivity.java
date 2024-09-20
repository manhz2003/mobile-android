package com.example.crud;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText etName, etQuantity;
    Button btnAdd, btnUpdate, btnDelete;
    ListView listView;
    ArrayList<String> itemList;
    ArrayAdapter<String> adapter;
    int selectedItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        listView = findViewById(R.id.listView);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        loadData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                int quantity = Integer.parseInt(etQuantity.getText().toString());
                dbHelper.addItem(name, quantity);
                loadData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemId != -1) {
                    String name = etName.getText().toString();
                    int quantity = Integer.parseInt(etQuantity.getText().toString());
                    dbHelper.updateItem(selectedItemId, name, quantity);
                    loadData();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemId != -1) {
                    dbHelper.deleteItem(selectedItemId);
                    loadData();
                }
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = itemList.get(position);
            String[] parts = selectedItem.split(" - ");
            selectedItemId = Integer.parseInt(parts[0]);
            etName.setText(parts[1]);
            etQuantity.setText(parts[2]);
        });
    }

    private void loadData() {
        itemList.clear();
        Cursor cursor = dbHelper.getAllItems();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY));
            itemList.add(id + " - " + name + " - " + quantity);
        }
        adapter.notifyDataSetChanged();
    }
}
