package com.example.beautytimes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DBHelper db;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        userId = getIntent().getLongExtra("userId", -1);

        RecyclerView rv = findViewById(R.id.rvServices);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ServicesAdapter ad = new ServicesAdapter(db.getServiceRows(), this::onServiceSelected);
        rv.setAdapter(ad);

        // Buttons must be wired INSIDE onCreate
        findViewById(R.id.btnAbout).setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));

        findViewById(R.id.btnMyBookings).setOnClickListener(v -> {
            Intent i = new Intent(this, MyBookingsActivity.class);
            i.putExtra("userId", userId);
            startActivity(i);
        });
    }

    private void onServiceSelected(String row) {
        // row example: "Haircut  •  45 min  •  SAR 60.0"
        String serviceName = row.split("•")[0].trim();
        long serviceId = db.getServiceIdByName(serviceName);
        if (serviceId < 0) {
            Toast.makeText(this, "Service not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar cal = Calendar.getInstance();

        // 1) Date picker
        DatePickerDialog dp = new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    String date = y + "-" + two(m + 1) + "-" + two(d);

                    // 2) Time picker
                    TimePickerDialog tp = new TimePickerDialog(
                            this,
                            (tView, hour, minute) -> {
                                String time = two(hour) + ":" + two(minute);
                                long id = db.insertAppointment(userId, serviceId, date, time, "");
                                if (id > 0) {
                                    Toast.makeText(this, "Booked " + serviceName + " on " + date + " at " + time, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(this, "Booking failed", Toast.LENGTH_SHORT).show();
                                }
                            },
                            cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE),
                            true
                    );
                    tp.setTitle("Select time");
                    tp.show();
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dp.setTitle("Select date");
        dp.show();
    }

    private String two(int x) { return (x < 10 ? "0" : "") + x; }
}