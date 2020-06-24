package ru.geekbrains.android2.semenovweather.ui.home.data5Days;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.android2.semenovweather.ui.home.data.WeatherRestModel;

public class ListModel5Days {

    @SerializedName("weather") public List<WeatherModel5Days> weatherModel5Days = new ArrayList<WeatherModel5Days>();
    @SerializedName("dt_txt") public String dtTxt;

    //    @SerializedName("list") public array[] weather;
//    @SerializedName("description") public String description;
//    @SerializedName("icon") public String icon;

//
//    @SerializedName("dt")
//    public Integer dt;
//
//    @SerializedName("main")
//    public Main main;
//
//
//    @SerializedName("clouds")
//    public Clouds clouds;
//
//    @SerializedName("wind")
//    public Wind wind;
//
//    @SerializedName("sys")
//    public Sys sys;
//
//    @SerializedName("rain")
//    public Rain rain;
//
//    private final static long serialVersionUID = -1899056469184678399L;
}
