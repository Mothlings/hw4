package com.example.hw4;

import android.app.Application;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import java.util.ArrayList;


public class MyApplication extends Application {
    public Matches myMatches;
    public String currentMatch;
    public static final String TAG = ActivityTimeSlot.class.getSimpleName();
    private String idApp;
    private Gson gson;
    private File file;

    //app.matches.getFirst();
    //app.myMatches.matches.getFirst();

    public MyApplication() {

    }

    public MyApplication(Matches matches) {
        this.myMatches = matches;
    }

    public void onCreate() {
        myMatches = new Matches();
        myMatches.matches= new ArrayList<Match>();
        super.onCreate();
    }

    public String getCurrentMatch() {
        return currentMatch;
    }

    public void setCurrentMatch(String currentMatch) {
        this.currentMatch = currentMatch;
    }

    private Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        return gson;
    }

    private File getFile() {
        if (file == null) {
            File filesDir = getFilesDir();
            file = new File(filesDir, "matches.json");
        }
        Log.i(TAG, file.getPath());
        return file;
    }

    public void saveToFile() {
        try {
            FileUtils.writeStringToFile(getFile(), getGson().toJson(myMatches));
        } catch (IOException e) {
            Log.d(TAG, "Can't save "+file.getPath());
        }
    }
    public boolean readFromFile() {
        if (!getFile().exists())  return false;
        try {
            myMatches = getGson().fromJson(FileUtils.readFileToString(getFile()) , Matches.class);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    public void save() {
        saveToFile();
    }

}
