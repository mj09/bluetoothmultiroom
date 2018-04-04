package com.example.maxpower.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    public static BluetoothAdapter bluetoothAdapter;
    ArrayList<String> discoveredDeviceList = new ArrayList<>();
    static String deviceName;
    String deviceHardwareAddress;
    public int deviceCounter = 0;
    int pickedDevice = 0;
    private static String pickedDeviceName;
    BluetoothDevice btdevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final Button enableBluetooth = (Button)findViewById(R.id.buttonEnableBluetooth);
        enableBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableBluetooth();
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
            }
        });

        final Button queeryExistingDevices = (Button)findViewById(R.id.buttonQueeryKnownDevices);
        queeryExistingDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryingPairedDevices();
            }
        });

        final Button discoverDevices = (Button)findViewById(R.id.buttonDiscover);
        discoverDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.startDiscovery();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(broadcastReceiver, filter);
              //  bluetoothAdapter.cancelDiscovery();
            }
        });

        final Button showDevices = (Button)findViewById(R.id.buttonShowDevices);
        showDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateButtonListOfFoundDevices(btdevice);
            }
        });

        final Button openForConnections = findViewById(R.id.buttonOpenForConnections);
        openForConnections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
                AcceptThread acceptThread = new AcceptThread(bluetoothAdapter);
                acceptThread.start();
            }
        });
    }

    public void EnableBluetooth() {

        if (bluetoothAdapter == null) {
            Log.e("EnableBluetooth()", "Device does not support Bluetooth");
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Log.e("EnableBluetooth()", "Bluetooth is enabled");
        }
        else{
            Toast.makeText(MainActivity.this, "Bluetooth is already enabled", Toast.LENGTH_SHORT).show();
        }
    }

    public void QueryingPairedDevices(){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if(pairedDevices.size() > 0){
            Log.e("QueeringPairedDevices()", "Searching for paired devices");
            for (BluetoothDevice device : pairedDevices){
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                Log.e("QueeringPairedDevices()", "deviceName: " + deviceName + "  MAC address: " + deviceHardwareAddress);
            }
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceName = device.getName();
                deviceHardwareAddress = device.getAddress();
                Log.e("BroadcastReceiver", "deviceName: " + deviceName + " Mac Address: " + deviceHardwareAddress);
                if(!discoveredDeviceList.contains(deviceHardwareAddress.toString())){
                    Log.e("BroadCastReceiver", discoveredDeviceList.toString());
                    discoveredDeviceList.add(deviceName + " " + deviceHardwareAddress);
                    deviceCounter++;
                }
            }
        }
    };

    private void CreateButtonListOfFoundDevices(final BluetoothDevice y){
        Log.e("CreateButtons", "Method started");
        for(int x = 0; x < discoveredDeviceList.size(); x++){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
            Log.e("CreateButtons", "For loop started" + " " + x);
            final Button button = new Button(this);
            button.setId(x);
            button.setText(discoveredDeviceList.get(x));
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bluetoothAdapter.cancelDiscovery();
                    pickedDevice = button.getId();
                    Log.e("CreateButton", "pickedDevice " + pickedDevice);
                    setDeviceName(discoveredDeviceList.get(pickedDevice));
                    ConnectThread connectThread = new ConnectThread(y);
                    connectThread.start();
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


}
