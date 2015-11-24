package com.example.alex.runtrak;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Run.*;

public class HistoryMore extends AppCompatActivity {

    TextView showDate, showStart, showDistance, showTime, showPace;
    Button seeMap, delete;
    RunData allRunData;
    Run showThisData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_more);
        showDate = (TextView) findViewById(R.id.textView10);
        showStart = (TextView) findViewById(R.id.textView11);
        showDistance = (TextView) findViewById(R.id.textView12);
        showTime = (TextView) findViewById(R.id.textView13);
        showPace = (TextView) findViewById(R.id.textView14);
        seeMap = (Button) findViewById(R.id.button7);
        delete = (Button) findViewById(R.id.button3);

        seeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HistoryMore.this)
                        .setTitle("Are you sure you want to delete this run?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allRunData.getHoldRuns().remove(allRunData.getRecordToOpen());
                                File saveFile = new File(getApplicationContext().getFilesDir(), "savefile");
                                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                                String json = gson.toJson( allRunData.getHoldRuns(), new TypeToken<ArrayList<Run>>(){}.getType());
                                try {
                                    if (!saveFile.exists()) {
                                        saveFile.createNewFile();
                                    }
                                    PrintWriter writer = new PrintWriter(saveFile);
                                    writer.write(json);
                                    writer.close();
                                }catch(IOException f){
                                    Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

        allRunData = ((RunData) getApplicationContext());
        showThisData = allRunData.getHoldRuns().get(allRunData.getRecordToOpen());

        if(showThisData.getRunBitCollectionSize() < 1){
            seeMap.setClickable(false);
            seeMap.setAlpha(0.2f);
        }

        showDate.setText(showThisData.getDate());
        showStart.setText(showThisData.getStartTime());
        showDistance.setText(showThisData.getNeatDistance() + " Miles");
        showTime.setText(showThisData.getNeatTime());
        showPace.setText(showThisData.getNeatPace());
    }
}
