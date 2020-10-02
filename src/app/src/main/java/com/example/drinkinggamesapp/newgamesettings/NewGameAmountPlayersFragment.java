package com.example.drinkinggamesapp.newgamesettings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.drinkinggamesapp.R;

public class NewGameAmountPlayersFragment extends Fragment{



    private NumberPicker numberPicker;
    public NewGameAmountPlayersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_game_amount_players, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Tell activity that the fragment finished attaching
        NewGameSetupActivity a = (NewGameSetupActivity) getActivity();
        a.setCurrentFragmentFinishedAttaching(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // NumberPicker
        numberPicker = getView().findViewById(R.id.picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
    }
}