package com.example.student.hatsker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by student on 7/26/17.
 */

public class GpsInfoGatherer implements Gatherer {

    private final Context context;

    GpsInfoGatherer(Context context)
    {
        this.context = context;
    }

    @Override
    public String getInfo() {

        LocationManager locationManager;
        String mprovider;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission
                    (context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return "Permission denied";
            }
            Location location = locationManager.getLastKnownLocation(mprovider);

            //locationManager.requestLocationUpdates(mprovider, 15000, 1, context.);

            if (location != null)
                onLocationChanged(location);
            else
                return "No Location Provider Found Check Your Code";
        }
        return "sf";
    }


    public String onLocationChanged(Location location) {
        return "GPS.longitude: " + Double.toString(location.getLongitude()) +
                "\nGPS.latitude: " +  Double.toString(location.getLatitude());
    }
    //http://startandroid.ru/ru/uroki/vse-uroki-spiskom/291-urok-138-opredelenie-mestopolozhenija-gps-koordinaty.html


}
