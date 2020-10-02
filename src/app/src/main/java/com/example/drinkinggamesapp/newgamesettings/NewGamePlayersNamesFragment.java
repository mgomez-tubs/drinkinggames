package com.example.drinkinggamesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NewGamePlayersNamesFragment extends Fragment {

    private LinearLayout mLinearLayout;

    public int getmAmount_of_players() {
        return mAmount_of_players;
    }

    private int mAmount_of_players;
    private EditText[] mPlayerNamesEditText;

    MyViewModel viewModel;

    public NewGamePlayersNamesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAmount_of_players = getArguments().getInt("amount_players");
        mPlayerNamesEditText = new EditText[mAmount_of_players];

        for(int i = 0; i < mAmount_of_players; i++){
            mPlayerNamesEditText[i] = new EditText(getActivity().getApplicationContext());
        }

        Log.d("Received", String.valueOf(mAmount_of_players));

        // Set Up View Model
        // The implementation in the fragment is not the same as in the activity.
        // In order to mantain communication to the activity ViewModel we need to get its ViewModel and not create another one
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        // Set up amount of players
        viewModel.initmPlayerNames(mAmount_of_players);
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
            addEditTextToLayout(mPlayerNamesEditText[i], i+1);
            final int finalI = i;
            mPlayerNamesEditText[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    viewModel.setmPlayerNames(mPlayerNamesEditText[finalI].getText().toString(), finalI);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void addEditTextToLayout(EditText editText, int order){
        // to convert to dps
        final float scale = getContext().getResources().getDisplayMetrics().density;

        //editText = new EditText(getActivity().getApplicationContext()); -> declare this in onViewCreated(), but dont do it two times!

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
        // Remove parent before inflating EditText, since parent cant be overwritten
        if(editText.getParent() != null){
            ((ViewGroup) editText.getParent()).removeView(editText);
        }
        mLinearLayout.addView(editText);
    }
}