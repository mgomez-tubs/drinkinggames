package com.example.drinkinggamesapp.newgamesettings;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
/*
    Refer to:
    https://medium.com/mindorks/how-to-communicate-between-fragments-and-activity-using-viewmodel-ca733233a51c
    and
    https://developer.android.com/topic/libraries/architecture/viewmodel#java

    for a dynamic VIewModel (using MutableLiveData)

 */
public class MyViewModel extends ViewModel {

    private int testValue;
    private String[] mPlayerNames;

    // No mutations, simple implementation
    public void setValue(int v){
        this.testValue = v;
    }

    public void initmPlayerNames(int amount_players){

            this.mPlayerNames = new String[amount_players];
    }

    public void setmPlayerNames(String name, int index){
        this.mPlayerNames[index] = name;
    }

    public String[] getmPlayerNames(){
        for(int i = 0 ; i<this.mPlayerNames.length; i++){
            if(this.mPlayerNames[i] == null){
                this.mPlayerNames[i] = "Player " + i;
            };

        }
        return this.mPlayerNames;
    }


}