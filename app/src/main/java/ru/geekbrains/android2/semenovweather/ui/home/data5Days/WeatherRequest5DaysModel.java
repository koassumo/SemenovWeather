package ru.geekbrains.android2.semenovweather.ui.home.data5Days;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherRequest5DaysModel {
    @SerializedName("cnt") public int cnt;
    @SerializedName("list") public List<ListModel5Days> list = new ArrayList<ListModel5Days>();
    //public ListModel5Days[] list;
    @SerializedName("cod") public int cod;
}
