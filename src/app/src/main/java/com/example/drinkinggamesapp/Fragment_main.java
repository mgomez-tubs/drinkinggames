package com.example.drinkinggamesapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

public class Fragment_main extends Fragment implements View.OnClickListener{

    VideoView videoView;

    public Fragment_main() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_new_game).setOnClickListener(this);

        // Background Video
        Log.d("onViewCreated","This was reached");
        videoView = (VideoView) view.findViewById(R.id.background_video);

        Uri uri = Uri.parse("android.resource://"+view.getContext().getPackageName()+"/"+R.raw.falls);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(1);
                videoView.start();
            }
        });

        /*
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_new_game:
                Log.d("Fragment_main", "Button New game pressed");
                Navigation.findNavController(v).navigate(R.id.action_navigation_fragment_main_to_newGameSetup);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }
}