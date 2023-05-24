package com.example.thriftymindfinal;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExpenseViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView;
    TextView budgetTextView;
    Button btnRemoveExpenses;

    public ExpenseViewHolder(@NonNull View itemView, ExpenseAdapter.ExpenseClickListener expenseClickListener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.expenses);
        budgetTextView = itemView.findViewById(R.id.budget);
        //btnRemoveExpenses = itemView.findViewById(R.id.btnRemoveExpenses);

        btnRemoveExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    expenseClickListener.onExpenseRemoveClick(position);
                }
            }
        });

    }


}


