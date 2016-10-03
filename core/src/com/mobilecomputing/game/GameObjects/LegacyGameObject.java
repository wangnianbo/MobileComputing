package com.mobilecomputing.game.GameObjects;


import java.util.ArrayList;
import java.util.HashSet;

import helperDataStructures.Point2D;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.mobilecomputing.game.Drawables.AnimationSetHolder;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.ViewableObject;
import com.mobilecomputing.game.World;
import com.mobilecomputing.game.Terrain.HaxWall;


public class LegacyGameObject extends ViewableObject {
	public boolean active = true;
	private World world;
	public boolean destroyOnDeactivate=false;
    //Horizontal speed: Is positive when object is moving right (effects x position each step)
    public float hspeed = 0;
    //Vertical speed: Is positive when object is going down (effects y position each step)
    public float vspeed = 0;
    public int maxHealth=95;
    public int health=maxHealth;
    public int identifier=UGameLogic.UNSET_INT;
    
    //Previous speeds;
    public float prevHspeed = 0;
    public float prevVspeed = 0;
	public AnimationSetHolder myAnimations=new AnimationSetHolder();
	//public SimpleCollider simpleCollider;
	public Shape2D shapeCollider;
	private LegacyGameObject _creator;
	public float terminalVelocity=10;
	public boolean bouncing=true;
	public boolean affectedByAirDrag=true;
    //Density of the object, for calculating the mass. MUST be solid;
    public float density = 0.5f;
    
    public int alignment;
    public boolean isImmovable = false;
    public boolean HasChanged(){
    	return hspeed!=prevHspeed || vspeed!=prevVspeed || x!=prevX || y!=prevY;
    }
    
    //When immovable is true, applying momentum to it does nothing;
    public float GetAirDragAppliedTo()
    {
        if (affectedByAirDrag && world != null)
        {
            float valToReturn = 0.05f;
            return valToReturn;
        }
        return 0;

    }
	
    public float GetSpeed()
    {
        return (float)UGameLogic.GetDistanceBetween(0, 0, hspeed, vspeed);
    }

	
	public LegacyGameObject getCreator(){
		return _creator;
	}
	public LegacyGameObject setCreator(LegacyGameObject g){
		_creator=g;
		if(_creator!=null)
		alignment=_creator.alignment;
		return g;
	}
	
    public HashSet<LegacyGameObject> objectsReactedToInCollision=new HashSet<LegacyGameObject>();
	
	public World getWorld(){
		return world;
	}
    public void SetWorld(World newWorld)
    {
        world = newWorld;
    }
	
	public LegacyGameObject(float x,float y){
		super(x,y);
		
	}
	

    //On adding an object to a given world;
    public void OnAddToWorld()
    {
    }

    
    
    public void UpdateRegionsTouching()
    {
    	
    }
    @Override 
    public void earlyUpdate(){
    	super.earlyUpdate();

    	prevHspeed=hspeed;
    	prevHspeed=hspeed;
        objectsReactedToInCollision.clear();
    }
    
    public void DisplayRecentDamage(){
    	if(damageToDisplay>0){
    		world.addObject(new DamageText(""+damageToDisplay,x,y));
			damageToDisplay=0;
    	}
    }
    
    @Override
    public void update(){
    	super.update();
    	if(damageToDisplay>0 && step%UGameLogic.lengthOfSecond/4==0){
    		DisplayRecentDamage();
    	}
    	float totalDrag=GetAirDragAppliedTo();
    	LimitSpeed();
        vspeed = vspeed * (1 - totalDrag);

        //Previously total drag was here

        hspeed = hspeed * (1 - totalDrag);
        
    	if(autoProgressAnimation){
    		myAnimations.Progress();
    	}
    	
    	
    	if(!CheckMove(x+hspeed,y+vspeed) && bouncing){
    		if(!CheckPositionAvailable(x+Math.signum(hspeed),y)){
    			hspeed=-hspeed;
    		}
    		if(!CheckPositionAvailable(x,y+Math.signum(vspeed))){
    			vspeed=-vspeed;
    		}
    		
    	}
    	
    
    }
    
    public void LimitSpeed()
    {
        float speedLimit = terminalVelocity;


        LimitSpeed(speedLimit,true);

    }
    
    public void LimitSpeed(float speedLimit,boolean manhattanMethod)
    {
        double sT = Math.sqrt(hspeed * hspeed + vspeed * vspeed);
        if (manhattanMethod)
        {
            if (Math.abs(hspeed) > Math.abs(speedLimit))
            {
                hspeed = Math.signum(hspeed) * speedLimit;
            }
            if (Math.abs(vspeed) > Math.abs(speedLimit))
            {
                vspeed = Math.signum(vspeed) * speedLimit;
            }

        }
        else
        {
            if (sT > speedLimit)
            {
                double ang = Math.atan2(vspeed, hspeed);
                hspeed = (float)(speedLimit * Math.cos(ang));
                vspeed = (float)(speedLimit * Math.sin(ang));
            }
        }
    }
    
    
    public boolean autoProgressAnimation=true;
	private boolean blockFurtherReactionFromOtherInCollision=false;
    
    @Override
    public void render(){
    	super.render();
    	SpriteImageData.ResetProperties();

    	if(myAnimations!=null){
    		myAnimations.Render(x,y,false);
    	}
    	SpriteImageData.ResetProperties();
    }
    
    public boolean CheckMove(float newX,float newY){
    	

    	if(CheckPositionAvailable(newX,newY)){
    		x=newX;
    		y=newY;

    		return true;
    	}
    	else{
        	Point2D point=CheckBinaryMoveDir(newX-x,newY-y);
        	x=point.x;
        	y=point.y;

    	}
    	
    	
    	return false;
    }
    

    
    public boolean CheckPositionAvailable(float newX, float newY)
    {


    	if(newX<0 || newY<0 || newX>world.width || newY>world.height){
    		return false;
    	}
    	ArrayList<LegacyGameObject> solidsTouching=getObjectsTouchingAtPosition(newX, newY, true,true,true);
    	if(solidsTouching.size()>0){
    		return false;
    	}
    	
    	
    	return true;
    }
    
    public Point2D CheckBinaryMoveDir(float diffX,float diffY){

        if (diffX == 0 && diffY == 0)
        {
            return new Point2D(x, y);
        }
        double dir = UGameLogic.dirToPoint(0, 0, diffX, diffY);

        double dirR=UGameLogic.TrueBearingsToRadians(dir);
        int high = (int)Math.ceil(Math.sqrt(diffX*diffX+diffY*diffY));
        int lastVal = 0;
        int low = 0;

        Point2D pointToReturn = new Point2D(x, y);
        float propX = x;
        float propY = y;
        while (low + 1 < high)
        {
            int mid = (low + high) / 2;
            propX = (float)(x+mid*Math.cos(dirR));
            propY = (float)(y-mid*Math.sin(dirR));
            if (CheckPositionAvailable(propX, propY))
            {
                lastVal = mid;
                low = mid;
                pointToReturn.x = propX;
                pointToReturn.y = propY;
            }
            else
            {
                high = mid;
            }
        }
        return pointToReturn;

    }

    
    
	public boolean CanSlideOnSolidSurfaces() {
		return true;
	}
	
    //Return all the objects that currently touch this object
    public ArrayList<LegacyGameObject> getObjectsTouchingAtPosition(float x1, float y1, boolean useMargin, boolean solidOnly, boolean unique)
    {
    	return getObjectsTouchingAtPosition(x1,y1,useMargin,solidOnly,unique,null);
    }
	
    public ArrayList<LegacyGameObject> getObjectsTouchingAtPosition(float x1, float y1, boolean useMargin, boolean solidOnly, boolean unique, Iterable<LegacyGameObject> touchableObjects)
    {
        HashSet<LegacyGameObject> listOfObjects = new HashSet<LegacyGameObject>();
        if (world == null)
            return UGameLogic.hashSetToList(listOfObjects);
        if (touchableObjects == null)
        {
        	
        	/*
            List<LegacyGameObject> touchableObjectsList = new List<LegacyGameObject>();
            //Get chunks to iterate over;
            IEnumerable<TerrainChunk> chunks;

                chunks = world.GetChunksTouching(simpleCollider, x1, y1);

            //Load the objects from whatever chunks you cover;
            foreach (TerrainChunk chunk in chunks)
            {

                if (!chunk.active)
                {
                    if(!UGameLogic.haxAllowed)
                        continue;
                }
                IEnumerable objectsIn = chunk.localObjects;
                foreach (LegacyGameObject other in objectsIn)
                {
                */
                    //skip objects you can't touch;
        		for(LegacyGameObject other :world.getActiveLocalObjects()){
                    if (!ShouldRegisterTouch(other, true))
                        continue;
                    //!((ArrayList)touchableObjects).contains(other)
                    if (!other.destroyed && other.active)
                    {
                        //SHOULD BE IDENTICAL TO OTHER REGION R1
                        //#region R1
                        if (((solidOnly) && !(ShouldTreatAsSolid(other, true))) || ((unique) && (objectsReactedToInCollision.contains(other))))
                        {
                            continue;
                        }
                        //An object shouldn't collide with itself;
                        if (other != this && other != null)
                        {
                            //check for collisions between the objects
                            if (CollisionOffsetBetween(other, x1, y1, useMargin))
                            {
                                listOfObjects.add(other);
                            }
                        }
                        //#endregion
                    }

                }
                /*
            }
            return listOfObjects.ToList<LegacyGameObject>();
            */
        }
        else
        {
            for (LegacyGameObject other : touchableObjects)
            {
                //Skip objects you can't touch;
                if (!ShouldRegisterTouch(other, true))
                    continue;
                //SHOULD BE IDENTICAL TO OTHER REGION R1

                if (((solidOnly) && !(ShouldTreatAsSolid(other, true))) || ((unique) && (objectsReactedToInCollision.contains(other))))
                {
                    continue;
                }
                //An object shouldn't collide with itself;
                if (other != this && other != null)
                {
                    //check for collisions between the objects
                    if (CollisionOffsetBetween(other, x1, y1, useMargin))
                    {

                        listOfObjects.add(other);
                    }
                }

            }
        }
        return UGameLogic.hashSetToList(listOfObjects);
        		//listOfObjects.ToList<LegacyGameObject>();
    }
    
    public boolean ShouldRegisterTouch(LegacyGameObject other, boolean caller)
    {
        if (!caller)
            return true;
        else
            return other.ShouldRegisterTouch(this, false);
    }


                	/*if(this.shapeCollider instanceof Polygon && obj2.shapeCollider instanceof Polygon){
            		Polygon poly1=((Polygon)this.shapeCollider);
            		Polygon poly2=((Polygon)this.shapeCollider);
                  	poly1.setPosition(newX, newY);
                  	poly2.setPosition(obj2.x,obj2.y);
                  	return Intersector.overlapConvexPolygons(poly1,poly2);
            	}
            	*/

    public boolean secondaryHitTest(LegacyGameObject o,float x,float y){
        return true;
    }


    //Check if this object is touching another given object;
    public boolean CollisionOffsetBetween(LegacyGameObject obj2, float newX, float newY, boolean useMargin)
    {
        if (obj2 != this || obj2 != this.getCreator())
        {
            if (this.shapeCollider != null && obj2.shapeCollider != null)
            {

                if(CollisionOffsetBetween(this.shapeCollider,newX,newY,obj2.shapeCollider,obj2.x,obj2.y)){
                
                    return secondaryHitTest(obj2,newX,newY) && obj2.secondaryHitTest(this,obj2.x,obj2.y);
                }
            	/*
                //boolean res = simpleCollider.CheckOffsetTouching(newX, newY, obj2.simpleCollider, obj2.x, obj2.y, useMargin);

            	if(this.shapeCollider instanceof Rectangle && obj2.shapeCollider instanceof Rectangle){
            		
            		Rectangle rec1=((Rectangle)this.shapeCollider);
            		Rectangle rec2=((Rectangle)obj2.shapeCollider);
            		float prev_x1=rec1.x;
            		float prev_y1=rec1.y;
            		float prev_x2=rec2.x;
            		float prev_y2=rec2.y;
            		
            		
                  	rec1.setPosition(newX+prev_x1, newY+prev_y1);
                  	rec2.setPosition(obj2.x+prev_x2,obj2.y+prev_y2);
                  	boolean res=Intersector.overlaps(rec1,rec2);
                  	rec1.setPosition(prev_x1, prev_y1);
                  	rec2.setPosition(prev_x2,prev_y2);
                  	return res;
            	}
            	else if(this.shapeCollider instanceof Rectangle && obj2.shapeCollider instanceof Circle){
            		
            		
            		
            		Rectangle rec1=((Rectangle)this.shapeCollider);
            		Circle circle2=((Circle)obj2.shapeCollider);
            		float prev_x1=rec1.x;
            		float prev_y1=rec1.y;
            		float prev_x2=circle2.x;
            		float prev_y2=circle2.y;
            		rec1.setPosition(newX+prev_x1, newY+prev_y1);
                  	circle2.setPosition(obj2.x+prev_x2,obj2.y+prev_y2);
                  	boolean res=Intersector.overlaps(circle2,rec1);
                  	rec1.setPosition(prev_x1, prev_y1);
                  	circle2.setPosition(prev_x2,prev_y2);
                  	return res;
            	}
            	else if(this.shapeCollider instanceof Circle && obj2.shapeCollider instanceof Rectangle){
            		
            		Rectangle rect2=((Rectangle)obj2.shapeCollider);
            		Circle circle1=((Circle)this.shapeCollider);
            		float prev_x1=circle1.x;
            		float prev_y1=circle1.y;
            		float prev_x2=rect2.x;
            		float prev_y2=rect2.y;
            		circle1.setPosition(newX+prev_x1, newY+prev_y1);
                  	rect2.setPosition(obj2.x+prev_x2,obj2.y+prev_y2);
                  	boolean res=Intersector.overlaps(circle1,rect2);
                  	circle1.setPosition(prev_x1, prev_y1);
                  	rect2.setPosition(prev_x2,prev_y2);
                  	return res;
                  	
            	}
            	else if(this.shapeCollider instanceof Circle && obj2.shapeCollider instanceof Circle){
            		
   
            		Circle circle1=((Circle)this.shapeCollider);
            		Circle circle2=((Circle)obj2.shapeCollider);
             		float prev_x1=circle1.x;
            		float prev_y1=circle1.y;
            		float prev_x2=circle2.x;
            		float prev_y2=circle2.y;
            		circle1.setPosition(newX+prev_x1, newY+prev_y1);
                  	circle2.setPosition(obj2.x+prev_x2,obj2.y+prev_y2);
                  	boolean res=Intersector.overlaps(circle1,circle2);
                  	circle1.setPosition(prev_x1, prev_y1);
                  	circle2.setPosition(prev_x2,prev_y2);
                  	return res;
            	}

            	else if(this.shapeCollider instanceof Polygon && obj2.shapeCollider instanceof Polygon){
            		if(obj2 instanceof com.mobilecomputing.game.GameObjects.Tanks.Missile || this instanceof com.mobilecomputing.game.GameObjects.Tanks.Missile){
            			int a=31;
            			//UGameLogic.LogMsg("test");
            		}
              		Polygon poly1=((Polygon)this.shapeCollider);
              		Polygon poly2=((Polygon)obj2.shapeCollider);
             		float prev_x1=poly1.getX();
            		float prev_y1=poly1.getY();
            		float prev_x2=poly2.getX();
            		float prev_y2=poly2.getY();
            		poly1.setPosition(newX+prev_x1, newY+prev_y1);
                  	poly2.setPosition(obj2.x+prev_x2,obj2.y+prev_y2);
                  	
                  	boolean res=Intersector.overlapConvexPolygons(poly1,poly2);
                  	poly1.setPosition(prev_x1, prev_y1);
                  	poly2.setPosition(prev_x2,prev_y2);
                  	return res;
            	}
            	
            	else if(this.shapeCollider instanceof Polygon || obj2.shapeCollider instanceof Polygon){
             		float prev_x1=0;
            		float prev_y1=0;
               		float prev_x2=0;
            		float prev_y2=0;
            		Polygon poly1;
            		Polygon poly2;
            		Rectangle rect2;
            		Circle circle2;
            		Rectangle rect1;
            		Circle circle1;
            		if(this.shapeCollider instanceof Polygon){
            			poly1=((Polygon)this.shapeCollider);
                 		prev_x1=poly1.getX();
                		prev_y1=poly1.getY();
                		poly1.setPosition(newX+prev_x1, newY+prev_y1);
                		boolean retVal=false;
                		if(obj2.shapeCollider instanceof Circle){
                			circle2=((Circle)obj2.shapeCollider);
                     		prev_x2=circle2.x;
                    		prev_y2=circle2.y;
                    		circle2.setPosition(obj2.x+prev_x2, obj2.y+prev_y2);
                    		retVal=UGameLogic.isCollision(poly1, circle2);
                       		circle2.setPosition(prev_x2,prev_y2);
                       		
                		}
                		if(obj2.shapeCollider instanceof Rectangle){
                			rect2=((Rectangle)obj2.shapeCollider);
                     		prev_x2=rect2.x;
                    		prev_y2=rect2.y;
                    		rect2.setPosition(obj2.x+prev_x2, obj2.y+prev_y2);
                    		retVal=UGameLogic.isCollision(poly1,rect2);
                    		rect2.setPosition(prev_x2,prev_y2);
                    		
                		}
                      	poly1.setPosition(prev_x1, prev_y1);
                      	return retVal;

            		}

            		else if(obj2.shapeCollider instanceof Polygon){
            			poly2=((Polygon)obj2.shapeCollider);
                 		prev_x2=poly2.getX();
                		prev_y2=poly2.getY();
                      	poly2.setPosition(obj2.x+prev_x2,obj2.y+prev_y2);
                      	boolean retVal=false;
                		if(this.shapeCollider instanceof Circle){
                			circle1=((Circle)this.shapeCollider);
                     		prev_x1=circle1.x;
                    		prev_y1=circle1.y;
                      		circle1.setPosition(newX+prev_x1, newY+prev_y1);
                      		UGameLogic.isCollision(poly2,circle1);
                      		circle1.setPosition(prev_x1,prev_y1);

                		}
                		if(this.shapeCollider instanceof Rectangle){
                			rect1=((Rectangle)this.shapeCollider);
                     		prev_x1=rect1.x;
                    		prev_y1=rect1.y;
                    		rect1.setPosition(newX+prev_x1, newY+prev_y1);
                      		retVal= UGameLogic.isCollision(poly2,rect1);
                    		rect1.setPosition(prev_x1,prev_y1);

                		}
                      	poly2.setPosition(prev_x2,prev_y2);
                		return retVal;
            		}
            		
            		

            	}
            	*/
            }
        }
        return false;
    }

    //Check if this object is touching another given object;
    public static boolean CollisionOffsetBetween(Shape2D collider1, float newX, float newY, Shape2D collider2,float x2,float y2)
    {




                //boolean res = simpleCollider.CheckOffsetTouching(newX, newY, obj2.simpleCollider, obj2.x, obj2.y, useMargin);
            	/*if(this.shapeCollider instanceof Polygon && obj2.shapeCollider instanceof Polygon){
            		Polygon poly1=((Polygon)this.shapeCollider);
            		Polygon poly2=((Polygon)this.shapeCollider);
                  	poly1.setPosition(newX, newY);
                  	poly2.setPosition(obj2.x,obj2.y);
                  	return Intersector.overlapConvexPolygons(poly1,poly2);
            	}
            	*/
                if(collider1 instanceof Rectangle && collider2 instanceof Rectangle){

                    Rectangle rec1=((Rectangle)collider1);
                    Rectangle rec2=((Rectangle)collider2);
                    float prev_x1=rec1.x;
                    float prev_y1=rec1.y;
                    float prev_x2=rec2.x;
                    float prev_y2=rec2.y;


                    rec1.setPosition(newX+prev_x1, newY+prev_y1);
                    rec2.setPosition(x2+prev_x2,y2+prev_y2);
                    boolean res=Intersector.overlaps(rec1,rec2);
                    rec1.setPosition(prev_x1, prev_y1);
                    rec2.setPosition(prev_x2,prev_y2);
                    return res;
                }
                else if(collider1 instanceof Rectangle && collider2 instanceof Circle){



                    Rectangle rec1=((Rectangle)collider1);
                    Circle circle2=((Circle)collider2);
                    float prev_x1=rec1.x;
                    float prev_y1=rec1.y;
                    float prev_x2=circle2.x;
                    float prev_y2=circle2.y;
                    rec1.setPosition(newX+prev_x1, newY+prev_y1);
                    circle2.setPosition(x2+prev_x2,y2+prev_y2);
                    boolean res=Intersector.overlaps(circle2,rec1);
                    rec1.setPosition(prev_x1, prev_y1);
                    circle2.setPosition(prev_x2,prev_y2);
                    return res;
                }
                else if(collider1 instanceof Circle && collider2 instanceof Rectangle){

                    Rectangle rect2=((Rectangle)collider2);
                    Circle circle1=((Circle)collider1);
                    float prev_x1=circle1.x;
                    float prev_y1=circle1.y;
                    float prev_x2=rect2.x;
                    float prev_y2=rect2.y;
                    circle1.setPosition(newX+prev_x1, newY+prev_y1);
                    rect2.setPosition(x2+prev_x2,y2+prev_y2);
                    boolean res=Intersector.overlaps(circle1,rect2);
                    circle1.setPosition(prev_x1, prev_y1);
                    rect2.setPosition(prev_x2,prev_y2);
                    return res;

                }
                else if(collider1 instanceof Circle && collider2 instanceof Circle){


                    Circle circle1=((Circle)collider1);
                    Circle circle2=((Circle)collider2);
                    float prev_x1=circle1.x;
                    float prev_y1=circle1.y;
                    float prev_x2=circle2.x;
                    float prev_y2=circle2.y;
                    circle1.setPosition(newX+prev_x1, newY+prev_y1);
                    circle2.setPosition(x2+prev_x2,y2+prev_y2);
                    boolean res=Intersector.overlaps(circle1,circle2);
                    circle1.setPosition(prev_x1, prev_y1);
                    circle2.setPosition(prev_x2,prev_y2);
                    return res;
                }

                else if(collider1 instanceof Polygon && collider2 instanceof Polygon){

                    Polygon poly1=((Polygon)collider1);
                    Polygon poly2=((Polygon)collider2);
                    float prev_x1=poly1.getX();
                    float prev_y1=poly1.getY();
                    float prev_x2=poly2.getX();
                    float prev_y2=poly2.getY();
                    poly1.setPosition(newX+prev_x1, newY+prev_y1);
                    poly2.setPosition(x2+prev_x2,y2+prev_y2);

                    boolean res=Intersector.overlapConvexPolygons(poly1,poly2);
                    poly1.setPosition(prev_x1, prev_y1);
                    poly2.setPosition(prev_x2,prev_y2);
                    return res;
                }

                else if(collider1 instanceof Polygon || collider2 instanceof Polygon){
                    float prev_x1=0;
                    float prev_y1=0;
                    float prev_x2=0;
                    float prev_y2=0;
                    Polygon poly1;
                    Polygon poly2;
                    Rectangle rect2;
                    Circle circle2;
                    Rectangle rect1;
                    Circle circle1;
                    if(collider1 instanceof Polygon){
                        poly1=((Polygon)collider1);
                        prev_x1=poly1.getX();
                        prev_y1=poly1.getY();
                        poly1.setPosition(newX+prev_x1, newY+prev_y1);
                        boolean retVal=false;
                        if(collider2 instanceof Circle){
                            circle2=((Circle)collider2);
                            prev_x2=circle2.x;
                            prev_y2=circle2.y;
                            circle2.setPosition(x2+prev_x2, y2+prev_y2);
                            retVal=UGameLogic.isCollision(poly1, circle2);
                            circle2.setPosition(prev_x2,prev_y2);

                        }
                        if(collider2 instanceof Rectangle){
                            rect2=((Rectangle)collider2);
                            prev_x2=rect2.x;
                            prev_y2=rect2.y;
                            rect2.setPosition(x2+prev_x2, y2+prev_y2);
                            retVal=UGameLogic.isCollision(poly1,rect2);
                            rect2.setPosition(prev_x2,prev_y2);

                        }
                        poly1.setPosition(prev_x1, prev_y1);
                        return retVal;

                    }

                    else if(collider2 instanceof Polygon){
                        poly2=((Polygon)collider2);
                        prev_x2=poly2.getX();
                        prev_y2=poly2.getY();
                        poly2.setPosition(x2+prev_x2,y2+prev_y2);
                        boolean retVal=false;
                        if(collider1 instanceof Circle){
                            circle1=((Circle)collider1);
                            prev_x1=circle1.x;
                            prev_y1=circle1.y;
                            circle1.setPosition(newX+prev_x1, newY+prev_y1);
                            UGameLogic.isCollision(poly2,circle1);
                            circle1.setPosition(prev_x1,prev_y1);

                        }
                        if(collider1 instanceof Rectangle){
                            rect1=((Rectangle)collider1);
                            prev_x1=rect1.x;
                            prev_y1=rect1.y;
                            rect1.setPosition(newX+prev_x1, newY+prev_y1);
                            retVal= UGameLogic.isCollision(poly2,rect1);
                            rect1.setPosition(prev_x1,prev_y1);

                        }
                        poly2.setPosition(prev_x2,prev_y2);
                        return retVal;
                    }






        }
        return false;
    }




    public boolean ShouldTreatAsSolid(LegacyGameObject other, boolean caller)
    {

    	if(other instanceof HaxWall){
    		return true;
    	}
        if (caller)
        {
            return other.ShouldTreatAsSolid(this, false);
        }
        else
        {
            return false;
        }
    }
    
    public void ReactToObjectCollisions(ArrayList<LegacyGameObject> objectsTouching)
    {
        for (LegacyGameObject obj : objectsTouching)
        {

            TryReactToCollision(obj);
        }
    }
    
    public int collisionPriorityScore(){
    	return 0;
    }
    
    public void TryReactToCollision(LegacyGameObject obj)
    {
        blockFurtherReactionFromOtherInCollision = false;
        if (!destroyed && !obj.destroyed)
        {
            if (!objectsReactedToInCollision.contains(obj))
            {
                objectsReactedToInCollision.add(obj);


                //Other object reacts;
                if (!obj.objectsReactedToInCollision.contains(this))
                {
                    obj.objectsReactedToInCollision.add(this);

                    if (collisionPriorityScore() > obj.collisionPriorityScore())
                    {
                        this.OnCollision(obj);
                        if (!blockFurtherReactionFromOtherInCollision)
                        {
                            obj.OnCollision(this);
                        }
                    }
                    else
                    {
                        obj.OnCollision(this);
                        if (!blockFurtherReactionFromOtherInCollision)
                        {
                            this.OnCollision(obj);
                        }
                    }
                }
                else
                {
                    this.OnCollision(obj);
                }
            }
        }
    }
    public void OnCollision(LegacyGameObject other)
    {
    }
    
    public void DrawCollider(){
    	SpriteImageData.DrawShape(shapeCollider, x,y);
    }
    

    public float centerX()
    {

            float cx = x;
            Point2D cc=colliderCenter(shapeCollider);
            if (shapeCollider != null)
                cx = cx +cc.x;
            return cx;

    }
    
    public float centerY()
    {

            float cy = y;
            Point2D cc=colliderCenter(shapeCollider);
            if (shapeCollider != null)
                cy = cy +cc.y;
            return cy;

    }
    
    public Point2D colliderCenter(Shape2D shape){
    	float x1=0;
    	float y1=0;
    	if(shape!=null){
    		if(shape instanceof Circle){
    			x1=((Circle)shape).x;
    			y1=((Circle)shape).y;
    		}
    		if(shape instanceof Rectangle){
    			Rectangle rect=((Rectangle)shape);
    			x1=rect.x+rect.getWidth()/2;
    			y1=rect.y+rect.getHeight()/2;
    		}
    		if(shape instanceof Polygon){
    			Polygon poly=((Polygon)shape);
    			x1=poly.getX();
    			y1=poly.getY();
    		}
    	}
    	Point2D newPoint=new Point2D(x1,y1);
    	return newPoint;
    }
    
    public float shapeArea(Shape2D shape){

    	if(shape!=null){
    		if(shape instanceof Circle){
	    			return ((Circle)shape).area();

    		}
    		if(shape instanceof Rectangle){
    			return ((Rectangle)shape).area();

    		}
    		if(shape instanceof Polygon){
    			Polygon poly=((Polygon)shape);
    			return poly.area();
    		}
    	}

    	return 0;
    }
    

    public void ApplyMomentum(float mx, float my)
    {
    	ApplyMomentum(mx,my,true);
    }
    
    //Apply momentum to a given object (basically speed taking the mass into account)
    public void ApplyMomentum(float mx, float my, boolean isMax)
    {
        if (!isImmovable)
        {

            float hspeed_delta = mx / GetMass();
            float vspeed_delta = my / GetMass();
            float prevHspeed = hspeed;
            float prevVspeed = vspeed;
            int signH = (int)Math.signum(hspeed_delta);
            int signV = (int)Math.signum(vspeed_delta);


            if (isMax)
            {
                hspeed = (float)UGameLogic.IncreaseInAndLimit(hspeed, hspeed_delta);
                vspeed = (float)UGameLogic.IncreaseInAndLimit(vspeed, vspeed_delta);
            }
            else
            {
                hspeed = hspeed + hspeed_delta;
                vspeed = vspeed + vspeed_delta;
            }
        }
    }
    
    public float roughAreaOccupied()
    {


            	
    	return shapeArea(shapeCollider);

        
    }

    //Get the mass of the object;
    public float GetMass()
    {
        return Math.max(roughAreaOccupied() * density, 1);
    }

    
    public boolean isDamageable=false;
    public void onInjury(LegacyGameObject src,int damageAmount){
    	recentDamageAlignment=src.alignment;
    	queueDisplayDamage(damageAmount);
    }
    
    
    public int recentDamageAlignment=0;
    
    public boolean injure(LegacyGameObject src,int damageAmount){
    
    	if(!isDamageable){
    		return false;
    	}
    	if(damageAmount<1){
    		return false;
    	}
    	health=health-damageAmount;
    	if(health<0){
    		Die();
    	}
    	onInjury(src,damageAmount);
    	return true;
    	
    }

    public void Die(){
    	if(!destroyed){
    		onDeath();
    		instanceDestroy();

    	}
    }
    
    public int damageToDisplay=0;
    public void queueDisplayDamage(int damageAmount){
    	damageToDisplay+=damageAmount;
    	
    }
    
}

