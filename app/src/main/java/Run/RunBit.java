package Run;

import android.location.Location;

/**
 * Created by Alex on 11/3/2015.
 */
public class RunBit {
    private double latitude;
    private double longitude;
    private long time;

    public RunBit(Location e){
        latitude = e.getLatitude();
        longitude = e.getLongitude();
        time = e.getTime();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getTime() {
        return time;
    }
}
