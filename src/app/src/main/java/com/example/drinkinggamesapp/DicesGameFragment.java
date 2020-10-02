package com.example.drinkinggamesapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class DicesGameFragment extends Fragment implements View.OnClickListener{

    public DicesGameFragment() {
        // Required empty public constructor
    }
    Dices dices;

    // Interface field
    public OnGameEventListener mListener;                          // Declare custom event listener, so we can call it anytime

    // Listener implementation. This function is called from the activity not from here.
    public void setGameEventListener(OnGameEventListener gameEvent){
        this.mListener = gameEvent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dices = new Dices();
        MaterialButton b = getActivity().findViewById(R.id.button_next_round);
        b.setEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dices_game, container, false);
    }

    Button b;
    MaterialTextView tv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b = view.findViewById(R.id.button_roll);
        tv = getView().findViewById(R.id.viewnumber);
        b.setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setGameEventListener((OnGameEventListener) getActivity());  // this isnt pretty but it does the job
                                                                    // should be in the activity according to https://developer.android.com/training/basics/fragments/communicating
                                                                    // but you cant do that with navigation controller.


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_roll:
                tv.setText(String.valueOf(dices.roll()));

                // Disable Button until next round
                b.setEnabled(false);
                // Send the signal
                mListener.onRoundFinished();
                break;
        }
    }
}