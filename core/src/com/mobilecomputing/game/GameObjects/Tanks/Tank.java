package com.mobilecomputing.game.GameObjects.Tanks;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.FontController;
import com.mobilecomputing.game.GameObjects.*;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.Drawables.SpriteImageData;


public class Tank extends LegacyGameObject {

	public int maxFuel=2000;
	public int fuel=maxFuel;
	public int fuelIncrement=400;
	public double directionAiming=90;
	public double prevDirectionAiming=90;
	public int delayBeforeFire;
	public int maxDelayBeforeFire= UGameLogic.lengthOfSecond/2;
	public float spawnX;
	public float spawnY;
	
	public Tank(float x, float y) {
		super(x, y);
		spawnX=x;
		spawnY=y;
		isDamageable=true;
		shapeCollider=new Rectangle(-16,-16,32,32);

		myAnimations.SetAnimationSet("tanks");
	}
	public com.mobilecomputing.game.GameObjects.Tanks.TankTeam team;
	
	@Override 
	public void earlyUpdate(){
		super.earlyUpdate();
		prevDirectionAiming=directionAiming;
	}
	
	@Override
	public void update(){
		super.update();
		if( (!turnEnded && fuel<1 && hasFired)){
			endTurn();
		}

		delayBeforeFire--;

	}
	
	public int endTurnTimer=0;
	public boolean hasFired=false;
	public Missile Fire(){
		if(!hasFired || true){
			delayBeforeFire=maxDelayBeforeFire;

			Missile missile=new Missile(this,x,y,10,directionAiming);
			getWorld().addObject(missile);

			hasFired=true;
			return missile;
		}
		endTurn();
		return null;
	}
	
	@Override
	public void render(){
    	SpriteImageData.ResetProperties();

    	SpriteImageData.rotation=(float)(90-directionAiming);
    	if(myAnimations!=null){
    		
    		myAnimations.Render(x,y,false);
    	}
    	SpriteImageData.color=getColor();
    	SpriteImageData.Draw("tankBody",x,y);
    	SpriteImageData.ResetProperties();
    	SpriteImageData.scaleX=0.8f;
    	SpriteImageData.scaleY=0.8f;
    	if(getWorld().activeTank==this){
    		//if(!MultiplayerController.IsMyTurn()){
    			SpriteImageData.color=new Color(1,0.25f,0.25f,1);
    		//}
    		SpriteImageData.Draw("arrowExp", x,(float)(y-32-8*Math.cos(step/10f)));
    	}
    	SpriteImageData.ResetProperties();
    	FontController.ResetProperties();
    	FontController.fontSize=12;
    	FontController.DrawString("hp: "+health,x-16,y+16);
    	if(getWorld().activeTank==this){
        	FontController.DrawString("Fuel: "+fuel,x-16,y+32);
    	}
    	

    	
	}
	
	public Color getColor(){
		if(team!=null){
			return team.getColor();
		}
		return Color.WHITE;
	}
	private boolean turnEnded=false;
	public boolean hasTurnEnded(){
		if(destroyed){
			turnEnded=true;
		}
		return turnEnded;
	}
	
	public void endTurn(){
		turnEnded=true;

	}
	
	public void startTurn(){
		turnEnded=false;
		hasFired=false;
		fuel=Math.min(fuel+fuelIncrement, maxFuel);
	}
	
	public void resetVariables(){
		health=maxHealth;
		hasFired=false;
		vspeed=0;
		hspeed=0;
		x=spawnX;
		y=spawnY;
	}
	
	
	@Override
	public boolean injure(LegacyGameObject src,int damageAmount){
		if(super.injure(src, damageAmount)){

			return true;
		}
    	return false;
	}
	
	public void onDeath(){
		getWorld().addObject(new Explosion(this,x,y,4));
		com.mobilecomputing.game.GameObjects.Tanks.TankTeam homeTeam=null;
		//getWorld().GetTeamByAlignment(recentDamageAlignment);
		if(homeTeam!=null){
			if(recentDamageAlignment!=alignment){
				homeTeam.incScore(50);
			}
			else{
				homeTeam.incScore(-50);
			}
		}
	}
	
	public HashMap<String,DamageText> statusMessages=new HashMap<String,DamageText>();
	public boolean AddStatusMessage(String statusMessage){
		return AddStatusMessage(new DamageText(statusMessage,x,y));
	}
	
	public boolean AddStatusMessage(DamageText text){
		String key=text.displayString.toLowerCase();
		if(statusMessages.containsKey(key)){
			DamageText otherText=statusMessages.get(key);
			if(otherText!=null && otherText!=text && !otherText.destroyed){
				return false;
			}
		}
		getWorld().addObject(text);
		statusMessages.put(key,text);
		return true;
	}
	
	public boolean HasChanged(){
		return super.HasChanged() || prevDirectionAiming!=directionAiming;
	}
	
	@Override
	public void onInjury(LegacyGameObject src,int damageAmount){
		super.onInjury(src,damageAmount);
		if(src!=null){
			com.mobilecomputing.game.GameObjects.Tanks.TankTeam homeTeam=null;
					//getWorld().GetTeamByAlignment(src.alignment);
			if(homeTeam!=null) {
				if (homeTeam != null && src.alignment != alignment) {
					homeTeam.incScore(damageAmount);
				} else {
					homeTeam.incScore(-damageAmount);
				}
			}
		}
	}
}
