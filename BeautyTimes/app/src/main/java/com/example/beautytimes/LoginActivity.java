package com.example.beautytimes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);

        EditText email = findViewById(R.id.etEmail);
        EditText pass  = findViewById(R.id.etPassword);
        Button login = findViewById(R.id.btnLogin);
        TextView goRegister = findViewById(R.id.tvGoRegister);

        login.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(pass.getText())) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            long userId = db.login(email.getText().toString(), pass.getText().toString());
            if (userId > 0) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("userId", userId);
                startActivity(i);
            } else {
                Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show();
            }
        });

        goRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}
