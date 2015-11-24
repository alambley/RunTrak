package Run;

import android.location.Location;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Run {

    private ArrayList<RunBit> runBitCollection;
    private int month, day, year, hour, min, sec;
    private long time;
    private double distance, pace;

    public Run(){
        runBitCollection = new ArrayList<>();
        runBitCollection.clear();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        sec = cal.get(Calendar.SECOND);
    }

    public void addRunBit(RunBit e){
        if (e != null){
            runBitCollection.add(e);
        }
    }

    public void finish(){
        if(runBitCollection.size() > 1){
            float tempdistance = 0;
            for(int counter = 0; counter < runBitCollection.size() - 1; counter++){
                float[] tempArray = new float[4];
                Location.distanceBetween(runBitCollection.get(counter).getLatitude(),runBitCollection.get(counter).getLongitude(),
                        runBitCollection.get(counter+1).getLatitude(),runBitCollection.get(counter+1).getLongitude(),tempArray);
                tempdistance += tempArray[0];
            }
            distance = tempdistance * 0.000621371;
            time = (runBitCollection.get(runBitCollection.size()-1).getTime() - runBitCollection.get(0).getTime()) / 1000;
            pace = ((double)time / 60) / distance;
        }
    }

    public int getRunBitCollectionSize() {
        return runBitCollection.size();
    }


    public double getDistance() {
        return distance;
    }


    public long getTime() {
        return time;
    }


    public double getPace() {
        return pace;
    }

    public String getNeatTime(){
        int tempsec = 0;
        int tempmin = 0;
        int temphour = 0;
        if(time < 60){
            tempsec = (int)time;
        }else if(time < 3600){
            tempmin = (int)time / 60;
            tempsec = (int)time - 60 * tempmin;
        }else{
            temphour = (int)time / 3600;
            tempmin = (int)time - 3600 * temphour / 60;
            tempsec = (int)time - 3600 * temphour - 60 * tempmin;
        }
        return String.format("%02d:%02d:%02d", temphour, tempmin, tempsec);
    }

    public String getNeatDistance(){
        return String.format("%.2f", distance);
    }

    public String getNeatPace(){
        int tempmin = 0;
        int tempsec = 0;
        double intermediate = 0;
        tempmin = (int)pace;
        intermediate = pace - tempmin;
        intermediate = intermediate * 60;
        intermediate = Math.round(intermediate);
        tempsec = (int)intermediate;
        return String.format("%02d:%02d",tempmin,tempsec);
    }

   /* //NEED TO FIX DISTANCE IF YOU WANT TO USE THIS
   public String getNeatCurrentPace(){
        if(runBitCollection.size() < 2){
            return String.format("%02d:%02d",0,0);
        }else{
            double tempPace, tempDistance;
            long tempTime;
            tempDistance = runBitCollection.get(runBitCollection.size()-1).distanceTo(runBitCollection.get(runBitCollection.size()-2)) * 0.000621371;
            tempTime = (runBitCollection.get(runBitCollection.size()-1).getTime() - runBitCollection.get(runBitCollection.size()-2).getTime()) / 1000;
            tempPace = ((double)tempTime / 60) / tempDistance;
            int tempmin = 0;
            int tempsec = 0;
            double intermediate = 0;
            tempmin = (int)tempPace;
            intermediate = tempPace - tempmin;
            intermediate = intermediate * 60;
            intermediate = Math.round(intermediate);
            tempsec = (int)intermediate;
            return String.format("%02d:%02d",tempmin,tempsec);
        }

    }*/

    public String toString(){
        return String.format("%02d/%02d/%04d %02d:%02d:%02d %.2f Miles", month, day, year, hour, min, sec, distance);
    }

    public String getDate(){
        return String.format("%02d/%02d/%04d",month,day,year);
    }

    public String getStartTime(){
        return String.format("%02d:%02d:%02d",hour,min,sec);
    }

    public ArrayList<RunBit> getRunBitCollection() {
        return runBitCollection;
    }
}
