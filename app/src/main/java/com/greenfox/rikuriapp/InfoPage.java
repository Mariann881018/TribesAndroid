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
import com.greenfox.rikuriapp.Retrofit.ResourceDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoPage extends AppCompatActivity {

    Button logout;
    ListView listBuildings;
    ListView listResources;
    String userName;
    TextView user_kingdom_name;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        userName = getIntent().getStringExtra("username");

        logout = (Button) findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InfoPage.this, "You successfully logged out!", Toast.LENGTH_LONG).show();
                logout();
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        user_kingdom_name = (TextView) findViewById(R.id.username);
        user_kingdom_name.setText(userName);

        token = getIntent().getStringExtra("token");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new AppConstants().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        Long kingdomId = getIntent().getLongExtra("id", 1L);
        getResources(jsonPlaceholderApi, new KingdomIdDto(kingdomId));

        getBuildings(jsonPlaceholderApi, new KingdomIdDto(kingdomId), token, kingdomId, userName);
    }

    public void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getAcademy(String extraIntent, Long kingdomId, String userName) {
        Intent intent = new Intent(this, Academy.class);
        intent.putExtra("token", extraIntent);
        intent.putExtra("id", kingdomId);
        intent.putExtra("username", userName);
        startActivity(intent);
    }

    public void getTownhallPage(String extraIntent, Long kingdomId, String userName) {
        Intent intent = new Intent(this, Townhall.class);
        intent.putExtra("token", extraIntent);
        intent.putExtra("id", kingdomId);
        intent.putExtra("username", userName);
        startActivity(intent);
    }

    public void getResources(JsonPlaceholderApi jsonPlaceholderApi, KingdomIdDto kingdomId) {
        String token = getIntent().getStringExtra("token");
        Call<List<ResourceDto>> callResource = jsonPlaceholderApi.callResources(kingdomId, token);
        callResource.enqueue(new Callback<List<ResourceDto>>() {

            @Override
            public void onResponse(Call<List<ResourceDto>> call, Response<List<ResourceDto>> response) {
                if (response.isSuccessful()) {
                    int i = response.code();
                    Toast.makeText(InfoPage.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                    List<ResourceDto> resourceDtos = new ArrayList<>();
                    resourceDtos = response.body();
                    getResourceListView(resourceDtos);
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

                    Toast.makeText(InfoPage.this, Integer.toString(i) + ": " + message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResourceDto>> call, Throwable t) {
                Toast.makeText(InfoPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getResourceListView(List<ResourceDto> resourceDtos){
        listResources = (ListView) findViewById(R.id.listResources);
        final String[] resources  = new String[resourceDtos.size()];
        for(int i = 0; i < resources.length; i++) {
            resources[i] = resourceDtos.get(i).getType().name() + ": " + resourceDtos.get(i).getAmount();
        }
        ArrayAdapter resourceArrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                Arrays.asList(resources));
        listResources.setAdapter(resourceArrayAdapter);
        listResources.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(InfoPage.this, "You selected: " + resources[i], Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getBuildings(JsonPlaceholderApi jsonPlaceholderApi, KingdomIdDto idDto, final String extraIntent, final Long kingdomId, final String userName){
        String token = getIntent().getStringExtra("token");
        Call<List<BuildingDto>> callBuildings = jsonPlaceholderApi.callBuildings(idDto, token);
        callBuildings.enqueue(new Callback<List<BuildingDto>>() {

            @Override
            public void onResponse(Call<List<BuildingDto>> call, Response<List<BuildingDto>> response) {
                if (response.isSuccessful()) {
                    int i = response.code();
                    Toast.makeText(InfoPage.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                    List<BuildingDto> buildingDtos = new ArrayList<>();
                    buildingDtos = response.body();
                    getBuildingListView(buildingDtos, extraIntent, kingdomId, userName);
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

                    Toast.makeText(InfoPage.this, Integer.toString(i) + ": " + message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BuildingDto>> call, Throwable t) {
                Toast.makeText(InfoPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getBuildingListView(final List<BuildingDto> buildingDtos, final String extraIntent, final Long kingdomId, final String username) {
        listBuildings = (ListView) findViewById(R.id.listBuildings);
        final String[] buildings = new String[buildingDtos.size()];
        for(int i = 0; i < buildings.length; i++){
            buildings[i] = buildingDtos.get(i).getType().name() + ": level " + buildingDtos.get(i).getLevel();
        }
        ArrayAdapter buildingArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                Arrays.asList(buildings));
        listBuildings.setAdapter(buildingArrayAdapter);
        listBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (buildingDtos.get(i).getType().name().equals("TOWNHALL")) {
                    getTownhallPage(extraIntent, kingdomId, username);
                } else if(buildingDtos.get(i).getType().name().equals("ACADEMY")) {
                    getAcademy(extraIntent, kingdomId, username);
                }
                Toast.makeText(InfoPage.this, "You selected: " + buildings[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
