package ru.geekbrains.android2.semenovweather.ui.selectoptions;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
        setOnKeyEnterAfterTypeTown();
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

    private void setOnKeyEnterAfterTypeTown() {
        townEditText.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (townEditText.getText().toString().equals("")) AlertDialogChangeTown();
                else {
                    saveSharedPrefs();
                    Bundle bundle = new Bundle();
                    bundle.putString("arg", "data");
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.action_nav_options_to_nav_home, bundle);
                }
                return true;
            }
            return false;
        });
    }

    private void AlertDialogChangeTown() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // (MainActivity.this) работать отказался
        // Вытащим макет диалога
        final View contentView = getLayoutInflater().inflate(R.layout.alert_dialog_to_know_town, null);
        // в билдере указываем заголовок окна (можно указывать как ресурс R.string., так и строку)
        builder.setTitle(R.string.alert_title_no_town)
                // Установим макет диалога (можно устанавливать любой view)
                .setMessage(R.string.alert_msg_input_town)
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
                        townEditText.setText(editText.getText().toString());
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
