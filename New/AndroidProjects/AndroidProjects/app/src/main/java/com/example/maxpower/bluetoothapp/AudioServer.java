package com.example.maxpower.bluetoothapp;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.rtp.AudioStream;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class AudioServer extends Thread {
    private static String TAG = "AudioServer";
    public AudioServer(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Expected sound file arg");
        File soundFile = AudioUtil.getSoundFile(args[0]);

        Log.e(TAG, "server: " + soundFile);
    }
}
