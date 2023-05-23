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
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddGoals extends AppCompatActivity {
    private Button button;
    private DatabaseReference goalDatabaseReference;
    private RecyclerView recyclerView;
    private GoalsAdapter goalsAdapter;
    private List<HashMap<String, Object>> goalsList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals);

        button = findViewById(R.id.btnAddGoal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGoalToDatabase();
            }
        });

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        EditText user = findViewById(R.id.txtUser);
        user.setText("Hello " + email + "!");

        recyclerView = findViewById(R.id.recyclerViewGoals);
        goalsList = new ArrayList<>();
        goalsAdapter = new GoalsAdapter(goalsList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(goalsAdapter);

        // Construct goal database reference
        goalDatabaseReference = FirebaseDatabase.getInstance().getReference().child("goal");
        goalDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                goalsList.clear();

                for (DataSnapshot goalSnapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> goal = (HashMap<String, Object>) goalSnapshot.getValue();
                    goalsList.add(goal);
                }

                goalsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void saveGoalToDatabase() {
        EditText goalNameEditText = findViewById(R.id.txtNameofGoal);
        String goalName = goalNameEditText.getText().toString().trim();

        if (!goalName.isEmpty()) {
            Intent intent = getIntent();
            String email = intent.getStringExtra("email");

            String goalKey = goalDatabaseReference.push().getKey();

            Map<String, Object> goalMap = new HashMap<>();
            goalMap.put("email", email);
            goalMap.put("goalName", goalName);

            goalDatabaseReference.child(goalKey).setValue(goalMap);

            Toast.makeText(AddGoals.this, "Added Successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Goal name is empty, show an error message
            goalNameEditText.setError("Please enter a goal name");
            goalNameEditText.requestFocus();
        }
    }
}
