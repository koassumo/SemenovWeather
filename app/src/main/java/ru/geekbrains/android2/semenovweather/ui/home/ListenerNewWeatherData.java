package ru.geekbrains.android2.semenovweather.ui.home;

import ru.geekbrains.android2.semenovweather.ui.home.data.WeatherRequestRestModel;
import ru.geekbrains.android2.semenovweather.ui.home.data5Days.WeatherRequest5DaysModel;

public interface ListenerNewWeatherData {

    public void showWeatherData(WeatherRequestRestModel model);
    public void show5DaysData(WeatherRequest5DaysModel model);
}
