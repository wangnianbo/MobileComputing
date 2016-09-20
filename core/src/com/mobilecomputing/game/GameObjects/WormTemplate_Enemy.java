package com.mobilecomputing.game.GameObjects;

/**
 * Created by Venom on 18/09/2016.
 */
public class WormTemplate_Enemy extends WormTemplate{
    public WormTemplate_Enemy(float x, float y, WormSegment startingSegment) {
        super(x,y,startingSegment);
    }


    public WormTemplate_Enemy(float x, float y, int segmentNo){
        super(x,y,segmentNo);
    }

    @Override
    public WormHead ConstructHead() {
        head=new WormHead_Enemy(x,y,this);
        return head;
    }


}
