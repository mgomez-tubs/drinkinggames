package com.example.drinkinggamesapp;

public class Game {
    // Players
    String[] players;

    public int getMin_players() {
        return min_players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public void setMin_players(int min_players) {
        this.min_players = min_players;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int min_players;
    int max_players;
    String name;

    public Game(){
    }
}