package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;

public class UserProfile extends AppCompatActivity {

    private EditText firstNameTextView;
    private EditText lastNameTextView;
    private EditText averageExpensesTextView;
    private ImageView home, savings, marketplace;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firstNameTextView = findViewById(R.id.FullName);
        averageExpensesTextView = findViewById(R.id.average);

        home = (ImageView) findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(UserProfile.this, Dashboard.class);
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

                Intent promptIntent = new Intent(UserProfile.this, Savings.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        DatabaseReference usersDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference expensesDatabaseReference = FirebaseDatabase.getInstance().getReference("expenses");

        usersDatabaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firstName = (String) dataSnapshot.child("fullname").getValue();
                    String lastName = (String) dataSnapshot.child("lastname").getValue();
                    firstNameTextView.setText(firstName + " " + lastName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        if (email != null) {
            // Construct expenses database reference
            expensesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("expenses").child(email);

            // Retrieve expense records from the database
            expensesDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int totalBudget = 0; // Variable to store the sum of all budgets
                    int expenseCount = 0; // Variable to store the count of expenses

                    for (DataSnapshot expenseSnapshot : dataSnapshot.getChildren()) {
                        HashMap<String, Object> expenseData = (HashMap<String, Object>) expenseSnapshot.getValue();
                        if (expenseData != null) {
                            int budget = Integer.parseInt(expenseData.get("budget").toString());
                            totalBudget += budget; // Add the budget to the totalBudget
                            expenseCount++; // Increment the expense count
                        }
                    }

                    // Calculate the average budget
                    double averageBudget = 0.0;
                    if (expenseCount > 0) {
                        averageBudget = (double) totalBudget / expenseCount;
                    }

                    // Set the text of the EditText to the average budget
                    // Assuming you have an EditText with the id average in your layout
                    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                    String formattedAverageBudget = decimalFormat.format(averageBudget);
                    averageExpensesTextView.setText("₱ " + formattedAverageBudget);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }
    }
}
