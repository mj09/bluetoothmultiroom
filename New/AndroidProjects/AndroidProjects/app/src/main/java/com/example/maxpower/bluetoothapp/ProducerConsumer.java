package com.example.maxpower.bluetoothapp;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

public class ProducerConsumer {
    private static int nextID = 0;
    private static synchronized int getID() {
        return nextID++;
    }
    private static String TAG = "ProducerConsumer";
    private byte[] audioBuffer = new byte[MainScreen.bufferSize];
    private int count;
    private int consumerCount;

    public void consume(OutputStream outputStream) throws InterruptedException {
        int id = getID();
        while(true) {
                synchronized (this) {
                    while(MainScreen.streamerList.contains(id)) {
                        Log.e(TAG, "Consumer - wait - StreamListSize is " + MainScreen.streamerList.size());
                        notifyAll();
                        wait();
                    }
                    try {
                            outputStream.write(audioBuffer, 0, count);
                            Log.d(TAG, "Consumer has written with id: " + id);
                            MainScreen.streamerList.add(id);
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                    Log.e(TAG, "Consumer notifying");
                    notify();
                }
        }
    }
    public void produceMasterSlave() throws InterruptedException{
        try {
            while ((count = ConnectionHandlerServer.inputStream.read(audioBuffer)) != -1) {
                synchronized (this) {
                    Log.e(TAG, "Producer has read. Streamers: " + ConnectionHandler.streamers);
                    //  Log.e(TAG, "Read file");
                    MainScreen.streamerList.clear();
                    notifyAll();
                    while (MainScreen.streamerList.size() < ConnectionHandler.streamers) {
                        Log.e(TAG, "Producer - wait");
                        wait();
                    }
                    ConnectionHandlerServer.audioTrack.write(audioBuffer,0, count);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "ProducerMasterSlave " + e.toString());
        }
    }

    public void produce() throws InterruptedException{
        try {
            while ((count = SingletonAudioStream.fileInputStream.read(audioBuffer)) != -1) {
                synchronized (this) {
                    Log.e(TAG, "Producer has read. Streamers: " + ConnectionHandler.streamers);
                    //  Log.e(TAG, "Read file");
                    MainScreen.streamerList.clear();
                    notifyAll();
                    while (MainScreen.streamerList.size() < ConnectionHandler.streamers) {
                        Log.e(TAG, "Producer - wait");
                        wait();
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }
}


