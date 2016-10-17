package com.mobilecomputing.game.menus;

import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Controller.GameMode;
import com.mobilecomputing.game.Drawables.SpriteImageData;

public class SettingsMenu extends Menu {


	ImageButton adsButton;
	public SettingsMenu(float x, float y,Menu prevMenu) {
		super(x, y);
		this.prevGameMenu=prevMenu;
		addElement(new ImageButton(Controller.projectionWidth/2,Controller.projectionHeight/3,SpriteImageData.GetByName("ui/button_change_skin"),"skins"));
		adsButton=new ImageButton(Controller.projectionWidth/2,Controller.projectionHeight*2/3,getAdsButtonImage(),"adsToggle");
		addElement(adsButton);
		addBackButton();
		
	
		// TODO Auto-generated constructor stub
	}
	
	public SpriteImageData getAdsButtonImage(){
		if(AdvertisementMenu.advertismentsOn){
			return SpriteImageData.GetByName("ui/button_ads_state_on");
		}
		else{
			return SpriteImageData.GetByName("ui/button_ads_state_off");
		}
	}
	
	public void update(){
		super.update();
	}
	
	public void render(){
		super.render();
	}
	
	public void receiveMessage(MenuElement sender,String msg){
		super.receiveMessage(sender, msg);
		msg=msg.toLowerCase();
		switch(msg){
		case "adstoggle":
			AdvertisementMenu.advertismentsOn=!AdvertisementMenu.advertismentsOn;
			adsButton.image=getAdsButtonImage();
		break;
		case "skins":
			Controller.activeMenu=(new SkinsMenu(0,0,this));
		break;


		}
	}
		
	

}
