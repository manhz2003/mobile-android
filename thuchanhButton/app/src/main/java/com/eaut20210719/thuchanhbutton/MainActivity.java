package com.eaut20210719.thuchanhbutton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private boolean showOnlyIncomplete = false;
    private boolean sortByPriority = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);

        RecyclerView recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        EditText editTextTaskName = findViewById(R.id.editTextTaskName);

        // Button thêm công việc mới
        Button buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = editTextTaskName.getText().toString().trim();
                if (!taskName.isEmpty()) {
                    addNewTask(taskName);
                    editTextTaskName.setText(""); // Xóa nội dung trong EditText sau khi thêm công việc
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Switch hiển thị chỉ công việc chưa hoàn thành
        Switch switchShowIncomplete = findViewById(R.id.switchShowIncomplete);
        switchShowIncomplete.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showOnlyIncomplete = isChecked;
            filterTasks();
        });

        // ToggleButton chuyển đổi chế độ sắp xếp
        ToggleButton toggleButtonSortMode = findViewById(R.id.toggleButtonSortMode);
        toggleButtonSortMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sortByPriority = isChecked;
            sortTasks();
        });

        // FloatingActionButton xóa các công việc đã hoàn thành
        FloatingActionButton fabClearCompleted = findViewById(R.id.fabClearCompleted);
        fabClearCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCompletedTasks();
            }
        });
    }

    private void addNewTask(String taskName) {
        taskList.add(new Task(taskName, false, false));
        sortTasks(); // Sắp xếp lại danh sách sau khi thêm công việc mới
        taskAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Đã thêm công việc mới", Toast.LENGTH_SHORT).show();
    }

    private void filterTasks() {
        taskAdapter.filterTasks(showOnlyIncomplete);
    }

    private void sortTasks() {
        taskAdapter.sortTasks(sortByPriority);
    }

    private void clearCompletedTasks() {
        taskAdapter.clearCompletedTasks();
        Toast.makeText(this, "hiển thị danh sách công việc", Toast.LENGTH_SHORT).show();
    }
}
