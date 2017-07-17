package com.example.student.hatsker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    boolean isRegistered = false;
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MainActivity this_=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button ServiceOnButton=(Button)findViewById(R.id.toService);

        final SMSreciever Res=new SMSreciever();

        final IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");


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

                        Log.i("DATA"," ID "+cursor.getString(0)+" NAME "+cursor.getString(1)+" PHONE "+cursor.getString(2));
                    }
                }
            }
        });


        Button getFile = (Button) findViewById(R.id.getFile);

        getFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                    startManagingCursor(cursor);

                    if (cursor.getCount() > 0)
                    {
                        while (cursor.moveToNext())
                        {

                            /** Getting Cache Directory */
                            File tempFile;
                            File cDir = getBaseContext().getCacheDir();

                            /* Makes a textfile in the absolute cache directory  */
                            tempFile = new File(cDir.getPath() + "/" + "textFile.txt") ;

                            /* Writing into the created textfile */
                            FileWriter writer=null;
                            try {
                                writer = new FileWriter(tempFile);
                                writer.write(" ID "+cursor.getString(0)+" NAME "+cursor.getString(1)+" PHONE "+cursor.getString(2));
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            String strLine="";
                            StringBuilder text = new StringBuilder();
                            try {
                                FileReader fReader = new FileReader(tempFile);
                                BufferedReader bReader = new BufferedReader(fReader);

                                while( (strLine=bReader.readLine()) != null  ){
                                    Log.i("DATA", strLine);
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }catch(IOException e){
                                e.printStackTrace();
                            }


                            //Log.i("DATA"," ID "+cursor.getString(0)+" NAME "+cursor.getString(1)+" PHONE "+cursor.getString(2));
                        }
                    }
                }

        });




    }

}
