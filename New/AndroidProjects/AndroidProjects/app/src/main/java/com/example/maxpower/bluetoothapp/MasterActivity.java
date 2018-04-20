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

import java.io.IOException;
import java.util.ArrayList;

public class MasterActivity extends AppCompatActivity {

    private final static String TAG = "MasterActivity";
    public static BluetoothAdapter bluetoothAdapter;
    String deviceName;
    public static ArrayList<String> discoveredDeviceList = new ArrayList<String>();
    String deviceHardwareAddress;
    public int deviceCounter = 0;
    public int arrayCounter = 0;
    public static String filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        filepath = Environment.getExternalStorageDirectory() + "/Music/soundfile.mp3";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Master Device");
        bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);
        final Button connectToDevices = findViewById(R.id.buttonConnectToDevices);
        connectToDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Show Devices Clicked");
                bluetoothAdapter.cancelDiscovery();
                startActivity(new Intent(MasterActivity.this, ShowDevicesActivity.class));

               // deviceName = ConnectThread.getDeviceName();
               // Toast.makeText(MasterActivity.this, "Connected to " + deviceName, Toast.LENGTH_SHORT).show();
            }
        });

        final Button startAudioStreaming = findViewById(R.id.buttonStartStreaming);
        startAudioStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean permissionGranted = ActivityCompat.checkSelfPermission(MasterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if(permissionGranted) {
                    streamMusic();
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
/*
        final Button stopAudioStreaming = findViewById(R.id.buttonStopStreaming);
        stopAudioStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      mediaPlayer.stop();
                Log.e(TAG, "Mediaplayer stopped");
            }
        });*/
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceName = device.getName();
                deviceHardwareAddress = device.getAddress();
               // Log.e("BroadcastReceiver", "deviceName: " + deviceName + " Mac Address: " + deviceHardwareAddress);
                if(!discoveredDeviceList.contains(deviceHardwareAddress)){
                //    Log.e("BroadCastReceiver", discoveredDeviceList.toString());
                    //discoveredDeviceList.add(deviceName + " " + deviceHardwareAddress);
                    deviceCounter++;
                    setList(deviceName + " " + deviceHardwareAddress);
                }
            }
        }
    };

    public ArrayList<String> getList() {
        return discoveredDeviceList;
    }

    public void setList(String x) {
        discoveredDeviceList.add(x);
        arrayCounter++;
    }

    public void streamMusic() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        filepath = Environment.getExternalStorageDirectory() + "/Music/soundfile.mp3";
        try {
            mediaPlayer.setDataSource(filepath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }
}
