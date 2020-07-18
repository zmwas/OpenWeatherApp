package com.zack.openweatherapp.weatherforecast.ui;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zack.openweatherapp.databinding.WeatherItemBinding;
import com.zack.openweatherapp.common.model.WeatherResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private List<WeatherResponse> weatherList;
    private Context context;

    public WeatherAdapter(Context context) {
        weatherList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        WeatherItemBinding binding = WeatherItemBinding.inflate(inflater, viewGroup, false);
        return new WeatherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        WeatherResponse weather = weatherList.get(i);
        weatherViewHolder.bind(weather);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public void setWeatherList(List<WeatherResponse> weatherList) {
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        WeatherItemBinding binding;

        WeatherViewHolder(@NonNull WeatherItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(WeatherResponse weather) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = null;
            try {
                date = formatter.parse(weather.getDtTxt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String strDate = DateFormat.getLongDateFormat(context).format(date);
            String time = DateFormat.getTimeFormat(context).format(date);

            binding.dateText.setText(strDate);
            binding.timeText.setText(time);
            binding.weatherDescription.setText(weather.getWeather().get(0).getDescription());
            binding.temperature.setText(String.format("%s ℃", convertToCelcisus(weather.getMain().getTemp())));
        }

        private String convertToCelcisus(Double tempInKelvin) {
            Double tempInCelsius = tempInKelvin - 273.15;
            return String.valueOf(Math.round(tempInCelsius*100.0)/100.0);
        }
    }
}
