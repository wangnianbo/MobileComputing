package com.mobilecomputing.game.WormSkins;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.Pellet;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormSegment;

public class WormSkin_Chameleon2 extends WormSkin {

	ArrayList<Color> colors=new ArrayList<Color>();
	public void ClearColors(){
		colors.clear();
	}
	public WormSkin_Chameleon2(){
		
	}

	public void onConsumePellet(Pellet pellet){
		colors.add(0,pellet.color);

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
    		if(colors.size()>0){
    			SpriteImageData.color=colors.get(0);
    		}
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
    		SpriteImageData.color=Color.WHITE;
    		int offset=segment.offsetWithinSnake();
    		if(colors.size()>offset+1){
    			SpriteImageData.color=colors.get(offset+1);
    		}


        	SpriteImageData.Draw("wormSegment",segment.x,segment.y);
		}
	}
	

}
