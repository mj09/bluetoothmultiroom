package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class ShowDevicesActivity extends AppCompatActivity {
    BluetoothDevice btdevice;
    MasterActivity masterActivity = new MasterActivity();
    private final static String TAG = "ShowDevicesActivity";
    public static BluetoothAdapter bluetoothAdapter;
    int pickedDevice = 0;
    private static String pickedDeviceName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<String> discoveredDeviceList = masterActivity.getList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_devices);
        Log.e(TAG, "OnCreate - before for loop" + masterActivity.arrayCounter);
        for (int x = 0; x < discoveredDeviceList.size(); x++) {
            Log.e(TAG, discoveredDeviceList.get(x));
        }
        CreateButtonListOfFoundDevices(discoveredDeviceList);
    }



    private void CreateButtonListOfFoundDevices(final ArrayList<String> list){
        Log.e("CreateButtons", "Method started");
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        for(int x = 0; x < list.size(); x++){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
            Log.e("CreateButtons", "For loop started" + " " + x);
            final Button button = new Button(this);
            button.setId(x);
            button.setText(list.get(x));
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickedDevice = button.getId();
                    Log.e("CreateButton", "pickedDevice " + pickedDevice);
                    setDeviceName(list.get(pickedDevice));
                    ConnectToServer(btdevice);
                }
            });
        }
    }

    public static String getDeviceName(){
        return pickedDeviceName;
    }
    public static void setDeviceName(String x){
        pickedDeviceName = x;
        Log.e("setDeviceName", pickedDeviceName);
    }

    private void ConnectToServer(final BluetoothDevice y) {
        ConnectThread connectThread = new ConnectThread(y);
        connectThread.start();
    }


}
