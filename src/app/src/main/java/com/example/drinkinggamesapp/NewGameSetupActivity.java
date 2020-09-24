package com.example.drinkinggamesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class NewGameSetupActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mLinearLayout;
    private NavController mNavController;
    private Intent mGameSetupOptions;
    private static final String AMOUNT_PLAYERS = "amount_players";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_setup);
        mLinearLayout = (LinearLayout) findViewById(R.id.layout_indicator);
        mNavController = (NavController) Navigation.findNavController(this, R.id.fragment_new_game);

        // Set up intent
        mGameSetupOptions = new Intent(this, GameActivity.class);

        // Add shapes and toggle first one
        addShapes();

        // Listen to when navController changes fragment
        mNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.newGameAmountPlayersFragment:
                        // Update Material Button
                        updateButtonNext();
                        updatePageIndicatorShapes(0);
                        break;
                    case R.id.newGamePlayersNamesFragment:
                        // Update Material Button
                        updateButtonNext();updatePageIndicatorShapes(1);
                        break;
                    case R.id.newGameSelectGames:
                        // Update Material Button
                        updateButtonNext();
                        updatePageIndicatorShapes(2);
                        break;
                    case R.id.newGameRoundsFragment:
                        // Update Material Button
                        updateButtonNext();
                        updatePageIndicatorShapes(3);
                        break;
                }
            }
        });

        findViewById(R.id.button_next).setOnClickListener(this);
        /*
         This Block is kept for future reference

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
        */
    }

    ImageView[] mPageIndicatorShape = null;

    private int pages = 4;
    private void addShapes(){
        mPageIndicatorShape = new ImageView[pages];
        for(int i = 0; i< pages;i++){
            mPageIndicatorShape[i] = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8,0,8,0);
            mPageIndicatorShape[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_inactive));
            mPageIndicatorShape[i].setLayoutParams(layoutParams);
            mLinearLayout.addView(mPageIndicatorShape[i]);
        }
    }

    public void updatePageIndicatorShapes(int currentPage){
        mPageIndicatorShape[currentPage].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_active));
        for(int i = 0 ; i < pages ; i++){
            if(i<currentPage||i>currentPage){
                mPageIndicatorShape[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_inactive));
            }
        }
    }

    public void updateButtonNext(){
        MaterialButton materialButton = findViewById(R.id.button_next);
        if(mNavController.getCurrentDestination().getId()==R.id.newGameRoundsFragment){
            materialButton.setText("Start Game");
        } else {
            materialButton.setText("Next");
        }
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
                switch (mNavController.getCurrentDestination().getId()){
                    case R.id.newGameAmountPlayersFragment:
                        // Pass amount of players
                        Bundle args = new Bundle();
                        NumberPicker picker = findViewById(R.id.picker);

                        // Pass options to the next fragment
                        args.putInt("amount_players", picker.getValue());

                        // Pass options to the game activity
                        mGameSetupOptions.putExtra("amount_players", picker.getValue());
                        Log.d("onClick", "Value sent:" + String.valueOf(picker.getValue()));

                        mNavController.navigate(R.id.action_newGameAmountPlayersFragment_to_newGamePlayersNamesFragment, args);
                        Log.d("Switch Block","Currently in New Game Players Fragment");
                        break;
                    case R.id.newGamePlayersNamesFragment:
                        mNavController.navigate(R.id.action_newGamePlayersNamesFragment_to_newGameSelectGames);
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
                            mNavController.navigate(R.id.action_newGameSelectGames_to_newGameRoundsFragment);
                        }
                        break;
                    case R.id.newGameRoundsFragment:

                        NumberPicker picker_rounds = findViewById(R.id.picker);
                        // Pass options to the game activity
                        mGameSetupOptions.putExtra("amount_rounds", picker_rounds.getValue());
                        //navController.navigate(R.id.action_newGameRoundsFragment_to_gameActivity);
                        startActivity(mGameSetupOptions);
                        break;
                }
        }
    }
}