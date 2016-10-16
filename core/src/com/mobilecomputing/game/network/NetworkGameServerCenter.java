package com.mobilecomputing.game.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Bill on 24/09/2016.
 */
public class NetworkGameServerCenter  {

    private Map<String, ClientConnection> clientConnectionMap;
    private Map<String, NetworkGameServerThread> networkGameServerThreadMap= new HashMap();
    NetworkGameServerCenter(Map clientConnectionMap){
        this.clientConnectionMap = clientConnectionMap;
        Iterator it = this.clientConnectionMap.keySet().iterator();
        while(it.hasNext()){
            String clientName = (String) it.next();
            ClientConnection clientConnection = (ClientConnection) clientConnectionMap.get(clientName);
            NetworkGameServerThread networkGameServerThread = new NetworkGameServerThread(clientConnection);
            networkGameServerThread.start();
            networkGameServerThreadMap.put(clientName, networkGameServerThread);

        }
    }

}
