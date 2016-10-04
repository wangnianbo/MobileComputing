package com.mobilecomputing.game.GameObjects;

import com.mobilecomputing.game.Terrain.TerrainChunk;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.slitherio;

/**
 * Created by Venom on 18/09/2016.
 */
public class WormHead_Enemy extends WormHead{
    double targetDir=0;
    public WormHead_Enemy(float x, float y, WormTemplate_Enemy worm){
        super(x,y,worm);

    }

    public Pellet targetPellet;
    
    @Override
    public void update(){

    	if(targetPellet!=null && targetPellet.destroyed){
    		targetPellet=null;
    	}
    	if(targetPellet==null && step%5==0){
    		
    		
        	double dist=1000;



			double initDist=dist;
			for(int tx=(int)(x-initDist);tx<(int)(x+initDist+TerrainChunk.chunkWidth);tx+=TerrainChunk.chunkWidth){
				for(int ty=(int)(y-initDist);ty<(int)(y+initDist+TerrainChunk.chunkWidth);ty+=TerrainChunk.chunkWidth){
					TerrainChunk chunk=getWorld().GetTerrainChunk(tx/TerrainChunk.chunkWidth,ty/TerrainChunk.chunkWidth);
					if(chunk!=null)
					for(LegacyGameObject o:chunk.localObjects){

						if(o instanceof Pellet){
							double potentialDist=UGameLogic.GetDistanceBetween(this,o);
							if(potentialDist<dist){
								dist=potentialDist;
								targetPellet=((Pellet)o);
							}
						}
					}
				}
			}



    	}
    	
        super.update();
        targetDir++;
    }

    @Override
    public double getTargetDir(){
    	if(targetPellet!=null){
    		targetDir=UGameLogic.dirToPoint(x,y,targetPellet.x,targetPellet.y);
    	}    	
        return targetDir;
    }

}
