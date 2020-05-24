package ru.geekbrains.android2.semenovweather.ui.home;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.android2.semenovweather.MainActivity;
import ru.geekbrains.android2.semenovweather.R;

public class HomeFragment extends Fragment {

    private TextView townTextView;
    private TextView temperatureTextView;
    private TextView pressureTextView;
    private TextView windTextView;
    private TextView skyTexView;

    private View changeTownBtn;

    private final Handler handler = new Handler();
    Typeface weatherFont;

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
        skyTexView = view.findViewById(R.id.skyTextView);

        changeTownBtn = view.findViewById(R.id.changeTownBtn);

        initFonts();
        updateWeatherData(townTextView.getText().toString());
        setOnChangeTownBtnClick();
    }

    private void initFonts(){
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"); //если в MainActivity, то getActivity(). не нужен
        skyTexView.setTypeface(weatherFont);
    }

    private void setOnChangeTownBtnClick() {
        changeTownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем билдер и передаем контекст приложения
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
                                updateWeatherData(editText.getText().toString());
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
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

    private void updateWeatherData(final String town) {
        new Thread() {
            @Override
            public void run() {
                final JSONObject jsonObject = WeatherDataLoader.getJSONData(town);
                if(jsonObject == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showAlertDialog();
                            Toast.makeText(getContext(), R.string.place_not_found,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(jsonObject);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject jsonObject) {
        //Log.d(LOG_TAG, "json: " + jsonObject.toString());
        try {
            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");

            setPlaceName(jsonObject);
            setDetails(details, main);
            setCurrentTemp(main);
            setUpdatedText(jsonObject);
            setWeatherIcon(details.getInt("id"),
                    jsonObject.getJSONObject("sys").getLong("sunrise") * 1000,
                    jsonObject.getJSONObject("sys").getLong("sunset") * 1000);
        } catch (Exception exc) {
            exc.printStackTrace();
            //Log.e(LOG_TAG, "One or more fields not found in the JSON data");
        }
    }


    private void setPlaceName(JSONObject jsonObject) throws JSONException {
        String cityText = jsonObject.getString("name").toUpperCase() + ", "
                + jsonObject.getJSONObject("sys").getString("country");
        townTextView.setText(cityText);
    }

    private void setDetails(JSONObject details, JSONObject main) throws JSONException {
        String detailsText = details.getString("description").toUpperCase() + "\n" + main.getString("pressure") + "hPa";
        pressureTextView.setText(detailsText);
    }

    private void setCurrentTemp(JSONObject main) throws JSONException {
        String currentTextText = String.format(Locale.getDefault(), "%.2f", main.getDouble("temp"));
        temperatureTextView.setText(currentTextText);
    }

    private void setUpdatedText(JSONObject jsonObject) throws JSONException {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String updateOn = dateFormat.format(new Date(jsonObject.getLong("dt") * 1000));
        String updatedText = "Last update: " + updateOn;
        windTextView.setText("100");
    }

    private void setWeatherIcon(int id, long sunrise, long sunset) {
        String icon = "";
        String skyPictureName = "snow_white";

        switch (id / 100) {
            case 2: {
                icon = getString(R.string.weather_thunder); skyPictureName = "z_thunder_white";
                break;
            }
            case 3: {
                if (id < 302) {
                    icon = getString(R.string.weather_drizzle); skyPictureName = "z_rain_light_white";
                } else {
                    icon = getString(R.string.weather_drizzle); skyPictureName = "z_rain_shower_white";
                }
                break;
            }
            case 5: {
                if (id < 502) {
                    icon = getString(R.string.weather_rainy); skyPictureName = "z_rain_light_white";
                } else {
                    icon = getString(R.string.weather_rainy); skyPictureName = "z_rain_shower_white";
                }
                break;
            }
            case 6: {
                icon = getString(R.string.weather_snowy); skyPictureName = "z_snow_white";
                break;
            }
            case 7: {
                icon = getString(R.string.weather_foggy); skyPictureName = "z_foggy_white";
                break;
            }
            case 8: {
                if (id > 802) {
                    icon = getString(R.string.weather_foggy); skyPictureName = "z_cloud_overcast_white";
                    break;
                }
                if (id == 802) {
                    icon = getString(R.string.weather_foggy); skyPictureName = "z_cloud_broken_white";
                    break;
                }
                if (id == 801 ) {
                    long currentTime = new Date().getTime();
                    if(currentTime >= sunrise && currentTime < sunset) {
                        icon = "\u2600"; skyPictureName = "z_cloud_few_white";
                    } else {
                        icon = getString(R.string.weather_clear_night); skyPictureName = "z_night_cloud_few_white";
                    }
                    break;
                }
                if (id == 800 ) {
                    long currentTime = new Date().getTime();
                    if(currentTime >= sunrise && currentTime < sunset) {
                        icon = "\u2600"; skyPictureName = "z_clear_sky_white";
                    } else {
                        icon = getString(R.string.weather_clear_night); skyPictureName = "z_night_clear_sky_white";
                    }
                }
            }
        }
        skyTexView.setText(id + ", " + icon);
    }
}
