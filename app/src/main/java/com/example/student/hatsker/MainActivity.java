package com.example.student.hatsker;

import android.content.ContentResolver;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    boolean isRegistered = false;
    int duration = Toast.LENGTH_SHORT;
    ExecutorService executorService;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final MainActivity this_=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executorService= Executors.newFixedThreadPool(5);


        Button ServiceOnButton=(Button)findViewById(R.id.toService);

        final SMSreciever Res=new SMSreciever();

        final IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        Button Sng=(Button)findViewById(R.id.SongsButton);
        Sng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mediaPlayer=new MediaPlayer();
                ContentResolver contentResolver=getContentResolver();
                Uri songUri= MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
                Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
                if (songCursor!=null && songCursor.moveToFirst())
                {
                    int songTitle=songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                    do {
//                        String songPath=songUri.getPath();
//                        try {
//                            mediaPlayer.setDataSource(songPath);
//                            mediaPlayer.start();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        String currentTitle=songCursor.getString(songTitle);
                        Log.d("hfgjg", currentTitle);
                    } while (songCursor.moveToNext());
                }

            }
        });





        ServiceOnButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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

        ServiceOffButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isRegistered)
                {
                    unregisterReceiver(Res);
                    isRegistered = false;
                    TextView offOn = (TextView)findViewById(R.id.offOn);
                    offOn.setText("Off");
                }
                else
                    Toast.makeText(this_, "Receiver is already off", duration).show();
            }
        });

        final Button getF=(Button)findViewById(R.id.FileGetter);

        getF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                GetFiles(path);
            }
        });

        Button getCon = (Button)findViewById(R.id.getContacts);

        getCon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String phoneNumber = null;
                Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
                String _ID = ContactsContract.Contacts._ID;
                String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
                String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

                Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;


                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

                if (cursor.getCount() > 0)
                {
                    while (cursor.moveToNext())
                    {
                        String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                        String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                        int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                        if (hasPhoneNumber > 0)
                        {
                            Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                            while (phoneCursor.moveToNext())
                            {
                                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                                Log.d ("hfgjg", name + " " + phoneNumber);
                            }
                        }
                    }
                }
            }
        });
    }

    public void GetFiles(String DirectoryPath) {
        Log.d ("hfgjg", DirectoryPath);
        File f = new File(DirectoryPath);
        f.mkdirs();
        File[] files = f.listFiles();
        if (files!=null)
        {
            for (int i = 0; i < files.length; i++)
            {
                Log.d("hfgjg", files[i].getAbsolutePath());
                if (files[i].isDirectory()) GetFiles(files[i].getAbsolutePath());
            }
        }
    }

    String path = android.os.Environment.;
}