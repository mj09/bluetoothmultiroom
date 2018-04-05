package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Max Power on 20-02-2018.
 */

public class AcceptThread extends Thread {
    public String remoteDeviceName;
    SlaveActivity slaveActivity = new SlaveActivity();
    private final BluetoothServerSocket btSocket;
    private static final UUID MY_UUID =
            UUID.fromString("9a927a5e-f8d9-4028-a6a0-a92cd286e956");
    public AcceptThread(BluetoothAdapter bluetoothAdapter) {
        BluetoothServerSocket temp = null;
        try {
            temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Client", MY_UUID);
        } catch (IOException e){
            Log.e("AcceptThread", "Socket listen() method failed" + e);
        }
        btSocket = temp;
    }

    public void run(){;
        BluetoothSocket socket = null;
        Log.e("run", "Before while loop");
        while (true){
            try {
                socket = btSocket.accept();
                Log.e("AcceptThread", "Bluetooth socket accepted");
                Log.e("AcceptThread", "Connected device MAC address: " + socket.getRemoteDevice().toString());
            }catch (IOException e){
                Log.e("run", "socket accept() method failed" + e);
                try {
                    btSocket.close();
                } catch (IOException ee) {
                    Log.e("Connectthread run", "Could not close client socket");
                }
                break;
            }

            if (socket == null) {
                Log.e("run", "a connection was accepted");
                try{
                    btSocket.close();
                }catch (IOException closeException){
                    Log.e("Connectthread run", "Could not close client socket");
                }
            }
            Log.e("run", "While running without anything happening");
        }
    }

    public void cancel(){
        try {
            btSocket.close();
        } catch (IOException e){
            Log.e("cancel", "could not close the connect socket" + e);
        }
    }
}
