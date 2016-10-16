package com.mobilecomputing.game.network.Bluetooth;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Bill on 19/09/2016.
 */
public interface BluetoothConnection {

    public InputStream getClientInputStream();

    public OutputStream getClientOutputStream();

    public InputStream getServerInputStream();

    public OutputStream getServerOutputStream();

    public boolean isNetGame();

    public boolean isBluetoothAvailable();

    public void turnBluetoothOn();

    public List<String> getDeviceList(List<String> deviceList);

    public void gotoBluetoothSet();

    boolean startDiscoverBluetooth();

}