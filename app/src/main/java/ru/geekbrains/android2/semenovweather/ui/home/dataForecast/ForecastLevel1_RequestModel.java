package ru.geekbrains.android2.semenovweather.ui.home.dataForecast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ForecastLevel1_RequestModel {
    @SerializedName("cnt") public int cnt;
    @SerializedName("list") public List<ForecastLevel2_ArrayListModel> list = new ArrayList<ForecastLevel2_ArrayListModel>();
    //public ListModel5Days[] list;
    @SerializedName("cod") public int cod;
}
