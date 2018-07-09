package com.example.a28062.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    final static String CHANGE_NETWORK = "android.net.conn.CONNECTIVITY_CHANGE";
    final static String SCREEN_ON = "android.intent.action.SCREEN_ON";
    private IntentFilter intentFilter;
    private NetworkChangeReceive changeReceive;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction(CHANGE_NETWORK);
        changeReceive = new NetworkChangeReceive();
        registerReceiver(changeReceive,intentFilter);
        button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(changeReceive);
    }

    class NetworkChangeReceive extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!= null&&networkInfo.isAvailable()) {
                Log.e("MainActivity", "网络已连接");
            }else {
                Log.e("MainActivity", "网络已中断");
            }
        }
    }

}
