package com.example.alex.runtrak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;

public class MainScreen extends AppCompatActivity {

    Button goal, free, history, settings;
    File saveFile;
    RunData allRunData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        goal = (Button) findViewById(R.id.button2);
        free = (Button) findViewById(R.id.button4);
        history = (Button) findViewById(R.id.button5);
        settings = (Button) findViewById(R.id.button6);

        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainScreen.this);
                LayoutInflater inflater;
                inflater = LayoutInflater.from(MainScreen.this);
                final View dialoglayout = inflater.inflate(R.layout.paceinput, null);
                         builder
                        .setTitle("Set Pace (Mile/Minute)")
                        .setView(dialoglayout)
                        .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText temp = (EditText) dialoglayout.findViewById(R.id.editText);
                                allRunData.setPaceToRun(Double.parseDouble(temp.getText().toString()));
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                builder.show();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });

        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), History.class));
            }
        });

        saveFile = new File(getApplicationContext().getFilesDir(), "savefile");
        allRunData = ((RunData) getApplicationContext());
        if(allRunData.getHoldRuns() == null){
            allRunData.setup(saveFile);
        }
    }

    public void onClickFree(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onClickHistory(View v){
        startActivity(new Intent(this, History.class));
    }
}
