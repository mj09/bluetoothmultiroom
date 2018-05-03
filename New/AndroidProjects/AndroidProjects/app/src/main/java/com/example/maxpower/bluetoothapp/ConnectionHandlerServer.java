package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.rtp.AudioStream;
import android.nfc.Tag;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
       byte[] buffer = new byte[128000];
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

