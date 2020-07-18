package com.zack.openweatherapp.weatherforecast.viewmodel;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zack.openweatherapp.LiveDataTestUtil;
import com.zack.openweatherapp.common.ApiNetworkUtility;
import com.zack.openweatherapp.common.model.Main;
import com.zack.openweatherapp.common.model.OpenWeatherMapResponse;
import com.zack.openweatherapp.common.model.SingleDayForecast;
import com.zack.openweatherapp.common.model.WeatherResponse;
import com.zack.openweatherapp.weatherforecast.repository.WeatherRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static com.zack.openweatherapp.ExecutorUtil.implementAsDirectExecutor;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class FetchWeatherViewModelTest {
    @Mock
    WeatherRepository weatherRepository;

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();
    FetchWeatherViewModel viewModel;
    MutableLiveData<SingleDayForecast> testLiveData = new MutableLiveData<>();
    MutableLiveData<List<WeatherResponse>> testForecastLiveData = new MutableLiveData<>();

    SingleDayForecast singleDayForecast = new SingleDayForecast();
    OpenWeatherMapResponse openWeatherMapResponse = new OpenWeatherMapResponse();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewModel = new FetchWeatherViewModel(weatherRepository);
        singleDayForecast.setBase("BASE");
        singleDayForecast.setMain(new Main());
        openWeatherMapResponse.setList(new ArrayList<>());
        openWeatherMapResponse.setCnt(100);
    }

    @Test
    public void test_fetching_of_current_weather() throws InterruptedException {
        testLiveData.setValue(singleDayForecast);
        Mockito.when(weatherRepository.fetchTodaysForecast(anyString(), anyString())).thenReturn(testLiveData);
        LiveData<SingleDayForecast> result = viewModel.fetchTodaysWeather("0","0");
        Assert.assertEquals(singleDayForecast, LiveDataTestUtil.getOrAwaitValue(result));
    }

    @Test
    public void test_fetching_of_five_day_forecast() throws InterruptedException {
        testForecastLiveData.setValue(openWeatherMapResponse.getList());
        Mockito.when(weatherRepository.fetchFiveDayForeCast(anyString(), anyString())).thenReturn(testForecastLiveData);
        LiveData<List<WeatherResponse>> result = viewModel.fetchFiveDayForecast("0","0");
        Assert.assertEquals(openWeatherMapResponse.getList(), LiveDataTestUtil.getOrAwaitValue(result));
    }

}


