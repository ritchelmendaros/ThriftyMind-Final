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

import java.util.HashMap;

public class AddSavings extends AppCompatActivity {
    private DatabaseReference savingsDatabaseReference;
    private EditText savingsNameEditText, savedMoneyEditText, totalSavedMoneyEditText;
    private String email, plannedBudget;
    private ImageView userProfile, home, marketplace, savings;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings);

        // Retrieve email and planned budget values from the intent
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        plannedBudget = intent.getStringExtra("plannedbudget");
        EditText user = findViewById(R.id.txtUser);
        user.setText("Hello " + email + "!");

        home = (ImageView) findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(AddSavings.this, Dashboard.class);
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

                Intent promptIntent = new Intent(AddSavings.this, UserProfile.class);
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
//                Intent promptIntent = new Intent(Dashboard.this, Marketplace.class);
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

                Intent promptIntent = new Intent(AddSavings.this, Savings.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });

        button = (Button) findViewById(R.id.btnGoal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(AddSavings.this, AddGoals.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });

        // Construct savings database reference
        savingsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("savings").child(email);

        savingsNameEditText = findViewById(R.id.txtSavingsName);
        savedMoneyEditText = findViewById(R.id.txtSavedMoney);
        totalSavedMoneyEditText = findViewById(R.id.txtsavings); // Updated EditText for displaying total saved money

        Button addSavingsButton = findViewById(R.id.btnAddSavings);
        addSavingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String savingsName = savingsNameEditText.getText().toString();
                String savedMoney = savedMoneyEditText.getText().toString();

                if (savingsName.isEmpty() || savedMoney.isEmpty()) {
                    Toast.makeText(AddSavings.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String savingsKey = savingsDatabaseReference.push().getKey();

                    HashMap<String, Object> savingsMap = new HashMap<>();
                    savingsMap.put("email", email);
                    savingsMap.put("savingsName", savingsName);
                    savingsMap.put("savedMoney", Double.parseDouble(savedMoney));

                    savingsDatabaseReference.child(savingsKey).setValue(savingsMap);

                    Toast.makeText(AddSavings.this, "Savings added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                    Intent promptIntent = new Intent(AddSavings.this, Savings.class);
                    promptIntent.putExtra("email", email);
                    startActivity(promptIntent);
                    finish();
                }
            }
        });

        EditText plannedBudgetEditText = findViewById(R.id.txtsavings);
        plannedBudgetEditText.setText("₱ " + plannedBudget);

        // Retrieve savings records from the database and calculate the total saved money
        savingsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double totalSavedMoney = 0.0;

                for (DataSnapshot savingsSnapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> savingsMap = (HashMap<String, Object>) savingsSnapshot.getValue();
                    if (savingsMap != null) {
                        double savedMoney = ((Number) savingsMap.get("savedMoney")).doubleValue();
                        totalSavedMoney += savedMoney; // Add the saved money to the totalSavedMoney
                    }
                }

                String formattedSavedMoney = String.format("%.2f", totalSavedMoney); // Format the total saved money with two decimal places
                totalSavedMoneyEditText.setText("₱ " + formattedSavedMoney); // Set the total saved money as the text for txtsavings
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddSavings.this, "Failed to retrieve savings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        savingsNameEditText.setText("");
        savedMoneyEditText.setText("");
    }
}
