package com.example.alex.runtrak;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import Run.*;

public class History extends AppCompatActivity {

    ListView runList;
    ArrayAdapter<String> arrayAdapter;
    RunData allRunData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        runList = (ListView) findViewById(R.id.listView);

        allRunData = ((RunData) getApplicationContext());

    }

    @Override
    protected void onResume() {
        allRunData = ((RunData) getApplicationContext());

        ArrayAdapter adapter = new RunListAdapter(this, R.layout.item_user);
        runList.setAdapter(adapter);
        adapter.addAll(allRunData.getHoldRuns());
        runList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                allRunData.setRecordToOpen(pos);
                startActivity(new Intent(getApplicationContext(),HistoryMore.class));
            }
        });
        super.onResume();
    }
}
