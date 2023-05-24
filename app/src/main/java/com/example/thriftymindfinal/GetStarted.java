package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetStarted extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        button = (Button) findViewById(R.id.btnGetStarted);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");

                Intent promptIntent = new Intent(GetStarted.this, Dashboard.class);
                promptIntent.putExtra("email", email);
                startActivity(promptIntent);
                finish();
            }
        });
    }
}
