package com.mobilecomputing.game.WormSkins;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormSegment;


public class WormSkin_SimpleColor extends WormSkin{
	
	Color headColor=Color.WHITE;
	Color bodyColor=Color.WHITE;
	public WormSkin_SimpleColor(){
		
	}

	public WormSkin_SimpleColor(Color color){
		headColor=color;
		bodyColor=color;
	}
	
	public WormSkin_SimpleColor(Color bodyC,Color headC){
		headColor=headC;
		bodyColor=bodyC;
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
			SpriteImageData.color=headColor;
			float x=head.x;
			float y=head.y;
			SpriteImageData.Draw("wormSegment",head.x,y);
    		SpriteImageData.color=Color.WHITE;
    		if(head.destroyed){
        		SpriteImageData.Draw("wormFaceDead",x,y);
    		}
    		else{
    			SpriteImageData.Draw("wormFace",x,y);
    		}
		}
	}

	public void subRenderTailSegment(WormSegment segment){
		if(segment!=null){
			SpriteImageData.color=bodyColor;
        	SpriteImageData.Draw("wormSegment",segment.x,segment.y);
		}
	}
	
}