package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;

import java.util.ArrayList;

public class VariablesAndMethods {

    public static BluetoothAdapter bluetoothAdapter;

    //Strings
    public static String deviceName;
    public static String deviceHardwareAddress;
    public static String filePath;

    //Integers
    public static int sampleRateofDevice;
    public static int framesPerBuffer;

    //Booleans
    public static boolean isMaster;

    //Lists
    public static ArrayList<String> discoveredDeviceList = new ArrayList<String>();

    //Methods

}
