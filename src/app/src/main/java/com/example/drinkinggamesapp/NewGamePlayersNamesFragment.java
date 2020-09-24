package com.example.drinkinggamesapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NewGamePlayersNamesFragment extends Fragment {

    private LinearLayout mLinearLayout;



    /*          //////////////////////          */
    /*
        Callback implementation
     */

    private OnCustomEventListener mListener;
    public interface OnCustomEventListener{
        public void onEvent(int event);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        /*
            Verify that the activity has the interface installed
         */
        if (context instanceof OnCustomEventListener) {
            Log.d("onAttach Fragment", "Activity has interface installed");
            mListener = (OnCustomEventListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + "ERROR");
        }
    }

    public void setCustomEventListener(OnCustomEventListener eventListener) {
        this.mListener=eventListener;
    }
    /*          //////////////////////          */

    public int getmAmount_of_players() {
        return mAmount_of_players;
    }

    private int mAmount_of_players;
    private EditText[] mPlayerNamesArray;

    public NewGamePlayersNamesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAmount_of_players = getArguments().getInt("amount_players");
        mPlayerNamesArray = new EditText[mAmount_of_players];
        Log.d("Received", String.valueOf(mAmount_of_players));
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

        mLinearLayout = getView().findViewById(R.id.midlayout);

        for(int i = 0 ; i < mAmount_of_players ; i++) {
            addEditTextToLayout(mPlayerNamesArray[i], i+1);
        }

        mListener.onEvent(1335);
    }

    private void addEditTextToLayout(EditText editText, int order){
        // to convert to dps
        final float scale = getContext().getResources().getDisplayMetrics().density;

        editText = new EditText(getActivity().getApplicationContext());

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
        mLinearLayout.addView(editText);
    }
}