package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.FileInputStream;
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
    private byte[] buffer;
    public static boolean connectionHandlerBoolean = true;
    private String hello = "Hello";

    public ConnectionHandler(BluetoothSocket socket) {
        bluetoothSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

     /*   try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error when creating inputstream");
        }*/

        try {
            tmpOut = socket.getOutputStream();

        } catch (IOException e) {
            Log.e(TAG, "Error when creating outputstream");
        }

    //    inputStream = tmpIn;
        outputStream = tmpOut;
    }

    public void run() {
        //  buffer = new byte[1024];
        //  int numBytes;
        while (connectionHandlerBoolean) {
          /*  try {
                numBytes = inputStream.read(buffer);
                Log.e(TAG, buffer.toString());
                //use input to something
            } catch (IOException e) {
                Log.e(TAG, "Inputstream disconnected", e);
                break;

            }*/

      //  write(hello.getBytes());
        }
    }

    public void write(byte[] bytes) {

        try {
            outputStream.write(bytes);
        }catch (IOException e) {
            Log.e(TAG, "Error when sending data ", e);
        }
    }



    public boolean getConnectionHandlerBoolean() {
        return connectionHandlerBoolean;
    }

    public void setConnectionHandlerBoolean(boolean x) {
        connectionHandlerBoolean = x;
    }
}

