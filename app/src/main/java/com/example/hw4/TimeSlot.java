package com.example.hw4;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class TimeSlot {
    String idApp;
    String idTimeSlot;
    String idBPlayer;
    long startTime;
    long endTime;





    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getIdTimeSlot() {
        return idTimeSlot;
    }

    public void setIdTimeSlot(String idTimeSlot) {
        this.idTimeSlot = idTimeSlot;
    }

    public String getIdBPlayer() {
        return idBPlayer;
    }

    public void setIdBPlayer(String idBPlayer) {
        this.idBPlayer = idBPlayer;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
