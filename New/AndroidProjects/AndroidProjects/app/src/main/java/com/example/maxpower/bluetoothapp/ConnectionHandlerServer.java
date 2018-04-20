package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class ConnectionHandlerServer extends Thread {
    private static final String TAG = "ConnectionHandlerServer";
    private final BluetoothSocket bluetoothSocket;
    private final InputStream inputStream;
    private byte[] buffer;
    public static boolean connectionHandlerBoolean = true;
    private String hello = "Hello";


    public ConnectionHandlerServer(BluetoothSocket socket) {
        bluetoothSocket = socket;
        InputStream tmpIn = null;
       // OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            Log.e(TAG, "Inputstream created");
        } catch (IOException e) {
            Log.e(TAG, "Error when creating inputstream");
        }

     /*   try {
            tmpOut = socket.getOutputStream();

        } catch (IOException e) {
            Log.e(TAG, "Error when creating outputstream");
        }*/

        inputStream = tmpIn;
      //  outputStream = tmpOut;
    }

    public void run() {
       Log.e(TAG, "Begin ConnectionHandlerServer");
       byte[] buffer = new byte[1024];
       int bytes;

       while(connectionHandlerBoolean) {
           try {
               if(inputStream.available() > 0) {
                   bytes = inputStream.read(buffer);
                   Log.e(TAG, "Received msg " + Integer.toString(bytes));
               }
               else SystemClock.sleep(100);
           } catch (IOException e) {
               Log.e(TAG, "disconnected", e);
               break;
           }
       }

    }
    public boolean getConnectionHandlerBoolean() {
        return connectionHandlerBoolean;
    }

    public void setConnectionHandlerBoolean(boolean x) {
        connectionHandlerBoolean = x;
    }
}

