package com.example.drinkinggamesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NewGamePlayersNamesFragment extends Fragment {

    LinearLayout linearLayout;
    int amount_of_players;


    public NewGamePlayersNamesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amount_of_players =getArguments().getInt("amount_players");
        Log.d("Received", String.valueOf(amount_of_players));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_game_players_names, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("add Player","add player");

        linearLayout = (LinearLayout) getView().findViewById(R.id.midlayout);

        for(int i = 0 ; i < amount_of_players ; i++) {
            addPlayerToLayout(i+1);
        }
    }

    private void addPlayerToLayout(int order){
        // to convert to dps
        final float scale = getContext().getResources().getDisplayMetrics().density;

        EditText editText = new EditText(getActivity().getApplicationContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        editText.setWidth((int) (450*scale*0.5f));
        editText.setBackgroundResource(android.R.drawable.editbox_background);
        editText.setLayoutParams(layoutParams);
        editText.setLines(1);
        editText.setMinLines(1);
        editText.setMaxLines(1);
        editText.setHint("Player " + order);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        linearLayout.addView(editText);

    }
}