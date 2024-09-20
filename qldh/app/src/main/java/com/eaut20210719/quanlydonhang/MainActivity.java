package com.eaut20210719.quanlydonhang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;

public class MainActivity extends AppCompatActivity {

    private EditText editTextProductName, editTextQuantity, editTextPrice;
    private RadioGroup radioGroupProductType;
    private CheckBox checkBoxGiftWrap, checkBoxWarranty;
    private ChipGroup chipGroupDelivery;
    private Button buttonCalculatePrice, buttonBackToHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các View
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextPrice = findViewById(R.id.editTextPrice);
        radioGroupProductType = findViewById(R.id.radioGroupProductType);
        checkBoxGiftWrap = findViewById(R.id.checkBoxGiftWrap);
        checkBoxWarranty = findViewById(R.id.checkBoxWarranty);
        chipGroupDelivery = findViewById(R.id.chipGroupDelivery);
        buttonCalculatePrice = findViewById(R.id.buttonCalculatePrice);
        buttonBackToHome = findViewById(R.id.buttonBackToHome);

        // Đặt sự kiện click cho buttonCalculatePrice
        if (buttonCalculatePrice != null) {
            buttonCalculatePrice.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, ShowOrdersActivity.class);
                intent.putExtra("productName", editTextProductName.getText().toString());
                intent.putExtra("quantity", Integer.parseInt(editTextQuantity.getText().toString()));
                intent.putExtra("price", Double.parseDouble(editTextPrice.getText().toString()));

                int selectedId = radioGroupProductType.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String productType = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";

                intent.putExtra("productType", productType);
                intent.putExtra("giftWrap", checkBoxGiftWrap.isChecked());
                intent.putExtra("warranty", checkBoxWarranty.isChecked());

                int selectedChipId = chipGroupDelivery.getCheckedChipId();
                String deliveryMethod = selectedChipId == R.id.chipFastDelivery ? "Fast" : selectedChipId == R.id.chipStandardDelivery ? "Standard" : "";

                intent.putExtra("deliveryMethod", deliveryMethod);

                startActivity(intent);
            });
        }

        // Đặt sự kiện click cho buttonBackToHome
        if (buttonBackToHome != null) {
            buttonBackToHome.setOnClickListener(view -> finish());
        }
    }
}
