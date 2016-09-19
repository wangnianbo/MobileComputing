package com.mobilecomputing.game.GameObjects;

import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.menus.GameOverMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class WormTemplate extends LegacyGameObject {




    int pointsPerSegment=10;
    public int wormLength;
    public WormTemplate(float x, float y, WormSegment startingSegment)
    {
        super(x, y);

        //simpleCollider = bodyRegionCollider;

        headSegment = startingSegment;
        WormSegment lastSegment = headSegment;
        while (lastSegment != null)
        {

            lastSegment._SetWorm(this);
            lastSegment = lastSegment.prevSegment;
        }
        if(head==null)
            ConstructHead();
        wormLength=getTailSegments().size()*pointsPerSegment;
    }

    public WormTemplate(float x, float y, int segmentNo)
    {
        super(x,y);

        //simpleCollider = bodyRegionCollider;

        CreateSegments(segmentNo);
        if (head == null)
            ConstructHead();
        wormLength=getTailSegments().size()*pointsPerSegment;
    }

    public int getScore(){
        return getTailSegments().size()*pointsPerSegment;
    }





    public WormHead ConstructHead() {
        head=new WormHead(x,y,this);
        return head;
    }


    public void CreateSegments(int segmentNo) {
        WormSegment lastSegment = null;
        headSegment = new WormSegment(this, lastSegment);

        lastSegment = ((WormSegment)headSegment);
        for (int i = 0; i < segmentNo - 1; i++)
        {

            WormSegment newSegment = new WormSegment(this, lastSegment);
            tailEndSegment=newSegment;
            lastSegment = newSegment;
        }
        wormLength=getTailSegments().size()*pointsPerSegment;

    }






    public WormSegment growByOneSegment(){
        return addSegment(new WormSegment(this,tailEndSegment));
    }

    public WormSegment shrinkByOneSegment(){
        WormSegment segmentToReturn=null;
        if(tailEndSegment!=null && tailEndSegment.nextSegment!=null){
            segmentToReturn=tailEndSegment;
            getWorld().addObject(new FadingSegment(segmentToReturn));


            WormSegment nextTail=segmentToReturn.nextSegment;
            segmentToReturn.breakLinkFromChain();
            tailEndSegment=nextTail;

        }

        return segmentToReturn;
    }

    private WormSegment addSegment(WormSegment segment) {
        if(segment==null)
            return null;
        if(tailEndSegment != null) {
            segment.LinkSegments(segment,tailEndSegment);
        } else if (headSegment != null) {
            segment.LinkSegments(segment,headSegment);

        } else {
            headSegment = segment;
        }
        tailEndSegment = segment;
        return segment;
    }

    //public AABB bodyRegionCollider = new AABB(0, 0, 1, 1);
    public WormHead head;




    public WormSegment headSegment;
    public WormSegment tailEndSegment;
    public ArrayList<WormSegment> getTailSegments()
    {

            ArrayList<WormSegment> listToReturn = new ArrayList<WormSegment>();
            WormSegment currentSegment = headSegment;
            while (currentSegment != null)
            {
                listToReturn.add(currentSegment);
                currentSegment = currentSegment.prevSegment;
            }
            return listToReturn;

    }



    public double getDirectionAiming()
    {
        return head.moveDir;
    }


    //On destroying the giant worm;
    @Override
    public void onDestroy()
    {
        head.instanceDestroy();
        super.onDestroy();
    }

    @Override
    public void onDeath(){
        super.onDeath();

        for(WormSegment segment:getTailSegments()){
            getWorld().addObject(new FadingSegment(segment));
        }
        getWorld().addObject(new FadingSegment(head));
    }


    //float lengthS=0;
    //Draw the head

    public float getCameraZoomRatio(){
        //if(getApproxLengthInPixels()!=lengthS) {
            float lengthS = getApproxLengthInPixels();

            float relLength=(float)Math.sqrt(Controller.projectionWidth*Controller.projectionWidth+Controller.projectionHeight*Controller.projectionHeight);
            relLength=relLength*3/4;
            float ratio=lengthS/relLength;
            return Math.max(ratio,1);

        //}
    }

    @Override
    public void render()
    {
        super.render();
        // simpleCollider.DrawOutline(x, y);
        LegacyGameObject t = this;
        ArrayList<WormSegment> segments=getTailSegments();
        Collections.reverse(segments);
        for (WormSegment currentSegment:segments)
        {
            currentSegment.Render();
            //currentSegment = currentSegment.prevSegment;
        }



        head.render();
    }


    @Override
    public void update()
    {

        super.update();
        /*
        if (world != null && world.activePlayer != null && !TreatAsInvisible(world.activePlayer))
        {
            target = world.activePlayer;
        }


        if (head != null)
            stunTimer = head.stunTimer;
        */
        // headSegment = null;
        head.SetWorld(getWorld());

        //UpdateBodyRegionCollider();
        //head.Update();
        x = head.x;
        y = head.y;


        //Make sure that the previous segments follow the head;
        LegacyGameObject curFollowing = this;

        for (WormSegment s :getTailSegments())
        {
            if (!s.destroyed)
            {
                //s.EarlyUpdate();
                //Move the segment closer;
                Random random=new Random();
                if (curFollowing != null)
                {
                    //Get the direction to the segment;
                    double dirS = UGameLogic.dirToPoint(curFollowing.x, curFollowing.y, s.x, s.y);
                    //Bring the segment within minimum distance;
                    double dirSR = UGameLogic.TrueBearingsToRadians(dirS);
                    float dist=segmentDistance;
                    if(head.boosting){
                        dist+=random.nextInt(5)-2;
                    }


                    s.x = curFollowing.x + dist * (float)Math.cos(dirSR);
                    s.y = curFollowing.y - dist * (float)Math.sin(dirSR);
                    //s.simpleCollider.SetRotation((int)dirS);
                }
                else
                {
                    break;
                }

                curFollowing = s;
            }
        }

        int curSize=getTailSegments().size();
        if(wormLength/10>curSize){
           growByOneSegment();
        }
        else if(wormLength/10<curSize){
            shrinkByOneSegment();
        }




        /*
        for (WormSegment s :markedSegments)
        {
            if (s != null && s.destroyed == false)
            {
                FormNewWorm(s);
            }
        }
                markedSegments.clear();
        */

    }

    public ArrayList<WormSegment> markedSegments = new ArrayList<WormSegment>();

    //Distance between segments;
    public int segmentDistance = 10;
    public int getApproxLengthInPixels(){
        return segmentDistance*(1+getTailSegments().size());
    }

    @Override
    public void OnAddToWorld()
    {
        super.OnAddToWorld();
        getWorld().addObject(head);
        //Activate();

    }

    //As heavy as its parts;
/*
    public override float roughAreaOccupied
    {
        get
        {
            float totalArea = 0;
            if (head != null)
                totalArea += head.roughAreaOccupied;
            if (tailSegments.Count > 0)
            {
                totalArea += tailSegments[0].roughAreaOccupied * tailSegments.Count;
            }
            return totalArea;
        }
    }
*/

/*
    public SimpleCollider UpdateBodyRegionCollider()
    {
        float minX = 0;
        float minY = 0;
        float maxX = 0;
        float maxY = 0;
        foreach (WormSegment s in tailSegments)
        {
            if (s.x - x < minX)
                minX = s.x - x;
            if (s.x - x > maxX)
                maxX = s.x - x;
            if (s.y - y < minY)
                minY = s.y - y;
            if (s.y - y > maxY)
                maxY = s.y - y;
        }
        int u = UGameLogic.tileWidth;
        bodyRegionCollider.SetBoundsF(minX - u, minY - u, maxX - minX + u * 2, maxY - minY + u * 2);
        return bodyRegionCollider;
    }
*/

        //UGameLogic.tileWidth;

    //@Override
    /*public void onActivate()
    {
        super.OnActivate();
        head.Activate();
    }

    @Override
    public void Activate()
    {
        base.Activate();
        head.Activate();
    }
    */






/*
    //A special case for which you check for collision with various sub components;
    public WormSegment lastSegmentTouched=null;
    public override bool SecondaryHitTest(LegacyGameObject obj2, float newX, float newY, bool useMargin, float newX2, float newY2)
    {
        float xDiff = newX - x;
        float yDiff = newY - y;
        SimpleCollider sc2 = obj2.simpleCollider;
        foreach (WormSegment s in tailSegments)
        {
            if (s.nextSegment != null)
            {
                s.rotation = (float)UGameLogic.DirToPoint(s.x, s.y, s.nextSegment.x, s.nextSegment.y);
            }
            else
            {
                s.rotation = (float)UGameLogic.DirToPoint(s.x, s.y, head.x, head.y);
            }
            if (s.simpleCollider.CheckOffsetTouching(s.x + xDiff, s.y + yDiff, sc2, newX2, newY2, true))
            {
                lastSegmentTouched = s;
                return true;
            }

        }
        return false;
    }
    #endregion;
    public bool injuryFromHead = false;

    public void FormNewWorm(WormSegment formationSegment)
    {
        if (world != null)
        {

            if (formationSegment != null)
            {
                world.AddObject(new GiantWorm(x, y, formationSegment));
            }
        }
        else
        {
            UGameLogic.LogMsgUnique("Error in GiantWorm.FormNewWorm() can't form a new worm if world is null");
        }
    }

    public override void UpdateRegionsTouching()
    {
        base.UpdateRegionsTouching();
        /*foreach(WormSegment segment in tailSegments)
        {
            segment.SetWorld(world);
            segment.UpdateRegionsTouching();
        }

    }
    //Get and set the object value;
    public override LegacyGameObject target
    {
        get { return head.target; }
        set { head.target = value; }
    }
    */
    //Get the direction aimed at by the head;


    /*
    int tileCollisionCount;
    HashSet<TileObjectPosition> recentlyTouched = new HashSet<TileObjectPosition>();

    //Check if the object can touch a given tile object;
    public override bool ShouldRegisterTouch(TileObjectPosition to)
    {
        return true;
    }

    public override List<TileObjectPosition> GetTilesBlockingAtPosition(float x1, float y1, bool useMargin)
    {
        return new List<TileObjectPosition>();
    }

    //Return the tiles that the current object is touching at a given position;
    public override List<TileObjectPosition> GetTilesTouchingAtPosition(float x1, float y1, TileObjectFilter filter)
    {
        return new List<TileObjectPosition>();
    }

    public override List<TileObjectPosition> GetTilesInRegion(float x1, float y1, float w, float h, TileObjectFilter filter)
    {

        return new List<TileObjectPosition>();

    }


    public override void OnTileCollision(TileObjectPosition tilePos)
    {
        recentlyTouched.Add(tilePos);
        tileCollisionCount++;
        //UGameLogic.LogMsg(""+tileCollisionCount);
        base.OnTileCollision(tilePos);
    }




    public override bool Injure(DamageSource src, float damageAmount, float x, float y)
    {
        if (src.entityCause != null && (src.entityCause.faction == this.faction && src.entityCause as CrystalEye==null && src.entityCause as EyeLazer==null))
        return false;

        if (head as GiantWormHead != null)
        {
            if (head.actionState.Equals(ms_default) || head.actionState.Equals(WormHeadTemplate.ms_post_shoot) || head.actionState.Equals(WormHeadTemplate.ms_telegraph_shoot_egg))
            {
                int accel = (int)damageAmount*2;

                head.phaseVarStep +=accel;
            }
        }


        if (src != null && injuryFromHead)
        {
            if(src as DamageSourceBurn != null)
            {
                //injuryFromHead = false;
            }
            if(src!=null && src.entityCause as ProjectileArrow != null && src as DamageSourceRanged!=null)
            {
                damageAmount +=1f;
            }
            if (src.entityCause as Lazer != null)
            {
                damageAmount /= 2f;
            }
            if (src as DamageSourceExplosion != null)
            {
                damageAmount /= 2.5f;
            }
        }
        if (!injuryFromHead)
        {
            if (this as GiantWorm != null)
            {
                //was 0.45
                damageAmount *= 0.3f;
            }
            else
            {
                damageAmount /= 2f;

            }
            //Find the closest one;
            foreach (WormSegment s in tailSegments)
            {
                if (s != headSegment)
                {
                    if (src.entityCause != null)
                    {
                        if (s.CollisionOffsetBetween(src.entityCause, s.x, s.y, false))
                        {
                            //s.marked=true;
                            s.Injure(src, damageAmount, x, y);
                        }
                    }
                }

            }

        }

        return base.Injure(src, damageAmount, x, y);
    }

*/



    //On collision with another game object
    /*public void OnCollision(LegacyGameObject other)
    {
        super.OnCollision(other);

        //Damage other entities
        //&& (this as GiantWorm==null || injuryFromHead || head.motionState==WormHeadTemplate.ms_lunge)
        if (!IsStunned() && CanMelee(other) && (this as GiantWorm==null || injuryFromHead || head.motionState == WormHeadTemplate.ms_lunge))
        {

            if ((other as LivingEntity != null || other as Shield!= null) && other.faction != faction)
            {
                int damageAmount = 15;
                if(this as CavernWorm != null)
                {
                    damageAmount = 10;
                }

                if (injuryFromHead)
                {
                    damageAmount *= 2;
                }
                other.Injure(new DamageSourceMelee(this, UGameLogic.lengthOfSecond / 2), damageAmount, x, y);

            }
        }
    }
    */


}




