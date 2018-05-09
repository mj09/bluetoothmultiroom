package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Michael on 04-04-2018.
 */

public class ConnectionHandler extends Thread {
    private static final String TAG = "ConnectionHandler";
    private final BluetoothSocket bluetoothSocket;
    //   private final InputStream inputStream;
    private final OutputStream outputStream;
    boolean startAudioOnce = true;
    public static boolean connectionHandlerBoolean = true;
    public static int streamers = 0;

    public ConnectionHandler(BluetoothSocket socket) {
        bluetoothSocket = socket;
        OutputStream tmpOut = null;
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error when creating outputstream");
        }
        outputStream = tmpOut;
    }

    public void run() {
        write();
    }

    public void write() {
        streamers++;
        MainScreen.hasSent.add(false);
        Log.e(TAG, "Number of playing devices: " + streamers);
        SingletonAudioStream.getSingletonAudioStream().streamMusic(outputStream);
        Log.e(TAG, "Hello");
    }



    public boolean getConnectionHandlerBoolean() {
        return connectionHandlerBoolean;
    }

    public void setConnectionHandlerBoolean(boolean x) {
        connectionHandlerBoolean = x;
    }
}

