package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MasterSlaveActivity extends AppCompatActivity {

    private final static String TAG = "MasterSlaveActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_slave);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Master Slave Device");

        VariablesAndMethods.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        VariablesAndMethods.bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Log.e(TAG, "Device is in discoverable mode");

        AcceptThread acceptThread = new AcceptThread(VariablesAndMethods.bluetoothAdapter);
        acceptThread.start();
        Log.e(TAG, "AcceptThread has started");



        final Button openForConnections = findViewById(R.id.buttonOpenForConnections);
        openForConnections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariablesAndMethods.bluetoothAdapter.cancelDiscovery();
                startActivity(new Intent(MasterSlaveActivity.this, ShowDevicesActivity.class));
            }
        });

        final Button connectToDevices = findViewById(R.id.buttonConnectToDevices);
        connectToDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                VariablesAndMethods.deviceName = device.getName();
                VariablesAndMethods.deviceHardwareAddress = device.getAddress();
                Log.e("BroadcastReceiver", "deviceName: " + VariablesAndMethods.deviceName + " Mac Address: " + VariablesAndMethods.deviceHardwareAddress);
                if(!VariablesAndMethods.discoveredDeviceList.contains(VariablesAndMethods.deviceHardwareAddress)){
                    //    Log.e("BroadCastReceiver", discoveredDeviceList.toString());
                    //discoveredDeviceList.add(deviceName + " " + deviceHardwareAddress);
                    VariablesAndMethods.discoveredDeviceList.add(VariablesAndMethods.deviceName + " " + VariablesAndMethods.deviceHardwareAddress);
                }
            }
        }
    };
}
