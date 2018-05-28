package com.example.maxpower.bluetoothapp;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ProducerConsumer {
    private static int nextID = 0;
    private static synchronized int getID() {
        return nextID++;
    }
    private static String TAG = "ProducerConsumer";
    private byte[] audioBuffer = new byte[MainScreen.bufferSize];
    private int count;
    private int consumerCount;
    private int startIndex = 0;
    private int endIndex = MainScreen.bufferSize;
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
                            Log.e(TAG, "Msg sent: " + audioBuffer.toString() + " " + count);
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
                    MainScreen.streamerList.clear();
                    notifyAll();
                    while (MainScreen.streamerList.size() < ConnectionHandler.streamers) {
                        Log.e(TAG, "Producer - wait");
                        wait();
                    }
                    MainScreen.control = false;
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

    public void producePreLoad() throws InterruptedException{

            while ((count = MasterActivity.preload.length) != -1) {
                synchronized (this) {
                    audioBuffer = Arrays.copyOfRange(MasterActivity.preload, startIndex, endIndex);
                    startIndex = startIndex + MainScreen.bufferSize;
                    endIndex = endIndex + MainScreen.bufferSize;
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

    }
}


