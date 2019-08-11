package com.greenfox.rikuriapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenfox.rikuriapp.Retrofit.JsonPlaceholderApi;
import com.greenfox.rikuriapp.Retrofit.KingdomIdDto;
import com.greenfox.rikuriapp.Retrofit.NewBuildingDto;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateBuilding extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button createBuildingButton;
    Button logout;
    TextView user_kingdom_name;
    String userName;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_building);
        userName = getIntent().getStringExtra("username");
        token = getIntent().getStringExtra("token");

        logout = (Button) findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateBuilding.this, "You successfully logged out!", Toast.LENGTH_LONG).show();
                logout();
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        user_kingdom_name = (TextView) findViewById(R.id.username);
        user_kingdom_name.setText(userName);

        radioGroup = findViewById(R.id.radioGroup);
        createBuildingButton = (Button) findViewById(R.id.createBuilding);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new AppConstants().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        final Long kingdomId = getIntent().getLongExtra("id", 1L);

        createBuildingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCreateNewBuilding(jsonPlaceholderApi, new KingdomIdDto(74L), token, 74L, userName);
            }
        });
    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Your selected building type is: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getTownhallPage(String extraIntent, Long kingdomId, String userName) {
        Intent intent = new Intent(this, Townhall.class);
        intent.putExtra("token", extraIntent);
        intent.putExtra("id", kingdomId);
        intent.putExtra("username", userName);
        startActivity(intent);
    }

    public void getCreateNewBuilding(JsonPlaceholderApi jsonPlaceholderApi, final KingdomIdDto kingdomIdDto, final String extraIntent, final Long kingdomId, final String userName) {
        String token = getIntent().getStringExtra("token");
        Call<NewBuildingDto> callNewBuilding = jsonPlaceholderApi.callNewBuilding(kingdomIdDto, token);
        callNewBuilding.enqueue(new Callback<NewBuildingDto>() {

            @Override
            public void onResponse(Call<NewBuildingDto> call, Response<NewBuildingDto> response) {
                if (response.isSuccessful()) {
                    int i = response.code();
                    Toast.makeText(CreateBuilding.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                    getTownhallPage(extraIntent, kingdomId, userName);
                } else {
                    int i = response.code();
                    String resp = null;
                    try {
                        resp = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String[] array = resp.split("\"");
                    String message = array[7];
                    message.replace("\"", "" );

                    Toast.makeText(CreateBuilding.this, Integer.toString(i) + ": " + message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewBuildingDto> call, Throwable t) {
                Toast.makeText(CreateBuilding.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
