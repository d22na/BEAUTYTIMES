package com.example.beautytimes;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(this);

        EditText name = findViewById(R.id.etName);
        EditText email = findViewById(R.id.etEmail);
        EditText pass = findViewById(R.id.etPassword);
        Button register = findViewById(R.id.btnRegister);

        register.setOnClickListener(v -> {
            if (TextUtils.isEmpty(name.getText()) ||
                    TextUtils.isEmpty(email.getText()) ||
                    TextUtils.isEmpty(pass.getText())) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = db.register(
                    name.getText().toString(),
                    email.getText().toString(),
                    pass.getText().toString());

            if (result > 0) {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
