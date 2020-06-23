package ru.geekbrains.android2.semenovweather.ui.selectoptions;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.android2.semenovweather.MainActivity;
import ru.geekbrains.android2.semenovweather.R;
import ru.geekbrains.android2.semenovweather.ui.home.IFragmentList;
import ru.geekbrains.android2.semenovweather.ui.home.RecyclerDataAdapterDays;

public class selectOptionsFragment extends Fragment implements IFragmentList {
    TextView townEditText;
    Button goHomeFragmentBtn;
    Button goHelpFragmentBtn;
    private final String townTextKey = "town_text_key";

    private RecyclerDataAdapterTowns adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_options, container, false);
        iniList(root);
//        ((MainActivity)requireActivity()).setOptionsFragmentList(this);
//        setHasOptionsMenu(true);
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

    private void iniList(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.recycleViewTowns);

        // Эта установка повышает производительность системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Устанавливаем адаптер
        adapter = new RecyclerDataAdapterTowns(initData(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(String.format("Element %d", i));
        }
        return list;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
        handleMenuItemClick(item);
        int id = item.getItemId();
        switch (id) {
            case R.id.add_context:
                adapter.addItem(String.format("New element %d", adapter.getItemCount()));
                return true;
            case R.id.update_context:
                adapter.updateItem(String.format("Updated element %d", adapter.getMenuPosition()), adapter.getMenuPosition());
                return true;
            case R.id.remove_context:
                adapter.removeItem(adapter.getMenuPosition());
                return true;
            case R.id.clear_context:
                adapter.clearItems();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void addItem(String str) {
        adapter.addItem(str);
    }

    @Override
    public void clearItems() {
        adapter.clearItems();
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.add_context: {
                //menuListAdapter.addItem();
                break;
            }
//            case R.id.menu_search: {
//                if(searchEditText.getVisibility() == View.VISIBLE) {
//                    searchEditText.setVisibility(View.GONE);
//                    Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_name));
//                } else {
//                    Objects.requireNonNull(getSupportActionBar()).setTitle("");
//                    searchEditText.setVisibility(View.VISIBLE);
//                    searchEditText.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//                            //Вам приходит на вход текст поиска - ищем его - в бд, через АПИ (сервер в инете) и т.д.
//                            Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
            case R.id.update_context: {
                //menuListAdapter.editItem(2500);
                break;
            }
            case R.id.remove_context: {
                //menuListAdapter.removeElement();
                break;
            }
            case R.id.clear_context: {
                //menuListAdapter.clearList();
                break;
            }
            case 12345: {
                Toast.makeText(getContext(), "Был нажат доп. элемент попап меню",
                        Toast.LENGTH_SHORT).show();
            }
//            default: {
//                if(id != R.id.menu_more) {
//                    Toast.makeText(getContext(), getString(R.string.action_not_found),
//                            Toast.LENGTH_SHORT).show();
//                }
//            }

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); 
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
