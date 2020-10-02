package com.example.drinkinggamesapp.newgamesettings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drinkinggamesapp.R;

public class NewGameConfirmSettings extends Fragment {

    private int amount_players;
    private String[] player_name;
    private int amount_rounds;

    public NewGameConfirmSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get amount_players
        amount_players = getArguments().getInt("amount_players");

        // Get players names
        // Init array
        player_name = new String[amount_players];
        for(int i = 0; i<amount_players; i++){
            player_name[i] = getArguments().getStringArray("player_names")[i];
        }

        // Get amount rounds
        amount_rounds = getArguments().getInt("amount_rounds");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_game_confirm_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Display Amount Players
        TextView tv1 = getView().findViewById(R.id.textView_amount_players);
        tv1.setText(String.valueOf(amount_players));

        // Display Player names
        TextView tv2 = getView().findViewById(R.id.textView_player_names);
        String names = new String();
        names += player_name[0];
        if(amount_players>1){
            for(int i = 1 ; i< amount_players; i++){
                names+=", "+ player_name[i];
            }
        }
        tv2.setText(names);

        // Display Games
        TextView tv3 = getView().findViewById(R.id.textView_selected_games);
        tv3.setText("All Games");

        // Display Rounds
        TextView tv4 = getView().findViewById(R.id.textView_rounds);
        tv4.setText(String.valueOf(amount_rounds));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Tell activity that the fragment finished attaching
        NewGameSetupActivity a = (NewGameSetupActivity) getActivity();
        a.setCurrentFragmentFinishedAttaching(true);
    }

}