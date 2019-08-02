package com.greenfox.rikuriapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenfox.rikuriapp.Retrofit.JsonPlaceholderApi;
import com.greenfox.rikuriapp.Retrofit.KingdomIdDto;
import com.greenfox.rikuriapp.Retrofit.ResourceDto;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoPage extends AppCompatActivity {

    ListView listBuildings;
    ListView listResources;
    TextView user_kingdom_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://calm-peak-87984.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        user_kingdom_name = (TextView) findViewById(R.id.username);

        listBuildings = (ListView) findViewById(R.id.listBuildings);
        final String buildings[] = {"Townhall", "Farm", "Mine", "Academy"};
        ArrayAdapter buildingArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                Arrays.asList(buildings));
        listBuildings.setAdapter(buildingArrayAdapter);
        listBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(InfoPage.this, "You selected: " + buildings[i], Toast.LENGTH_LONG).show();
            }
        });

        listResources = (ListView) findViewById(R.id.listResources);
        final String resources[] = {"Gold", "Food"};
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

        getResourcesAndBuildings(jsonPlaceholderApi, new KingdomIdDto(1L), resources);
    }

    public void getResourcesAndBuildings(JsonPlaceholderApi jsonPlaceholderApi, KingdomIdDto kingdomId, String list[]){
        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MyIsImV4cCI6MTU2NTU4MTA5MX0.vFearFaUK77XoYoeGJzcue8pWmgNUeHWvUhLPRhsk330o1cFKmA7NHkg0wehuim9owPP_IOwZ84npHlS_drQWQ";
        Call<List<ResourceDto>> callResource = jsonPlaceholderApi.callResources(kingdomId, token);
        callResource.enqueue(new Callback<List<ResourceDto>>() {

            @Override
            public void onResponse(Call<List<ResourceDto>> call, Response<List<ResourceDto>> response) {
                if(response.isSuccessful()){
                    int i = response.code();
                    Toast.makeText(InfoPage.this, Integer.toString(i), Toast.LENGTH_LONG).show();

                }else{
                    int i = response.code();
                    String error = "Status: " + i;
                    Toast.makeText(InfoPage.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResourceDto>> call, Throwable t) {
                Toast.makeText(InfoPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }});
    }
}
