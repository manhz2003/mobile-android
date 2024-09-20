package com.eaut20210719.btth;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_FOLDER = 1;
    private AutoCompleteTextView autoCompleteSearch;
    private Button btnSelectFolder, btnShowImages;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ArrayAdapter<String> adapter; // Khai báo ArrayAdapter toàn cục
    private ArrayList<Uri> imageList = new ArrayList<>();
    private ArrayList<String> imageNames = new ArrayList<>(); // Danh sách tên ảnh để gợi ý tìm kiếm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần giao diện
        autoCompleteSearch = findViewById(R.id.autoCompleteSearch);
        btnSelectFolder = findViewById(R.id.btnSelectFolder);
        btnShowImages = findViewById(R.id.btnShowImages);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageAdapter = new ImageAdapter(this, imageList, imageNames); // Truyền cả tên ảnh
        recyclerView.setAdapter(imageAdapter);

        // Xử lý sự kiện nút chọn thư mục
        btnSelectFolder.setOnClickListener(v -> openFolderSelector());

        // Nút hiển thị tất cả hình ảnh
        btnShowImages.setOnClickListener(v -> {
            if (!imageList.isEmpty()) {
                imageAdapter.notifyDataSetChanged(); // Cập nhật lại giao diện RecyclerView
            } else {
                Toast.makeText(MainActivity.this, "Chưa có hình ảnh nào để hiển thị", Toast.LENGTH_SHORT).show();
            }
        });

        // Khởi tạo gợi ý ban đầu cho AutoCompleteTextView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, imageNames);
        autoCompleteSearch.setAdapter(adapter);

        // Lắng nghe sự thay đổi trong AutoCompleteTextView để hiển thị hình ảnh theo từ khóa nhập vào
        autoCompleteSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchImages(s.toString()); // Tìm kiếm và hiển thị các hình ảnh khớp với ký tự nhập vào
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Hàm mở cửa sổ chọn thư mục
    private void openFolderSelector() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, REQUEST_CODE_PICK_FOLDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();  // Nhận URI từ Intent
            loadImagesFromFolder(uri);  // Truyền URI vào hàm xử lý
        }
    }

    // Hàm tải danh sách hình ảnh từ thư mục
    private void loadImagesFromFolder(Uri treeUri) {
        ContentResolver contentResolver = getContentResolver();
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, DocumentsContract.getTreeDocumentId(treeUri));

        try (Cursor cursor = contentResolver.query(childrenUri,
                new String[]{DocumentsContract.Document.COLUMN_DOCUMENT_ID, DocumentsContract.Document.COLUMN_DISPLAY_NAME, DocumentsContract.Document.COLUMN_MIME_TYPE},
                null, null, null)) {

            if (cursor != null) {
                imageList.clear();
                imageNames.clear(); // Reset danh sách tên ảnh

                while (cursor.moveToNext()) {
                    String documentId = cursor.getString(0);
                    String displayName = cursor.getString(1);
                    String mimeType = cursor.getString(2);

                    // Chỉ thêm tệp hình ảnh (jpg, png) vào danh sách
                    if (mimeType != null && (mimeType.equals("image/jpeg") || mimeType.equals("image/png"))) {
                        Uri fileUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId);
                        // Thay vì File, lưu trữ Uri
                        imageList.add(fileUri);
                        imageNames.add(displayName); // Thêm tên ảnh vào danh sách tên
                    }
                }

                if (imageList.isEmpty()) {
                    Toast.makeText(this, "Không có hình ảnh trong thư mục này", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật gợi ý từ khóa cho AutoCompleteTextView
                    adapter.notifyDataSetChanged(); // Cập nhật gợi ý
                    imageAdapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi đọc thư mục", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm tìm và hiển thị hình ảnh theo từ khóa nhập vào
    private void searchImages(String keyword) {
        ArrayList<Uri> filteredImages = new ArrayList<>();

        for (int i = 0; i < imageList.size(); i++) {
            Uri imageUri = imageList.get(i);
            String displayName = imageNames.get(i);

            // Kiểm tra nếu tên tệp chứa ký tự nhập vào
            if (displayName.toLowerCase().contains(keyword.toLowerCase())) {
                filteredImages.add(imageUri);
            }
        }

        if (!filteredImages.isEmpty()) {
            imageAdapter.updateImageList(filteredImages); // Hiển thị các ảnh chứa từ khóa
        } else {
            Toast.makeText(this, "Không tìm thấy hình ảnh", Toast.LENGTH_SHORT).show();
        }
    }
}
