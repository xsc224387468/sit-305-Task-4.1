package com.example.task41p;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        MaterialButton goToTasksButton = findViewById(R.id.go_to_tasks_button);
        goToTasksButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TaskEditActivity.class);
            startActivity(intent);
        });
    }
} 