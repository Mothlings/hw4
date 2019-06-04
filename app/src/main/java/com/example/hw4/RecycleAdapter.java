package com.example.hw4;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private ArrayList<TimeSlot> List;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tID;
        public TextView tStartTime;
        public TextView tEndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tID = itemView.findViewById(R.id.tId);
            tStartTime = itemView.findViewById(R.id.tStartTime);
            tEndTime = itemView.findViewById(R.id.tEndTime);
        }
    }

    public RecycleAdapter(ArrayList<TimeSlot> list) {
        List = list;
    }

    @Override
    public ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimeSlot currentItem = List.get(position);
        holder.tID.setText("ID: "+currentItem.getIdTimeSlot());
        holder.tStartTime.setText("Start Time:"+(currentItem.getStartTime()));
        holder.tEndTime.setText("End Time: "+ currentItem.getEndTime());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}