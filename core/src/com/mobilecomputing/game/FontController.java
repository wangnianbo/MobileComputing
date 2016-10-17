package com.mobilecomputing.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

		float drawX=x;
		float drawY=y;
		if(centeredX || centeredY){
			GlyphLayout glyphLayout = new GlyphLayout();

			glyphLayout.setText(selectedFont,string);
			if(centeredX){
				float w=glyphLayout.width;
				drawX-=w/2;
			}
			if(centeredY){
				float h=glyphLayout.height;
				drawY-=h/2;
			}
		}

		selectedFont.draw(Controller.batch, string,drawX,Controller.screenHeight-drawY);
	}
	
	//public static float scaleX=1;
	//public static float scaleY=1;
	public static void ResetProperties(){
		//scaleX=1;
		//scaleY=1;
		selectedFont=defaultFont;
		color=Color.WHITE;
		fontSize=15;
		centeredX=false;
		centeredY=false;
	}
	
	public static int fontSize=14;
	public static boolean centeredX;
	public static boolean centeredY;
	
	
	/*public static void setScale(float newScaleX,float newScaleY){
		scaleX=newScaleX;
		scaleY=newScaleY;
	}*/
	
	
	
}
