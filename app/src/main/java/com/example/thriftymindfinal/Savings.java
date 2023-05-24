package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Savings extends AppCompatActivity {
    private Button button;
    private RecyclerView recyclerView;
    private SavingsAdapter savingsAdapter;
    private List<HashMap<String, Object>> savingsList;
    private TextView txtAllSavedMoney;
    private ImageView userProfile, home, marketplace, savings, logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        EditText user = findViewById(R.id.txtUser);
        recyclerView = findViewById(R.id.recyclerViewSavings);
        txtAllSavedMoney = findViewById(R.id.txtallsavedmoney);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        user.setText("Hello " + email + "!");

        userProfile = (ImageView) findViewById(R.id.userprofile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(Savings.this, UserProfile.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });

//        button = (Button) findViewById(R.id.btnExpenses);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = getIntent();
//                String email = intent.getStringExtra("email");
//
//                Intent promptIntent = new Intent(Savings.this, Dashboard.class);
//                promptIntent.putExtra("email", email);
//                startActivity(promptIntent);
//                finish();
//            }
//        });
        home = (ImageView) findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(Savings.this, Dashboard.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });
        marketplace = (ImageView) findViewById(R.id.Market);
        marketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(Savings.this, Marketplace.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });
        savings = (ImageView) findViewById(R.id.Savings);
        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(Savings.this, Savings.class);
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

                Intent promptIntent = new Intent(Savings.this, AddGoals.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });

        button = findViewById(R.id.btnAddSavings);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(Savings.this, AddSavings.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });

        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Savings.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize RecyclerView and its adapter
        savingsList = new ArrayList<>();
        savingsAdapter = new SavingsAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(savingsAdapter);

        // Retrieve savings records from the database
        DatabaseReference savingsRef = FirebaseDatabase.getInstance().getReference().child("savings").child(email);
        savingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                savingsList.clear();
                double totalSavedMoney = 0.0;

                for (DataSnapshot savingsSnapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> savings = (HashMap<String, Object>) savingsSnapshot.getValue();
                    savingsList.add(savings);

                    // Calculate the sum of savedMoney
                    Object savedMoneyObj = savings.get("savedMoney");
                    if (savedMoneyObj instanceof Double) {
                        totalSavedMoney += (Double) savedMoneyObj;
                    } else if (savedMoneyObj instanceof Long) {
                        totalSavedMoney += ((Long) savedMoneyObj).doubleValue();
                    }
                }

                savingsAdapter.setSavingsList(savingsList);

                // Display the sum of savedMoney in txtallsavedmoney
                DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                String formattedTotalSavedMoney = decimalFormat.format(totalSavedMoney);
                txtAllSavedMoney.setText("₱" + formattedTotalSavedMoney);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}

