package com.zack.openweatherapp.weatherforecast.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zack.openweatherapp.weatherforecast.repository.WeatherRepository;

public class FetchViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    private WeatherRepository repository;

    public FetchViewModelFactory(@NonNull WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FetchWeatherViewModel(repository);
    }
}
