package com.greenfox.rikuriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button infoPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        infoPage = (Button) findViewById(R.id.btnLogin2);
        infoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoPage();
            }
        });
    }

    public void infoPage() {
        Intent intent = new Intent(this, InfoPage.class);
        startActivity(intent);
    }
}
