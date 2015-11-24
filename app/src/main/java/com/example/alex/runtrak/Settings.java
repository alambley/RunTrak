package com.example.alex.runtrak;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Run.Run;

public class Settings extends AppCompatActivity {

    Button clearHistory;
    RunData allRunData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        allRunData = ((RunData) getApplicationContext());

        clearHistory = (Button) findViewById(R.id.button8);
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Are you sure you want to delete all run history?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allRunData.getHoldRuns().clear();
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
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }
}
