package ru.geekbrains.android2.semenovweather.ui.home;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.geekbrains.android2.semenovweather.ui.home.dataForecast.ForecastLevel1_RequestModel;

public interface IOpenWeather5days {
    @GET("data/2.5/forecast")
    Call<ForecastLevel1_RequestModel> loadWeather5Days (@Query("q") String city,
                                                        @Query("appid") String keyApi,
                                                        @Query("units") String units);
}
