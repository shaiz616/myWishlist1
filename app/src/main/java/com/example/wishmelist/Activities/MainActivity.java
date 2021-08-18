package com.example.wishmelist.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.wishmelist.Classes.User;
import com.example.wishmelist.R;

public class MainActivity extends AppCompatActivity {

    private User u;
    private LandingActivity landing ;
    private TextView welcomeTxtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        u = landing.user;
        /*
        TODO: need to check if user is null
         */
        System.out.println("in main line 21" + u.getName());
        welcomeTxtV = findViewById(R.id.welcomeTxt);
        welcomeTxtV.setText("welcome " + u.getName());
    }
}