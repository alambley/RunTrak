package com.example.alex.runtrak;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import Run.*;


public class MainActivity extends AppCompatActivity {

    Run tempRun;
    LocationManager locationManager;
    TextView showdistance,showsize,showtime,showpace,showtestpace,GOAL,DIFFY,Inspire;
    SharedPreferences prefs;
    Boolean go, done;
    File saveFile;
    RunData allRunData;
    Button startfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        done = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempRun = new Run();
        showsize = (TextView) findViewById(R.id.textView);
        showtime = (TextView) findViewById(R.id.textView2);
        showdistance = (TextView) findViewById(R.id.textView3);
        startfinish = (Button) findViewById(R.id.button);
        showpace = (TextView) findViewById(R.id.textView4);
        showtestpace = (TextView) findViewById(R.id.textView15);
        GOAL = (TextView) findViewById(R.id.textView19);
        DIFFY = (TextView) findViewById(R.id.textView20);
        Inspire = (TextView) findViewById(R.id.textView21);

        allRunData = ((RunData) getApplicationContext());

        if(allRunData.getPaceToRun() == 0){
            showpace.setText("");
            showtestpace.setText("");
            GOAL.setText("");
            DIFFY.setText("");
            Inspire.setText("");
        }else{
            showpace.setText(Double.toString(allRunData.getPaceToRun()));
        }


        saveFile = new File(getApplicationContext().getFilesDir(), "savefile");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean initGo = prefs.getBoolean("go", false);
        if(initGo){
            go = true;
        }else{
            go = false;
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListener);
        }catch (SecurityException e){

        }
    }

      private LocationListener locationListener = new LocationListener()
    {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            startfinish.setClickable(true);
            startfinish.setAlpha(1);
            if(go){
                tempRun.addRunBit(new RunBit(location));
                tempRun.finish();
                printChange();
            }
            if(done){
                try {
                    locationManager.removeUpdates(locationListener);
                    locationManager = null;
                }catch(SecurityException e){}
            }
        }
    };

    public void onClickAdd(View e){
        if(go){
            go = false;
            done = true;
            allRunData.getHoldRuns().add(tempRun);
            tempRun = new Run();
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            String json = gson.toJson( allRunData.getHoldRuns(), new TypeToken<ArrayList<Run>>(){}.getType());
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                PrintWriter writer = new PrintWriter(saveFile);
                writer.write(json);
                writer.close();
                Toast.makeText(getApplicationContext(), "Saved Run in History", Toast.LENGTH_SHORT).show();
            }catch(IOException f){
                Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_SHORT).show();
            }
            allRunData.setPaceToRun(0);
            finish();
        }else{
            go = true;
        }
    }

    public void onClickEnd(View e){
        if(!go){
            tempRun = new Run();
            printChange();
        }
    }

    public void onClickSave(View e){
        if(!go){
            allRunData.getHoldRuns().add(tempRun);
            tempRun = new Run();
            printChange();
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            String json = gson.toJson( allRunData.getHoldRuns(), new TypeToken<ArrayList<Run>>(){}.getType());
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                PrintWriter writer = new PrintWriter(saveFile);
                writer.write(json);
                writer.close();
                Toast.makeText(getApplicationContext(), "Saved Run in History", Toast.LENGTH_SHORT).show();
            }catch(IOException f){
                Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("go", go);
        editor.apply();
        done = true;
        super.onDestroy();
    }

    public void printChange(){
        showsize.setText(tempRun.getNeatTime());
        showtime.setText(tempRun.getNeatDistance());
        showdistance.setText(tempRun.getNeatPace());
        if(allRunData.getPaceToRun() != 0){
            double test = ((allRunData.getPaceToRun() - tempRun.getPace()) / allRunData.getPaceToRun()) * 100;
            if(test > 1000){
                showtestpace.setText("1000%+");
            }else if(test < -1000){
                showtestpace.setText("-1000%+");
            }else{
                String ayy = String.format("%.1f", test);
                ayy = ayy.concat("%");
                showtestpace.setText(ayy);
            }
            if(test >= 0){
                showtestpace.setTextColor(Color.parseColor("#00FF00"));
                Inspire.setText("KEEP UP THE PACE");
            }
            else{
                showtestpace.setTextColor(Color.parseColor("#FF0000"));
                Inspire.setText("SPEED UP");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}







