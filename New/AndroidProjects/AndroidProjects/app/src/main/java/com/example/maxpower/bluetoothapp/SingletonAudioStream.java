package com.example.maxpower.bluetoothapp;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Arrays;

public class SingletonAudioStream {

    private static SingletonAudioStream singletonAudioStream;

    private static String TAG = "SingletonAudioStream";
    private File soundFile = new File(MasterActivity.filepath);
    byte[] audioBuffer = new byte[MainScreen.bufferSize];
    public static FileInputStream fileInputStream;
    private final ProducerConsumer producerConsumer = new ProducerConsumer();

    private SingletonAudioStream() {
        try {
            fileInputStream = new FileInputStream(soundFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerConsumer.produce();
                } catch (InterruptedException e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
        producerThread.start();

    }
    public void streamMusic(final OutputStream outputStream) {

        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerConsumer.consume(outputStream);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
        Log.d(TAG, "Before consumerthread.start");
        consumerThread.start();
        try {
            consumerThread.join();
        } catch (InterruptedException e) {
            e.toString();
        }
   /*     try {
            int count;

            while ((count = fileInputStream.read(audioBuffer)) != -1) {
                try {
                    outputStream.write(audioBuffer, 0, count);
                } catch (IOException e) {
                    Log.e(TAG, "Error when sending data " + e);
                }
            }

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }*/
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
