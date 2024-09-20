package com.eaut20210719.thuchanh1609;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView productList;
    Button viewCartButton, addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo database helper và các thành phần giao diện
        databaseHelper = new DatabaseHelper(this);
        productList = findViewById(R.id.productList);
        viewCartButton = findViewById(R.id.viewCartButton);
        addProductButton = findViewById(R.id.addProductButton);

        // Hiển thị danh sách sản phẩm
        loadProductList();

        // Xem giỏ hàng
        viewCartButton.setOnClickListener(v -> {
            CartDialogFragment cartDialog = new CartDialogFragment();
            cartDialog.show(getSupportFragmentManager(), "CartDialog");
        });

        // Thêm sản phẩm mới
        addProductButton.setOnClickListener(v -> showAddProductDialog());
    }

    private void loadProductList() {
        List<Product> products = databaseHelper.getAllProducts();
        ProductAdapter adapter = new ProductAdapter(this, products);
        productList.setAdapter(adapter);

        productList.setOnItemClickListener((parent, view, position, id) -> {
            Product product = (Product) adapter.getItem(position);
            // Hiển thị chi tiết sản phẩm
            showProductDetailDialog(product);

            // Hiển thị dialog đánh giá sản phẩm
            showAddReviewDialog(product); // Thêm dòng này để mở dialog đánh giá
        });
    }

    private void showAddProductDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_product, null);
        TextView productNameInput = dialogView.findViewById(R.id.productNameInput);
        TextView productDescriptionInput = dialogView.findViewById(R.id.productDescriptionInput);
        TextView productPriceInput = dialogView.findViewById(R.id.productPriceInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm sản phẩm mới")
                .setView(dialogView)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String productName = productNameInput.getText().toString();
                    String productDescription = productDescriptionInput.getText().toString();
                    String productPriceText = productPriceInput.getText().toString();

                    if (!productName.isEmpty() && !productDescription.isEmpty() && !productPriceText.isEmpty()) {
                        double productPrice = Double.parseDouble(productPriceText);

                        // Thêm sản phẩm vào cơ sở dữ liệu
                        databaseHelper.addProduct(productName, productDescription, productPrice);

                        // Cập nhật lại danh sách sản phẩm
                        loadProductList();

                        Toast.makeText(MainActivity.this, "Sản phẩm đã được thêm", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null);

        builder.show();
    }

    private void showProductDetailDialog(Product product) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_product_detail, null);
        TextView productName = dialogView.findViewById(R.id.productName);
        TextView productDescription = dialogView.findViewById(R.id.productDescription);
        TextView productPrice = dialogView.findViewById(R.id.productPrice);
        SeekBar quantitySeekBar = dialogView.findViewById(R.id.quantitySeekBar);
        TextView quantityText = dialogView.findViewById(R.id.quantityText);
        Button viewReviewsButton = dialogView.findViewById(R.id.viewReviewsButton);
        Chronometer chronometer = dialogView.findViewById(R.id.chronometer);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(String.valueOf(product.getPrice()));

        // Bắt đầu đếm thời gian
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chi tiết sản phẩm")
                .setView(dialogView)
                .setPositiveButton("Thêm vào giỏ hàng", (dialog, which) -> {
                    int quantity = quantitySeekBar.getProgress() + 1;
                    if (quantity > 0) {
                        databaseHelper.addToCart(product.getId(), quantity);
                        Toast.makeText(MainActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Đóng", null);

        builder.show();

        // Xử lý nút xem đánh giá
        viewReviewsButton.setOnClickListener(v -> showReviewsDialog(product));

        // Cập nhật số lượng từ SeekBar
        quantitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantityText.setText(String.valueOf(progress + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void showReviewsDialog(Product product) {
        List<Review> reviews = databaseHelper.getReviewsForProduct(product.getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đánh giá cho " + product.getName());

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_reviews, null);
        ListView reviewsListView = dialogView.findViewById(R.id.reviewsListView);
        ReviewAdapter reviewAdapter = new ReviewAdapter(this, reviews);
        reviewsListView.setAdapter(reviewAdapter);

        builder.setView(dialogView)
                .setPositiveButton("Đóng", null)
                .show();
    }

    private void showAddReviewDialog(Product product) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        EditText reviewContentInput = dialogView.findViewById(R.id.reviewContentInput);
        Button selectDateButton = dialogView.findViewById(R.id.selectDateButton);
        Button selectTimeButton = dialogView.findViewById(R.id.selectTimeButton);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        Spinner categorySpinner = dialogView.findViewById(R.id.categorySpinner);
        ProgressBar progressBar = dialogView.findViewById(R.id.progressBar);  // Get ProgressBar

        // Set up ArrayAdapter for category spinner
        String[] categories = {"Điện tử", "Thời trang", "Thực phẩm", "Đồ gia dụng"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Variables to store selected date and time
        final String[] selectedDate = {""};
        final String[] selectedTime = {""};

        // Capture selected date
        selectDateButton.setOnClickListener(v -> {
            showDatePickerDialog(selectDateButton, selectedDate);
        });

        // Capture selected time
        selectTimeButton.setOnClickListener(v -> {
            showTimePickerDialog(selectTimeButton, selectedTime);
        });

        // Set up AlertDialog to handle the "Lưu" button
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đánh giá sản phẩm")
                .setView(dialogView)
                .setPositiveButton("Lưu", null);  // null to handle the click event manually later

        AlertDialog alertDialog = builder.create();
        alertDialog.show();  // Show the dialog

        // Handle positive button click event manually to control the ProgressBar and flow
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            String content = reviewContentInput.getText().toString();
            float rating = ratingBar.getRating();
            String category = categorySpinner.getSelectedItem().toString();

            // Check if date and time have been selected, if not set them to "N/A"
            String reviewDate = selectedDate[0].isEmpty() ? "N/A" : selectedDate[0];
            String reviewTime = selectedTime[0].isEmpty() ? "N/A" : selectedTime[0];

            // Check if the content is filled
            if (!content.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);  // Show progress bar
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false); // Disable the button to prevent multiple clicks

                // Simulate a delay to save data (useful for async operations like DB or API calls)
                new Thread(() -> {
                    try {
                        // Simulating the saving process (this could be a database or API call)
                        Thread.sleep(2000); // Simulate delay in saving process

                        // Run on the UI thread to update the UI elements
                        runOnUiThread(() -> {
                            // Save the review including the date and time
                            databaseHelper.addReview(product.getId(), content, reviewDate, reviewTime, rating, category);

                            // Hide the progress bar after the operation is complete
                            progressBar.setVisibility(View.GONE);
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true); // Re-enable the button
                            Toast.makeText(MainActivity.this, "Đánh giá đã được lưu", Toast.LENGTH_SHORT).show();

                            // Dismiss the dialog
                            alertDialog.dismiss();
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();  // Start the simulated save process on a separate thread

            } else {
                Toast.makeText(MainActivity.this, "Vui lòng nhập nội dung đánh giá", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePickerDialog(Button selectDateButton, String[] selectedDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            selectedDate[0] = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            selectDateButton.setText(selectedDate[0].isEmpty() ? "N/A" : selectedDate[0]);  // Display selected date
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(Button selectTimeButton, String[] selectedTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            selectedTime[0] = String.format("%02d:%02d", selectedHour, selectedMinute);
            selectTimeButton.setText(selectedTime[0].isEmpty() ? "N/A" : selectedTime[0]);  // Display selected time
        }, hour, minute, true);
        timePickerDialog.show();
    }


}
