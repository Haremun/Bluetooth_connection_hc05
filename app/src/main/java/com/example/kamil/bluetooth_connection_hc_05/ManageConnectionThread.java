package com.example.kamil.bluetooth_connection_hc_05;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ManageConnectionThread extends Thread{

    private final BluetoothSocket mSocket;
    private final InputStream mInputStream;
    private final OutputStream mOutputStream;
    private byte[] mBuffer;

    public ManageConnectionThread(BluetoothSocket socket){
        mSocket = socket;


        InputStream inTmp = null;
        OutputStream outTmp = null;
        try {
            inTmp = mSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outTmp = mSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mInputStream = inTmp;
        mOutputStream = outTmp;

    }
    public void run(){
        Log.i("BluetoothTest", "ManageConnectionThread_run");
        write();

    }
    public void write(){

        try {
            byte[] temp = "test".getBytes();
            mOutputStream.write(temp);
            Log.i("BluetoothTest", temp[0] + " " + temp[1] + " " + temp[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
