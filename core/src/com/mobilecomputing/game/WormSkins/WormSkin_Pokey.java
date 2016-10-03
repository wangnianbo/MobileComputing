package com.mobilecomputing.game.WormSkins;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormSegment;


public class WormSkin_Pokey extends WormSkin{
	

	public boolean greenVariant=false;
	public WormSkin_Pokey(boolean isGreenVariant){
		this.greenVariant=isGreenVariant;
	}
	

	
	/*public Color getHeadColor()
	{
		return headColor;
	}
	
	public Color tailSegmentColor(int offset){

		return bodyColor;
	}
	*/
	public void subRenderHead(WormHead head){
		if(head!=null){
    		SpriteImageData.color=Color.WHITE;
			float x=head.x;
			float y=head.y;
			SpriteImageData.scaleX=SpriteImageData.scaleX*0.67f;
			SpriteImageData.scaleY=SpriteImageData.scaleY*0.67f;
			if(greenVariant){
				SpriteImageData.Draw("pokeyFaceGreen", x, y);
			}
			else{
				SpriteImageData.Draw("pokeyFace",x,y);
			}

		}
	}

	public void subRenderTailSegment(WormSegment segment){
		
		if(segment!=null){
			if(segment.offsetWithinSnake()%2==1){
				

				SpriteImageData.color=Color.WHITE;
				SpriteImageData.scaleX=SpriteImageData.scaleX*0.67f;
				SpriteImageData.scaleY=SpriteImageData.scaleY*0.67f;
				if(greenVariant){
	        		SpriteImageData.Draw("pokeySegmentGreen",segment.x,segment.y);
				}
				else{
        		SpriteImageData.Draw("pokeySegment",segment.x,segment.y);
				}
			}
		}
			
	}
	
}