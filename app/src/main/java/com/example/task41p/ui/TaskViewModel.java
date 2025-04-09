package com.example.task41p.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.task41p.data.Task;
import com.example.task41p.data.TaskRepository;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private final LiveData<List<Task>> allTasks;
    
    public TaskViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }
    
    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
    
    public LiveData<Task> getTask(long taskId) {
        return repository.getTask(taskId);
    }
    
    public void insert(Task task) {
        repository.insert(task);
    }
    
    public void update(Task task) {
        repository.update(task);
    }
    
    public void delete(Task task) {
        repository.delete(task);
    }
} 