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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    String[] goal_data = {"Food", "Allowance", "Projects"};
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_dashboard);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_dashboard:
                    return true;
                case R.id.bottom_barter:
                    startActivity(new Intent(getApplicationContext(), Marketplace.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_chat:
                    startActivity(new Intent(getApplicationContext(), Chat.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        List<String> items = new LinkedList<>();
        items.add("Transportation");

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

