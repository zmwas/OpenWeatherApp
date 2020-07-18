package com.zack.openweatherapp.weatherforecast.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zack.openweatherapp.common.model.OpenWeatherMapResponse;
import com.zack.openweatherapp.common.model.SingleDayForecast;
import com.zack.openweatherapp.common.model.WeatherResponse;
import com.zack.openweatherapp.weatherforecast.repository.WeatherRepository;

import java.util.List;

public class FetchWeatherViewModel extends ViewModel {
    private WeatherRepository weatherRepository;

    public FetchWeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public LiveData<List<WeatherResponse>> fetchFiveDayForecast(String latitude, String longitude) {
        return weatherRepository.fetchFiveDayForeCast(latitude, longitude);
    }

    public LiveData<SingleDayForecast> fetchTodaysWeather(String latitude, String longitude) {
        return weatherRepository.fetchTodaysForecast(latitude, longitude);
    }

}
