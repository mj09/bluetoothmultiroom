package com.example.maxpower.bluetoothapp;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class VariablesAndMethods {

    public static BluetoothAdapter bluetoothAdapter;

    //Strings
    public static String deviceName;
    public static String deviceHardwareAddress;
    public static String filePath;

    //Integers

    //Booleans
    public static boolean isMaster;

    //Lists
    public static ArrayList<String> discoveredDeviceList = new ArrayList<String>();

    //Methods

}
