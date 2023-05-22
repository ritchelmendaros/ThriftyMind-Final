package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExpenses extends AppCompatActivity {
    private DatabaseReference expensesDatabaseReference;
    private EditText nameofexpenses, budget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        // Retrieve email value from the intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

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
                    promptIntent.putExtra("budget", Double.parseDouble(budgetValue));
                    startActivity(promptIntent);
                    finish();
                }
            }
        });
    }

    private void clearFields() {
        nameofexpenses.setText("");
        budget.setText("");
    }
}
