package ru.geekbrains.android2.semenovweather.ui.home;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherRepo5Days {
    private static OpenWeatherRepo5Days singleton = null;
    private IOpenWeather5days API5Days;

    private OpenWeatherRepo5Days() {
        API5Days = createAdapter();
    }

    public static OpenWeatherRepo5Days getSingleton() {
        if(singleton == null) {
            singleton = new OpenWeatherRepo5Days();
        }
        return singleton;
    }

    public IOpenWeather5days getAPI5Days() {
        return API5Days;
    }

    private IOpenWeather5days createAdapter() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(IOpenWeather5days.class);
    }
}
