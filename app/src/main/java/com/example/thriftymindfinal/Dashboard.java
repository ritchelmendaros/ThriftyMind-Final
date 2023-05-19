package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    String[] goal_data = {"Food", "Allowance", "Projects"};
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        List<String> items = new LinkedList<>();
        items.add("Tansportation");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Goals goal = new Goals(items);
        recyclerView.setAdapter(goal);

        findViewById(R.id.btnAddExpenses).setOnClickListener(view -> {
            items.add(0, goal_data[count % 3]);
            count++;
            goal.notifyItemInserted(0);
            recyclerView.scrollToPosition(0);
        });
    }
}
