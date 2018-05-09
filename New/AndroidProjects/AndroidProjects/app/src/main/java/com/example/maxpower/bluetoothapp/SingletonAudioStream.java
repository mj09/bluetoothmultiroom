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

    byte[] audioBuffer = new byte[MainScreen.bufferSize];
    public static FileInputStream fileInputStream;
    private final ProducerConsumer producerConsumer = new ProducerConsumer();

    private SingletonAudioStream() {
        if(VariablesAndMethods.isMaster) {
            try {
                fileInputStream = new FileInputStream(MasterActivity.soundFile);
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
        else if(!VariablesAndMethods.isMaster) {
            Thread producerMasterSlaveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        producerConsumer.produceMasterSlave();
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            });
            producerMasterSlaveThread.start();
        }
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
