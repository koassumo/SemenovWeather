package ru.geekbrains.android2.semenovweather.ui.home;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.Date;

import ru.geekbrains.android2.semenovweather.R;
import ru.geekbrains.android2.semenovweather.ui.home.data.WeatherRequestRestModel;

public class HomeFragment extends Fragment implements ListenerNewWeatherData{

    private TextView townTextView;
    private TextView temperatureTextView;
    private TextView pressureTextView;
    private TextView windTextView;
    private TextView skyTextView;
    private TextView lastUpdateTextView;
    private ImageView skyImageView;
    private View changeTownBtn;

    private final String townTextKey = "town_text_key";

    private final Handler handler = new Handler();
    Typeface weatherFont;

    private UpdateWeatherData updateWeatherData = new UpdateWeatherData(this);;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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

        initFonts();
        getSharedPrefs();

        updateWeatherData.updateByTown(townTextView.getText().toString());
        setOnChangeTownBtnClick();
    }


    private void initFonts() {
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"); //если в MainActivity, то getActivity(). не нужен
        skyTextView.setTypeface(weatherFont);
    }

    private void setOnChangeTownBtnClick() {
        changeTownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем билдер и передаем контекст приложения
                AlertDialogChangeTown();
            }
        });
    }

    private void AlertDialogChangeTown() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // (MainActivity.this) работать отказался
        // Вытащим макет диалога
        final View contentView = getLayoutInflater().inflate(R.layout.alert_dialog_to_know_town, null);
        // в билдере указываем заголовок окна (можно указывать как ресурс R.string., так и строку)
        builder.setTitle("Город")
                // Установим макет диалога (можно устанавливать любой view)
                .setMessage("Введите название города")
                // можно указать и пиктограмму
                .setIcon(R.mipmap.ic_launcher_round)
                .setView(contentView)
                // запре на клик вне окна и на выход кнопкой back
                .setCancelable(false)
                // устанавливаем кнопку (название кнопки также можно задавать строкой)
                .setPositiveButton("Готово", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = contentView.findViewById(R.id.editText);
                        Toast.makeText(getActivity(), String.format("Введен город: %s", editText.getText().toString()), Toast.LENGTH_SHORT)
                                .show();
                        townTextView.setText(editText.getText().toString());
                        setSharedPrefs();
                        updateWeatherData.updateByTown(editText.getText().toString());
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setSharedPrefs() {
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

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // (MainActivity.this) работать отказался
        builder.setTitle(R.string.exclamation)
                .setMessage(R.string.press_button)
                .setIcon(R.mipmap.ic_launcher_round)
                .setCancelable(false)
                .setPositiveButton(R.string.button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Кнопка нажата", Toast.LENGTH_SHORT).show();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showWeatherData(WeatherRequestRestModel model) {
        townTextView.setText(model.name + ", " + model.sys.country);
        temperatureTextView.setText("" + model.main.temp);
        pressureTextView.setText(model.main.pressure + "mm");
        windTextView.setText(model.wind.speed + "m/s");

        // далее установка картинки погоды
        String skyPictureName;
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

        long sunrise = model.sys.sunrise * 1000;
        long sunset = model.sys.sunset * 1000;

        int idWeather = model.weather[0].id;
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
        Resources res = getResources();
        int resID = res.getIdentifier(skyPictureName, "drawable", getContext().getPackageName());
        skyImageView.setImageResource(resID);
    }
}
