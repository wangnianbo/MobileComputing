package com.mobilecomputing.game.GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.FontController;

public class DamageText extends LegacyGameObject{


	private int maxHorizontalDev=30;
	private int horizontalDev=maxHorizontalDev/2;
	private int initLife=100;
	private int life=initLife;
	public String displayString="0";
	
	public DamageText(String text,float x1,float y1){
		super(x1,y1);
		vspeed=-3;
		hspeed=3;
		displayString=text;
	}
	

	
	//Move the object
	@Override
	public void update(){
		super.update();
		horizontalDev+=hspeed;
		if(horizontalDev>maxHorizontalDev){
			hspeed=-Math.abs(hspeed);
		}
		else if(horizontalDev<0)
		{
			hspeed=Math.abs(hspeed);
		}
		
		
		//myView.myPanel.setWidgetPosition(myLabel, (int)getX()+horizontalDev, (int)getY()); 
		life=life-1;
		if(life<0){
			instanceDestroy();
		}
	}

	@Override
	public void render(){
		super.render();
		FontController.ResetProperties();
        FontController.color=Color.RED;
        FontController.fontSize=12;
        FontController.DrawString(displayString,x,y);
    	
	}
	
	@Override
	public void onDestroy() {

	}
}
