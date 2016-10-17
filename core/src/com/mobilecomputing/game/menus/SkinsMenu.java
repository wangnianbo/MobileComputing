package com.mobilecomputing.game.menus;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Controller.GameMode;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.World;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.Pellet;
import com.mobilecomputing.game.GameObjects.WormSegment;
import com.mobilecomputing.game.GameObjects.WormTemplate;
import com.mobilecomputing.game.GameObjects.WormTemplate_Enemy;
import com.mobilecomputing.game.GameObjects.WormTemplate_SkinDemo;
import com.mobilecomputing.game.WormSkins.*;

public class SkinsMenu extends Menu{
	public ImageButton cycleLeft;
	public ImageButton cycleRight;

	public ImageButton backButton;
	public WormTemplate worm;
	public WormSkin skin;
	private World world;

	public SkinsMenu(float x,float y,Menu prevMenu){
		super(x,y);

		prevGameMenu=prevMenu;
		addBackButton();
		world=new World(false,true);
		world.width=2560;
		world.height=2560;
		
		SpriteImageData arrow=SpriteImageData.GetByName("ui/arrowUp");
		cycleLeft=new ImageButton(arrow.getWidth()/2,Controller.projectionHeight/2,arrow,"left");

		cycleLeft.rotation=90;
		cycleRight=new ImageButton(Controller.projectionWidth-arrow.getWidth()/2,Controller.projectionHeight/2,arrow,"right");
		cycleRight.rotation=270;
		//backButton=new ImageButton(Controller.projectionWidth/2,Controller.projectionHeight-32,SpriteImageData.GetByName("ui/button_close"),"back");
		addElement(cycleLeft);
		addElement(cycleRight);
		//addElement(backButton);
		worm=new WormTemplate_SkinDemo(Controller.projectionWidth/2,50,40);
		world.addObject(worm);
		SpriteImageData confirmImage=SpriteImageData.GetByName("ui/button_confirm");
		addElement(new ImageButton(Controller.projectionWidth/2,Controller.projectionHeight-confirmImage.getHeight()/2,confirmImage,"confirm"));

	
		worm.skin =WormSkin.chosenSkin();
		
	};
	

	public void receiveMessage(MenuElement sender,String msg){
		super.receiveMessage(sender, msg);
		msg=msg.toLowerCase();
		switch(msg){
			case "left":
				WormSkin.DecrementSkinOffset();
			break;
			case "right":
				WormSkin.IncrementSkinOffset();
			break;

			case "confirm":

				onBackPressed();
			break;
		}
		worm.skin =WormSkin.chosenSkin();
	}
	
	public void update(){
		super.update();
		worm.update();

		worm.head.update();
		float xDiff=worm.head.x-worm.head.prevX;
		float yDiff=worm.head.y-worm.head.prevY;
		for(WormSegment seg:worm.getTailSegments()){
			seg.x-=xDiff;
			seg.y-=yDiff;
		}
		
		worm.head.x-=xDiff;
		worm.head.y-=yDiff;

		if(step%(UGameLogic.lengthOfSecond)==3){
		if(worm.skin.equals(WormSkin.chameleonSkin1)){
			WormSkin.chameleonSkin1.onConsumePellet(new Pellet(0,0));
		}
		if(worm.skin.equals(WormSkin.chameleonSkin2)){

				WormSkin.chameleonSkin2.onConsumePellet(new Pellet(0,0));
			
		}
		}
		super.update();
	}
	
	public void render(){
		worm.render();
		super.render();
	}
	
	
	
	
}
