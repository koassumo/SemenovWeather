package ru.geekbrains.android2.semenovweather.ui.home;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class RenderWeatherData {

    JSONObject jsonObject;
    JSONObject weather;
    JSONObject main;
    JSONObject wind;
    JSONObject sys;

    public RenderWeatherData(JSONObject jsonObject) {
        //Log.d(LOG_TAG, "json: " + jsonObject.toString());
        try {
            this.jsonObject = jsonObject;
            weather = jsonObject.getJSONArray("weather").getJSONObject(0);
            main = jsonObject.getJSONObject("main");
            wind = jsonObject.getJSONObject("wind");
            sys = jsonObject.getJSONObject("sys");

//            setUpdatedText(jsonObject);
//            setWeatherIcon(details.getInt("id"),
//                    jsonObject.getJSONObject("sys").getLong("sunrise") * 1000,
//                    jsonObject.getJSONObject("sys").getLong("sunset") * 1000);
        } catch (Exception exc) {
            exc.printStackTrace();
            //Log.e(LOG_TAG, "One or more fields not found in the JSON data");
        }
    }


    public String getPlaceName() throws JSONException {
        return jsonObject.getString("name").toUpperCase() + ", " + jsonObject.getJSONObject("sys").getString("country");
    }

    public String getCurrentTemp() throws JSONException {
        return String.format(Locale.getDefault(), "%.2f", main.getDouble("temp"));
    }

    public String getPressure() throws JSONException {
        return weather.getString("description").toUpperCase() + "\n" + main.getString("pressure") + " hPa";
    }

    public String getWind() throws JSONException {
        return wind.getString("speed") + " m/s";
    }
//
//    private void setUpdatedText(JSONObject jsonObject) throws JSONException {
//        DateFormat dateFormat = DateFormat.getDateTimeInstance();
//        String updateOn = dateFormat.format(new Date(jsonObject.getLong("dt") * 1000));
//        String updatedText = "Last update: " + updateOn;
//        windTextView.setText("100");
//    }
}
