package com.example.drinkinggamesapp;

import java.util.Random;

public class Dices extends Game {
    public Random rand = new Random();

    public Dices(String[] players) {
        super(players);
        super.setMin_players(2);
        super.setMax_players(10);
        super.setName("Dices");
    }
}
