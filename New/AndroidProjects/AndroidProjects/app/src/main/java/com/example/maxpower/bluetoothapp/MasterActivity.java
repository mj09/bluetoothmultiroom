package com.example.maxpower.bluetoothapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MasterActivity extends AppCompatActivity {

    private final static String TAG = "MasterActivity";
    public static File soundFile = new File(VariablesAndMethods.filePath);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VariablesAndMethods.filePath = Environment.getExternalStorageDirectory() + "/Music/song1.wav";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        VariablesAndMethods.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Master Device");
        VariablesAndMethods.bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);

        final Button connectToDevices = findViewById(R.id.buttonConnectToDevices);
        connectToDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Show Devices Clicked");
                startActivity(new Intent(MasterActivity.this, ShowDevicesActivity.class));
            }
        });

        final Button startAudioStreaming = findViewById(R.id.buttonStartStreaming);
        startAudioStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean permissionGranted = ActivityCompat.checkSelfPermission(MasterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if(permissionGranted) {
                }
                else
                    ActivityCompat.requestPermissions(MasterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
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
