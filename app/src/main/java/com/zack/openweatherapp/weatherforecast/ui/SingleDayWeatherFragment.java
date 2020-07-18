package com.zack.openweatherapp.weatherforecast.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zack.openweatherapp.R;
import com.zack.openweatherapp.databinding.SingleDayWeatherLayoutBinding;

public class SingleDayWeatherFragment extends Fragment {
    SingleDayWeatherLayoutBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.single_day_weather_layout, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        WeatherAdapter adapter = new WeatherAdapter(getContext());
        adapter.setWeatherList(getArguments().getParcelableArrayList("list"));
        binding.weatherList.setLayoutManager(layoutManager);
        binding.weatherList.setAdapter(adapter);
        return binding.getRoot();
    }
}
