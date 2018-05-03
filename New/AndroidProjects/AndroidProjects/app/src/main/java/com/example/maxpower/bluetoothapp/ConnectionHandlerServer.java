package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothSocket;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class ConnectionHandlerServer extends Thread {
    private static final String TAG = "ConnectionHandlerServer";
    private final BluetoothSocket bluetoothSocket;
    private final InputStream inputStream;
 //   private byte[] buffer;
    public static boolean connectionHandlerBoolean = true;
    private String hello = "Hello";
    int minSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
    AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize, AudioTrack.MODE_STREAM);
    boolean removeWaveHeader = true;

    public ConnectionHandlerServer(BluetoothSocket socket) {
        bluetoothSocket = socket;
        InputStream tmpIn = null;
       // OutputStream tmpOut = null;
        Log.e(TAG, "buffersize " + minSize);
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
       byte[] buffer = new byte[MainScreen.bufferSize];
        int count;
        audioTrack.play();
        try {
            while ((count = inputStream.read(buffer)) != -1) {
                audioTrack.write(buffer, 0, count);
            }
        } catch (IOException e) {

        }

   /*    while(connectionHandlerBoolean) {
           try {
           //    if(inputStream.available() > 0) {
             //  while((count = inputStream.read(buffer)) != -1) {
                    inputStream.read(buffer);
                    audioTrack.write(buffer, 0, buffer.length);
                    audioTrack.play();
             //  }
                     //  audioTrack.flush();
                    //   Log.e(TAG, "Received msg " + Integer.toString(bytes));

            //   }
             //  else SystemClock.sleep(100);
           } catch (IOException e) {
               Log.e(TAG, "disconnected", e);
               break;
           }
       }*/

    }

    public boolean getConnectionHandlerBoolean() {
        return connectionHandlerBoolean;
    }

    public void setConnectionHandlerBoolean(boolean x) {
        connectionHandlerBoolean = x;
    }
}

