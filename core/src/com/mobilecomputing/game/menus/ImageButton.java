package com.mobilecomputing.game.menus;

import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.UGameLogic;

public class ImageButton extends Button{
	public SpriteImageData image;
	public ImageButton(float x,float y,SpriteImageData image,String identifier){
		super(x,y);
		this.image=image;
		this.shapeCollider=new Rectangle(-image.getWidth()/2,-image.getHeight()/2,image.getWidth(),image.getHeight());
		setIdentifier(identifier);
	}
	
	public void render(){
		
		SpriteImageData.ResetProperties();
		/*if(parentMenu!=null && parentMenu.getSelectedElement()!=this){
			SpriteImageData.color=new Color(1,1,1,0.25f);
		}
		*/
		image.Draw(x,y);
		//SpriteImageData.DrawShape(shapeCollider,x,y);
	}


}
