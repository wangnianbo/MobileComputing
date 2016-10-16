package com.mobilecomputing.game.network;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Bill on 24/09/2016.
 */
public class ClientConnection {
    ClientConnection(InputStream serverToClientInputStream, OutputStream serverToClientOutputStream){
        this.serverToClientInputStream = serverToClientInputStream;
        this.serverToClientOutputStream = serverToClientOutputStream;
    }
    InputStream serverToClientInputStream;
    OutputStream serverToClientOutputStream;
}
