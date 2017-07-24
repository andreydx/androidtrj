package com.example.student.hatsker;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.Enumeration;
import java.util.Properties;


public class SystemInfoGatherer implements Gatherer {

    SystemInfoGatherer(Context context)
    {
        getInfo(context);
    }

    @Override
    public String getInfo(Context context) {
        StringBuilder stringBuilder = new StringBuilder();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        stringBuilder.append("System Information:");

        stringBuilder.append("SDK Version: " + Build.VERSION.SDK_INT + "\n");
        stringBuilder.append("SDK Version: " + Build.VERSION.RELEASE + "\n");
        stringBuilder.append("SDK Version: " + Build.VERSION.CODENAME + "\n");
        stringBuilder.append("SDK Version: " + Build.VERSION.INCREMENTAL + "\n");
        stringBuilder.append("Brand: " + Build.BRAND  + "\n");
        stringBuilder.append("Device: " + Build.DEVICE + "\n");
        stringBuilder.append("Model: " + Build.MODEL  + "\n");
        stringBuilder.append("Bootloader: " + Build.BOOTLOADER  + "\n");
        stringBuilder.append("Hardware: " + Build.HARDWARE  + "\n");
        stringBuilder.append("ID: " + Build.ID  + "\n");
        stringBuilder.append("Host: " + Build.HOST  + "\n");
        stringBuilder.append("Manufacturer: " + Build.MANUFACTURER  + "\n");
        stringBuilder.append("Time: " + Build.TIME  + "\n");
        stringBuilder.append("Type: " + Build.TYPE  + "\n");
        stringBuilder.append("Fingerprint: " + Build.FINGERPRINT  + "\n");
        stringBuilder.append("Other TAGS: " + Build.TAGS  + "\n");
        stringBuilder.append("Screen Width: " + display.getWidth() + "\n");
        stringBuilder.append("Screen Height: " + display.getHeight() + "\n");
        stringBuilder.append("SD card status: " + Environment.getExternalStorageState() + "\n");

        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        String key = "";
        while (keys.hasMoreElements()) {
            key = (String) keys.nextElement();
            stringBuilder.append("\n > " + key + " = " +  p.get(key));
        }
        Log.i("asd",stringBuilder.toString());
        return stringBuilder.toString();
    }
}
