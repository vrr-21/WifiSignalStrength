package com.example.varunrao.wifisignalstrength;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.*;
import android.net.wifi.*;
import java.util.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread t=new Thread(this);
        t.start();

    }
    @Override
    public void run() {
        Button next=(Button)findViewById(R.id.goToNext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,RSSIDetected.class);

                startActivity(i);
            }
        });
        try {
            final SQLiteDatabase sqlDB=openOrCreateDatabase("db123#4",MODE_PRIVATE,null);
            sqlDB.execSQL("CREATE TABLE IF NOT EXISTS RSSIs(EventNo int AUTO_INCREMENT,rssiVal int,timeMeasured VARCHAR)");
            sqlDB.execSQL("DELETE FROM RSSIs");
            final Vector<String> RSSIDetails=new <String>Vector();
            ListView listView = (ListView) findViewById(R.id.RSSIDisplay);
            final ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,RSSIDetails);
            listView.setAdapter(arrayAdapter);
            while(true)
            {
                //arrayAdapter.clear();
                //arrayAdapter.notifyDataSetChanged();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final TextView signalDisplayer=(TextView)findViewById(R.id.signalDisplay);

                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED)
                        {
                            // Level of current connection
                            int rssi = wifiManager.getConnectionInfo().getRssi();
                            signalDisplayer.setText("Signal Strength is " + rssi + " dbM");
                            System.out.println(rssi);
                            RSSIDetails.add(0,rssi+"dbMs@"+Calendar.getInstance().getTime());
                            arrayAdapter.notifyDataSetChanged();
                            sqlDB.execSQL("INSERT INTO RSSIs(rssiVal,timeMeasured) VALUES("+rssi+",'"+Calendar.getInstance().getTime()+"')");
                        }
                        else
                        {
                            signalDisplayer.setText("Wifi not enabled!");
                        }
                    }
                });
                /*Cursor getRssis=sqlDB.rawQuery("SELECT * FROM RSSIs ORDER BY timeMeasured DESC",null);
                RSSIDetails.clear();
                arrayAdapter.notifyDataSetChanged();
                int a;
                String b=null;
                getRssis.moveToFirst();
                if(getRssis!=null)
                {
                    do {
                        a=getRssis.getInt(1);
                        b=getRssis.getString(2);
                        String n=a+"dbMs@"+b;
                        RSSIDetails.add(n);
                    }
                    while (getRssis.moveToNext());
                }*/
                Thread.sleep(1000);
                //arrayAdapter.addAll(RSSIDetails);
                /*ListView listView = (ListView) findViewById(R.id.RSSIDisplay);
                ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,RSSIDetails);
                listView.setAdapter(arrayAdapter);
                */
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}