package com.example.student.hatsker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



public class OnBootStartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent newPanel = new Intent(context, NewPanel.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newPanel);
        }

    }

}
