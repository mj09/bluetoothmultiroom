package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Max Power on 20-02-2018.
 */

public class AcceptThread extends Thread {
    private final String TAG = "AcceptThread";
    public static int updateInteger;
    public static String remoteDeviceName;
    SlaveActivity slaveActivity = new SlaveActivity();
    private final BluetoothServerSocket btSocket;
    private static final UUID MY_UUID =
            UUID.fromString("9a927a5e-f8d9-4028-a6a0-a92cd286e956");

    public AcceptThread(BluetoothAdapter bluetoothAdapter) {
        BluetoothServerSocket temp = null;
        try {
            temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Client", MY_UUID);
            setUpdateInteger(1);
        } catch (IOException e) {
            Log.e("AcceptThread", "Socket listen() method failed" + e);
        }
        btSocket = temp;
    }

    public void run() {
        ;
        BluetoothSocket socket = null;
        while (true) {
            try {
                socket = btSocket.accept();
                setUpdateInteger(2);
                Log.e("AcceptThread", "Bluetooth socket accepted");
                Log.e("AcceptThread", "Connected device MAC address: " + socket.getRemoteDevice().toString());
                setRemoteDeviceName(socket.getRemoteDevice().toString());
            } catch (IOException e) {
                Log.e("run", "socket accept() method failed" + e);
                setUpdateInteger(3);
                try {
                    btSocket.close();
                    setUpdateInteger(4);
                } catch (IOException ee) {
                    Log.e("Connectthread run", "Could not close client socket");
                }
                break;
            }

            if (socket == null) {
                Log.e("run", "a connection was accepted");
                try {
                    btSocket.close();
                } catch (IOException closeException) {
                    Log.e("Connectthread run", "Could not close client socket");
                }
            }
            ConnectionHandlerServer connectionHandlerServer = new ConnectionHandlerServer(socket);
            connectionHandlerServer.start();
        }
    }

    public void cancel() {
        try {
            btSocket.close();
            setUpdateInteger(5);
        } catch (IOException e) {
            Log.e("cancel", "could not close the connect socket" + e);
        }
    }

    public static String getRemoteDeviceName() {
        return remoteDeviceName;
    }

    private static void setRemoteDeviceName(String x) {
        remoteDeviceName = x;
    }

    public static Integer getUpdateInteger() {
        return updateInteger;
    }

    public static void setUpdateInteger(Integer x) {
        updateInteger = x;
    }
}
