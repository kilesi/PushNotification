package com.example.fcmnotifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Second extends AppCompatActivity {
    TextView receivedCookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        receivedCookies = findViewById(R.id.tvReceivedCookies);
        String cookies = getIntent().getStringExtra("cookies");
        receivedCookies.setText(cookies);
        int totalCookies = Integer.valueOf(cookies);
        if (totalCookies < 50) {
            Toast.makeText(this, "You will get smaller bonus", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You will get HUGE bonus", Toast.LENGTH_LONG).show();
        }
    }
}
