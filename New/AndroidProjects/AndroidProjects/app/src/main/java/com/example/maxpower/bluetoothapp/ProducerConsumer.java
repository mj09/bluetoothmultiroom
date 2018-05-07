package com.example.maxpower.bluetoothapp;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;

public class ProducerConsumer {

    private static String TAG = "ProducerConsumer";
    private byte[] audioBuffer = new byte[MainScreen.bufferSize];
    private int count;
    private int consumerCount;
    public void consume(OutputStream outputStream) throws InterruptedException {

        while(true) {
        synchronized (this) {

                synchronized (MainScreen.streamerList) {
                    while(MainScreen.streamerList.size() == 0) {
                        Log.e(TAG, "Consumer - wait - StreamListSize is " + MainScreen.streamerList.size());
                        wait();
                    }
                    Log.e(TAG, "Consumer has started");
                    try {
                            outputStream.write(audioBuffer, 0, count);

                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                    MainScreen.streamerList.remove(MainScreen.streamerList.size() - 1);
                    Log.e(TAG, "Consumer StreamerListSize: " +  MainScreen.streamerList.size() +
                            " Streamers" + ConnectionHandler.streamers);

                    notify();

                }
            }
        }
    }
    public void produce() throws InterruptedException{
        try {
            while ((count = SingletonAudioStream.fileInputStream.read(audioBuffer)) != -1) {

                synchronized (this) {
                    while (MainScreen.streamerList.size() == ConnectionHandler.streamers) {
                        Log.e(TAG, "Producer - wait");
                        wait();
                    }
                    Log.e(TAG, "Producer has started. Streamers: " + ConnectionHandler.streamers);
                    //  Log.e(TAG, "Read file");

                    notify();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }
}


