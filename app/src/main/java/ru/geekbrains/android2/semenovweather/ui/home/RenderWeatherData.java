package ru.geekbrains.android2.semenovweather.ui.home;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import ru.geekbrains.android2.semenovweather.R;

public class RenderWeatherData {

    JSONObject jsonObject;
    JSONObject weather;
    JSONObject main;
    JSONObject wind;
    JSONObject sys;

    public RenderWeatherData(JSONObject jsonObject) {
        try {
            this.jsonObject = jsonObject;
            weather = jsonObject.getJSONArray("weather").getJSONObject(0);
            main = jsonObject.getJSONObject("main");
            wind = jsonObject.getJSONObject("wind");
            sys = jsonObject.getJSONObject("sys");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public String getPlaceName() throws JSONException {
        return jsonObject.getString("name") + ", " + jsonObject.getJSONObject("sys").getString("country");
    }

    public String getCurrentTemp() throws JSONException {
        long temperature = main.getLong("temp");
        return (temperature > 0) ? "+" + temperature : "" + temperature;
    }

    public String getPressure() throws JSONException {
        return main.getString("pressure") + " hPa";
    }

    public String getWind() throws JSONException {
        return wind.getString("speed") + " m/s";
    }

    public String getTimeUpdatedText() throws JSONException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String updateOn = dateFormat.format(new Date(jsonObject.getLong("dt") * 1000));
        return  weather.getString("description").toUpperCase() + "\n" + "Last update: " + updateOn;
    }

    public String getSkyImage () throws JSONException {

        final int GROUP_THUNDERSTORM = 2;
        final int GROUP_DRIZZLE = 3;
          final int DRIZZLE_LIGHT = 301;
        final int GROUP_RAIN = 5;
          final int RAIN_LIGHT = 501;
        final int GROUP_SNOW = 6;
        final int GROUP_FOG = 7;
        final int GROUP_CLOUDS = 8;
          final int CLOUDS_CLEAR = 800;
          final int CLOUDS_FEW = 801;
          final int CLOUDS_BROKEN = 802;

        long sunrise = sys.getLong("sunrise") * 1000;
        long sunset = sys.getLong("sunset") * 1000;

        int idWeather = weather.getInt("id");
        int idWeatherGroup = idWeather / 100;

        String skyPictureName = "z_snow_white";
        switch (idWeatherGroup) {
            case GROUP_THUNDERSTORM: {
                skyPictureName = "z_thunder_white";
                break;
            }
            case GROUP_DRIZZLE: {
                if (idWeather <= DRIZZLE_LIGHT) {
                    skyPictureName = "z_rain_light_white";
                } else {
                    skyPictureName = "z_rain_shower_white";
                }
                break;
            }
            case GROUP_RAIN: {
                if (idWeather <= RAIN_LIGHT) {
                    skyPictureName = "z_rain_light_white";
                } else {
                    skyPictureName = "z_rain_shower_white";
                }
                break;
            }
            case GROUP_SNOW: {
                skyPictureName = "z_snow_white";
                break;
            }
            case GROUP_FOG: {
                skyPictureName = "z_foggy_white";
                break;
            }
            case GROUP_CLOUDS: {
                if (idWeather > CLOUDS_BROKEN) {
                    skyPictureName = "z_cloud_overcast_white";
                    break;
                }
                if (idWeather == CLOUDS_BROKEN) {
                    skyPictureName = "z_cloud_broken_white";
                    break;
                }
                if (idWeather == CLOUDS_FEW ) {
                    long currentTime = new Date().getTime();
                    if(currentTime >= sunrise && currentTime < sunset) {
                        skyPictureName = "z_cloud_few_white";
                    } else {
                        skyPictureName = "z_night_cloud_few_white";
                    }
                    break;
                }
                if (idWeather == CLOUDS_CLEAR ) {
                    long currentTime = new Date().getTime();
                    if(currentTime >= sunrise && currentTime < sunset) {
                        skyPictureName = "z_clear_sky_white";
                    } else {
                        skyPictureName = "z_night_clear_sky_white";
                    }
                }
            }
        }
        return skyPictureName;
    }
}
