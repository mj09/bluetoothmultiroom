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
  //  private final InputStream inputStream;
    public static InputStream inputStream;
    public static boolean connectionHandlerBoolean = true;
    private int numberOfMsgs;

    private static int minSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
    public static AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize, AudioTrack.MODE_STREAM);

    public ConnectionHandlerServer(BluetoothSocket socket) {
        bluetoothSocket = socket;
        InputStream tmpIn = null;
        Log.e(TAG, "buffersize " + minSize);
        try {
            tmpIn = socket.getInputStream();
            Log.e(TAG, "Inputstream created");
        } catch (IOException e) {
            Log.e(TAG, "Error when creating inputstream");
        }
        inputStream = tmpIn;
    }

    public void run() {
        Log.e(TAG, "Begin ConnectionHandlerServer");
       byte[] buffer = new byte[MainScreen.bufferSize];
       byte[] temp = new byte[MainScreen.bufferSize * 4];
        int count;
        int tempcount;
        int startIndex = 0;
        numberOfMsgs = 0;
        audioTrack.play();
        try {
            while ((count = inputStream.read(buffer)) != -1) {
               // System.arraycopy(buffer, 0, temp, startIndex, buffer.length);

                audioTrack.write(buffer, 0, count);

            }
            numberOfMsgs++;
        } catch (IOException e) {

        }
    }

    public boolean getConnectionHandlerBoolean() {
        return connectionHandlerBoolean;
    }

    public void setConnectionHandlerBoolean(boolean x) {
        connectionHandlerBoolean = x;
    }
}

