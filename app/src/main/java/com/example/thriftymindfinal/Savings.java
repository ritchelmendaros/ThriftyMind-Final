package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Savings extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        button = (Button) findViewById(R.id.btnExpenses);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Savings.this, Dashboard.class);
                startActivity(intent);
            }
        });
        button = (Button) findViewById(R.id.btnAddGoals);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Savings.this, AddGoals.class);
                startActivity(intent);
            }
        });
    }
}