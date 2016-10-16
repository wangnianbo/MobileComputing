package com.mobilecomputing.game.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bill on 24/09/2016.
 */
public class NetworkGameServerThread extends Thread {

    private ClientConnection clientConnection;
    NetworkGameServerThread(ClientConnection clientConnection){
        this.clientConnection = clientConnection;
    }
    @Override
   public void run(){

    }
}
