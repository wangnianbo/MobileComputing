package com.mobilecomputing.game.GameObjects.Tanks;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.GameObjects.LegacyGameObject;
import com.mobilecomputing.game.SoundController;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.Drawables.SpriteImageData;

public class Missile extends Projectile {
	public Missile(LegacyGameObject creator, float x, float y, float speed, double dir){
		super(creator,x,y);
        float ang=(float)UGameLogic.TrueBearingsToRadians(dir);
        directionAiming=dir;
        this.hspeed = (float)Math.cos(ang) * speed;
        this.vspeed = (float)-Math.sin(ang)*speed;
        int w=12;
        int h=20;
        Rectangle treeRect=new Rectangle(-w/2,-h/2,w,h);
        shapeCollider=new Polygon(new float[] {
                treeRect.x, treeRect.y,
                treeRect.x, treeRect.y + treeRect.height,
                treeRect.x + treeRect.width, treeRect.y + treeRect.height,
                treeRect.x + treeRect.width, treeRect.y
            });
        bouncing=false;
	}
	
	
	public void update(){
		super.update();
    	//((Polygon)(shapeCollider)).setRotation((float)directionAiming);
		directionAiming=UGameLogic.dirToPoint(0, 0,hspeed,-vspeed);
	}
	int bounces=0;
	int maxBounces=2;
	@Override
    public boolean CheckMove(float newX,float newY){

    	boolean val=super.CheckMove(newX,newY);
    	if(!val){
    		if(bounces<maxBounces){
    			bounces++;
    			if(!CheckPositionAvailable(x+UGameLogic.smallestUnit*10*Math.signum(hspeed),y)){
        			hspeed=-hspeed;
    			}
    			if(!CheckPositionAvailable(x,y+UGameLogic.smallestUnit*10*Math.signum(vspeed))){
        			vspeed=-vspeed;
    			}
    			//bounced=true;
    		}
    		else{
    			instanceDestroy();
    		}
    	}

    	return val;
    }
	
	@Override
	public void OnAddToWorld(){
		super.OnAddToWorld();
		SoundController.PlaySound("projectile/plasmaShot");
	}
	@Override
	public void onDestroy(){
		//getWorld().addObject(new Explosion(this,x,y,4));
	}
	public double directionAiming;
	
	@Override
	public void render(){
		SpriteImageData.ResetProperties();
		SpriteImageData.rotation=(float)directionAiming-90;
		SpriteImageData.Draw("missile", x, y);


		//SpriteImageData.DrawShape(shapeCollider,x,y);
		//DrawCollider();

	}
	@Override
	public void OnCollision(LegacyGameObject other){
		if((other.alignment!=alignment || bounces>0) && !ShouldTreatAsSolid(other,true)){
			instanceDestroy();
		}

		
	}
}
