package com.example.menuapplication;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.menuapplication.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNavView();
    }

    private void initNavView() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.navigate(R.id.navigation_table);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            ArrayList<Integer> list=new ArrayList<>();

            list.add(R.id.navigation_home);
            list.add(R.id.navigation_dashboard);
            list.add(R.id.navigation_notifications);
            list.add(R.id.navigation_price);
            list.add(R.id.navigation_table);

            if(destination.getId()==R.id.navigation_table){
                navView.setVisibility(View.GONE);
                getSupportActionBar().hide();
            }
            else {
                navView.setVisibility(View.VISIBLE);
                getSupportActionBar().show();
            }
        });
    }
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

}