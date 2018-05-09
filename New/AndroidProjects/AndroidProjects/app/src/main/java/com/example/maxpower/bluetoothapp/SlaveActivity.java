package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;

public class SlaveActivity extends AppCompatActivity {


    private final static String TAG = "SlaveActivity";
    public static BluetoothAdapter bluetoothAdapter;
    private Handler handler = new Handler();
    private int updateStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slave);
        handler.postDelayed(runnable, 100);
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
        String y = "Status of connection:";
        TextView updateTextView = findViewById(R.id.updateTextView);
        updateTextView.append(y);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateStatus = AcceptThread.getUpdateInteger();
            if (updateStatus == 1) {
                TextView updateTextView = findViewById(R.id.updateTextView);
                updateTextView.append(" \n");
                String status = "Waiting for a connection";
                updateTextView.append(status);
                AcceptThread.setUpdateInteger(0);
            }
            if (updateStatus == 2) {
                TextView updateTextView = findViewById(R.id.updateTextView);
                updateTextView.append(" \n");
                String status = "Device " + AcceptThread.getRemoteDeviceName() + " has connected";
                updateTextView.append(status);
                AcceptThread.setUpdateInteger(0);
            }
            if (updateStatus == 3) {
                TextView updateTextView = findViewById(R.id.updateTextView);
                updateTextView.append(" \n");
                String status = "Socket accept method failed";
                updateTextView.append(status);
                AcceptThread.setUpdateInteger(0);
            }
            if (updateStatus == 4) {
                TextView updateTextView = findViewById(R.id.updateTextView);
                updateTextView.append(" \n");
                String status = "Socket is closed";
                updateTextView.append(status);
                AcceptThread.setUpdateInteger(0);
            }
            if (updateStatus == 5) {
                TextView updateTextView = findViewById(R.id.updateTextView);
                updateTextView.append(" \n");
                String status = "Closing socket";
                updateTextView.append(status);
                AcceptThread.setUpdateInteger(0);
            }
            handler.postDelayed(runnable, 100);
        }
    };
}
