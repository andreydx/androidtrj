package com.example.student.hatsker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.util.Log;

import java.util.List;

/**
 * Created by Игорь on 25.07.2017.
 */

public class InstalledAppsGatherer implements Gatherer {




    @Override
    public String getInfo(Context context) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Installed Apps:\n");

        List<PackageInfo> packList = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packList.size(); i++) {
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = packInfo.applicationInfo.loadLabel
                        (context.getPackageManager()).toString();

                stringBuilder.append("App № " + Integer.toString(i) + " " + appName + "\n");

            }
        }
        Log.i("asd",stringBuilder.toString());
        return stringBuilder.toString();
    }
}
