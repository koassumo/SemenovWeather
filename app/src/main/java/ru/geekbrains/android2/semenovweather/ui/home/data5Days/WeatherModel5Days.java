package ru.geekbrains.android2.semenovweather.ui.home.data5Days;

import com.google.gson.annotations.SerializedName;

public class WeatherModel5Days {

    @SerializedName("id") public int id;
    @SerializedName("main") public String main;
    @SerializedName("description") public String description;
    @SerializedName("icon") public String icon;
}
