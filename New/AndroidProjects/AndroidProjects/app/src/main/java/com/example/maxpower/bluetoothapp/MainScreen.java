package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainScreen extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    private final static String TAG = "MainScreen";
    public static BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
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
                    //go to master activity
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
                    //go to slave activity
                    Log.e(TAG, "buttonSlave clicked");
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
}
