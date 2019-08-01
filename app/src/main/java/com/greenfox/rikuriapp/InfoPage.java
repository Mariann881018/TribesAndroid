package com.greenfox.rikuriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class InfoPage extends AppCompatActivity {

    ListView listBuildings;
    ListView listResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

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
        ArrayAdapter resourceArrayAdapter = new ArrayAdapter(this,
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
}
