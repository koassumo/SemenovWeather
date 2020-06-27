package ru.geekbrains.android2.semenovweather.ui.home;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.geekbrains.android2.semenovweather.BuildConfig;
import ru.geekbrains.android2.semenovweather.ui.home.dataCurrentWeather.WeatherRequestRestModel;
import ru.geekbrains.android2.semenovweather.ui.home.dataForecast.ForecastLevel1_RequestModel;

public class UpdateWeatherData {
    HomeFragment homeFragment;
    SQLiteDatabase database;

    public UpdateWeatherData(HomeFragment homeFragment, SQLiteDatabase database) {
        this.homeFragment = homeFragment;
        this.database = database;
    }

    public void updateByTown(String town) {
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(town,
                BuildConfig.WEATHER_API_KEY, "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            homeFragment.showCurrentWeatherData(response.body());
                        } else {
                            //Похоже, код у нас не в диапазоне [200..300) и случилась ошибка
                            //обрабатываем ее
                            if (response.code() == 500) {
                                //ой, случился Internal Server Error. Решаем проблему
                            } else if (response.code() == 401) {
                                //не авторизованы, что-то с этим делаем.
                                //например, открываем страницу с логинкой
                            }// и так далее
                        }
                    }

                    @Override                    //сбой при интернет подключении
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
//                        Toast.makeText(getBaseContext(), getString(R.string.network_error),
//                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void update5Days(String town) {
        OpenWeatherRepo5Days.getSingleton().getAPI5Days().loadWeather5Days(town,
                BuildConfig.WEATHER_API_KEY, "metric")
                .enqueue(new Callback<ForecastLevel1_RequestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ForecastLevel1_RequestModel> call,
                                           @NonNull Response<ForecastLevel1_RequestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            homeFragment.show5DaysForecastData(response.body());
                        } else {
                            if (response.code() == 500) {
                            } else if (response.code() == 401) {
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ForecastLevel1_RequestModel> call, Throwable t) {
                    }
                });
    }
}

