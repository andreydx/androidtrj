package com.example.student.hatsker;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.nfc.Tag;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    boolean isRegistered = false;
    int duration = Toast.LENGTH_SHORT;
    MainActivity act=this;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MainActivity this_=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executorService = Executors.newFixedThreadPool(4);


        Button ServiceOnButton=(Button)findViewById(R.id.toService);

        final SMSreciever Res=new SMSreciever();

        final IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        final GetInfo getInfo = new GetInfo();


        ServiceOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRegistered)
                {
                    registerReceiver(Res,intentFilter);
                    isRegistered=true;
                    TextView offOn = (TextView)findViewById(R.id.offOn);
                    offOn.setText("On");
                }
                else
                {
                    Toast.makeText(this_, "Receiver is already on", duration).show();
                }



            }
        });


        Button ServiceOffButton=(Button)findViewById(R.id.offService);

        ServiceOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegistered) {
                    unregisterReceiver(Res);
                    isRegistered = false;
                    TextView offOn = (TextView)findViewById(R.id.offOn);
                    offOn.setText("Off");
                } else
                    Toast.makeText(this_, "Receiver is already off", duration).show();
            }
        });

        Button getCon = (Button)findViewById(R.id.getContacts);

        getCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                startManagingCursor(cursor);

                if (cursor.getCount() > 0)
                {
                    while (cursor.moveToNext())
                    {
                        // process them as you want
                        Log.i("DATA"," ID "+cursor.getString(0)+" NAME "+cursor.getString(1)+" PHONE "+cursor.getString(2));
                    }
                }
            }
        });


        final Button getCallLog = (Button)findViewById(R.id.CallLg);

        getCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Call details: ",getCallDetails());
            }
        });

        final Button getSysInfo = (Button)findViewById(R.id.sysInfo);
        getSysInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("System info details: ",getInfo.getInfosAboutDevice(act));
                installedApps();
            }
        });



    }

    private String getCallDetails() {
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
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
            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                    + dir + " \nCall Date:--- " + callDayTime
                    + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
        }
        managedCursor.close();

        return sb.toString();


    }
    public void installedApps()
    {
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);        for (int i=0; i < packList.size(); i++)
        {
            PackageInfo packInfo = packList.get(i);
            if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Log.d("App № " + Integer.toString(i), appName);
            }
        }
    }
}
