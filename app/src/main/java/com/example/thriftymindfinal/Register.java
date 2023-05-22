package com.example.thriftymindfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dbthriftymind-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fname = findViewById(R.id.txtFirstName);
        final EditText email = findViewById(R.id.txtEmail);
        final EditText lname = findViewById(R.id.txtLastName);
        final EditText pass = findViewById(R.id.txtPass);
        final Button registerbtn = findViewById(R.id.btnregister);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fnametxt = fname.getText().toString();
                final String emailtxt = email.getText().toString();
                final String lnametxt = lname.getText().toString();
                final String passtxt = pass.getText().toString();

                if (fnametxt.isEmpty() || emailtxt.isEmpty() || lnametxt.isEmpty() || passtxt.isEmpty()) {
                    Toast.makeText(Register.this, "Data is required.", Toast.LENGTH_SHORT).show();
                } else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(emailtxt)) {
                                Toast.makeText(Register.this, "Email is already registered.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Store the user information in the "users" table
                                databaseReference.child("users").child(emailtxt).child("fullname").setValue(fnametxt);
                                databaseReference.child("users").child(emailtxt).child("email").setValue(emailtxt);
                                databaseReference.child("users").child(emailtxt).child("lastname").setValue(lnametxt);
                                databaseReference.child("users").child(emailtxt).child("password").setValue(passtxt);

                                // Create a default budget for the user in the "budget" table
                                Budget budget = new Budget(emailtxt, 5000.00);
                                databaseReference.child("budget").child(emailtxt).setValue(budget);

                                Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Register.this, Login.class);
                                intent.putExtra("email", emailtxt);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle the cancellation
                        }
                    });
                }
            }
        });

        TextView loginTextView = findViewById(R.id.Login);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the LoginActivity
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}

