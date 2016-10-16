package com.mobilecomputing.game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Bill on 24/09/2016.
 */
public class MessageCenter {

    InputStream toServerInStream = null;
    OutputStream toServerOutStream = null;

    MessageCenter(InputStream toServerInStream, OutputStream toServerOutStream){
        this.toServerInStream = toServerInStream;
        this.toServerOutStream = toServerOutStream;
    }
    public void sendMessage(String message) throws IOException {
        toServerOutStream.write(message.toString().getBytes());
        toServerOutStream.flush();
    }
    public String receiveMessage() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(toServerInStream));
        return br.readLine();

    }
}
