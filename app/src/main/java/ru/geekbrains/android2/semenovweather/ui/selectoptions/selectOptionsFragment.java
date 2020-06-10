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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ru.geekbrains.android2.semenovweather.R;

public class selectOptionsFragment extends Fragment {

    TextView townEditText;
    Button goHomeFragmentBtn;
    Button goHelpFragmentBtn;
    private final String townTextKey = "town_text_key";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_options, container, false);
        townEditText = root.findViewById(R.id.townSelectEditText);
        goHomeFragmentBtn = root.findViewById(R.id.goBackHomeFragmentBtn);
        goHelpFragmentBtn = root.findViewById(R.id.goHelpFragmentBtn);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnGoHomeFragmentBtnClick();
        setOnGoHelpFragmentBtnClick();
        readSharedPrefs();
    }

    private void setOnGoHomeFragmentBtnClick() {
        goHomeFragmentBtn.setOnClickListener(v -> {
            saveSharedPrefs();
            Bundle bundle = new Bundle();
            bundle.putString("arg", "data");
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_options_to_nav_home, bundle);
        });
    }

    private void setOnGoHelpFragmentBtnClick() {
        goHelpFragmentBtn.setOnClickListener(v -> {
            saveSharedPrefs();
            Bundle bundle = new Bundle();
            bundle.putString("arg", "data");
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_options_to_nav_help, bundle);
        });
    }

    private void saveSharedPrefs() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = defaultPrefs.edit();
        String text = townEditText.getText().toString();
        editor.putString(townTextKey, text);
        editor.apply();
    }

    private void readSharedPrefs() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String text = defaultPrefs.getString(townTextKey, "");
        townEditText.setText(text);
    }
}
