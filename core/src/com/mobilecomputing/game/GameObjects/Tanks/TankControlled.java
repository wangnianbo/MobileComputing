package com.mobilecomputing.game.GameObjects.Tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mobilecomputing.game.UGameLogic;


public class TankControlled extends Tank {

	
	

	
	public TankControlled(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	
	public TankControlled(float i, float j, TankTeam team) {
		// TODO Auto-generated constructor stub
		super(i,j);
		this.team=team;
		team.addMember(this);
		alignment=team.getAlignment();
		
	}

	public void update(){
		super.update();

		float delta=2;
		float deltaF=0;
		boolean verticalChanged=false;
		autoProgressAnimation=false;
		if(getWorld().activeTank==this && !getWorld().hasGameEnded()){
			if(fuel>0){
				if(Gdx.input.isKeyPressed(Input.Keys.UP)){
					deltaF=delta;
					fuel=Math.max(fuel-3,0);
				}
				else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
					deltaF=-delta/2;
					fuel=Math.max(fuel-3,0);
				}
				double turnSpeed=2f;
				if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
					fuel=Math.max(fuel-1,0);
					directionAiming-=turnSpeed;
				}
				else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
					fuel=Math.max(fuel-1,0);
				
					directionAiming+=turnSpeed;
				}
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				AddStatusMessage("Out of fuel!");
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				
				if(delayBeforeFire<0){
					Fire();
				}
			}
			if(Math.abs(deltaF)>0){
				autoProgressAnimation=true;
				directionAiming=UGameLogic.PMod(directionAiming,360);
				double directionAimingR=UGameLogic.TrueBearingsToRadians(directionAiming);
				float deltaX=(float)(deltaF*Math.cos(directionAimingR));
				float deltaY=(float)(deltaF*-Math.sin(directionAimingR));
				CheckMove(x+deltaX,y);
				CheckMove(x,y+deltaY);
			}

		}
		
		/*
		
		if(getWorld().activeCharacter==this){
			if(Gdx.input.isKeyPressed(Input.Keys.UP)){
				directionAiming=0;
				verticalChanged=true;
				deltaY=-delta;
			}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			directionAiming=180;
			verticalChanged=true;
			deltaY=delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			deltaX=-delta;
			if(!verticalChanged){
				directionAiming=270;
			}
			else{
				directionAiming=directionAiming-45*(directionAiming==0? 1:-1);
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			deltaX=delta;
			if(!verticalChanged){
				directionAiming=90;
			}
			else{
				directionAiming=directionAiming+45*(directionAiming==0? 1:-1);

			}
		}
*/
		
	}
	

	/*
	 * 	public void update(){
		super.update();
		float deltaX=0;
		float deltaY=0;
		float delta=5;

		boolean verticalChanged=false;
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			directionAiming=0;
			verticalChanged=true;
			deltaY=-delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			directionAiming=180;
			verticalChanged=true;
			deltaY=delta;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			deltaX=-delta;
			if(!verticalChanged){
				directionAiming=270;
			}
			else{
				directionAiming=directionAiming-45*(directionAiming==0? 1:-1);
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			deltaX=delta;
			if(!verticalChanged){
				directionAiming=90;
			}
			else{
				directionAiming=directionAiming+45*(directionAiming==0? 1:-1);

			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			
			if(delayBeforeFire<0){
				Fire();
			}
		}

		CheckMove(x+deltaX,y);
		CheckMove(x,y+deltaY);
	}
	 */
	 
}
