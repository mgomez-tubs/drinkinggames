package com.example.drinkinggamesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NewGameSetupActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout linearLayout;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_setup);
        linearLayout = (LinearLayout) findViewById(R.id.layout_indicator);
        navController = (NavController) Navigation.findNavController(this, R.id.fragment_new_game);

        // Add shapes
        addShape();
        addShape();
        addShape();
        addShape();

        findViewById(R.id.button_next).setOnClickListener(this);

        switch (navController.getCurrentDestination().getId()){
            case R.id.newGamePlayersFragment:
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
        Log.d("addShape","this");
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
        switch(v.getId()){
            case R.id.button_next:
                // Switch between destinations, depending on the current Fragment (we are controlling the fragments from the Activity, therefore we need to do it like this)
                switch (navController.getCurrentDestination().getId()){
                    case R.id.newGamePlayersFragment:
                        navController.navigate(R.id.action_newGamePlayersFragment_to_newGamePlayersNamesFragment);
                        Log.d("Switch Block","Currently in New Game Players Fragment");
                        break;
                    case R.id.newGamePlayersNamesFragment:
                        navController.navigate(R.id.action_newGamePlayersNamesFragment_to_newGameSelectGames);
                        Log.d("Switch Block","Currently in NewGame Players Names Fragment");
                        break;
                    case R.id.newGameSetup:
                        Log.d("Switch Block","Currently in NewGame Setup");
                        break;
                }
        }
    }
}