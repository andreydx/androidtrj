package com.example.student.hatsker;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager {

    File tempFile;
    File path = Environment.getExternalStorageDirectory();
    String fileName = "textfile.txt";


    private final String DELETE = "detete";
    private final String REWRITE = "rewrite";
    private final String APPEND = "append";

    public FileManager(String cmd, Context context)
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
            case DELETE: delete();
                break;
            case REWRITE: rewrite(new GathererBuilder().build(cmdForNext, context).getInfo());
                break;
            case APPEND: append(new GathererBuilder().build(cmdForNext, context).getInfo());
                break;
        }
    }

    public void rewrite(String string){

        tempFile = new File(path + "/" + fileName);

        FileWriter writer;
        try {

            writer = new java.io.FileWriter(tempFile);
            writer.write(string);
            writer.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void delete()
    {
        tempFile = new File(path + "/" + fileName);
        tempFile.delete();
    }

    public void append(String string)
    {
        tempFile = new File(path + "/" + fileName);

        FileWriter writer;
        FileReader fileReader;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        String strLine="";

        try {

            writer = new java.io.FileWriter(tempFile);
            fileReader = new FileReader(tempFile);
            bufferedReader = new BufferedReader(fileReader);
            stringBuilder = new StringBuilder();

            while ((strLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(strLine);
                Log.i("Ffda", stringBuilder.toString());
            }

            Log.i("Ssda", stringBuilder.toString());

            stringBuilder.append("\n" + string);


            writer.write(stringBuilder.toString());
            writer.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }


}
















