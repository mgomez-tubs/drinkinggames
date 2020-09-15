package com.example.drinkinggamesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Accessing the items of the bottom navigation bar directly will not work!
        BottomNavigationView navView = findViewById(R.id.bottom_nav);
        // Configure the Navigation View as an object to allow access
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_profile, R.id.navigation_settings)
                .build();

        // Set Up navigation controller for this class
        // something is wrong here below
        NavController navController = Navigation.findNavController(this, R.id.fragment_main);
    }
}