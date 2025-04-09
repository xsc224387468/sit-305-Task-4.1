package com.example.task41p;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.task41p.data.Task;
import com.example.task41p.ui.TaskViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskEditActivity extends AppCompatActivity {
    public static final String EXTRA_TASK_ID = "task_id";
    
    private TextInputEditText titleEdit;
    private TextInputEditText descriptionEdit;
    private TextInputEditText dueDateEdit;
    private TextInputLayout titleLayout;
    private TextInputLayout descriptionLayout;
    private TextInputLayout dueDateLayout;
    private MaterialButton saveButton;
    
    private TaskViewModel taskViewModel;
    private Task currentTask;
    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        
        titleEdit = findViewById(R.id.title_edit);
        descriptionEdit = findViewById(R.id.description_edit);
        dueDateEdit = findViewById(R.id.due_date_edit);
        titleLayout = findViewById(R.id.title_layout);
        descriptionLayout = findViewById(R.id.description_layout);
        dueDateLayout = findViewById(R.id.due_date_layout);
        saveButton = findViewById(R.id.save_button);
        
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        
        long taskId = getIntent().getLongExtra(EXTRA_TASK_ID, -1);
        if (taskId != -1) {
            taskViewModel.getTask(taskId).observe(this, task -> {
                if (task != null) {
                    currentTask = task;
                    titleEdit.setText(task.getTitle());
                    descriptionEdit.setText(task.getDescription());
                    dueDateEdit.setText(dateFormat.format(task.getDueDate()));
                    calendar.setTime(task.getDueDate());
                }
            });
        }
        
        dueDateEdit.setOnClickListener(v -> showDatePicker());
        dueDateEdit.setText(dateFormat.format(calendar.getTime()));
        
        saveButton.setOnClickListener(v -> saveTask());
    }
    
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                dueDateEdit.setText(dateFormat.format(calendar.getTime()));
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    
    private void saveTask() {
        String title = titleEdit.getText().toString().trim();
        String description = descriptionEdit.getText().toString().trim();
        
        if (TextUtils.isEmpty(title)) {
            titleLayout.setError("Title is required");
            return;
        }
        
        if (TextUtils.isEmpty(description)) {
            descriptionLayout.setError("Description is required");
            return;
        }
        
        if (currentTask != null) {
            currentTask.setTitle(title);
            currentTask.setDescription(description);
            currentTask.setDueDate(calendar.getTime());
            taskViewModel.update(currentTask);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            Task newTask = new Task(title, description, calendar.getTime());
            taskViewModel.insert(newTask);
            Toast.makeText(this, "Task created", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
} 