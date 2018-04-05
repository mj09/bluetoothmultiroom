package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class SlaveActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper());
    private final static String TAG = "SlaveActivity";
    public static BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slave);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Slave Device");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Log.e(TAG, "Device is in discoverable mode");

        AcceptThread acceptThread = new AcceptThread(bluetoothAdapter);
        acceptThread.start();
        Log.e(TAG, "AcceptThread has started");
    }

    public void connectionAcceptMessage(String x) {
        Toast.makeText(SlaveActivity.this, "Connection established with " + x, Toast.LENGTH_LONG);
    }



}
