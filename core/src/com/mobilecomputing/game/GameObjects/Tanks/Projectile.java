package com.mobilecomputing.game.GameObjects.Tanks;

import com.mobilecomputing.game.GameObjects.LegacyGameObject;
import com.mobilecomputing.game.UGameLogic;


public class Projectile extends LegacyGameObject
{
    public int maxLifeTimer = 150;
    public int lifeTimer = 150;
    public double maxPhaseDist = 0;
    public double phaseDist = 0;
    @Override
    public void update()
    {
        super.update();
        if (lifeTimer <= 0)
        {
            instanceDestroy();
            return;
        }
        lifeTimer = lifeTimer - 1;
        if (phaseDist <= maxPhaseDist)
        {
            
            phaseDist = phaseDist + UGameLogic.GetDistanceBetween(x, y, prevX, prevY);
        }
    }

    public Projectile(LegacyGameObject creator,float x, float y){
    	super(x, y);
    	affectedByAirDrag=false;
    	setCreator(creator);
        destroyOnDeactivate = true;

    }

    //Set the phase dist;
    @Override
    public void OnAddToWorld()
    {
    	/*
        if (simpleCollider != null)
        {
            maxPhaseDist = Math.Min(simpleCollider.hitRadius*2, UGameLogic.tileWidth/2);

        }
        */
        super.OnAddToWorld();
    }

    //by default projectiles should not be able to climb or slide down slopes;
    @Override
    public boolean CanSlideOnSolidSurfaces()
    {
    	return false;
    }


}
