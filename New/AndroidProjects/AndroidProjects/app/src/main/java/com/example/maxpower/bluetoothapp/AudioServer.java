package com.example.maxpower.bluetoothapp;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioServer extends Thread {
    private static String TAG = "AudioServer";
    public AudioServer(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Expected sound file arg");
        File soundFile = AudioUtil.getSoundFile(args[0]);

        Log.e(TAG, "server: " + soundFile);

        try {
            FileInputStream in = new FileInputStream(soundFile);
        }catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }
}
