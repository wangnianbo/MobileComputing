package com.mobilecomputing.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.LegacyGameObject;
import com.mobilecomputing.game.GameObjects.WormTemplate_Enemy;
import com.mobilecomputing.game.FontController;
import com.mobilecomputing.game.SoundController;
import com.mobilecomputing.game.slitherio;

public class DefaultGameHud extends Menu {
	public DefaultGameHud(float x, float y){
		super(x,y);

			SoundController.PlaySound("explosion");
	}



	@Override 
	public void receiveMessage(MenuElement sender,String msg){
		super.receiveMessage(sender, msg);
		msg=msg.toLowerCase();

	}
	@Override
	public void update(){
		super.update();

	}

	@Override
	public void render(){
		super.render();
		SpriteImageData.ResetProperties();
		FontController.ResetProperties();
		

		if(Controller.world!=null && Controller.world.activeCharacter!=null){
			int rank=1;
			int wormCount=1;
			int score=Controller.world.activeCharacter.getScore();
			for(LegacyGameObject o: Controller.world.getActiveLocalObjects()){
				if(o instanceof WormTemplate_Enemy){
					int rivalScore=((WormTemplate_Enemy)o).getScore();
					wormCount++;
					if(score<rivalScore){
						rank++;
					}
				}
			}
			
			FontController.centeredX=true;
			FontController.DrawString("SCORE "+ score+"   RANK "+rank+"/"+wormCount+"    UPS: "+(int)slitherio.lastUPS,Controller.projectionWidth/2-10, 10);
		}
		FontController.ResetProperties();
	}


	@Override
	public void onGlobalTap(float x,float y){
		super.onGlobalTap(x,y);

	}
  
}
