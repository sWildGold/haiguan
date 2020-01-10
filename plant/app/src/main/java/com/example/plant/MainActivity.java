package com.example.plant;


import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.plant.ui.dashboard.DashboardFragment;
import com.example.plant.ui.home.HomeFragment;
import com.example.plant.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Fragment> fragments;
    FragmentManager fm;
    private boolean islogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new DashboardFragment());
        fragments.add(new NotificationsFragment());
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, fragments.get(0), "扫描")
                .add(R.id.fragment_container, fragments.get(1), "查询")
                .add(R.id.fragment_container, fragments.get(2), "小知识")
                .commit();

        fm.beginTransaction()
                .hide(fragments.get(1))
                .hide(fragments.get(2))
                .commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fm.beginTransaction()
                                .hide(fragments.get(1))
                                .hide(fragments.get(2))
                                .show(fragments.get(0))
                                .commit();
                        return true;
                    case R.id.navigation_dashboard:
                        fm.beginTransaction()
                                .hide(fragments.get(0))
                                .hide(fragments.get(2))
                                .show(fragments.get(1))
                                .commit();
                        return true;
                    case R.id.navigation_notifications:
                        fm.beginTransaction()
                                .hide(fragments.get(0))
                                .hide(fragments.get(1))
                                .show(fragments.get(2))
                                .commit();
                        return true;
                }
                return false;
            }

        });
    }

    @Override
    public void onBackPressed() {

    }

}
