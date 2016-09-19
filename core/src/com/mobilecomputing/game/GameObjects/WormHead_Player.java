package com.mobilecomputing.game.GameObjects;

import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.slitherio;

/**
 * Created by Venom on 18/09/2016.
 */
public class WormHead_Player extends WormHead{
    public WormHead_Player(float x,float y,WormTemplate_Player worm){
        super(x,y,worm);

    }

    @Override
    public void update(){
        super.update();
        timeSinceLastTap++;
        moveDir= UGameLogic.TryRotateTowardsAlt(moveDir,getTargetDir(),7,0,360);
        if(slitherio.dragging){

            //UGameLogic.LogMsg("Target Dir "+targetDir);


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
