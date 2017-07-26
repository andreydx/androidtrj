package com.example.student.hatsker;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * Created by Игорь on 26.07.2017.
 */

public class Receivers {
    private static SmsReceiver smsReceiver;
    private static IntentFilter smsIntentFilter;

    private static final String SMS_RECEIVER = "smsReceiver";

    public static void invokeAllReceivers() {
        smsReceiver = new SmsReceiver();
        smsIntentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    }

    public static BroadcastReceiver getReceiver(String cmd) {
        switch (cmd)
        {
            case SMS_RECEIVER: return smsReceiver;

            default: return null;
        }
    }

    public static IntentFilter getIntentFilter(String cmd)
    {
        switch(cmd)
        {
            case SMS_RECEIVER: return smsIntentFilter;

            default: return null;
        }
    }
}
