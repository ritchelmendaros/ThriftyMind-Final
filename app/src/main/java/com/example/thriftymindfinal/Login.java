package com.example.thriftymindfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thriftymindfinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

public class Login extends AppCompatActivity {
    private Button button;
    private TextView signUpTextView;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dbthriftymind-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.txtEmail);
        final EditText password = findViewById(R.id.txtPass);
        final Button loginbtn = findViewById(R.id.btnLogin);
        final TextView registerbtn = findViewById(R.id.SignUp);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailtxt = email.getText().toString();
                final String passwordtxt = password.getText().toString();

                if(emailtxt.isEmpty() || passwordtxt.isEmpty()){
                    Toast.makeText(Login.this, "Data is required.", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(emailtxt)){
                                final String getPassword = snapshot.child(emailtxt).child("password").getValue(String.class);
                                if(getPassword.equals(passwordtxt)){
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, GetStarted.class));
                                    finish();
                                } else{
                                    Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();

                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Wrong Email", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}
