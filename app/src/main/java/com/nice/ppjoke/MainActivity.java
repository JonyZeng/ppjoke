package com.nice.ppjoke;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nice.ppjoke.utils.NavGraphBuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NavController mNavController;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        mNavController = NavHostFragment.findNavController(fragment);
        NavGraphBuilder.build(mNavController, this, fragment.getId());
        navView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i(TAG, "onNavigationItemSelected: itemId=" + item.getItemId());
        mNavController.navigate(item.getItemId());
        return !TextUtils.isEmpty(item.getTitle());

    }
}