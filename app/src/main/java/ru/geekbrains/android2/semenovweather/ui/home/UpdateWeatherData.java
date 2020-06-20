package ru.geekbrains.android2.semenovweather.ui.home;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.geekbrains.android2.semenovweather.BuildConfig;
import ru.geekbrains.android2.semenovweather.ui.home.data.WeatherRequestRestModel;

public class UpdateWeatherData {
    HomeFragment homeFragment;

    public UpdateWeatherData(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public void updateByTown (String town) {
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(town,
                BuildConfig.WEATHER_API_KEY, "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            homeFragment.renderWeather(response.body());
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

                    //сбой при интернет подключении
                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
//                        Toast.makeText(getBaseContext(), getString(R.string.network_error),
//                                Toast.LENGTH_SHORT).show();
                    }
                });


    }



}

