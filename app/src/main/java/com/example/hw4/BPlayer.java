package com.example.hw4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BPlayer implements Serializable{
    String idApp;
    String idBPlayer;
    String name;
    String number;
    String Comment;
    public ArrayList<TimeSlot> timeSlotList;

    public BPlayer(){
        idBPlayer = UUID.randomUUID().toString().replace("-", "");
        timeSlotList = new ArrayList<TimeSlot>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getIdBPlayer() {
        return idBPlayer;
    }

    public void setIdBPlayer(String idBPlayer) {
        this.idBPlayer = idBPlayer;
    }

    public ArrayList<TimeSlot> getTimeSlotList() {
        return timeSlotList;
    }

    public void setTimeSlotList(ArrayList<TimeSlot> timeSlotList) {
        this.timeSlotList = timeSlotList;
    }
}
