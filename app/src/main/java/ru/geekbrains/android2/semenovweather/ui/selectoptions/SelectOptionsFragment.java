package ru.geekbrains.android2.semenovweather.ui.selectoptions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.geekbrains.android2.semenovweather.Constants;
import ru.geekbrains.android2.semenovweather.MainActivity;
import ru.geekbrains.android2.semenovweather.R;
import ru.geekbrains.android2.semenovweather.database.DatabaseHelper;
import ru.geekbrains.android2.semenovweather.database.NotesTable;

public class SelectOptionsFragment extends Fragment implements IFragmentList {
//    private final String HISTORY_LIST = "history_list";
    TextView townEditText;
    Button goHomeFragmentBtn;
    Button goHelpFragmentBtn;
    CheckBox pressureCheckBox;
    CheckBox windCheckBox;
    SQLiteDatabase database;

    private RecyclerDataAdapterTowns adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_options, container, false);
        initDB();
        iniList(root);
//        ((MainActivity)requireActivity()).setOptionsFragmentList(this);
//        setHasOptionsMenu(true);
        townEditText = root.findViewById(R.id.townSelectEditText);
        goHomeFragmentBtn = root.findViewById(R.id.goBackHomeFragmentBtn);
        goHelpFragmentBtn = root.findViewById(R.id.goHelpFragmentBtn);
        pressureCheckBox = root.findViewById(R.id.pressureCheckBox);
        windCheckBox = root.findViewById(R.id.windCheckBox);
        return root;
    }

    private void initDB() {
        database = new DatabaseHelper(getContext()).getWritableDatabase();
//        NotesTable.deleteAll(database);
//        NotesTable.addNote("dsf", database);
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
        adapter = new RecyclerDataAdapterTowns(initData(),this, database);
        recyclerView.setAdapter(adapter);

        // Установим слушателя
        adapter.SetOnItemClickListener(new RecyclerDataAdapterTowns.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                townEditText.setText(String.format("%s", ((TextView) view).getText(), position));
                //Toast.makeText(getActivity(), String.format("%s - %d", ((TextView) view).getText(), position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> initData() {
        //List<String> list = new ArrayList<>();
        //NotesTable.addNote(elements.size(), database);
        //Set<String> historyList = readSharedPrefsList();
        //Set<String> historyList = new HashSet<>();
        //List<String> list = new ArrayList<String>(historyList);
//        List<String> list = new ArrayList<>();
//        List<Integer> listInt = new ArrayList<>(NotesTable.getAllNotes(database));
        List<String> list = new ArrayList<>(NotesTable.getAllNotes(database));
        Collections.reverse(list);
        //        for (Integer i: listInt) {
//            list.add(String.valueOf(i));
//        }
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
//            case R.id.remove_context:
//                adapter.removeItem(adapter.getMenuPosition());
//                return true;
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
                    hideKeyboard((Activity) getActivity());
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

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = activity.getCurrentFocus();
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = contentView.findViewById(R.id.editText);
                        Toast.makeText(getActivity(), String.format(getString(R.string.town_is_entered), editText.getText().toString()), Toast.LENGTH_SHORT)
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
                                Toast.makeText(getActivity(), getString(R.string.key_is_pressed), Toast.LENGTH_SHORT).show();
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
        String textNewTown = townEditText.getText().toString();
        editor.putString(Constants.TOWN_TEXT_KEY, textNewTown);
        Boolean isPressureCheckBoxChecked = pressureCheckBox.isChecked();
        editor.putBoolean(Constants.PRESSURE_CHECKBOX_KEY, isPressureCheckBoxChecked);
        Boolean isWindCheckBoxChecked = windCheckBox.isChecked();
        editor.putBoolean(Constants.WIND_CHECKBOX_KEY, isWindCheckBoxChecked);
        // сохранение сета (аррей не берет)
//        Set <String> historyList;
//        historyList = new HashSet<>(adapter.readList());
//        historyList.add(textNewTown);
//        editor.putStringSet(HISTORY_LIST, historyList);
        editor.apply();
    }

    private void readSharedPrefs() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String text = defaultPrefs.getString(TOWN_TEXT_KEY, "");
        townEditText.setText(text);
        boolean isPressureActivated = defaultPrefs.getBoolean(Constants.PRESSURE_CHECKBOX_KEY, true);
        pressureCheckBox.setChecked(isPressureActivated);
        boolean isWindActivated = defaultPrefs.getBoolean(Constants.WIND_CHECKBOX_KEY, true);
        windCheckBox.setChecked(isWindActivated);
    }

//    private HashSet<String> readSharedPrefsList() {
//        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//        //Boolean isSetExist = defaultPrefs.getBoolean("is_set_exist", false);
//        Set<String> defHistoryList = new HashSet<>();
//        defHistoryList.add("");
//        Set<String> historyList = defaultPrefs.getStringSet(HISTORY_LIST, defHistoryList);
//        return (HashSet<String>) historyList;
//    }
}
