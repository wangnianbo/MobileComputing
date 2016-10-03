package com.mobilecomputing.game.WormSkins;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.Pellet;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormSegment;

public class WormSkin_Chameleon extends WormSkin {
	public Color bodyColor=Color.WHITE;
	public WormSkin_Chameleon(){
		
	}

	public void onConsumePellet(Pellet pellet){
		Color c1=bodyColor;
		Color c2=pellet.color;
		float frac=Math.min(pellet.value,10)/10f;
		float frac2=1-frac;
		bodyColor=new Color((c1.r*frac2+c2.r*frac),(c1.g*frac2+c2.g*frac),(c1.b*frac+c2.b*frac2),1);
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
			SpriteImageData.color=bodyColor;
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
