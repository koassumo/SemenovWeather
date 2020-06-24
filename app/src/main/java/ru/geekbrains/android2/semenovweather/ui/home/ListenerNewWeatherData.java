package ru.geekbrains.android2.semenovweather.ui.home;

import ru.geekbrains.android2.semenovweather.ui.home.dataCurrentWeather.WeatherRequestRestModel;
import ru.geekbrains.android2.semenovweather.ui.home.dataForecast.ForecastLevel1_RequestModel;

public interface ListenerNewWeatherData {

    public void showCurrentWeatherData(WeatherRequestRestModel model);
    public void show5DaysForecastData(ForecastLevel1_RequestModel model);
}
