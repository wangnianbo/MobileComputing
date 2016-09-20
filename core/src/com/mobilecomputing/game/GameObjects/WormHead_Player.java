package com.mobilecomputing.game.GameObjects;

import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.slitherio;

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
        if(slitherio.dragging) {
            return UGameLogic.dirToPoint(x, y, slitherio.lastWorldDragX, slitherio.lastWorldDragY);
        }
        else{
            return moveDir;
        }
    }

}
