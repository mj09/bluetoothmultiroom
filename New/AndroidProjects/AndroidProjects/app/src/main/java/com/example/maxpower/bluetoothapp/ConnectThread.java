package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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
    private static String getDeviceName;

    public ConnectThread(BluetoothDevice btDevice){

        tmp = ShowDevicesActivity.getDeviceName();
        receivedDeviceName = tmp.toString();
        Log.e("connectthread", "receivedDeviceName " + receivedDeviceName);
        receivedDeviceName = receivedDeviceName.substring(receivedDeviceName.length() - 17);
        Log.e("Connectthread", receivedDeviceName);
        btDevice = btAdapter.getRemoteDevice("08:3D:88:25:8B:CA");


        UUID uuid = UUID.fromString("9a927a5e-f8d9-4028-a6a0-a92cd286e956");

        BluetoothSocket temp = null;
        mmDevice = btDevice;

        try {
            if (mmDevice == null) {
                Log.e("ConnectThread()", "No bluetooth device");
            } else {
                temp = btDevice.createRfcommSocketToServiceRecord(uuid);
                Log.e(TAG, "createRfcommSockerToServiceRecord" + uuid.toString());
            }
        }   catch (IOException e){
            Log.e("Connectthread()", "Method failed");
        }
        btSocket = temp;

        //if(btSocket.getRemoteDevice().toString() != null)
          //  setDeviceName(btSocket.getRemoteDevice().toString());
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
            }
        }catch (IOException connectException){
            Log.e("ConnectThread run", "Unable to connect, closing socket and return");
            try{
                btSocket.close();
            }catch (IOException closeException){
                Log.e("Connectthread run", "Could not close client socket");
            }
            return;
        }
    }

}
