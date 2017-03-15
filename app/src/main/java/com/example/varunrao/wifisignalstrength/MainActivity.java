package com.example.varunrao.wifisignalstrength;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.*;
import android.net.wifi.*;
import java.util.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView signalDisplayer=(TextView)findViewById(R.id.signalDisplay);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED)
        {
            // Level of current connection
            int rssi = wifiManager.getConnectionInfo().getRssi();
            signalDisplayer.setText("Signal Strength is " + rssi + " dbM");
        }
        else
        {
            signalDisplayer.setText("Wifi not enabled!");
        }

        final Button updateData=(Button)findViewById(R.id.update);

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED)
                {
                    // Level of current connection
                    int rssi = wifiManager.getConnectionInfo().getRssi();
                    signalDisplayer.setText("Signal Strength is " + rssi + " dbM");
                }
                else
                {
                    signalDisplayer.setText("Wifi not enabled!");
                }
            }
        });
    }
}
