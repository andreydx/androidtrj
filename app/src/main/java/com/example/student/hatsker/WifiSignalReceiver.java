package com.example.student.hatsker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by student on 7/21/17.
 */

public class WifiSignalReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent)
    {
        new Toast(context).makeText(context,"Wifi Status Changed", Toast.LENGTH_SHORT).show();
    }


}
