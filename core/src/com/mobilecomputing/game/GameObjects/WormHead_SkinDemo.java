package com.mobilecomputing.game.GameObjects;

import com.mobilecomputing.game.Terrain.TerrainChunk;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.slitherio;

/**
 * Created by Venom on 18/09/2016.
 */
public class WormHead_SkinDemo extends WormHead{
    double targetDir=0;

    public WormHead_SkinDemo(float x, float y, WormTemplate_SkinDemo worm){
        super(x,y,worm);

    }


    @Override
    public void update(){

    	rotationSpeed=3;
        super.update();

    }

    @Override
    public double getTargetDir(){
        if(step/(UGameLogic.lengthOfSecond*3/5)%2==1){
        	targetDir=35;
        }
        else{
        	targetDir=325;
        }
        return targetDir;
    }

}
