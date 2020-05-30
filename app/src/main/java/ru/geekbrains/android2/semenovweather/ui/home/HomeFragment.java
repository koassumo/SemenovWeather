package ru.geekbrains.android2.semenovweather.ui.home;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONException;
import org.json.JSONObject;

import ru.geekbrains.android2.semenovweather.R;

public class HomeFragment extends Fragment {

    private TextView townTextView;
    private TextView temperatureTextView;
    private TextView pressureTextView;
    private TextView windTextView;
    private TextView skyTextView;
    private TextView lastUpdateTextView;
    private ImageView skyImageView;
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
        skyTextView = view.findViewById(R.id.lastUpdateTextView);
        lastUpdateTextView = view.findViewById(R.id.lastUpdateTextView);
        skyImageView = view.findViewById(R.id.skyImageView);

        changeTownBtn = view.findViewById(R.id.changeTownBtn);

        initFonts();
        updateWeatherData(townTextView.getText().toString());
        setOnChangeTownBtnClick();
    }

    private void initFonts(){
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"); //если в MainActivity, то getActivity(). не нужен
        skyTextView.setTypeface(weatherFont);
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
                            RenderWeatherData renderWeather = new RenderWeatherData(jsonObject);
                            try {
                                townTextView.setText(renderWeather.getPlaceName());
                                temperatureTextView.setText(renderWeather.getCurrentTemp());
                                Toast.makeText(getActivity(), String.format("Введен город: %s", renderWeather.getCurrentTemp()), Toast.LENGTH_SHORT)
                                        .show();
                                pressureTextView.setText(renderWeather.getPressure());
                                windTextView.setText(renderWeather.getWind());
                                lastUpdateTextView.setText(renderWeather.getTimeUpdatedText());
                                Resources res = getResources();
                                int resID = res.getIdentifier(renderWeather.getSkyImage(), "drawable", getContext().getPackageName());
                                skyImageView.setImageResource(resID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }.start();
    }
}
