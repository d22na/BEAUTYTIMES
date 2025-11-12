package com.example.beautytimes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private final List<String> bookingsList;

    public BookingsAdapter(List<String> bookingsList) {
        this.bookingsList = bookingsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String booking = bookingsList.get(position);

        // Format: "Haircut • 2025-11-19 • 05:29"
        String[] parts = booking.split(" • ");

        if (parts.length >= 3) {
            holder.serviceName.setText(parts[0].trim());
            holder.bookingDate.setText("Date: " + parts[1].trim());
            holder.bookingTime.setText("Time: " + parts[2].trim());
        } else {
            holder.serviceName.setText(booking);
            holder.bookingDate.setText("");
            holder.bookingTime.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName, bookingDate, bookingTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.bookingServiceName);
            bookingDate = itemView.findViewById(R.id.bookingDate);
            bookingTime = itemView.findViewById(R.id.bookingTime);
        }
    }
}