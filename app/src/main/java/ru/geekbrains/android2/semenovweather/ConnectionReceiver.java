package ru.geekbrains.android2.semenovweather;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ConnectionReceiver extends BroadcastReceiver {

    private int messageId = 1000;


    @Override
    public void onReceive(Context context, Intent intent) {

        String wifiStatus = "wifi is connected";

        if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if(networkInfo.isConnected()) {
                // Wifi is connected
            }
        } else if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI &&
                    ! networkInfo.isConnected()) {
                wifiStatus = "Wifi is disconnected";
            }
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ConnectivityActionReceiver")
                .setContentText(wifiStatus);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());

        Toast.makeText(context.getApplicationContext(), wifiStatus, Toast.LENGTH_LONG).show();
    }
}