package com.example.thriftymindfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        TableLayout tableLayout = findViewById(R.id.tbldashboard);
//
//// Create table rows
//        for (int i = 0; i < 3; i++) {
//            TableRow tableRow = new TableRow(this);
//            tableRow.setLayoutParams(new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//
//            // Create text views for each cell in the row
//            for (int j = 0; j < 3; j++) {
//                TextView textView = new TextView(this);
//                textView.setLayoutParams(new TableRow.LayoutParams(
//                        TableRow.LayoutParams.WRAP_CONTENT,
//                        TableRow.LayoutParams.WRAP_CONTENT));
//                textView.setPadding(16, 16, 16, 16); // Adjust padding as needed
//                textView.setText("Cell " + i + "-" + j);
//
//                // Add the text view to the table row
//                tableRow.addView(textView);
//            }
//
//            // Add the table row to the table layout
//            tableLayout.addView(tableRow);
//        }
    }
}