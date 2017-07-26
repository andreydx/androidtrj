package com.example.student.hatsker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by student on 7/26/17.
 */

public class ReceiverManager {

    private final String REGISTER = "on";
    private final String UNREGISTER = "off";
    public SMSreciever smsReciever;


    ReceiverManager(String cmd, Context context)
    {
        smsReciever = new SMSreciever();

        switch(cmd)
        {
            case REGISTER: registerReceiver(smsReciever, context, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
                break;
            case UNREGISTER: unregisterReceiver(smsReciever, context);
                break;
        }
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, Context context, IntentFilter intentFilter)
    {
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver, Context context)
    {
        context.unregisterReceiver(broadcastReceiver);
    }

}
