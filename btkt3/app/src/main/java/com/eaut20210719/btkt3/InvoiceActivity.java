package com.eaut20210719.btkt3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class InvoiceActivity extends AppCompatActivity {
    private TextView tvInvoiceDetails;
    private Button btnBackToProduct, btnExit;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        tvInvoiceDetails = findViewById(R.id.tvInvoiceDetails);
        btnBackToProduct = findViewById(R.id.btnBackToProduct);
        btnExit = findViewById(R.id.btnExit);

        dbHelper = new DatabaseHelper(this);

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        List<Product> products = dbHelper.getAllProducts();

        // Tính toán tổng tiền và VAT
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getTotalPrice();
        }
        double vat = totalPrice * 0.1;
        double totalAmount = totalPrice + vat;

        // Hiển thị thông tin hóa đơn
        StringBuilder invoiceDetails = new StringBuilder();
        for (Product product : products) {
            invoiceDetails.append(product.toString()).append("\n");
        }
        invoiceDetails.append("\nThành tiền: ").append(totalPrice)
                .append("\nVAT (10%): ").append(vat)
                .append("\nTổng tiền: ").append(totalAmount);

        tvInvoiceDetails.setText(invoiceDetails.toString());

        btnBackToProduct.setOnClickListener(v -> finish());
        btnExit.setOnClickListener(v -> {
            Intent mainIntent = new Intent(InvoiceActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }
}
