package com.example.drinkinggamesapp;

import java.util.Random;

public class Dices extends Game {
    private Random rand = new Random();

    public Dices() {
        super.setMin_players(1);
        super.setMax_players(1);
        super.setName("Dices");
    }

    public int roll(){
        return rand.nextInt(6) + 1;
    }
}