package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

public class Login extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.btnLogin);
        EditText username = (EditText) findViewById(R.id.txtEmail);
        EditText password = (EditText) findViewById(R.id.txtPass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readCredentials(Login.this);
            }
        });

    }
    public void readCredentials(Context context) {
        // Retrieve the stored credentials from the credentials.xml file
        Resources res = context.getResources();
        XmlResourceParser parser = res.getXml(R.xml.data);

        String username = "";
        String password = "";

        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if (tagName.equals("username")) {
                        username = parser.nextText();
                    } else if (tagName.equals("password")) {
                        password = parser.nextText();
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Compare the retrieved credentials with the input values
        EditText txtUsername = ((Login) context).findViewById(R.id.txtEmail);
        EditText txtPassword = ((Login) context).findViewById(R.id.txtPass);

        String inputUsername = txtUsername.getText().toString();
        String inputPassword = txtPassword.getText().toString();

        if (inputUsername.equals(username) && inputPassword.equals(password)) {
            // Credentials match, proceed with login
            // Add your login logic here
            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
        } else {
            // Credentials do not match, show an error message
            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}