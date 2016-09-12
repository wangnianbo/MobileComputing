package com.mobilecomputing.game.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Drawables.SpriteImageData;

/**
 * Created by Venom on 6/09/2016.
 */
public class WormSegment extends LegacyGameObject{
    @Override
    public void OnCollision(LegacyGameObject other)
    {
        //base.OnCollision(other);
        /*WormTemplate gw = (WormTemplate)worm;
        gw.injuryFromHead = false;
        gw.OnCollision(other);
        gw.injuryFromHead = false;*/
    }


    protected WormTemplate worm;
    public void _SetWorm(WormTemplate newWorm){
        worm = newWorm;
    }



    public WormSegment prevSegment;
    public WormSegment nextSegment;
    public int baseSpeed = 0;
    public Boolean marked = false;



    //A part of the giant worm;
    public WormSegment(WormTemplate worm, WormSegment nextSegment){
        super(worm.x,worm.y);
        /*
        //Width of the head;
        int h_width=UGameLogic.tileWidth*3/2;
        //Height of the head;
        int h_height=UGameLogic.tileWidth*3/2;
        //Collider used for the head;
        simpleCollider = new HitQuad(-h_width / 2, -h_height / 2, h_width, h_height);
        */
        //new HitPentagon(new Point2D(-h_width/2,-h_height/2),new Point2D(-h_width/2,h_height/2),new Point2D(h_width/2,h_height/2),new Point2D(h_width/2,h_height/2),new Point2D(h_width/2,h_height/2+1));
        shapeCollider=new Rectangle(-14,-14,28,28);
        this.worm = worm;
        LinkSegments(this, nextSegment);
    }

    //Link to segments together;
    public void LinkSegments(WormSegment prevSegment, WormSegment nextSegment)
    {

        if(prevSegment!=null){
            prevSegment.nextSegment = nextSegment;

        }
        if (nextSegment != null){
            nextSegment.prevSegment = prevSegment;
        }
        else
        {
            worm.headSegment = prevSegment;

        }
    }

    //Set the next segment in the series;
    public void setNextSegment(WormSegment newSegment)
    {
        //Unlink the previous arrangement
        if (nextSegment != null){

            nextSegment.prevSegment = null;
        }
        nextSegment = newSegment;
    }

    public void setPrevSegment(WormSegment newSegment)
    {
        if (prevSegment != null)
            prevSegment.nextSegment = null;
        prevSegment = newSegment;
    }

    public void breakLinkFromChain()
    {
        setNextSegment(null);
        setPrevSegment(null);
    }

    public void omitLinkFromChain()
    {

        LinkSegments(prevSegment, nextSegment);
        prevSegment = null;
        nextSegment = null;
    }

    //Break from the chain;
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        WormSegment prevSegment = this.prevSegment;
        omitLinkFromChain();
        /*breakLinkFromChain();
        if(worm as GiantWorm!=null)
            ((GiantWorm)worm).markedSegments.Add(prevSegment);
         */
    }







    //Draw the head

    public void Render()
    {
        SpriteImageData.ResetProperties();

        //simpleCollider.DrawOutline(x, y, SpriteImageData.color);

        super.render();
        //Reset scaling, rotation and color values to defaults.
        SpriteImageData.ResetProperties();
        // UGameLogic.LogMsg("Rotation "+moveDir);
       // SpriteImageData.rotation=(float)-moveDir;
        //Draw an image
        subRender();
        //SpriteImageData.DrawShape(shapeCollider,x,y);


        //DrawImage("wormSegment", x, y);
    }

    public void subRender(){
        SpriteImageData.Draw("wormSegment",x,y);
    }


    /*
    public override Boolean Injure(DamageSource src, float damageAmount,float x,float y)
    {
        if (worm != null)
        {
            return worm.Injure(src, damageAmount,x,y);

        }
        else
        {
           return Injure(src,damageAmount,x,y);
        }
        return false;
    }
    */
}
