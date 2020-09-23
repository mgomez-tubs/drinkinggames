package com.example.drinkinggamesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    RoundController roundController = null;
    TextView currentRoundTextView = null;
    int amount_of_rounds;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Receive Intent
        amount_of_rounds = getIntent().getExtras().getInt("amount_players");

        // Create Round Controller
        roundController = new RoundController(amount_of_rounds);
        findViewById(R.id.button_next).setOnClickListener(this);

        currentRoundTextView = findViewById(R.id.current_round);
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            mToast.cancel();
            startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));     // The last flag is to clear the stacked activities
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
            case R.id.button_next:
                roundController.nextRound();
                currentRoundTextView.setText(roundController.roundString());
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }
}

class RoundController{
    private int numberOfRounds;
    private int current_round;

    public RoundController(int numberOfRounds){
         this.numberOfRounds = numberOfRounds;
         current_round = 1;
    }

    public int getCurrent_round() {
        return current_round;
    }
    public void nextRound(){
        if(current_round<=numberOfRounds){
            this.current_round+=1;
        } else {
            // do nuffin
        }
    }

    public String roundString(){
        if(current_round<=numberOfRounds) {
            return "Round " + this.current_round;
        } else {
            return "GAME OVER NO MORE ROUNDS 😦" ;
        }
    }
}