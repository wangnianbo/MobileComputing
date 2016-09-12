package com.mobilecomputing.game.GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.UGameLogic;

/**
 * Created by Venom on 12/09/2016.
 */
public class FadingSegment extends LegacyGameObject{
    public WormSegment segment;
    public WormHead head;
    public int maxLifeTimer= UGameLogic.lengthOfSecond*2;
    public int lifeTimer;
    public FadingSegment(WormSegment segment){
        super(segment.x,segment.y);
        this.segment=segment;
        lifeTimer=maxLifeTimer;
        int segmentIndex=0;
        for(int k=0;k<segment.worm.getTailSegments().size();k++){
            WormSegment otherSegment=segment.worm.getTailSegments().get(k);
            if(otherSegment==segment){
                segmentIndex=k;
                break;
            }
        }
        float speed=1*segmentIndex/(float)Math.max(segment.worm.getTailSegments().size(),1);

        if(segment.nextSegment!=null){
            double dirR=UGameLogic.dirToPoint(segment.nextSegment.x,segment.nextSegment.y,segment.x,segment.y,false);
            hspeed=(float)(Math.cos(dirR)*speed);
            vspeed=(float)(-Math.sin(dirR)*speed);
        }
        else if(segment.prevSegment!=null){
            double dirR=UGameLogic.dirToPoint(segment.x,segment.y,segment.prevSegment.x,segment.prevSegment.y,false);
            hspeed=(float)(Math.cos(dirR)*speed);
            vspeed=(float)(-Math.sin(dirR)*speed);
        }
    }


    public float GetAirDragAppliedTo()
    {
        if (affectedByAirDrag && getWorld() != null)
        {
            float valToReturn = 0.01f;
            return valToReturn;
        }
        return 0;

    }

    public FadingSegment(WormHead head){
        super(head.x,head.y);
        this.head=head;
        lifeTimer=maxLifeTimer;
    }


    public void update(){
        super.update();
        lifeTimer--;

        if(lifeTimer<0){
            Die();
        }
        if(segment!=null) {
            segment.x = x;
            segment.y = y;
        }
        if(head!=null){
            head.x=x;
            head.y=y;
        }
    }

    @Override
    public void render(){
        super.render();
        SpriteImageData.ResetProperties();
        float scale=0.8f*Math.max(lifeTimer,1)/(float)maxLifeTimer;
        SpriteImageData.scaleX = scale;
        SpriteImageData.scaleY = scale;
        //SpriteImageData.color = new Color(1, 1, 1, Math.min(scale + 0.5f, 1));
        if(head!=null){

            head.subRenderDead();
        }
        if(segment!=null) {




            this.segment.subRender();
        }


    }


}
