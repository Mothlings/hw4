package com.example.hw4;

import java.util.ArrayList;

public class Matches {
    public ArrayList<Match> matches;


    public Matches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public Matches() {
        this.matches = new ArrayList<Match>();
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }
}
