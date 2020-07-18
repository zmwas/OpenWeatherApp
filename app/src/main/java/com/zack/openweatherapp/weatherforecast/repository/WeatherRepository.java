package com.zack.openweatherapp.weatherforecast.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zack.openweatherapp.common.ApiNetworkUtility;
import com.zack.openweatherapp.common.model.OpenWeatherMapResponse;
import com.zack.openweatherapp.common.model.SingleDayForecast;
import com.zack.openweatherapp.common.model.WeatherResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WeatherRepository {
    private ApiNetworkUtility apiNetworkUtility;
    private List<WeatherResponse> weatherList = new ArrayList<>();
    private Executor executor;
    private MutableLiveData<List<WeatherResponse>> fiveDayForecastLiveData;
    private MutableLiveData<SingleDayForecast> weatherForecastLiveData;

    public WeatherRepository(ApiNetworkUtility apiNetworkUtility, Executor executor) {
        this.apiNetworkUtility = apiNetworkUtility;
        this.executor = executor;
    }

    public LiveData<List<WeatherResponse>> fetchFiveDayForeCast(String latitude, String longitude) {
        fiveDayForecastLiveData = new MutableLiveData<>();
        URL url = null;

        String template = "http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&appid=c6e381d8c7ff98f0fee43775817cf6ad";
        String s = String.format(template, latitude, longitude);
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URL finalUrl = url;
        executor.execute(() -> {
            try {
                Gson gson = new GsonBuilder().create();
                String json = null;
                if (finalUrl != null) {
                    json = apiNetworkUtility.fetchApiResult(finalUrl, "GET");
                }
                OpenWeatherMapResponse weatherResponse = gson.fromJson(json, OpenWeatherMapResponse.class);
                weatherList = weatherResponse.getList();
                fiveDayForecastLiveData.postValue(weatherList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return fiveDayForecastLiveData;
    }

    public LiveData<SingleDayForecast> fetchTodaysForecast(String latitude, String longitude) {
        weatherForecastLiveData = new MutableLiveData<>();
        URL url = null;
        String template = "http://api.openweathermap.org/data/2.5/weather?lat=0&lon=0&appid=c6e381d8c7ff98f0fee43775817cf6ad";
        String s = String.format(template, latitude, longitude);
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URL finalUrl = url;
        executor.execute(() -> {
            try {
                Gson gson = new GsonBuilder().create();
                String json = null;
                if (finalUrl != null) {
                    json = apiNetworkUtility.fetchApiResult(finalUrl, "GET");
                }
                SingleDayForecast weatherResponse = gson.fromJson(json, SingleDayForecast.class);
                weatherForecastLiveData.postValue(weatherResponse);

            } catch (IOException  e) {
                e.printStackTrace();
            }
        });
        return weatherForecastLiveData;
    }

}
