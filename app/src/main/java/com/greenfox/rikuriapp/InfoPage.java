package com.greenfox.rikuriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

public class InfoPage extends AppCompatActivity {

    ListView listBuildings;
    ListView listResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        listBuildings = (ListView) findViewById(R.id.listBuildings);
        String buildings[] = {"Townhall", "Farm", "Mine", "Academy"};
        ArrayAdapter buildingArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                Arrays.asList(buildings));
        listBuildings.setAdapter(buildingArrayAdapter);

        listResources = (ListView) findViewById(R.id.listResources);
        String resources[] = {"Gold", "Food"};
        ArrayAdapter resourceArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                Arrays.asList(resources));
        listResources.setAdapter(resourceArrayAdapter);
    }
}
