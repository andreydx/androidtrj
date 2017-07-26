package com.example.student.hatsker;

import android.content.Context;

/**
 * Created by student on 7/26/17.
 */

public class DecisionMaker {

    private final String RECEIVER_MANAGER = "receiverManager";
    private final String FILE_MANAGER = "fileManager";

    public void makeDecision(Context context, String cmd)
    {
        switch(cmd)
        {
            case RECEIVER_MANAGER: callReceiverManager(context, cmd);
                break;
            case FILE_MANAGER: callFileManager(cmd, context);
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


}
