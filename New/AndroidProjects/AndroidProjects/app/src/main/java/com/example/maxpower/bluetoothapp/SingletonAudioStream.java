package com.example.maxpower.bluetoothapp;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class SingletonAudioStream {

    private static SingletonAudioStream singletonAudioStream;

    private static String TAG = "SingletonAudioStream";
    private static File soundFile = new File(MasterActivity.filepath);
    static byte[] audioBuffer;
    private SingletonAudioStream() {

    }

    public static void streamMusic(OutputStream outputStream) {
        try {
            FileInputStream fileInputStream = new FileInputStream(soundFile);

            byte buffer[] = new byte[128000];
            int count;

            while ((count = fileInputStream.read(buffer)) != -1) {
                try {
                    outputStream.write(buffer, 0, count);
               //    setAudioBuffer(buffer, count);
                } catch (IOException e) {
                    Log.e(TAG, "Error when sending data " + e);
                }
            }

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public static byte[] getAudioBuffer() {
        return audioBuffer;
    }

    public static void setAudioBuffer(byte[] x, int b) {
        audioBuffer = Arrays.copyOf(x, b);
    }

    public static SingletonAudioStream getSingletonAudioStream() {
        if (singletonAudioStream == null) {
            singletonAudioStream = new SingletonAudioStream();
        }
        return singletonAudioStream;
    }
}
