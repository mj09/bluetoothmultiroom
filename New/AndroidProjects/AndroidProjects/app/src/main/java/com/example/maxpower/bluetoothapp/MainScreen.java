package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {
    public final static ArrayList<Boolean> hasSent = new ArrayList<>();
    public final static ArrayList<Integer> streamerList = new ArrayList<>();
    public final static int bufferSize = 88000;
    private final static int REQUEST_ENABLE_BT = 1;
    private final static String TAG = "MainScreen";
    public static BluetoothAdapter bluetoothAdapter;
    public static boolean isSlave;
    public static boolean control = true;
    public static boolean control2 = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getSampleRate();
        getOptimalBufferSize();
        ActionBar actionBar = getSupportActionBar();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000FF")));
        final Button buttonMaster = findViewById(R.id.buttonMasterDevice);
        buttonMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isEnabled()) {
                    Toast.makeText(MainScreen.this, "Enable Bluetooth to proceed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Bluetooth is not enabled");
                }
                else {
                    VariablesAndMethods.isMaster = true;
                    Log.e(TAG, "buttonMaster clicked");
                    startActivity(new Intent(MainScreen.this, MasterActivity.class));
                }
            }
        });

        final Button buttonSlave = findViewById(R.id.buttonSlave);
        buttonSlave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!bluetoothAdapter.isEnabled()) {
                    Toast.makeText(MainScreen.this, "Enable Bluetooth to proceed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Bluetooth is not enabled");
                }
                else {
                    Log.e(TAG, "buttonSlave clicked");
                    isSlave = true;
                    startActivity(new Intent(MainScreen.this, SlaveActivity.class));
                }
            }
        });

        final Button buttonMasterSlave = findViewById(R.id.buttonMasterSlave);
        buttonMasterSlave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!bluetoothAdapter.isEnabled()) {
                    Toast.makeText(MainScreen.this, "Enable Bluetooth to proceed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Bluetooth is not enabled");
                }
                else {
                    //go to master slave activity
                    Log.e(TAG, "buttonMasterSlave clicked");
                    VariablesAndMethods.isMaster = false;
                    isSlave = false;
                    startActivity(new Intent(MainScreen.this, MasterSlaveActivity.class));
                }
            }
        });

        final Button enableBluetooth = (Button)findViewById(R.id.buttonEnableBluetooth);
        enableBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableBluetooth();
            }
        });
    }

    public void EnableBluetooth() {

        if (bluetoothAdapter == null) {
            Log.e("EnableBluetooth()", "Device does not support Bluetooth");
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Log.e("EnableBluetooth()", "Bluetooth is enabled");
        }
        else{
            Toast.makeText(MainScreen.this, "Bluetooth is already enabled", Toast.LENGTH_SHORT).show();
        }
    }

    public void getSampleRate() {
        if(Build.VERSION.SDK_INT >= 17) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            String sampleRateStr = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            VariablesAndMethods.sampleRateofDevice = Integer.parseInt(sampleRateStr);
            if (VariablesAndMethods.sampleRateofDevice == 0)
                VariablesAndMethods.sampleRateofDevice = 44000;
            Log.e(TAG, "Samplerate" + VariablesAndMethods.sampleRateofDevice);
            Toast.makeText(MainScreen.this, "Samplerate of device: " + sampleRateStr, Toast.LENGTH_SHORT).show();
        }
    }

    public void getOptimalBufferSize() {
        if(Build.VERSION.SDK_INT >= 17) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            String framesPerBufferStr = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
            VariablesAndMethods.framesPerBuffer = Integer.parseInt(framesPerBufferStr);
            if (VariablesAndMethods.framesPerBuffer == 0)
                VariablesAndMethods.framesPerBuffer = 256;
            Toast.makeText(MainScreen.this, "FramesPerBuffer of device: " + framesPerBufferStr, Toast.LENGTH_LONG).show();
            }
        }


    public static void setStreamerList(int x) {
        streamerList.add(x);
    }
}
