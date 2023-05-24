package com.example.thriftymindfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {
    private List<HashMap<String, Object>> goalsList;

    public GoalsAdapter(List<HashMap<String, Object>> goalsList) {
        this.goalsList = goalsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView goalNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            goalNameTextView = itemView.findViewById(R.id.goals);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goals_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int reversedPosition = goalsList.size() - position - 1;
        HashMap<String, Object> goal = goalsList.get(reversedPosition);

        // Set goal name to TextView
        String goalName = (String) goal.get("goalName");
        holder.goalNameTextView.setText(goalName);
    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    public void addGoal(HashMap<String, Object> goal) {
        goalsList.add(0, goal); // Add the new goal at the beginning of the list
        notifyItemInserted(0); // Notify the adapter about the new item at position 0
    }
}
