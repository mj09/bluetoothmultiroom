package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Max Power on 09-02-2018.
 */

public class ConnectThread extends Thread {
    private final String TAG = "ConnectThread";
    private final BluetoothSocket btSocket;
    private final BluetoothDevice mmDevice;
    String receivedDeviceName;
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    String tmp;
    static int connectionStatus = 0;

    public ConnectThread(BluetoothDevice btDevice){
        tmp = ShowDevicesActivity.getDeviceName();
        receivedDeviceName = tmp.toString();
        Log.e("connectthread", "receivedDeviceName " + receivedDeviceName);
        receivedDeviceName = receivedDeviceName.substring(receivedDeviceName.length() - 17);
        Log.e("Connectthread", receivedDeviceName);
        btDevice = btAdapter.getRemoteDevice(receivedDeviceName);


        UUID uuid = UUID.fromString("9a927a5e-f8d9-4028-a6a0-a92cd286e956");

        BluetoothSocket temp = null;
        mmDevice = btDevice;

        try {
            if (mmDevice == null) {
                Log.e("ConnectThread()", "No bluetooth device");
            } else {
                temp = btDevice.createRfcommSocketToServiceRecord(uuid);
                Log.e(TAG, "createRfcommSockerToServiceRecord" + uuid.toString());
                setConnectionStatus(1);
            }
        }   catch (IOException e){
            Log.e("Connectthread()", "Method failed");
        }
        btSocket = temp;
    }

    public void run(){
            btAdapter.cancelDiscovery();
        try{
            if(btSocket == null) {
                Log.e(TAG, "Btsocket is null");
                return;
            }
            else {
                btSocket.connect();
                Log.e(TAG, "btsocket.connect");
                setConnectionStatus(2);
                    }
        }catch (IOException connectException){
            Log.e("ConnectThread run", "Unable to connect, closing socket and return");
            setConnectionStatus(3);
            try{
                btSocket.close();
            }catch (IOException closeException){
                Log.e("Connectthread run", "Could not close client socket");
            }
            return;
        }

        ConnectionHandler connectionHandler = new ConnectionHandler(btSocket);
        connectionHandler.start();
    }
    public void cancel() {
        try {
            btSocket.close();
            setConnectionStatus(4);
            Log.e(TAG, "cancel - socket closed");
        } catch (IOException e) {
            Log.e(TAG, "Could not close client socket", e);
        }
    }

    public static Integer getConnectionInteger() {
        return connectionStatus;
    }

    public static void setConnectionStatus(int x) {
        connectionStatus = x;
    }
}
