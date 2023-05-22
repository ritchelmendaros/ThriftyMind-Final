package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    String[] goal_data = {"Food", "Allowance", "Projects"};
    int count = 0;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        button = (Button) findViewById(R.id.btnSaving);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Savings.class);
                startActivity(intent);
            }
        });

        List<String> items = new LinkedList<>();
        items.add("Transportation");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Goals goal = new Goals(items);
        recyclerView.setAdapter(goal);

        findViewById(R.id.btnAddExpenses).setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, AddExpenses.class);
            startActivity(intent);
//            items.add(0, goal_data[count % 3]);
//            count++;
//            goal.notifyItemInserted(0);
//            recyclerView.scrollToPosition(0);
        });

        // Show the tip/notification dialog
        showTipDialog();
    }

    private void showTipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("TIP OF THE DAY")
                .setMessage("Cut down on unnecessary expenses by making your own coffee at home " +
                        "instead of buying it from cafes. Investing in a good quality coffee maker " +
                        "can save you a significant amount of money in the long run.")
                .setPositiveButton("Thanks!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Positive button click logic
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}