package com.mobilecomputing.game.bluetooth;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Bill on 8/10/2016.
 */
public class BluetoothStream {
   public static InputStream ServerInStream = null;
    public static  OutputStream ServerOutStream = null;

    public static InputStream ClientInStream = null;
    public static OutputStream ClientOutStream = null;
    public static boolean isNetGame = false;
}
