package com.mobilecomputing.game.WormSkins;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormSegment;


	public class WormSkin_BlackAndGold extends WormSkin{
		

		public WormSkin_BlackAndGold(){
			
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
				SpriteImageData.color=Color.GOLDENROD;
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
				
				if(segment.offsetWithinSnake()%2==0){
					SpriteImageData.color=Color.BLACK;
				}
				else{
					SpriteImageData.color=Color.GOLDENROD;
				}
	        	SpriteImageData.Draw("wormSegment",segment.x,segment.y);
			}
		}
		
	}

