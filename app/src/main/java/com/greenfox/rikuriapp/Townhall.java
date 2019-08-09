package com.greenfox.rikuriapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenfox.rikuriapp.Retrofit.BuildingDto;
import com.greenfox.rikuriapp.Retrofit.JsonPlaceholderApi;
import com.greenfox.rikuriapp.Retrofit.KingdomIdDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Townhall extends AppCompatActivity {

    Button logout;
    ListView listBuildings;
    String userName;
    TextView user_kingdom_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_townhall);
        userName = getIntent().getStringExtra("username");
        logout = (Button) findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Townhall.this, "You successfully logged out!", Toast.LENGTH_LONG).show();
                logout();
            }
        });

        Gson gson = new GsonBuilder()
          .setLenient()
          .create();

        user_kingdom_name = (TextView) findViewById(R.id.username);
        user_kingdom_name.setText(userName);

        Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://calm-peak-87984.herokuapp.com")
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build();

        final JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        Long kingdomId = getIntent().getLongExtra("id", 1L);
        getBuildings(jsonPlaceholderApi, new KingdomIdDto(kingdomId));
    }

    public void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getBuildings(JsonPlaceholderApi jsonPlaceholderApi, KingdomIdDto idDto){
        String token = getIntent().getStringExtra("token");
        Call<List<BuildingDto>> callBuildings = jsonPlaceholderApi.callBuildings(idDto, token);
        callBuildings.enqueue(new Callback<List<BuildingDto>>() {

            @Override
            public void onResponse(Call<List<BuildingDto>> call, Response<List<BuildingDto>> response) {
                if (response.isSuccessful()) {
                    int i = response.code();
                    Toast.makeText(Townhall.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                    List<BuildingDto> buildingDtos = new ArrayList<>();
                    buildingDtos = response.body();
                    getBuildingListView(buildingDtos);

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

                    Toast.makeText(Townhall.this, Integer.toString(i) + ": " + message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BuildingDto>> call, Throwable t) {
                Toast.makeText(Townhall.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getBuildingListView(List<BuildingDto> buildingDtos){
        listBuildings = (ListView) findViewById(R.id.listBuildings);
        final String[] buildings = new String[buildingDtos.size()];
        for(int i = 0; i < buildings.length; i++){
            buildings[i] =buildingDtos.get(i).getType().name() + ": level " + buildingDtos.get(i).getLevel();
        }
        ArrayAdapter buildingArrayAdapter = new ArrayAdapter(this,
          android.R.layout.simple_list_item_1,
          Arrays.asList(buildings));
        listBuildings.setAdapter(buildingArrayAdapter);
        listBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Townhall.this, "You selected: " + buildings[i], Toast.LENGTH_LONG).show();
            }
        });
    }
}