package com.ore.vicse.firebase.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ore.vicse.firebase.R;

public class HomeActivity extends AppCompatActivity {

    public static final String user="names";
    private TextView txtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtHome = (TextView) findViewById(R.id.textHome);
        String user= getIntent().getStringExtra("names");
        txtHome.setText("¡Bienvenido "+ user +"!");

    }
}

