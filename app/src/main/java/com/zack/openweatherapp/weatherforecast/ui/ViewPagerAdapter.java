package com.zack.openweatherapp.weatherforecast.ui;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zack.openweatherapp.common.model.WeatherResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ViewPagerAdapter extends FragmentPagerAdapter {
    private  List<List<WeatherResponse>> weatherResponses;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior,List<List<WeatherResponse>> weatherResponses ) {
        super(fm, behavior);
        this.weatherResponses = weatherResponses;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) weatherResponses.get(position));
        SingleDayWeatherFragment fragment = new SingleDayWeatherFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return weatherResponses.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        WeatherResponse weatherResponse = weatherResponses.get(position).get(0);
        Date date = new Date((long)weatherResponse.getDt()*1000);
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        return simpleDateformat.format(date);
    }
}
