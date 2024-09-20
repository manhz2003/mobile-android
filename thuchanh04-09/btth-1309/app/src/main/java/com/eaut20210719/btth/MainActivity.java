package com.eaut20210719.btth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageSwitcher imageSwitcher;
    private WebView webView;
    private int[] images = { R.drawable.image1, R.drawable.image2, R.drawable.image3 };

    private int imageIndex = 0;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập ImageSwitcher
        imageSwitcher = findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(() -> {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        });

        // Hiển thị hình ảnh đầu tiên trong ImageSwitcher
        imageSwitcher.setImageResource(images[0]);

        // Thiết lập các nút chuyển đổi hình ảnh
        Button prevButton = findViewById(R.id.previousButton);
        Button nextButton = findViewById(R.id.nextButton);

        prevButton.setOnClickListener(v -> {
            if (imageIndex > 0) {
                imageIndex--;
                imageSwitcher.setImageResource(images[imageIndex]);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (imageIndex < images.length - 1) {
                imageIndex++;
                imageSwitcher.setImageResource(images[imageIndex]);
            }
        });

        // Thiết lập WebView
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://images.pexels.com/photos/235990/pexels-photo-235990.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2");

        // Thiết lập ScrollView cho phần mô tả sản phẩm
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView descriptionTextView = findViewById(R.id.long_description);
        descriptionTextView.setText("Đây là ảnh sỏi đá");

        // HorizontalScrollView cho hình ảnh liên quan đã được thiết lập trong XML
    }
}
