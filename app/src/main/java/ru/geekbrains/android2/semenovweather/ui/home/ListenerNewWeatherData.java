package ru.geekbrains.android2.semenovweather.ui.home;

import ru.geekbrains.android2.semenovweather.ui.home.dataCurrentWeather.WeatherRequestRestModel;
import ru.geekbrains.android2.semenovweather.ui.home.dataForecast.ForecastLevel1_RequestModel;

public interface ListenerNewWeatherData {

    void showCurrentWeatherData(WeatherRequestRestModel model);
    void show5DaysForecastData(ForecastLevel1_RequestModel model);
}
