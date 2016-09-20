package com.mobilecomputing.game.GameObjects;

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

    @Override
    public void update(){

        super.update();
        targetDir++;
    }

    @Override
    public double getTargetDir(){
        return targetDir;
    }

}
