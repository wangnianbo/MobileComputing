package com.mobilecomputing.game.menus;

import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.FontController;
import com.mobilecomputing.game.SoundController;

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
		if(Controller.world!=null && Controller.world.activeCharacter!=null)
			FontController.DrawString("SCORE "+Controller.world.activeCharacter.getScore() ,Controller.projectionWidth/2-10, 10);

		FontController.ResetProperties();
	}


	@Override
	public void onGlobalTap(float x,float y){
		super.onGlobalTap(x,y);

	}
  
}
