package com.mobilecomputing.game.menus;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.UGameLogic;

public class ImageButton extends Button{
	public SpriteImageData image;
	private float scaleX=1f;
	private float scaleY=1f;
	public float rotation=0;
	public boolean centered=true;
	public ImageButton(float x,float y,SpriteImageData image,String identifier){
		super(x,y);
		this.image=image;
		setScale(1,1);
		setIdentifier(identifier);
	}
	
	public void setScale(float sx,float sy){
		scaleX=sx;
		scaleY=sy;
		updateCollider();
	}
	
	public void setCentered(boolean newCentered){
		this.centered=newCentered;
	}
	
	
	public Shape2D updateCollider(){
		float offsetX=0;
		float offsetY=0;
		if(centered){
			offsetX=-image.getWidth()/2*scaleX;
			offsetY=-image.getHeight()/2*scaleY;
		}
		this.shapeCollider=new Rectangle(offsetX,offsetY,image.getWidth()*scaleX,image.getHeight()*scaleY);
		return this.shapeCollider;
	}
	
	public void render(){
		
		SpriteImageData.ResetProperties();
		SpriteImageData.rotation=rotation;
		SpriteImageData.scaleX=scaleX;
		SpriteImageData.scaleY=scaleY;
		/*if(parentMenu!=null && parentMenu.getSelectedElement()!=this){
			SpriteImageData.color=new Color(1,1,1,0.25f);
		}
		*/
		image.Draw(x,y,centered);
		//SpriteImageData.DrawShape(shapeCollider,x,y);
	}


}
