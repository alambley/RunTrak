package com.example.alex.runtrak;

import android.app.Application;
import android.util.Log;
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

import Run.Run;

public class RunData extends Application{
    private ArrayList<Run> holdRuns;
    private int recordToOpen;
    private double paceToRun;

    public RunData(){
        paceToRun = 0;
    }

    public void setup(File e){
        paceToRun = 0;
        if(holdRuns == null){
            holdRuns = new ArrayList<>();
        }
        Gson gson = new Gson();
        Type listOfTestObject = new TypeToken<ArrayList<Run>>(){}.getType();
        String grabData = "";
        StringBuilder b = new StringBuilder();
        long test = System.currentTimeMillis();
        try{
            BufferedReader in = new BufferedReader(new FileReader(e));
            String line = in.readLine();
            while(line != null){
                b.append(line);
                line = in.readLine();
            }
            grabData = b.toString();
            Log.d("Load Time Hunt",".json string loaded in " + (System.currentTimeMillis() - test) + " ms.");
            test = System.currentTimeMillis();
            holdRuns = gson.fromJson(grabData, listOfTestObject);
            Log.d("Load Time Hunt",".json deserialized in " + (System.currentTimeMillis() - test) + " ms.");
        }catch(FileNotFoundException f){
            holdRuns = new ArrayList<>();
        }catch(IOException f){

        }
    }

    public ArrayList<Run> getHoldRuns() {
        return holdRuns;
    }

    public int getRecordToOpen() {
        return recordToOpen;
    }

    public void setRecordToOpen(int recordToOpen) {
        this.recordToOpen = recordToOpen;
    }

    public double getPaceToRun() {
        return paceToRun;
    }

    public void setPaceToRun(double paceToRun) {
        this.paceToRun = paceToRun;
    }
}
