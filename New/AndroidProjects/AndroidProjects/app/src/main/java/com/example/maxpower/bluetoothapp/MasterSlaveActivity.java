package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MasterSlaveActivity extends AppCompatActivity {

    public static BluetoothAdapter bluetoothAdapter;
    private final static String TAG = "MasterSlaveActivity";
    BluetoothDevice btdevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_slave);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Master Slave Device");

        final Button openForConnections = findViewById(R.id.buttonOpenForConnections);
        openForConnections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
                AcceptThread acceptThread = new AcceptThread(bluetoothAdapter);
                acceptThread.start();
                Log.e(TAG, "openForConnections has started");
            }
        });

        final Button connectToDevices = findViewById(R.id.buttonConnectToDevices);
        connectToDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectThread connectThread = new ConnectThread(btdevice);
                connectThread.start();
                Log.e(TAG, "connectToDevices has started");
            }
        });

        final Button showConnectedDevices = findViewById(R.id.buttonShowConnectedDevices);
        showConnectedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "showConnectedDevices has started");
            }
        });
    }
}
