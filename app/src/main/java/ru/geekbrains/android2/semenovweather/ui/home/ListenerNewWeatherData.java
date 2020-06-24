package ru.geekbrains.android2.semenovweather.ui.home;

import ru.geekbrains.android2.semenovweather.ui.home.dataCurrentWeather.WeatherRequestRestModel;
import ru.geekbrains.android2.semenovweather.ui.home.dataForecast.ForecastLevel1_RequestModel;

public interface ListenerNewWeatherData {

    public void showWeatherData(WeatherRequestRestModel model);
    public void show5DaysData(ForecastLevel1_RequestModel model);
}
