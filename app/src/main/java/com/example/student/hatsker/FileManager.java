package com.example.student.hatsker;


import android.os.Environment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager {

    File tempFile;
    File path = Environment.getExternalStorageDirectory();


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
