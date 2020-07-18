package com.zack.openweatherapp.common;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zack.openweatherapp.R;
import com.zack.openweatherapp.bookmarkcities.ui.CityFragment;
import com.zack.openweatherapp.bookmarkcities.ui.HomeFragment;
import com.zack.openweatherapp.databinding.ActivityLayoutBinding;
import com.zack.openweatherapp.help.HelpFragment;
import com.zack.openweatherapp.weatherforecast.ui.WeatherForecastFragment;

public class MainActivity extends AppCompatActivity {
    ActivityLayoutBinding binding;
    private final static String TAG_FRAGMENT = "HOME_FRAGMENT";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_layout);
        setSupportActionBar(binding.toolbar);
        HomeFragment fragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(binding.content.getId(), fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                HelpFragment fragment = new HelpFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(TAG_FRAGMENT);
                transaction.replace(binding.content.getId(), fragment);
                transaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
