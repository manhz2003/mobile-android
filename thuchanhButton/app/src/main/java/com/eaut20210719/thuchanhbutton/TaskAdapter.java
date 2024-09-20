package com.eaut20210719.thuchanhbutton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> originalList;
    private List<Task> filteredList;

    public TaskAdapter(List<Task> taskList) {
        this.originalList = taskList;
        this.filteredList = new ArrayList<>(taskList);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = filteredList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filterTasks(boolean showOnlyIncomplete) {
        filteredList = new ArrayList<>();
        for (Task task : originalList) {
            if (!showOnlyIncomplete || !task.isCompleted()) {
                filteredList.add(task);
            }
        }
        notifyDataSetChanged();
    }

    public void sortTasks(boolean sortByPriority) {
        if (sortByPriority) {
            filteredList.sort((task1, task2) -> Boolean.compare(task2.isPriority(), task1.isPriority()));
        } else {
            filteredList.sort((task1, task2) -> {
                int index1 = originalList.indexOf(task1);
                int index2 = originalList.indexOf(task2);
                return Integer.compare(index1, index2);
            });
        }
        notifyDataSetChanged();
    }


    public void clearCompletedTasks() {
        originalList.removeIf(Task::isCompleted);
        filterTasks(false);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTask;
        private final CheckBox checkBoxCompleted;
        private final ImageButton imageButtonPriority;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
            imageButtonPriority = itemView.findViewById(R.id.imageButtonPriority);
        }

        public void bind(Task task) {
            textViewTask.setText(task.getName());
            checkBoxCompleted.setChecked(task.isCompleted());
            imageButtonPriority.setImageResource(task.isPriority() ? android.R.drawable.star_on : android.R.drawable.star_off);

            checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> task.setCompleted(isChecked));
            imageButtonPriority.setOnClickListener(v -> {
                task.setPriority(!task.isPriority());
                imageButtonPriority.setImageResource(task.isPriority() ? android.R.drawable.star_on : android.R.drawable.star_off);
            });
        }
    }
}
