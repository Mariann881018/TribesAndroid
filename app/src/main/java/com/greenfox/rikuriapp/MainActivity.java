package com.greenfox.rikuriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button register;
    protected Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (Button) findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Let's register you!", Toast.LENGTH_LONG).show();
                register();
            }
        });

        login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Let's sign you in!", Toast.LENGTH_LONG).show();
                login();
            }
        });
    }

    public void register() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}