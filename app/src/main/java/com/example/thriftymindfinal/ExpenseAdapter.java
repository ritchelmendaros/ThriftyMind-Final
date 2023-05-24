package com.example.thriftymindfinal;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private List<Expense> expenses;
    private ExpenseClickListener expenseClickListener;

    public ExpenseAdapter(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
//        Expense expense = expenses.get(position);
//        holder.nameTextView.setText(expense.getName());
//        holder.budgetTextView.setText("₱" + String.format("%.2f", expense.getBudget()));

        int reversedPosition = expenses.size() - position - 1;
        Expense reversedExpense = expenses.get(reversedPosition);
        holder.nameTextView.setText(reversedExpense.getName());
        holder.budgetTextView.setText("₱" + String.format("%.2f", reversedExpense.getBudget()));

//        holder.btnRemoveExpenses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Pass the position to the onExpenseRemoveClick method in the Dashboard activity
//                if (expenseClickListener != null) {
//                    expenseClickListener.onExpenseRemoveClick(position);
//                }
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenseClickListener(ExpenseClickListener listener) {
        this.expenseClickListener = listener;
    }

    public interface ExpenseClickListener {
        void onExpenseRemoveClick(int position);
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView budgetTextView;
        Button btnRemoveExpenses;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.expenses);
            budgetTextView = itemView.findViewById(R.id.budget);
//            btnRemoveExpenses = itemView.findViewById(R.id.btnRemoveExpenses);
//
//            btnRemoveExpenses.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION && expenseClickListener != null) {
//                        expenseClickListener.onExpenseRemoveClick(position);
//                    }
//                }
//            });
        }
    }
}
