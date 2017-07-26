package com.example.student.hatsker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPanel extends AppCompatActivity {

    Context this_ = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_panel);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final EditText cmdLine = (EditText)findViewById(R.id.cmdLine);
        cmdLine.setText("1");
        Button enter = (Button)findViewById(R.id.enter);

        Receivers.invokeAllReceivers();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DecisionMaker().makeDecision(this_, cmdLine.getText().toString());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
