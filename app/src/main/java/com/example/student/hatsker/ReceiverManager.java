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

        String cmdHere="";
        for (int i = 0; i < cmd.length(); i++) {
            if (cmd.charAt(i) == ' ')
            {
                cmdHere = cmd.substring(0, i);
                break;
            }
        }
        switch(cmdHere)
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
