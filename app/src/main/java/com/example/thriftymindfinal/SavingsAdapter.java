package com.example.thriftymindfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.SavingsViewHolder> {
    private List<HashMap<String, Object>> savingsList;

    public void setSavingsList(List<HashMap<String, Object>> savingsList) {
        this.savingsList = savingsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_savings, parent, false);
        return new SavingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingsViewHolder holder, int position) {
        int reversedPosition = savingsList.size() - position - 1;
        HashMap<String, Object> savings = savingsList.get(reversedPosition);
        String savingsName = (String) savings.get("savingsName");

        // Get the savedMoney value and convert it to a double
        Object savedMoneyObj = savings.get("savedMoney");
        double savedMoney = 0.0;
        if (savedMoneyObj instanceof Double) {
            savedMoney = (Double) savedMoneyObj;
        } else if (savedMoneyObj instanceof Long) {
            savedMoney = ((Long) savedMoneyObj).doubleValue();
        }

        // Set the values in the ViewHolder
        holder.tvSavingsName.setText(savingsName);
        holder.tvSavedMoney.setText("₱" + String.format("%.2f", savedMoney));
    }

    @Override
    public int getItemCount() {
        return savingsList != null ? savingsList.size() : 0;
    }

    public static class SavingsViewHolder extends RecyclerView.ViewHolder {
        TextView tvSavingsName;
        TextView tvSavedMoney;

        public SavingsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSavingsName = itemView.findViewById(R.id.savings);
            tvSavedMoney = itemView.findViewById(R.id.money);
        }
    }
}
