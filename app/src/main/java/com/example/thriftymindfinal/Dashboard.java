package com.example.thriftymindfinal;

import static com.example.thriftymindfinal.R.id.txtplannedbudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dashboard extends AppCompatActivity {
    private DatabaseReference expensesDatabaseReference;
    private DatabaseReference budgetDatabaseReference;
    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private List<Expense> expenses;
    private Button button;
    private EditText plannedbudget;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        SharedPreferences sharedPreferences = getSharedPreferences("TipPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isTipShown", false);
        editor.apply();

        button = findViewById(R.id.btnSaving);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(Dashboard.this, Savings.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });
        button = findViewById(R.id.btnGoal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(Dashboard.this, AddGoals.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenses = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(expenses);
        recyclerView.setAdapter(expenseAdapter);

        findViewById(R.id.btnAddExpenses).setOnClickListener(view -> {
            Intent intent = getIntent();
            String email = intent.getStringExtra("email");

            Intent promptIntent = new Intent(Dashboard.this, AddExpenses.class);
            promptIntent.putExtra("email", email);
            startActivity(promptIntent);
            finish();
        });

        // Retrieve email value from the intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        EditText user = findViewById(R.id.txtUser);
        user.setText("Hello " + email + "!");

        // Check if email is null
        if (email != null) {
            // Construct expenses database reference
            expensesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("expenses").child(email);

            // Retrieve expense records from the database
            expensesDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    expenses.clear();
                    int totalBudget = 0; // Variable to store the sum of all budgets

                    for (DataSnapshot expenseSnapshot : dataSnapshot.getChildren()) {
                        Expense expense = expenseSnapshot.getValue(Expense.class);
                        if (expense != null) {
                            expenses.add(expense);
                            totalBudget += expense.getBudget(); // Add the budget to the totalBudget
                        }
                    }
                    expenseAdapter.notifyDataSetChanged();

                    // Set the text of the EditText to the sum of all budgets
                    // Assuming you have an EditText with the id txtplannedbudget in your layout
                    EditText plannedBudgetEditText = findViewById(R.id.txtbudget);
                    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                    String formattedTotalBudget = decimalFormat.format(totalBudget);
                    plannedBudgetEditText.setText("₱ " + formattedTotalBudget);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Dashboard.this, "Failed to retrieve expenses", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            // Handle the case when email is null
            Toast.makeText(Dashboard.this, "Email is null", Toast.LENGTH_SHORT).show();
        }

        // Show the tip/notification dialog
        showTipDialog();
    }

    private void showTipDialog() {
        SharedPreferences sharedPreferences = getSharedPreferences("TipPrefs", MODE_PRIVATE);
        boolean isTipShown = sharedPreferences.getBoolean("isTipShown", false);

        if (!isTipShown) {
            List<String> tipMessages = new ArrayList<>();
            tipMessages.add("Cut down on unnecessary expenses by making your own coffee at home instead of buying it from cafes. Investing in a good quality coffee maker can save you a significant amount of money in the long run.");
            tipMessages.add("Save money on groceries by planning your meals in advance and creating a shopping list based on your meal plan.");
            tipMessages.add("Consider using public transportation or carpooling to save on transportation costs and reduce your carbon footprint.");
            tipMessages.add("Track your expenses regularly to identify areas where you can cut back and save money.");
            tipMessages.add("Avoid impulse purchases by waiting 24 hours before making a non-essential purchase.");
            tipMessages.add("Look for deals, discounts, and coupons before making any purchase to save money.");
            tipMessages.add("Consider cooking meals at home instead of dining out to save money on food expenses.");
            tipMessages.add("Automate your savings by setting up automatic transfers to a savings account.");
            tipMessages.add("Buy generic or store-brand products instead of branded items to save money without sacrificing quality.");
            tipMessages.add("Take advantage of loyalty programs and rewards to get discounts and earn points on your purchases.");

            Random random = new Random();
            int randomIndex = random.nextInt(tipMessages.size());
            String randomMessage = tipMessages.get(randomIndex);

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            builder.setTitle("TIP OF THE DAY")
                    .setMessage(randomMessage)
                    .setPositiveButton("Thanks!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Positive button click logic
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

            // Update the flag to indicate that the tip has been shown
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isTipShown", true);
            editor.apply();
        }
    }
}
