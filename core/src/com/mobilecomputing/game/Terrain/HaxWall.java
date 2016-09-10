package com.mobilecomputing.game.Terrain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.LegacyGameObject;

public class HaxWall extends LegacyGameObject{
	public HaxWall(float x,float y){
		super(x,y);
		shapeCollider=new Rectangle(0,0,64,64);
		isImmovable=true;
	}
	
	public void render(){
		SpriteImageData.ResetProperties();
		SpriteImageData.Draw("wall",x,y,false);
		//DrawCollider();
		//SpriteImageData.DrawShape(shapeCollider, x, y, new Color(255,0,0,128));
	}
	
}
