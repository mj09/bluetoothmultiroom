package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by Michael on 06-03-2018.
 */

public class ConnectedThread extends Thread {
    private final static String TAG = "ConnectedThread";
    private final BluetoothSocket mmSocket;
    private final InputStream mmInstream;
    private final OutputStream mmOutStream;

    public ConnectedThread(BluetoothSocket socket, String socketType) {
        Log.e(TAG, "create ConnectedThread: " + socketType);
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "temp socket not created");
        }

        mmInstream = tmpIn;
        mmOutStream = tmpOut;

    }

    public void run() {
        Log.i(TAG, "Begin mConnectThread");
        byte[] buffer = new byte[1024];
        int bytes;

        while(true) {
            try {
                bytes = mmInstream.read(buffer);
                Log.e(TAG, Arrays.toString(buffer));
            } catch (IOException e) {
                Log.e(TAG, "Disconnected", e);
            }
        }
    }

    public void write(byte[] buffer) {
        try {
            String test = "test";
            buffer = test.getBytes();
            mmOutStream.write(buffer);
            Log.e(TAG, Arrays.toString(buffer));
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }

    }
}

