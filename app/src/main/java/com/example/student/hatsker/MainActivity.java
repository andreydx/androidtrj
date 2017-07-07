package com.example.student.hatsker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MainActivity this_=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button ServiceOnButton=(Button)findViewById(R.id.toService);
        final SMSreciever Res=new SMSreciever();
        ServiceOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Res.onReceive(this_, intent);
//                startService(new Intent(this_, MyService.class));

            }
        });
//        Button ServiceOffButton=(Button)findViewById(R.id.offService);
//        ServiceOffButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopService(new Intent(this_, MyService.class));
//            }
//        });
    }
}
