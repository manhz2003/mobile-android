package com.eaut20210719.btkt3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductActivity extends AppCompatActivity {
    private EditText etProductName, etUnitPrice, etQuantity;
    private Button btnCalculate, btnPrintInvoice, btnLogout;
    private TextView tvResult;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        etProductName = findViewById(R.id.etProductName);
        etUnitPrice = findViewById(R.id.etUnitPrice);
        etQuantity = findViewById(R.id.etQuantity);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnPrintInvoice = findViewById(R.id.btnPrintInvoice);
        btnLogout = findViewById(R.id.btnLogout);
        tvResult = findViewById(R.id.tvResult);

        dbHelper = new DatabaseHelper(this);

        btnCalculate.setOnClickListener(v -> calculateInvoice());
        btnPrintInvoice.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, InvoiceActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void calculateInvoice() {
        String productName = etProductName.getText().toString().trim();
        String unitPriceStr = etUnitPrice.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();

        if (productName.isEmpty() || unitPriceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double unitPrice = Double.parseDouble(unitPriceStr);
            int quantity = Integer.parseInt(quantityStr);

            if (unitPrice <= 0 || quantity <= 0) {
                throw new NumberFormatException();
            }

            Product product = new Product(productName, unitPrice, quantity);
            String result = product.toString();
            tvResult.setText(result);
            dbHelper.addProduct(product);

            // Clear input fields
            etProductName.setText("");
            etUnitPrice.setText("");
            etQuantity.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Đơn giá và số lượng phải là số dương", Toast.LENGTH_SHORT).show();
        }
    }
}
