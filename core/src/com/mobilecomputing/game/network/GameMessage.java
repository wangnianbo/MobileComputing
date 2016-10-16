package com.mobilecomputing.game.network;

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import jdk.nashorn.api.scripting.JSObject;

/**
 * Created by Bill on 24/09/2016.
 */
public class GameMessage {

//    GameMessage(String jsonMessage) throws ParseException {
//
//        JSONParser jsonParser = new JSONParser();
//
//        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonMessage);
//        String messageType = (String) jsonObject.get("messageType");
//        String messageContent = (String) jsonObject.get("messageType");
//        this.messageType = messageType;
//        this.messageContent = messageContent;
//
//    }
    GameMessage(String messageType, String messageContent){
        this.messageType = messageType;
        this.messageContent = messageContent;
    }

    public final static String GAME_START = "GAME_START";
    private String messageType ;
    private String messageContent;
}
