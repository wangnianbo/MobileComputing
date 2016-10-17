package com.mobilecomputing.game.WormSkins;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormSegment;
import com.mobilecomputing.game.menus.LaunchMenu;

public abstract class WormSkin {

	public abstract void subRenderHead(WormHead head);
	public abstract void subRenderTailSegment(WormSegment segment);
	private static ArrayList<WormSkin> _allSkins;
	private static int skinOffset=0;
	public static WormSkin chosenSkin(){
		return WormSkin.allSkins().get(skinOffset);
	}
	
	public static void IncrementSkinOffset(){
		skinOffset--;
		if(skinOffset<0){
			skinOffset=WormSkin.allSkins().size()-1;
		}
	}
	
	public static void DecrementSkinOffset(){
		skinOffset++;
		if(skinOffset>=WormSkin.allSkins().size()){
			skinOffset=0;
		}
	}
	public static WormSkin_Chameleon chameleonSkin1=new WormSkin_Chameleon();
	public static WormSkin_Chameleon2 chameleonSkin2=new WormSkin_Chameleon2();
	public static ArrayList<WormSkin> allSkins(){
		if(_allSkins==null){
			_allSkins=new ArrayList<WormSkin>();
			_allSkins.add(chameleonSkin2);
			_allSkins.add(chameleonSkin1);

			_allSkins.add(new WormSkin_Pokey(true));
			_allSkins.add(new WormSkin_Pokey(false));

			_allSkins.add(new WormSkin_Christmas());

			_allSkins.add(new WormSkin_SimpleColor(new Color(1,0,0,1)));
			_allSkins.add(new WormSkin_SimpleColor(new Color(0,1,0,1)));
			_allSkins.add(new WormSkin_SimpleColor(new Color(0,0,1,1)));

		}
		return _allSkins;
	}
	
	private static boolean extraSkinsUnlocked=false;
	public static void unlockSkins(){
		LaunchMenu.setLastMessage("New Skins Unlocked :D!!!");
		
		if(!extraSkinsUnlocked){
			ArrayList<WormSkin> skins=allSkins();
			skins.add(new WormSkin_BlackAndGold());
			skins.add(new WormSkin_SimpleColor(new Color(UGameLogic.effectsRandom.nextFloat()*0.9f+0.1f,UGameLogic.effectsRandom.nextFloat()*0.9f+0.1f,UGameLogic.effectsRandom.nextFloat()*0.9f+0.1f,1)));
			skins.add(new WormSkin_SimpleColor(new Color(0.1f,0.1f,0.1f,1)));
			extraSkinsUnlocked=true;
		}		
	}
}


