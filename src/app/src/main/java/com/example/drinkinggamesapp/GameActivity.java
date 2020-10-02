package com.example.drinkinggamesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkinggamesapp.mainscreen.MainActivity;
import com.google.android.material.button.MaterialButton;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, OnGameEventListener{

    private RoundController roundController;
    private GameController gameController;
    private TextView currentRoundTextView;
    private int amount_of_rounds;
    private Toast mToast;
    private NavController mNavController;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Receive Intent containing game settings
        amount_of_rounds = getIntent().getExtras().getInt("amount_rounds");

        // Create NavController
        mNavController = (NavController) Navigation.findNavController(this , R.id.fragment_game);

        // Create Game Controller
        gameController = new GameController(mNavController, 10);

        // Reference XML
        findViewById(R.id.button_next_round).setOnClickListener(this);
        currentRoundTextView = findViewById(R.id.current_round);
        currentRoundTextView.setText(gameController.roundString());

        // Set up Game Controller
        gameController = new GameController(mNavController,10);

        // Following 2 blocks handle the start of the game and should be moved together
        // Start Game
        //gameController.startGame();

        // Observe changes of round values
        if(gameController.getCurrent_round_mutable() == null ){
            Log.d("Changes","Not ready");
        } else {
            Log.d("Changes","Success");
            gameController.getCurrent_round_mutable().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    Log.d("observer","The value of the current round was changed!");
                    currentRoundTextView.setText(gameController.roundString());
                }
            });
        }

        // Listen to destination changes
        mNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.waitingForSetupFragment:
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //mNavController.navigate(R.id.action_waitingForSetupFragment_to_dicesGameFragment);
                                    //mNavController.navigate(R.id.dicesGameFragment);        // not entirely sure what this does, maybe it doesnt add the fragment to the bakstack. but surely it saves up some arrows. probably cant set an animation, but thats ok
                                    Log.d("Timed Fragment Change", "Successfully navigated to dices Fragment");
                                    gameController.startGame();

                                } catch (Exception e) {
                                    Log.d("Timer Fragment Change", "Didn't successfully navigate to dices Fragment");
                                }
                            }
                        }, 2500);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            mToast.cancel();
            startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));     // The last flag is to clear the stacked activities
            //super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        mToast = Toast.makeText(this, "Press back again to return to the main menu", Toast.LENGTH_SHORT);
        mToast.show();
        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_next_round:
                gameController.nextRound();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onRoundFinished() {
        /*
            The sole purpouse of this should be to re enable the next round button
         */
        Log.d("onRoundFinished", "Round was finished");
        MaterialButton b = findViewById(R.id.button_next_round);
        b.setEnabled(true);
        // gameController.nextRound();
    }
}

class RoundController implements OnGameEventListener{

    NavController mNavController;
    Game currentGame;
    public RoundController(NavController navController, Game game){
        this.mNavController = navController;
        this.currentGame = game;

    }

    public void start(){
        if(currentGame.name=="Dices"){
            Log.d("start","A new layout will be inflated.");
            mNavController.navigate(R.id.dicesGameFragment);
        }
    }

    @Override
    public void onRoundFinished() {
        Log.d("Round Controller","The round has finished!!!! Aaaaa");
    }
}


class GameController{
    private Game currentGame;
    private int participants;
    private WinConditions winCondition;
    private NavController mNavController;
    private RoundController roundController;

    /*
        Constructor
     */

    public GameController(NavController navController, int numberOfRounds){
        this.mNavController = navController;
        this.numberOfRounds = numberOfRounds;
        this.current_round = 1;                                              // Set the starting current round -> = 1
        this.current_round_mutable = new MutableLiveData<>(1);         // Set the starting current round

        // Set Current Game - afterwards this should be dynamically created
        //this.generateGameList();
        this.setCurrentGame(new Dices());
    }

    /*
            General round control
     */

    private int numberOfRounds;
    private int current_round;
    private MutableLiveData<Integer> current_round_mutable;             // use LiveData to monitor changes to the current round

    public MutableLiveData<Integer> getCurrent_round_mutable() {
        return current_round_mutable;
    }

    // Start Game
    public void startGame(){
        this.current_round = 1;                                              // Set the starting current round -> = 1
        this.current_round_mutable.setValue(1);         // Set the starting current round

        // Initialize First Round
        this.initRound(new Dices(),this.participants,0, 0);            // Instead of dices, game in current round
    }

    private int getCurrent_round() {
        return current_round;
    }


    public void nextRound(){
        if(current_round<=numberOfRounds){                               // next rounds
            // End last round
            Log.d("nextRound","Up the current round counter");
            this.endRound();
            this.current_round+=1;
            this.getCurrent_round_mutable().setValue(this.current_round);

            // Init next round
            this.initRound(new Dices(),this.participants,0, 0);
        } else {                                                                // Last round
            // do nuffin
        }
    }

    private void setCurrentGame(Game game){

    }

    public String roundString(){
        if(current_round<=numberOfRounds) {
            return "Round "+ this.current_round + "/ " + this.numberOfRounds;
        } else {
            return "😦 GAME OVER NO MORE ROUNDS 😦" ;
        }
    }

    /*
            Single round control
     */

    public void initRound(Game currentGame, int participants, int roundType, int winCondition) {
        Log.d("initRound", "Round " + this.current_round + " was initiated");

        this.currentGame = currentGame;
        this.participants = participants;

        this.roundController = new RoundController(this.mNavController, currentGame);

        this.roundController.start();       // This should also inflate the layout
    }

    private void endRound(){
        this.roundController = null;
    }

    /*
        Win conditions object
     */

    static class WinConditions{
        String[] winCondition = new String[]{
                "MAX_SCORE",
                "MIN_SCORE"
        };

        int winConditionID;

        public void set(int winConditionID) {
            this.winConditionID = winConditionID;
        }
    }
}