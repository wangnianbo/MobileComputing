package com.mobilecomputing.game.network.Bluetooth;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Bluetooth Connection Interface
 * Created by Bill on 19/09/2016.
 */
public interface BluetoothConnection {

    /**
     * Get Client Input Stream
     * @return
     */
    public InputStream getClientInputStream();

    /**
     * Get Client Output Stream
     * @return
     */
    public OutputStream getClientOutputStream();

    /**
     * Get Serve Input Stream
     * @return
     */
    public InputStream getServerInputStream();

    /**
     * Get Server Output Stream
     * @return
     */
    public OutputStream getServerOutputStream();

    /**
     * Wheth it is a net game
     * @return true if is net game
     */
    public boolean isNetGame();

    /**
     * Go to Bluetooth Set Activity!
     */
    public void gotoBluetoothSet();


}