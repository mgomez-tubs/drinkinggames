package com.example.drinkinggamesapp.mainscreen;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.drinkinggamesapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Up navigation controller for this class
        navController = Navigation.findNavController(this, R.id.fragment_main);
        // Accessing the items of the bottom navigation bar directly will not work!
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}