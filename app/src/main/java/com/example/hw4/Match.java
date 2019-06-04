package com.example.hw4;
import java.util.ArrayList;
import java.util.UUID;

public class Match {
    public BPlayer player;
    String idMatch;

    public Match() {
        this.idMatch = UUID.randomUUID().toString().replace("-", "");;
        player = new BPlayer();
    }

    public BPlayer getPlayers() {
        return player;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setPlayers(BPlayer players) {
        this.player = players;
    }

    @Override
    public String toString() {
        return idMatch;
    }
}

