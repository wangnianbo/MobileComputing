package com.mobilecomputing.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mobilecomputing.game.Controller;

public class FontController {
	public static BitmapFont defaultFont;
	public static void InitializeFonts(){
		defaultFont=new BitmapFont();
		selectedFont=defaultFont;
	}

	public static Color color=Color.WHITE;
	public static BitmapFont selectedFont;

	public static void TransformFont(BitmapFont font, int newFontSize){
		float scaleX=newFontSize/15f;
		float scaleY=newFontSize/15f;
		if(font.getData().scaleX!=scaleX && font.getData().scaleY!=scaleY)
		{
			font.getData().setScale(scaleX,scaleY);
		}

	}

	public static void DrawString(String string,float x,float y){
		TransformFont(selectedFont,fontSize);
		selectedFont.setColor(color);

		selectedFont.draw(Controller.batch, string,x,Controller.screenHeight-y);
	}
	
	//public static float scaleX=1;
	//public static float scaleY=1;
	public static void ResetProperties(){
		//scaleX=1;
		//scaleY=1;
		selectedFont=defaultFont;
		color=Color.WHITE;
		fontSize=15;
	}
	
	public static int fontSize=14;
	
	
	
	
	/*public static void setScale(float newScaleX,float newScaleY){
		scaleX=newScaleX;
		scaleY=newScaleY;
	}*/
	
	
	
}
