package com.greenfox.rikuriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    protected Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = (Button) findViewById(R.id.btnReg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Register.this, "Your registration was successful!", Toast.LENGTH_LONG).show();
                login();
            }
        });
    }

    public void login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}