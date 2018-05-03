package com.example.maxpower.bluetoothapp;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class SingletonAudioStream {

    private static SingletonAudioStream singletonAudioStream;

    private static String TAG = "SingletonAudioStream";
    private File soundFile = new File(MasterActivity.filepath);
    byte[] audioBuffer = new byte[MainScreen.bufferSize];

    FileInputStream fileInputStream;

    private SingletonAudioStream() {
        try {
            fileInputStream = new FileInputStream(soundFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void streamMusic(OutputStream outputStream) {
        try {
            int count;

            while ((count = fileInputStream.read(audioBuffer)) != -1) {
                try {
                    outputStream.write(audioBuffer, 0, count);
               //    setAudioBuffer(buffer, count);
                } catch (IOException e) {
                    Log.e(TAG, "Error when sending data " + e);
                }
            }

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public byte[] getAudioBuffer() {
        return audioBuffer;
    }

    public void setAudioBuffer(byte[] x, int b) {
        audioBuffer = Arrays.copyOf(x, b);
    }

    public static SingletonAudioStream getSingletonAudioStream() {
        if (singletonAudioStream == null) {
            singletonAudioStream = new SingletonAudioStream();
        }
        return singletonAudioStream;
    }
}
