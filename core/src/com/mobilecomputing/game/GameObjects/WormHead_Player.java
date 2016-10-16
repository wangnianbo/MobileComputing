package com.mobilecomputing.game.GameObjects;

import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.slitherio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Venom on 18/09/2016.
 */
public class WormHead_Player extends WormHead{
    public float lastDragX =UGameLogic.UNSET_INT;
    public float lastDragY =UGameLogic.UNSET_INT;
    public float lastTapX =UGameLogic.UNSET_INT;
    public float lastTapY =UGameLogic.UNSET_INT;
    int timeSinceLastTap=100;
    int boostTolerance=UGameLogic.lengthOfSecond/4;
    public static boolean released=false;
    //What to do on each step
    public static void SignalReleased(){
        released=true;

    }

    public WormHead_Player(float x,float y,WormTemplate_Player worm){
        super(x,y,worm);

    }

    @Override
    public void update(){
        super.update();
        timeSinceLastTap++;

        if(slitherio.dragging){

            //UGameLogic.LogMsg("Target Dir "+targetDir);

            //UGameLogic.LogMsg("t "+timeSinceLastTap);
            if(released)
                UGameLogic.LogMsg("Time since last Tap "+timeSinceLastTap);
            if(timeSinceLastTap<boostTolerance && released){

                boosting=true;
                timeBoosting=0;
            }
            if(getWorm().getTailSegments().size()<3){
                boosting=false;
            }

            released=false;
            lastDragX =slitherio.lastWorldDragX;
            lastDragY =slitherio.lastWorldDragY;
        }
        else{
            //UGameLogic.LogMsg("n "+timeSinceLastTap);
            boosting=false;
            if(slitherio.lastWorldTapX!=lastTapX || slitherio.lastWorldTapY!=lastTapY){
                lastTapX=slitherio.lastWorldTapX;
                lastTapY=slitherio.lastWorldTapY;

                timeSinceLastTap=0;
            }
        }
    }

    @Override
    public double getTargetDir(){
        double targetDir ;
        if(slitherio.dragging) {
            targetDir =  UGameLogic.dirToPoint(x, y, slitherio.lastWorldDragX, slitherio.lastWorldDragY);
        }
        else{
            targetDir = moveDir;
        }
        if(slitherio.bluetoothConnection.isNetGame() == true){

            StringBuffer sb = new StringBuffer();
            sb.append(targetDir);
            sb.append("\n");
            OutputStream ServerOutStream = slitherio.bluetoothConnection.getServerOutputStream();
            OutputStream ClientOutStream = slitherio.bluetoothConnection.getClientOutputStream();
            if (ServerOutStream !=null){
                try {
                    ServerOutStream.write(sb.toString().getBytes());
                    ServerOutStream.flush();
                    System.out.println("@ "+sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("@ Server sending fail");
                }
            }
            if (ClientOutStream!=null){
                try {
                    ClientOutStream.write(sb.toString().getBytes());
                    ClientOutStream.flush();
                    System.out.println("@ "+sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("@ Client sending fail");
                }


            }
            InputStream ServerInStream = slitherio.bluetoothConnection.getServerInputStream();
            InputStream ClientInStream = slitherio.bluetoothConnection.getClientInputStream();
            double serverMoveDir = targetDir;
            double clientMoveDir = targetDir;
            if(ServerInStream != null){
                try {


                    BufferedReader br = new BufferedReader(new InputStreamReader(ServerInStream));
                    serverMoveDir = Double.parseDouble(br.readLine());

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("@ exception");

                }
            }
            if(ClientInStream != null){
                try {


                    BufferedReader br = new BufferedReader(new InputStreamReader(ClientInStream));
                    clientMoveDir = Double.parseDouble(br.readLine());

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("@ exception");

                }
            }
            targetDir = (serverMoveDir + clientMoveDir) /2;

        }
        return targetDir;
    }

}
