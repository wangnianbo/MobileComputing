package com.mobilecomputing.game.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.LegacyGameObject;
import com.mobilecomputing.game.GameObjects.WormTemplate;
import com.mobilecomputing.game.GameObjects.WormTemplate_Enemy;
import com.mobilecomputing.game.FontController;
import com.mobilecomputing.game.SoundController;
import com.mobilecomputing.game.UGameLogic;
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

		drawRadar(75,750);
		SpriteImageData.ResetProperties();
		FontController.ResetProperties();
		

		if(Controller.world!=null && Controller.world.activeCharacter!=null){
			int rank=1;
			int wormCount=1;
			int score=Controller.world.activeCharacter.getScore();
			ArrayList<WormTemplate> ranking=new ArrayList<WormTemplate>();
			for(LegacyGameObject o: Controller.world.getActiveLocalObjects()){
				if(o instanceof WormTemplate){
					ranking.add((WormTemplate)o);
				}
				if(o instanceof WormTemplate_Enemy){
					int rivalScore=((WormTemplate_Enemy)o).getScore();
					wormCount++;
					if(score<rivalScore){
						rank++;
					}
				}
			}
			Collections.sort(ranking, new Comparator<WormTemplate>() {
			    public int compare(WormTemplate s1, WormTemplate s2) {
			        return ((Integer)s2.getScore()).compareTo((Integer)s1.getScore());
			    }
			});
			
			rank=1;
			int proposedRank=1;
			FontController.ResetProperties();
			float leaderboardsX=slitherio.LeftGuiScreenX()+9;
			FontController.DrawString("Leaderboards ",leaderboardsX, 20);
			for(WormTemplate template : ranking){
				String name=template.name;
				FontController.ResetProperties();
				if(template==Controller.world.activeCharacter){
					rank=proposedRank;
					FontController.color=new Color(0,1,1,1);
					name="You";
				}

				score=template.getScore();
				if(proposedRank<=10){

					
					FontController.centeredX=false;
					FontController.DrawString(proposedRank+" "+name+": "+score,leaderboardsX+3 , 20+FontController.fontSize*proposedRank);
				}
				proposedRank++;

			}
			FontController.ResetProperties();
			FontController.centeredX=true;
			//"    UPS: "+(int)slitherio.lastUPS
			FontController.DrawString("SCORE "+ score+"   RANK "+rank+"/"+wormCount,Controller.projectionWidth/2-10, 10);
		}

	}

	public void drawRadar(int radarSize,int radarRange){
		SpriteImageData.ResetProperties();
		SpriteImageData radarImage=SpriteImageData.GetByName("ui/radar");
		float s=Math.min(radarSize/((float)radarImage.getWidth()),radarSize/((float)radarImage.getHeight()));
		SpriteImageData.scaleX=s;
		SpriteImageData.scaleY=s;
		
		float radarX=slitherio.RightGuiScreenX()- s*radarImage.getWidth()/2;
		float radarY= s*radarImage.getHeight()/2;
		radarImage.Draw(radarX,radarY,true);
		float radarRadius=radarImage.getWidth()*0.485f*s;
		if(Controller.world!=null && Controller.world.activeCharacter!=null){
			WormTemplate player=Controller.world.activeCharacter;
			for(LegacyGameObject o: Controller.world.getActiveLocalObjects()){
				if(o instanceof WormTemplate_Enemy){
					float dist=(float)UGameLogic.GetDistanceBetween(o, player);
					float diffX=(o.centerX()-player.centerX())*radarRadius/radarRange;
					float diffY=(o.centerY()-player.centerY())*radarRadius/radarRange;
					if(dist<radarRange){
						SpriteImageData.color=new Color(1,0,0,1);
						SpriteImageData.scaleX=0.2f;
						SpriteImageData.scaleY=0.2f;
						SpriteImageData.Draw("explosionFlare",radarX+diffX,radarY+diffY);
					}
				}
				
			}
		}

	}

	@Override
	public void onGlobalTap(float x,float y){
		super.onGlobalTap(x,y);

	}
  
}
