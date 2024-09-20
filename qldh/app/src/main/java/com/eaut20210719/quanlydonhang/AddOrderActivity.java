package com.eaut20210719.quanlydonhang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class AddOrderActivity extends AppCompatActivity {

    private EditText editTextProductName, editTextQuantity, editTextPrice;
    private RadioGroup radioGroupProductType;
    private CheckBox checkBoxGiftWrap, checkBoxWarranty;
    private ChipGroup chipGroupDelivery;
    private Button buttonCalculatePrice, buttonBackToHome;

    private List<Order> orderList = new ArrayList<>(); // danh sách đơn hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextPrice = findViewById(R.id.editTextPrice);
        radioGroupProductType = findViewById(R.id.radioGroupProductType);
        checkBoxGiftWrap = findViewById(R.id.checkBoxGiftWrap);
        checkBoxWarranty = findViewById(R.id.checkBoxWarranty);
        chipGroupDelivery = findViewById(R.id.chipGroupDelivery);
        buttonCalculatePrice = findViewById(R.id.buttonCalculatePrice);
        buttonBackToHome = findViewById(R.id.buttonBackToHome);

        buttonCalculatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePrice();
            }
        });

        buttonBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang chủ
                Intent intent = new Intent(AddOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void calculatePrice() {
        String productName = editTextProductName.getText().toString();
        int quantity = Integer.parseInt(editTextQuantity.getText().toString());
        double price = Double.parseDouble(editTextPrice.getText().toString());

        // Xử lý loại sản phẩm
        int selectedTypeId = radioGroupProductType.getCheckedRadioButtonId();
        RadioButton selectedType = findViewById(selectedTypeId);
        String productType = selectedType.getText().toString();

        // Xử lý gói quà và bảo hành
        boolean giftWrap = checkBoxGiftWrap.isChecked();
        boolean warranty = checkBoxWarranty.isChecked();

        // Xử lý giao hàng
        Chip selectedChip = findViewById(chipGroupDelivery.getCheckedChipId());
        String deliveryType = selectedChip != null ? selectedChip.getText().toString() : "";

        // Tính giá
        double totalPrice = quantity * price;

        // Lưu đơn hàng vào danh sách
        Order order = new Order(productName, quantity, price, productType, giftWrap, warranty, deliveryType, totalPrice);
        orderList.add(order);

        // Hiển thị thông báo
        // (Thay thế bằng cách hiển thị giá hoặc lưu dữ liệu theo yêu cầu)
    }
}
