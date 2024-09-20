package com.eaut20210719.quanlydonhang;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {

    private TextView textViewOrderSummary, textViewTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        textViewOrderSummary = findViewById(R.id.textViewOrderSummary);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);

        List<Order> orderList = (List<Order>) getIntent().getSerializableExtra("orderList");

        // Hiển thị đơn hàng và tổng giá
        displayOrderSummary(orderList);
    }

    private void displayOrderSummary(List<Order> orderList) {
        StringBuilder summary = new StringBuilder();
        double totalPrice = 0.0;

        for (Order order : orderList) {
            summary.append(order.getProductName()).append(": ")
                    .append(order.getQuantity()).append(" x ")
                    .append(order.getPrice()).append(" = ")
                    .append(order.getTotalPrice()).append("\n");
            totalPrice += order.getTotalPrice();
        }

        textViewOrderSummary.setText(summary.toString());
        textViewTotalPrice.setText("Tổng giá: " + totalPrice);
    }
}
