package com.mobilecomputing.game.bluetooth;





import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.mobilecomputing.game.AndroidLauncher;
import com.mobilecomputing.game.network.Bluetooth.BluetoothConnection;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;



/**
 * Android Implementation of Bluetooth Connection
 * Created by Bill on 19/09/2016.
 */
public class AndroidBluetoothConnection   implements BluetoothConnection {
    /**
     *
     */
    AndroidLauncher androidLauncher;
    public AndroidBluetoothConnection(AndroidLauncher androidLauncher){
        this.androidLauncher = androidLauncher;
    }


    /**
     * Get Client Input Stream
     * @return
     */
    @Override
    public InputStream getClientInputStream() {
        return BluetoothStream.ClientInStream;
    }

    /**
     * Get Client Output Stream
     * @return
     */
    @Override
    public OutputStream getClientOutputStream() {
        return BluetoothStream.ClientOutStream;
    }

    /**
     * Get Server Input Stream
     * @return
     */
    @Override
    public InputStream getServerInputStream() {
        return BluetoothStream.ServerInStream;
    }

    /**
     * Get Server Output Stream
     * @return
     */
    @Override
    public OutputStream getServerOutputStream() {
        return BluetoothStream.ServerOutStream;
    }

    /**
     * is it a net game
     * @return
     */
    @Override
    public boolean isNetGame() {
        return BluetoothStream.isNetGame;
    }

    /**
     * Switch to Bluetooth connection
     */
    @Override
    public void gotoBluetoothSet() {
        androidLauncher.gotoBluetoothSet();
    }

}
