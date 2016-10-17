package com.mobilecomputing.game.menus;

import java.util.ArrayList;
import java.util.HashMap;

import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Controller.GameMode;
import com.mobilecomputing.game.FontController;
import com.mobilecomputing.game.SoundController;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.Drawables.SpriteImageData;

public class AdvertisementMenu extends Menu {
	//button to close;
	ImageButton closeButton;
	//All images to display start with "staticAds";
	ArrayList<SpriteImageData> addImages=new ArrayList<SpriteImageData>();

	public AdvertisementMenu(float x,float y){
		super(x,y);
		SpriteImageData closeImage=SpriteImageData.GetByName("ui/button_close");
		closeButton=new ImageButton(0,0,closeImage,"close");
		closeButton.setScale(32/((float)closeImage.getWidth()),32/(float)closeImage.getWidth());
		closeButton.setCentered(false);
		addElement(closeButton);

		HashMap<String,SpriteImageData> allImagesMap=SpriteImageData.getAllImages();
		//All images to display start are in the directory "staticAds";
		for(String key:allImagesMap.keySet()){
			if(key.startsWith("staticads")){
				SpriteImageData imageToAdd=SpriteImageData.GetByName(key);
				if(imageToAdd!=null)
				addImages.add(imageToAdd);
			}
		}
	}



	@Override 
	public void receiveMessage(MenuElement sender,String msg){
		super.receiveMessage(sender, msg);
		msg=msg.toLowerCase();
		//Swap to splash screen on close;
		switch(msg){
			case "close":
				UGameLogic.LogMsg("Local");
				Controller.swapGameMode(GameMode.SPLASH);
			break;

		}

	}
	
	public void update(){
		super.update();
		/*
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			setSelectedIndex(getSelectedIndex()-1);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			setSelectedIndex(getSelectedIndex()+1);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			MenuElement element=getSelectedElement();
			if(element!=null){
				element.OnPressed();
			}
		}
		*/
	}
	@Override
	public void onGlobalTap(float x,float y){
		super.onGlobalTap(x,y);
		//addElement(new ClickTest(x,y));
	}
	
	public static int advertisementOffset=0;
	public final int maxDelayBeforeSwitch=UGameLogic.lengthOfSecond*3;
	public int delayBeforeSwitch=-1;
	public static boolean advertismentsOn=true;

	
	@Override
	public void render(){

		FontController.fontSize=20;
		FontController.DrawString("A Word From Our Sponsors...",Controller.projectionHeight/2,3);

		SpriteImageData.ResetProperties();
		//After a set delay, swap advertisement;
		delayBeforeSwitch--;
		if(delayBeforeSwitch<0){
			delayBeforeSwitch=maxDelayBeforeSwitch;
			advertisementOffset++;
			int size=addImages.size();
			if(advertisementOffset>=size){
				advertisementOffset=0;
			}

		}
		//Scale the advertisement to fit the window below the close button;
		SpriteImageData advertisement=addImages.get(advertisementOffset);
		float sx=Controller.projectionWidth/(float)advertisement.getWidth();
		float sy=Controller.projectionHeight/(float)advertisement.getHeight();
		float s=Math.min(sx, sy);
		SpriteImageData.scaleX=s;
		SpriteImageData.scaleY=s;
		advertisement.Draw(Controller.projectionWidth/2-(advertisement.getWidth()/2)*s,32,false);
		super.render();


	}
  
}
