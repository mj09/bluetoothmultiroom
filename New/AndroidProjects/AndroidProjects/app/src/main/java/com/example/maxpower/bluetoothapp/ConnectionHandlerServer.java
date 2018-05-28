package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothSocket;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ConnectionHandlerServer extends Thread {
    private static final String TAG = "ConnectionHandlerServer";
    private final BluetoothSocket bluetoothSocket;
  //  private final InputStream inputStream;
    public static InputStream inputStream;
    public static boolean connectionHandlerBoolean = true;
 //   ArrayList<byte[]> byteArray = new ArrayList<>();
 //   private AudioPlay audioPlay = new AudioPlay();

    public byte[] temp = new byte[MainScreen.bufferSize];
    private static int minSize = AudioTrack.getMinBufferSize(24000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
    public static AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 24000, AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT, MainScreen.bufferSize, AudioTrack.MODE_STREAM);

    public ConnectionHandlerServer(BluetoothSocket socket) {
        bluetoothSocket = socket;
        InputStream tmpIn = null;
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
        int count;
        audioTrack.play();
        try {
            while ((count = inputStream.read(buffer)) != -1) {
                while(!MainScreen.control);

                    audioTrack.write(buffer, 0, count);

                  //  audioPlay.playMusic(buffer, count);

                  //  temp = Arrays.copyOf(buffer, count);
                    Log.e(TAG, "Received bytes " + buffer.toString() + " buffer.length: " + buffer.length +
                    "count: " + count);
            }
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

