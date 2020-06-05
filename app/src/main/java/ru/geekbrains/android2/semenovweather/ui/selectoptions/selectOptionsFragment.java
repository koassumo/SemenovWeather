package ru.geekbrains.android2.semenovweather.ui.selectoptions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.geekbrains.android2.semenovweather.R;

public class selectOptionsFragment extends Fragment {

    TextView townEditText;
    Button goBackMainActivityBtn;
    private final String townTextKey = "town_text_key";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        townEditText = root.findViewById(R.id.townSelectEditText);
        goBackMainActivityBtn = root.findViewById(R.id.goBackMainActivityBtn);
        return root;
    }

    private void setOnChangeTownBtnClick() {
        goBackMainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // ????????????????????????????

            }
        });
    }

    private void setSharedPrefs() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = defaultPrefs.edit();
        String text = townEditText.getText().toString();
        editor.putString(townTextKey, text);
        editor.apply();

    }

    private void getSharedPrefs() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String text = defaultPrefs.getString(townTextKey, "");
        townEditText.setText(text);
    }
}
