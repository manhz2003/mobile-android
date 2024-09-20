package com.eaut20210719.quanlydonhang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowOrdersActivity extends AppCompatActivity {

    private TextView textViewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders);

        textViewOrders = findViewById(R.id.textViewOrders);

        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        int quantity = intent.getIntExtra("quantity", 0);
        double price = intent.getDoubleExtra("price", 0);
        String productType = intent.getStringExtra("productType");
        boolean giftWrap = intent.getBooleanExtra("giftWrap", false);
        boolean warranty = intent.getBooleanExtra("warranty", false);
        String deliveryMethod = intent.getStringExtra("deliveryMethod");

        String orderSummary = String.format(
                "Product Name: %s\nQuantity: %d\nPrice: %.2f\nProduct Type: %s\nGift Wrap: %b\nWarranty: %b\nDelivery Method: %s",
                productName, quantity, price, productType, giftWrap, warranty, deliveryMethod
        );

        textViewOrders.setText(orderSummary);
    }
}
