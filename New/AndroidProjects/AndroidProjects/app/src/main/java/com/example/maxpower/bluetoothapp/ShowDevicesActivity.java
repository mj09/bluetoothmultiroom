package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowDevicesActivity extends AppCompatActivity {
    BluetoothDevice btdevice;
    MasterActivity masterActivity = new MasterActivity();
    private final static String TAG = "ShowDevicesActivity";
    public static BluetoothAdapter bluetoothAdapter;
    int pickedDevice = 0;
    private static String pickedDeviceName;
    private Handler handler = new Handler();
    private boolean buttonColourBoolean = false;
    private int connectionStatus;
    private static ArrayList<Integer> listOfClickedDevices;
    private boolean closeSocketBoolean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listOfClickedDevices = new ArrayList<Integer>();
        handler.postDelayed(runnable, 100);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Connectable Devices");
        ArrayList<String> discoveredDeviceList = masterActivity.getList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_devices);
        Log.e(TAG, "OnCreate - before for loop" + masterActivity.arrayCounter);
        for (int x = 0; x < discoveredDeviceList.size(); x++) {
            Log.e(TAG, discoveredDeviceList.get(x));
        }
        CreateButtonListOfFoundDevices(discoveredDeviceList);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            connectionStatus = ConnectThread.getConnectionInteger();
            if (connectionStatus == 1) {
                Button button = findViewById(pickedDevice);
                button.setBackgroundColor(Color.YELLOW);
                ConnectThread.setConnectionStatus(0);
                Toast.makeText(ShowDevicesActivity.this, "Connecting to " + button.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            if (connectionStatus == 2) {
                Button button = findViewById(pickedDevice);
                button.setBackgroundColor(Color.GREEN);
                ConnectThread.setConnectionStatus(0);
                Toast.makeText(ShowDevicesActivity.this, "Connected to " + button.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            if (connectionStatus == 3) {
                Button button = findViewById(pickedDevice);
                button.setBackgroundColor(Color.RED);
                ConnectThread.setConnectionStatus(0);
                Toast.makeText(ShowDevicesActivity.this, "Could not connect to " + button.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            if (connectionStatus == 4) {
                Button button = findViewById(pickedDevice);
                button.setBackgroundColor(R.drawable.button_border);
                ConnectThread.setConnectionStatus(0);
                Toast.makeText(ShowDevicesActivity.this, "Could not connect to " + button.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            handler.postDelayed(this, 100);
        }
    };

    private void CreateButtonListOfFoundDevices(final ArrayList<String> list){
        Log.e("CreateButtons", "Method started");
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);
        for(int x = 0; x < list.size(); x++){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
            Log.e("CreateButtons", "For loop started" + " " + x);
            final Button button = new Button(this);
            button.setId(x);
            button.setText(list.get(x));
          //  button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 30);
            button.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(button);
            button.setBackgroundResource(R.drawable.button_border);
            button.setTextColor(Color.WHITE);
            button.setOnClickListener(new View.OnClickListener() {
                boolean hasbeenClicked = false;
                ConnectThread connectThread;
                @Override
                public void onClick(View v) {
                    if(hasbeenClicked)
                        connectThread.cancel();
                    else {
                        pickedDevice = button.getId();
                        if (!listOfClickedDevices.contains(pickedDevice)) {
                            Log.e("CreateButton", "pickedDevice " + pickedDevice);
                            setDeviceName(list.get(pickedDevice));
                            connectThread = new ConnectThread(btdevice);
                            connectThread.start();
                            listOfClickedDevices.add(pickedDevice);
                        } else
                            ConnectionHandler.connectionHandlerBoolean = false;
                    }
                    hasbeenClicked = !hasbeenClicked;
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
