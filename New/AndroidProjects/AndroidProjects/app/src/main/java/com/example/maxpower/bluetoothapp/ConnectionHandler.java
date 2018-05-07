package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

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
        //while (connectionHandlerBoolean) {
          /*  try {
                numBytes = inputStream.read(buffer);
                Log.e(TAG, buffer.toString());
                //use input to something
            } catch (IOException e) {
                Log.e(TAG, "Inputstream disconnected", e);
                break;

            }*/

        write();
        // }
    }

    public void write() {
        // audioStream.streamAudio(outputStream);
        //audioStream.start();
        streamers++;
        MainScreen.hasSent.add(false);
        Log.e(TAG, "Number of playing devices: " +streamers);
        SingletonAudioStream.getSingletonAudioStream().streamMusic(outputStream);
      //  byte[] buffer;

       /* while (connectionHandlerBoolean)
            try {
                buffer = SingletonAudioStream.getAudioBuffer();
                Log.e(TAG, "buffer " + buffer);
                outputStream.write(buffer, 0, buffer.length);

            } catch (IOException e) {
                Log.e(TAG, "Error when sending data " + e);
            }*/
    }



    public boolean getConnectionHandlerBoolean() {
        return connectionHandlerBoolean;
    }

    public void setConnectionHandlerBoolean(boolean x) {
        connectionHandlerBoolean = x;
    }
}

