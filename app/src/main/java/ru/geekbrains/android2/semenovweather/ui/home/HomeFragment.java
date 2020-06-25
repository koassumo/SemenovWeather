package ru.geekbrains.android2.semenovweather.ui.home;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.geekbrains.android2.semenovweather.R;
import ru.geekbrains.android2.semenovweather.ui.home.dataCurrentWeather.WeatherRequestRestModel;
import ru.geekbrains.android2.semenovweather.ui.home.dataForecast.ForecastLevel1_RequestModel;

public class HomeFragment extends Fragment implements ListenerNewWeatherData {

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

    private TextView townTextView;
    private TextView temperatureTextView;
    private TextView pressureTextView;
    private TextView windTextView;
    private TextView skyTextView;
    private TextView lastUpdateTextView;
    private ImageView skyImageView;
    private View changeTownBtn;
    private TextView textForecastSky;

    private final String townTextKey = "town_text_key";

    private final Handler handler = new Handler();
    Typeface weatherFont;

    private UpdateWeatherData updateWeatherData = new UpdateWeatherData(this);

    private RecyclerDataAdapterDays adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initList(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        townTextView = view.findViewById(R.id.townTextView);
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        pressureTextView = view.findViewById(R.id.pressureTextView);
        windTextView = view.findViewById(R.id.windTextView);
        skyTextView = view.findViewById(R.id.lastUpdateTextView);
        lastUpdateTextView = view.findViewById(R.id.lastUpdateTextView);
        skyImageView = view.findViewById(R.id.skyImageView);
        changeTownBtn = view.findViewById(R.id.changeTownBtn);
        textForecastSky = view.findViewById(R.id.textForecastSky);
        //        searchEditText = findViewById(R.id.searchEditText);

        initFonts();
        getSharedPrefs();
        updateWeatherData.updateByTown(townTextView.getText().toString());
        updateWeatherData.update5Days(townTextView.getText().toString());
        setOnChangeTownBtnClick();
    }

    private void initList(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.recycleViewDays);
        // Эта установка повышает производительность системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Устанавливаем адаптер
        adapter = new RecyclerDataAdapterDays(initData(), initTime(), initSky(), initTemp(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add(String.format("Element %d", i));
        }
        return list;
    }

    private List<String> initTime() {
        List<String> listTime = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            listTime.add(String.format("Time %d", i));
        }
        return listTime;
    }

    private List<String> initSky() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add(String.format("%d", i));
        }
        return list;
    }

    private List<String> initTemp() {
        List<String> listTime = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            listTime.add(String.format("+%d", i));
        }
        return listTime;
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = requireActivity().getMenuInflater();
//        inflater.inflate(R.menu.context_menu, menu);
//    }

//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
//        handleMenuItemClick(item);
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.add_context:
//                adapter.addItem(String.format("New element %d", adapter.getItemCount()));
//                return true;
//            case R.id.update_context:
//                adapter.updateItem(String.format("Updated element %d", adapter.getMenuPosition()), adapter.getMenuPosition());
//                return true;
//            case R.id.remove_context:
//                adapter.removeItem(adapter.getMenuPosition());
//                return true;
//            case R.id.clear_context:
//                adapter.clearItems();
//                return true;
//        }
//        return super.onContextItemSelected(item);
//    }

//    @Override
//    public void addItem(String str) {
//        adapter.addItem(str);
//    }
//
//    @Override
//    public void clearItems() {
//        adapter.clearItems();
//    }

//    private void handleMenuItemClick(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        switch (id) {
//            case R.id.add_context: {
//                //menuListAdapter.addItem();
//                break;
//            }
////            case R.id.menu_search: {
////                if(searchEditText.getVisibility() == View.VISIBLE) {
////                    searchEditText.setVisibility(View.GONE);
////                    Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_name));
////                } else {
////                    Objects.requireNonNull(getSupportActionBar()).setTitle("");
////                    searchEditText.setVisibility(View.VISIBLE);
////                    searchEditText.addTextChangedListener(new TextWatcher() {
////                        @Override
////                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////                        }
////
////                        @Override
////                        public void onTextChanged(CharSequence s, int start, int before, int count) {
////                        }
////
////                        @Override
////                        public void afterTextChanged(Editable s) {
////                            //Вам приходит на вход текст поиска - ищем его - в бд, через АПИ (сервер в инете) и т.д.
////                            Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_SHORT).show();
////                        }
////                    });
////                }
////            }
//            case R.id.update_context: {
//                //menuListAdapter.editItem(2500);
//                break;
//            }
//            case R.id.remove_context: {
//                //menuListAdapter.removeElement();
//                break;
//            }
//            case R.id.clear_context: {
//                //menuListAdapter.clearList();
//                break;
//            }
//            case 12345: {
//                Toast.makeText(getContext(), "Был нажат доп. элемент попап меню",
//                        Toast.LENGTH_SHORT).show();
//            }
////            default: {
////                if(id != R.id.menu_more) {
////                    Toast.makeText(getContext(), getString(R.string.action_not_found),
////                            Toast.LENGTH_SHORT).show();
////                }
////            }
//
//            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//    }

    private void initFonts() {
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"); //если в MainActivity, то getActivity(). не нужен
        temperatureTextView.setTypeface(weatherFont);
    }

    private void setOnChangeTownBtnClick() {
        changeTownBtn.setOnClickListener(v -> {
            saveSharedPrefs();
            Bundle bundle = new Bundle();
            bundle.putString("arg", "data");
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_home_to_nav_options, bundle);
        });
    }

    private void saveSharedPrefs() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = defaultPrefs.edit();
        String text = townTextView.getText().toString();
        editor.putString(townTextKey, text);
        editor.apply();
    }

    private void getSharedPrefs() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String text = defaultPrefs.getString(townTextKey, "");
        townTextView.setText(text);
    }

    @Override
    public void showCurrentWeatherData(WeatherRequestRestModel model) {
        townTextView.setText(model.name + ", " + model.sys.country);

        int pressureInteger = (int) Math.round(model.main.pressure * 0.750063755419211);
        pressureTextView.setText(pressureInteger + " мм рт ст");

        int windInteger = (int) Math.round(model.wind.speed);
        windTextView.setText(windInteger + " м/с");

        int temperatureInteger = (int) Math.round(model.main.temp);
        String temperature = Integer.toString(temperatureInteger);
        if (temperatureInteger > 0) temperature = "+" + temperature;
        else if (temperatureInteger < 0) temperature = "-" + temperature;
        temperatureTextView.setText(temperature);

        // далее установка картинки погоды

        long sunrise = model.sys.sunrise * 1000;
        long sunset = model.sys.sunset * 1000;
        int idWeather = model.weather[0].id;

        String skyPictureName = defineSkyWeatherPicture(sunrise, sunset, idWeather);

        Resources res = getResources();
        int resID = res.getIdentifier(skyPictureName, "drawable", getContext().getPackageName());
        skyImageView.setImageResource(resID);

//        String icon = "";
//        String rrr = "z_thunder_white";
//        icon = getString(R.string.rrr);
//        temperatureTextView.setText(icon);
    }

    @Override
    public void show5DaysForecastData(ForecastLevel1_RequestModel model) {

        for (int i = 0; i < 40; i++) {
            // формат nextDateAndTime = "2020-06-24 15:00"
            String nextDateAndTime = model.list.get(i).dtTxt;

            String greenwichDate = nextDateAndTime.substring(0, 10);
            String moscowDate = greenwichDate;
            String greenwichTime = nextDateAndTime.substring(11, 13);
            int moscowTimeInteger = Integer.parseInt(greenwichTime) + 3;
            //boolean isAfterMidNight = false;
            if (moscowTimeInteger >= 24) {
                moscowTimeInteger -= 24;
                SimpleDateFormat gsonFormatter = new SimpleDateFormat("yyyy-MM-dd");//задаю формат даты
                Date date = null;
                try {
                    date = gsonFormatter.parse(moscowDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar instance = Calendar.getInstance();
                instance.setTime(date); //устанавливаем дату, с которой будет производить операции
                instance.add(Calendar.DAY_OF_MONTH, 1);// прибавляем 3 дня к установленной дате
                moscowDate = gsonFormatter.format(instance.getTime()); // получаем измененную дату
            }
            String moscowTime = moscowTimeInteger + ":00";

            String nextSky = Integer.toString(model.list.get(i).weather.get(0).id);

            int nextTempInteger = (int) Math.round((model.list.get(i).main.temp));
            String nextTemp = Integer.toString(nextTempInteger);
            if (nextTempInteger > 0) nextTemp = "+" + nextTemp;
            else if (nextTempInteger < 0) nextTemp = "-" + nextTemp;

            long sunrise = model.city.sunrise * 1000;
            long sunset = model.city.sunset * 1000;

            int idWeather = model.list.get(i).weather.get(0).id;
            String icon = defineSkyWeatherIcon(sunrise, sunset, idWeather);

            adapter.updateItem(moscowDate, moscowTime, icon, nextTemp, i);
        }
    }

    public String defineSkyWeatherPicture(long sunrise, long sunset, int idWeather) {
        String skyPictureName;

        int idWeatherGroup = idWeather / 100;

        skyPictureName = "z_snow_white";
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
                if (idWeather == CLOUDS_FEW) {
                    long currentTime = new Date().getTime(); // для дневного времени отображается солнце, для ночного - луна
                    if (currentTime >= sunrise && currentTime < sunset) {
                        skyPictureName = "z_cloud_few_white";
                    } else {
                        skyPictureName = "z_night_cloud_few_white";
                    }
                    break;
                }
                if (idWeather == CLOUDS_CLEAR) {
                    long currentTime = new Date().getTime();
                    if (currentTime >= sunrise && currentTime < sunset) {
                        skyPictureName = "z_clear_sky_white";
                    } else {
                        skyPictureName = "z_night_clear_sky_white";
                    }
                }
            }
        }
        return skyPictureName;
    }


    public String defineSkyWeatherIcon(long sunrise, long sunset, int idWeather) {
        String icon = "";

        int idWeatherGroup = idWeather / 100;

        switch (idWeatherGroup) {
            case GROUP_THUNDERSTORM: {
                icon = getString(R.string.weather_thunder);
                break;
            }
            case GROUP_DRIZZLE: {
                if (idWeather <= DRIZZLE_LIGHT) {
                    icon = getString(R.string.weather_drizzle);
                } else {
                    icon = getString(R.string.weather_rainy);
                }
                break;
            }
            case GROUP_RAIN: {
                if (idWeather <= RAIN_LIGHT) {
                    icon = getString(R.string.weather_drizzle);
                } else {
                    icon = getString(R.string.weather_rainy);
                }
                break;
            }
            case GROUP_SNOW: {
                icon = getString(R.string.weather_snowy);
                break;
            }
            case GROUP_FOG: {
                icon = getString(R.string.weather_foggy);
                break;
            }
            case GROUP_CLOUDS: {
                if (idWeather > CLOUDS_BROKEN) {
                    icon = getString(R.string.weather_cloudy);
                    break;
                }
                if (idWeather == CLOUDS_BROKEN) {
                    icon = getString(R.string.weather_cloudy);
                    break;
                }
                if (idWeather == CLOUDS_FEW) {
                    long currentTime = new Date().getTime(); // для дневного времени отображается солнце, для ночного - луна
                    if (currentTime >= sunrise && currentTime < sunset) {
                        icon = getString(R.string.weather_sunny);
                    } else {
                        icon = getString(R.string.weather_clear_night);
                    }
                    break;
                }
                if (idWeather == CLOUDS_CLEAR) {
                    long currentTime = new Date().getTime();
                    if (currentTime >= sunrise && currentTime < sunset) {
                        icon = getString(R.string.weather_sunny);
                    } else {
                        icon = getString(R.string.weather_clear_night);
                    }
                }
            }
        }
        return icon;
    }
}
