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
import com.greenfox.rikuriapp.Retrofit.JsonPlaceholderApi;
import com.greenfox.rikuriapp.Retrofit.KingdomIdDto;
import com.greenfox.rikuriapp.Retrofit.TroopDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Academy extends AppCompatActivity {

    Button logout;
    ListView listTroops;
    String userName;
    TextView user_kingdom_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy);
        userName = getIntent().getStringExtra("username");

        logout = (Button) findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Academy.this, "You successfully logged out!", Toast.LENGTH_LONG).show();
                logout();
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        user_kingdom_name = (TextView) findViewById(R.id.username);
        user_kingdom_name.setText(userName);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new AppConstants().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        Long kingdomId = getIntent().getLongExtra("id", 1L);
        getTroops(jsonPlaceholderApi, new KingdomIdDto(kingdomId));
    }

    public void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getTroops(JsonPlaceholderApi jsonPlaceholderApi, KingdomIdDto kingdomId) {
        String token = getIntent().getStringExtra("token");
        Call<List<TroopDto>> callTroop = jsonPlaceholderApi.callTroops(kingdomId, token);
        callTroop.enqueue(new Callback<List<TroopDto>>() {

            @Override
            public void onResponse(Call<List<TroopDto>> call, Response<List<TroopDto>> response) {
                if (response.isSuccessful()) {
                    int i = response.code();
                    Toast.makeText(Academy.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                    List<TroopDto> troopDtos = new ArrayList<>();
                    troopDtos = response.body();
                    getTroopListView(troopDtos);
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

                    Toast.makeText(Academy.this, Integer.toString(i) + ": " + message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TroopDto>> call, Throwable t) {
                Toast.makeText(Academy.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getTroopListView(List<TroopDto> troopDtos){
        listTroops = (ListView) findViewById(R.id.listTroops);
        final String[] troops  = new String[troopDtos.size()];
        for(int i = 0; i < troops.length; i++) {
            troops[i] = "Level : " + troopDtos.get(i).getLevel() + ", Hp: " + troopDtos.get(i).getHp() + ", Attack: " + troopDtos.get(i).getAttack() + ", Defense: " + troopDtos.get(i).getDefense();
        }
        ArrayAdapter troopArrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                Arrays.asList(troops));
        listTroops.setAdapter(troopArrayAdapter);
        listTroops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(Academy.this, "You selected: " + troops[i], Toast.LENGTH_LONG).show();
            }
        });
    }
}
