package com.example.beautytimes;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        Toast.makeText(this, "Your bookings loaded", Toast.LENGTH_SHORT).show();

        long userId = getIntent().getLongExtra("userId", -1);
        if (userId < 0) {
            Toast.makeText(this, "Open My Bookings from Main after login", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        DBHelper db = new DBHelper(this);
        ArrayList<String> bookings = db.getBookingsForUser(userId);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BookingsAdapter adapter = new BookingsAdapter(bookings);
        recyclerView.setAdapter(adapter);
    }
}