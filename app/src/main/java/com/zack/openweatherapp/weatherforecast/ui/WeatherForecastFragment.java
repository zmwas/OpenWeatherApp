package com.zack.openweatherapp.weatherforecast.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.zack.openweatherapp.R;
import com.zack.openweatherapp.common.ApiNetworkUtility;
import com.zack.openweatherapp.common.model.OpenWeatherMapResponse;
import com.zack.openweatherapp.common.model.SingleDayForecast;
import com.zack.openweatherapp.common.model.Weather;
import com.zack.openweatherapp.common.model.WeatherResponse;
import com.zack.openweatherapp.databinding.FragmentWeatherForecastLayoutBinding;
import com.zack.openweatherapp.common.model.BookMarkedCity;
import com.zack.openweatherapp.weatherforecast.repository.WeatherRepository;
import com.zack.openweatherapp.weatherforecast.viewmodel.FetchViewModelFactory;
import com.zack.openweatherapp.weatherforecast.viewmodel.FetchWeatherViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.zack.openweatherapp.ServiceLocatorUtil.executor;

public class WeatherForecastFragment extends Fragment {
    private FragmentWeatherForecastLayoutBinding binding;
    private WeatherAdapter weatherAdapter;
    private FetchWeatherViewModel viewModel;
    private BookMarkedCity bookMark;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_forecast_layout, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiNetworkUtility apiNetworkUtility = new ApiNetworkUtility();
        WeatherRepository fetchWeatherRepository = new WeatherRepository(apiNetworkUtility, executor);
        viewModel = new ViewModelProvider(this,
                new FetchViewModelFactory(fetchWeatherRepository))
                .get(FetchWeatherViewModel.class);
        if (getArguments() != null)
            bookMark = (BookMarkedCity) getArguments().getSerializable("bookmark");
        if (bookMark != null) fetchForecast();

    }

    private void fetchForecast() {
        String longitude = String.valueOf(bookMark.getLongitude());
        String latitude = String.valueOf(bookMark.getLatitude());
        viewModel.fetchTodaysWeather(latitude, longitude).observe(this, this::updateWeather);
        viewModel.fetchFiveDayForecast(latitude, longitude).observe(this, this::updateFiveDayForecast);
    }

    private void updateWeather(SingleDayForecast singleDayForecast) {
        String wind = singleDayForecast.getWind() != null ?
                String.valueOf(singleDayForecast.getWind().getSpeed()) : "0.0";
        String humidity = singleDayForecast.getMain().getHumidity() != null ?
                String.valueOf(singleDayForecast.getMain().getHumidity()) : "0.0";
        String temp = String.format("%s ℃", convertToCelcisus(singleDayForecast.getMain().getTemp()));
        String description = singleDayForecast.getWeather().get(0).getDescription();
        binding.todayDescription.setText(description);
        binding.todayWind.setText(getString(R.string.wind).concat(": ")
                .concat(wind));
        binding.todayTemperature.setText(getString(R.string.temperature).concat(": ")
                .concat(temp));
        binding.todayHumidity.setText(getString(R.string.humidity).concat(": ")
                .concat(humidity));
    }

    private String convertToCelcisus(Double tempInKelvin) {
        Double tempInCelsius = tempInKelvin - 273.15;
        return String.valueOf(Math.round(tempInCelsius * 100.0) / 100.0);
    }

    private void updateFiveDayForecast(List<WeatherResponse> weatherResponses) {
        SingleDayWeatherFragment singleDayWeatherFragment = new SingleDayWeatherFragment();
        List<List<WeatherResponse>> response = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<WeatherResponse> list = new ArrayList<>();
            response.add(list);
        }
        populateResponseList(weatherResponses, response);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 0, response);
        ViewPager pager = binding.pager;
        pager.setAdapter(viewPagerAdapter);
        binding.tabs.setupWithViewPager(pager);
    }

    private void populateResponseList(List<WeatherResponse> weatherResponses, List<List<WeatherResponse>> response) {
        Date today = new Date();
        for (int i = 0; i < weatherResponses.size(); i++) {
            WeatherResponse weatherResponse = weatherResponses.get(i);
            Date date = new Date((long) weatherResponse.getDt() * 1000);
            int differenceInDays = (int) ((date.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
            List<WeatherResponse> list = response.get(differenceInDays);
            list.add(weatherResponse);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherAdapter = new WeatherAdapter(getContext());
    }
}
