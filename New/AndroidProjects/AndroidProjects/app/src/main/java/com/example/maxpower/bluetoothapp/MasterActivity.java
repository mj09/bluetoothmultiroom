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
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MasterActivity extends AppCompatActivity {

    private final static String TAG = "MasterActivity";
    public static BluetoothAdapter bluetoothAdapter;
    String deviceName;
    BluetoothDevice btdevice;
    public ArrayList<String> discoveredDeviceList = new ArrayList<>();
    String deviceHardwareAddress;
    public int deviceCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
      //  final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.song);
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
                startActivity(new Intent(MasterActivity.this, ShowDevicesActivity.class));

               // deviceName = ConnectThread.getDeviceName();
               // Toast.makeText(MasterActivity.this, "Connected to " + deviceName, Toast.LENGTH_SHORT).show();
            }
        });

        final Button startAudioStreaming = findViewById(R.id.buttonStartStreaming);
        startAudioStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        mediaPlayer.start();
                Log.e(TAG, "Mediaplayer started");
            }
        });

        final Button showConnectedDevices = findViewById(R.id.buttonShowConnectedDevices);
        showConnectedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "showConnectedDevices has started");
            }
        });

        final Button stopAudioStreaming = findViewById(R.id.buttonStopStreaming);
        stopAudioStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      mediaPlayer.stop();
                Log.e(TAG, "Mediaplayer stopped");
            }
        });
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceName = device.getName();
                deviceHardwareAddress = device.getAddress();
                Log.e("BroadcastReceiver", "deviceName: " + deviceName + " Mac Address: " + deviceHardwareAddress);
                if(!discoveredDeviceList.contains(deviceHardwareAddress)){
                    Log.e("BroadCastReceiver", discoveredDeviceList.toString());
                    discoveredDeviceList.add(deviceName + " " + deviceHardwareAddress);
                    deviceCounter++;
                    intent.putExtra(deviceName + deviceHardwareAddress, discoveredDeviceList);
                }
            }
        }
    };


}
