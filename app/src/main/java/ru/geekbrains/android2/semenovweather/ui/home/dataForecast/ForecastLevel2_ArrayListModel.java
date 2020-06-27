package ru.geekbrains.android2.semenovweather.ui.home.dataForecast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ForecastLevel2_ArrayListModel {

    @SerializedName("weather") public List<ForecastLevel3_WeatherModel> weather = new ArrayList<ForecastLevel3_WeatherModel>();
    @SerializedName("main") public ForecastLevel3_MainModel main;
    @SerializedName("dt") public long dt;
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
