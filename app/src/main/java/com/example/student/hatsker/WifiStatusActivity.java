package com.example.student.hatsker;

import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class WifiStatusActivity extends AppCompatActivity {

    boolean onOff=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_status);
    }



    protected void onStart()
    {
        super.onStart();



        final TextView textView = (TextView)findViewById(R.id.wifiRecStatus);
        final Button onWifiRec = (Button)findViewById(R.id.onWifi);
        final Button offWifiRec = (Button)findViewById(R.id.offWifi);
        final Button getNets = (Button)findViewById(R.id.getNetworks);

        final WifiSignalReceiver wifiSignalReceiver = new WifiSignalReceiver();
        final IntentFilter intentFilter = new IntentFilter("android.net.wifi.WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION");

        onWifiRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!onOff)
                {
                    registerReceiver(wifiSignalReceiver,intentFilter);
                    onOff = true;
                    textView.setText("On");
                }
            }
        });

        offWifiRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOff)
                {
                    unregisterReceiver(wifiSignalReceiver);
                    onOff = false;
                    textView.setText("Off");
                }
            }
        });

        getNets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
                List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();

                Log.i("Wifi", configs.toString());
            }
        });





    }




}
