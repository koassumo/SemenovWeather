package ru.geekbrains.android2.semenovweather.ui.home.data5Days;

import com.google.gson.annotations.SerializedName;

public class WeatherRequest5DaysModel {
    @SerializedName("cnt") public int cnt;
    @SerializedName("list") public ListModel5Days[] list;
    @SerializedName("cod") public int cod;
}
