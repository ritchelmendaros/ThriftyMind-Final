package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddExpenses extends AppCompatActivity {
    private DatabaseReference expensesDatabaseReference;
    private EditText nameofexpenses, user, budget, displayexp;
    private ImageView userProfile, home, marketplace, savings;
    private Button button;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        // Retrieve email value from the intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String plannedBudget = intent.getStringExtra("plannedbudget");
        user = findViewById(R.id.txtUser);
        user.setText("Hello " + email + "!");
        displayexp = findViewById(R.id.txtplannedbudget);
        displayexp.setText(plannedBudget);

        home = (ImageView) findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(AddExpenses.this, Dashboard.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });
        userProfile = (ImageView) findViewById(R.id.userprofile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(AddExpenses.this, UserProfile.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });
//        marketplace = (ImageView) findViewById(R.id.Market);
//        marketplace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = getIntent();
//                String email = intent.getStringExtra("email");
//
//                Intent promptIntent = new Intent(AddExpenses.this, Marketplace.class);
//                promptIntent.putExtra("email", email);
//                startActivity(promptIntent);
//                finish();
//            }
//        });
        savings = (ImageView) findViewById(R.id.Savings);
        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(AddExpenses.this, Savings.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });

        ImageView logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddExpenses.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        button = (Button) findViewById(R.id.btnGoals);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(AddExpenses.this, AddGoals.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });


        // Construct expenses database reference
        expensesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("expenses").child(email);

        nameofexpenses = findViewById(R.id.txtNameofExpenses);
        budget = findViewById(R.id.txtAllotedBudget);

        Button addExpensesButton = findViewById(R.id.btnAddExpenses);
        addExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameofexpenses.getText().toString();
                String budgetValue = budget.getText().toString();

                if (name.isEmpty() || budgetValue.isEmpty()) {
                    Toast.makeText(AddExpenses.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a unique key for the expense entry
                    String expenseKey = expensesDatabaseReference.push().getKey();

                    // Create an Expense object with the entered data
                    Expense expense = new Expense(name, Double.parseDouble(budgetValue));

                    // Store the expense under the generated key
                    expensesDatabaseReference.child(expenseKey).setValue(expense);

                    Toast.makeText(AddExpenses.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                    Intent promptIntent = new Intent(AddExpenses.this, Dashboard.class);
                    promptIntent.putExtra("email", email);
                    promptIntent.putExtra("plannedbudget", plannedBudget);
                    startActivity(promptIntent);
                    finish();
                }
            }
        });

        // Retrieve expense records from the database and calculate the total budget
        expensesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double totalBudget = 0.0;

                for (DataSnapshot expenseSnapshot : dataSnapshot.getChildren()) {
                    Expense expense = expenseSnapshot.getValue(Expense.class);
                    if (expense != null) {
                        totalBudget += expense.getBudget(); // Add the budget to the totalBudget
                    }
                }

                String formattedBudget = String.format("%.2f", totalBudget); // Format the total budget with two decimal places
                displayexp.setText("₱ " + formattedBudget);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddExpenses.this, "Failed to retrieve expenses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        nameofexpenses.setText("");
        budget.setText("");
    }
}
