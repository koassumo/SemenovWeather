package ru.geekbrains.android2.semenovweather.ui.home;

import android.content.res.Resources;

import java.util.Date;

import ru.geekbrains.android2.semenovweather.ui.home.data.WeatherRequestRestModel;

public class SkyPictureName {

//    HomeFragment homeFragment;
//
//    public SkyPictureName (HomeFragment homeFragment) {
//        this.homeFragment = homeFragment;
//
//        homeFragment.townTextView.setText(model.name + ", " + model.sys.country);
//        temperatureTextView.setText("" + model.main.temp);
//        pressureTextView.setText(model.main.pressure + "mm");
//        windTextView.setText(model.wind.speed + "m/s");
//
//        // далее установка картинки погоды
//        String skyPictureName;
//        final int GROUP_THUNDERSTORM = 2;
//        final int GROUP_DRIZZLE = 3;
//        final int DRIZZLE_LIGHT = 301;
//        final int GROUP_RAIN = 5;
//        final int RAIN_LIGHT = 501;
//        final int GROUP_SNOW = 6;
//        final int GROUP_FOG = 7;
//        final int GROUP_CLOUDS = 8;
//        final int CLOUDS_CLEAR = 800;
//        final int CLOUDS_FEW = 801;
//        final int CLOUDS_BROKEN = 802;
//
//        long sunrise = model.sys.sunrise * 1000;
//        long sunset = model.sys.sunset * 1000;
//
//        int idWeather = model.weather[0].id;
//        int idWeatherGroup = idWeather / 100;
//
//        skyPictureName = "z_snow_white";
//        switch (idWeatherGroup) {
//            case GROUP_THUNDERSTORM: {
//                skyPictureName = "z_thunder_white";
//                break;
//            }
//            case GROUP_DRIZZLE: {
//                if (idWeather <= DRIZZLE_LIGHT) {
//                    skyPictureName = "z_rain_light_white";
//                } else {
//                    skyPictureName = "z_rain_shower_white";
//                }
//                break;
//            }
//            case GROUP_RAIN: {
//                if (idWeather <= RAIN_LIGHT) {
//                    skyPictureName = "z_rain_light_white";
//                } else {
//                    skyPictureName = "z_rain_shower_white";
//                }
//                break;
//            }
//            case GROUP_SNOW: {
//                skyPictureName = "z_snow_white";
//                break;
//            }
//            case GROUP_FOG: {
//                skyPictureName = "z_foggy_white";
//                break;
//            }
//            case GROUP_CLOUDS: {
//                if (idWeather > CLOUDS_BROKEN) {
//                    skyPictureName = "z_cloud_overcast_white";
//                    break;
//                }
//                if (idWeather == CLOUDS_BROKEN) {
//                    skyPictureName = "z_cloud_broken_white";
//                    break;
//                }
//                if (idWeather == CLOUDS_FEW) {
//                    long currentTime = new Date().getTime(); // для дневного времени отображается солнце, для ночного - луна
//                    if (currentTime >= sunrise && currentTime < sunset) {
//                        skyPictureName = "z_cloud_few_white";
//                    } else {
//                        skyPictureName = "z_night_cloud_few_white";
//                    }
//                    break;
//                }
//                if (idWeather == CLOUDS_CLEAR) {
//                    long currentTime = new Date().getTime();
//                    if (currentTime >= sunrise && currentTime < sunset) {
//                        skyPictureName = "z_clear_sky_white";
//                    } else {
//                        skyPictureName = "z_night_clear_sky_white";
//                    }
//                }
//            }
//        }
//        Resources res = getResources();
//        int resID = res.getIdentifier(skyPictureName, "drawable", getContext().getPackageName());
//        skyImageView.setImageResource(resID);
//    }
}
