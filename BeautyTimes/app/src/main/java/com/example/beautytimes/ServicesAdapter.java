package com.example.beautytimes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.Holder> {

    private final List<String> list;
    private final OnClick listener;

    public interface OnClick {
        void onClick(String item);
    }

    public ServicesAdapter(List<String> list, OnClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_service, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String item = list.get(position);

        String[] parts = item.split(" • ");

        if (parts.length >= 3) {
            holder.serviceName.setText(parts[0]); // Service name 
            holder.serviceDetails.setText(parts[1] + " • " + parts[2]); // Duration • Price
        } else {
            holder.serviceName.setText(item);
            holder.serviceDetails.setText("");
        }

        holder.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView serviceName;
        TextView serviceDetails;

        Holder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.rowTitle);
            serviceDetails = itemView.findViewById(R.id.rowSubtitle);
        }
    }
}