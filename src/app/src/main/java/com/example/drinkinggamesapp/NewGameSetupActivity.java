package com.example.drinkinggamesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class NewGameSetupActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout linearLayout;
    private NavController navController;
    private Intent gameSetupOptions;
    public static final String AMOUNT_PLAYERS = "amount_players";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_setup);
        linearLayout = (LinearLayout) findViewById(R.id.layout_indicator);
        navController = (NavController) Navigation.findNavController(this, R.id.fragment_new_game);

        // Set up intent
        gameSetupOptions = new Intent(this, GameActivity.class);

        // Add shapes
        addShape();
        addShape();
        addShape();
        addShape();

        findViewById(R.id.button_next).setOnClickListener(this);

        switch (navController.getCurrentDestination().getId()){
            case R.id.newGameAmountPlayersFragment:
                Log.d("Switch Block","Currently in New Game Players Fragment");
                break;
            case R.id.newGamePlayersNamesFragment:
                Log.d("Switch Block","Currently in NewGame Players Names Fragment");
                break;
            case R.id.newGameSetup:
                Log.d("Switch Block","Currently in NewGame Setup");
                break;
        }
    }

    private void addShape(){
        ImageView shape = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);
        shape.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_inactive));
        shape.setLayoutParams(layoutParams);
        linearLayout.addView(shape);
    }

    @Override
    public void onClick(View v) {
        /*  Transition between Fragments is controlled from this fragment
            All the logic lies in this switch statements
         */
        switch(v.getId()){
            case R.id.button_next:
                /*
                        Switch between destinations, depending on the current Fragment
                        (we are controlling the fragments from the Activity, therefore we need to do it like this)
                 */
                switch (navController.getCurrentDestination().getId()){
                    case R.id.newGameAmountPlayersFragment:
                        // Pass amount of players
                        Bundle args = new Bundle();
                        NumberPicker picker = findViewById(R.id.picker);

                        // Pass options to the next fragment
                        args.putInt("amount_players", picker.getValue());

                        // Pass options to the game activity
                        gameSetupOptions.putExtra("amount_players", picker.getValue());
                        Log.d("onClick", "Value sent:" + String.valueOf(picker.getValue()));

                        navController.navigate(R.id.action_newGameAmountPlayersFragment_to_newGamePlayersNamesFragment, args);
                        Log.d("Switch Block","Currently in New Game Players Fragment");
                        break;
                    case R.id.newGamePlayersNamesFragment:
                        navController.navigate(R.id.action_newGamePlayersNamesFragment_to_newGameSelectGames);
                        Log.d("Switch Block","Currently in NewGame Players Names Fragment");
                        break;
                    case R.id.newGameSelectGames:
                        Log.d("Switch Block","Currently in NewGame Setup");
                        SwitchMaterial switchMaterial = findViewById(R.id.switch_all_games);
                        Log.d("newGameSetup","Toast will be shown");
                        if(!switchMaterial.isChecked()){
                            Toast toast = Toast.makeText(getApplicationContext(), "You need to pick a game", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            navController.navigate(R.id.action_newGameSelectGames_to_newGameRoundsFragment);
                        }
                        break;
                    case R.id.newGameRoundsFragment:
                        NumberPicker picker_rounds = findViewById(R.id.picker);

                        // Pass options to the game activity
                        gameSetupOptions.putExtra("amount_rounds", picker_rounds.getValue());
                        //navController.navigate(R.id.action_newGameRoundsFragment_to_gameActivity);
                        startActivity(gameSetupOptions);
                        break;
                }
        }
    }
}