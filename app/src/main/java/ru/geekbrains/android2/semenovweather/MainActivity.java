package ru.geekbrains.android2.semenovweather;

import android.content.BroadcastReceiver;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import ru.geekbrains.android2.semenovweather.database.DatabaseHelper;
import ru.geekbrains.android2.semenovweather.ui.home.IFragmentList;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private IFragmentList fragmentList;
    private BroadcastReceiver batteryReceiver = new BatteryReceiver();
//    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
//        initDB();
        initDrawer();
//        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
    }

//    private void initDB() {
//        database = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
//    }

//    public void setHomeFragmentList(HomeFragment fragment){
//        fragmentList = fragment;
//    }
//
//    public void setOptionsFragmentList(selectOptionsFragment fragment){
//        fragmentList = fragment;
//    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_options, R.id.nav_help)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // при открытии приложения отображает текст из пуш-уведомления
    @Override
    protected void onStart() {
        super.onStart();
        try {
            String data = (String) getIntent().getExtras().get("ticketId");
            Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        } catch (NullPointerException exc) {
            Log.e("TAG", "NullPointer in MainActivity! First launch?");
        }
    }

}
