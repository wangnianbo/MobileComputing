package com.mobilecomputing.game.bluetooth;





import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.mobilecomputing.game.AndroidLauncher;
import com.mobilecomputing.game.network.Bluetooth.BluetoothConnection;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;



/**
 * Created by Bill on 19/09/2016.
 */
public class AndroidBluetoothConnection   implements BluetoothConnection {

    AndroidLauncher androidLauncher;
    public AndroidBluetoothConnection(AndroidLauncher androidLauncher){
        this.androidLauncher = androidLauncher;
    }



    @Override
    public InputStream getClientInputStream() {
        return BluetoothStream.ClientInStream;
    }

    @Override
    public OutputStream getClientOutputStream() {
        return BluetoothStream.ClientOutStream;
    }

    @Override
    public InputStream getServerInputStream() {
        return BluetoothStream.ServerInStream;
    }

    @Override
    public OutputStream getServerOutputStream() {
        return BluetoothStream.ServerOutStream;
    }

    @Override
    public boolean isNetGame() {
        return BluetoothStream.isNetGame;
    }

    @Override
    public boolean isBluetoothAvailable() {
        return false;
    }

    @Override
    public void turnBluetoothOn() {

    }

    @Override
    public List<String> getDeviceList(List<String> deviceList) {
        return null;
    }

    @Override
    public void gotoBluetoothSet() {
        androidLauncher.gotoBluetoothSet();
    }

    @Override
    public boolean startDiscoverBluetooth() {
        return false;
    }
}
