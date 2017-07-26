package com.example.student.hatsker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by student on 7/26/17.
 */

public class ReceiverManager {

    private final String REGISTER = "register";
    private final String UNREGISTER = "unregister";


    ReceiverManager(String cmd, Context context)
    {

        String cmdHere="";
        String cmdForNext="";

        for (int i = 0; i < cmd.length(); i++) {
            if (cmd.charAt(i) == ' ')
            {
                cmdHere = cmd.substring(0, i);
                cmdForNext = cmd.substring(i+1);
                break;
            }
        }

        switch(cmdHere)
        {
            case REGISTER: registerReceiver(Receivers.getReceiver(cmdForNext), context, Receivers.getIntentFilter(cmdForNext));
                break;
            case UNREGISTER: unregisterReceiver(Receivers.getReceiver(cmdForNext), context);
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
