



package com.example.student.hatsker;

        import java.util.Enumeration;
        import java.util.Properties;

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.ContentResolver;
        import android.content.Context;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.content.pm.PackageManager.NameNotFoundException;
        import android.content.res.Configuration;
        import android.graphics.Point;
        import android.location.LocationManager;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.net.NetworkInfo.State;
        import android.os.Build;
        import android.os.Environment;
        import android.os.Looper;
        import android.os.PowerManager;
        import android.provider.Settings;
        import android.text.TextUtils;
        import android.view.Display;


public class GetInfo {
    public static String getInfosAboutDevice(Activity a) {
        String s = "";
        try {
            PackageInfo pInfo = a.getPackageManager().getPackageInfo(
                    a.getPackageName(), PackageManager.GET_META_DATA);
            s += "\n APP Package Name: " + a.getPackageName();
            s += "\n APP Version Name: " + pInfo.versionName;
            s += "\n APP Version Code: " + pInfo.versionCode;
            s += "\n";
        } catch (NameNotFoundException e) {
        }
        s += "\n OS Version: " + System.getProperty("os.version") + " ("
                + android.os.Build.VERSION.INCREMENTAL + ")";
        s += "\n OS API Level: " + android.os.Build.VERSION.SDK;
        s += "\n Device: " + android.os.Build.DEVICE;
        s += "\n Model (and Product): " + android.os.Build.MODEL + " ("
                + android.os.Build.PRODUCT + ")";
        // TODO add application version!

        // more from
        // http://developer.android.com/reference/android/os/Build.html :
        s += "\n Manufacturer: " + android.os.Build.MANUFACTURER;
        s += "\n Other TAGS: " + android.os.Build.TAGS;

        s += "\n screenWidth: "
                + a.getWindow().getWindowManager().getDefaultDisplay()
                .getWidth();
        s += "\n screenHeigth: "
                + a.getWindow().getWindowManager().getDefaultDisplay()
                .getHeight();
        s += "\n Keyboard available: "
                + (a.getResources().getConfiguration().keyboard != Configuration.KEYBOARD_NOKEYS);

        s += "\n Trackball available: "
                + (a.getResources().getConfiguration().navigation == Configuration.NAVIGATION_TRACKBALL);
        s += "\n SD Card state: " + Environment.getExternalStorageState();
        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        String key = "";
        while (keys.hasMoreElements()) {
            key = (String) keys.nextElement();
            s += "\n > " + key + " = " + (String) p.get(key);
        }
        return s;
    }
}