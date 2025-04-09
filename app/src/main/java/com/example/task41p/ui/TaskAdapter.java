package com.example.task41p.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.task41p.R;
import com.example.task41p.data.Task;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {
    private final OnTaskClickListener listener;
    private final OnTaskDeleteListener deleteListener;
    
    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }
    
    public interface OnTaskDeleteListener {
        void onTaskDelete(Task task);
    }
    
    public TaskAdapter(OnTaskClickListener listener, OnTaskDeleteListener deleteListener) {
        super(new DiffUtil.ItemCallback<Task>() {
            @Override
            public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                return oldItem.getId() == newItem.getId();
            }
            
            @Override
            public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                return oldItem.getTitle().equals(newItem.getTitle()) &&
                       oldItem.getDescription().equals(newItem.getDescription()) &&
                       oldItem.getDueDate().equals(newItem.getDueDate());
            }
        });
        this.listener = listener;
        this.deleteListener = deleteListener;
    }
    
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = getItem(position);
        holder.bind(task);
    }
    
    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descriptionView;
        private final TextView dueDateView;
        private final MaterialButton deleteButton;
        private final SimpleDateFormat dateFormat;
        
        TaskViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.task_title);
            descriptionView = itemView.findViewById(R.id.task_description);
            dueDateView = itemView.findViewById(R.id.task_due_date);
            deleteButton = itemView.findViewById(R.id.delete_button);
            dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onTaskClick(getItem(position));
                }
            });
        }
        
        void bind(Task task) {
            titleView.setText(task.getTitle());
            descriptionView.setText(task.getDescription());
            dueDateView.setText(dateFormat.format(task.getDueDate()));
            
            deleteButton.setOnClickListener(v -> deleteListener.onTaskDelete(task));
        }
    }
} 