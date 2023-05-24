package com.example.thriftymindfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Goals extends RecyclerView.Adapter<DemoVH> {
    List<String> items;

    public Goals(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DemoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goals_data, parent, false);
        return new DemoVH(view).GoalsData(this);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoVH holder, int position) {
        holder.textview.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class DemoVH extends RecyclerView.ViewHolder {
    TextView textview;
    private Goals goalsdata ;
    public DemoVH(@NonNull View itemView) {
        super(itemView);
        textview = (TextView) itemView.findViewById(R.id.goals);
////        itemView.findViewById(R.id.btnRemoveGoal).setOnClickListener(view -> {
//            goalsdata.items.remove(getAdapterPosition());
//            goalsdata.notifyItemRemoved(getAdapterPosition());
//        });
    }
    public DemoVH GoalsData(Goals goaldata) {
        this.goalsdata = goaldata;
        return this;
    }
}
