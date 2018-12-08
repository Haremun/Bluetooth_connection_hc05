package com.example.kamil.bluetooth_connection_hc_05;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnectThread extends Thread {

    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothConnectionEvent bluetoothConnectionEvent;

    public BluetoothConnectThread(BluetoothDevice device, BluetoothConnectionEvent event) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
        } catch (IOException e) {
            Log.i("BluetoothTest", "Socket's create() method failed", e);
        }
        mmSocket = tmp;
        this.bluetoothConnectionEvent = event;
    }

    public void run() {
        Log.i("BluetoothTest", "Connecting...");
        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.i("BluetoothTest", "Could not close the client socket", closeException);
            }
            return;
        }
        Log.i("BluetoothTest", "Connected");
        ManageConnectionThread manageConnectionThread = new ManageConnectionThread(mmSocket);
        manageConnectionThread.start();
        bluetoothConnectionEvent.onBluetoothConnected(manageConnectionThread);

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.

    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e("", "Could not close the client socket", e);
        }
    }
}
