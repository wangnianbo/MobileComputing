package com.mobilecomputing.game.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Drawables.SpriteImageData;

/**
 * Created by Venom on 29/08/2016.
 */
public class Pellet extends LegacyGameObject{
    int value=100;
    public Pellet(float x, float y){
        super(x,y);
        //For detecting collisions
        shapeCollider=new Rectangle(-11,-11,22,22);
    }

    //What to do on each step
    @Override
    public void update(){
        super.update();
    }

    //What to draw to represent the object
    @Override
    public void render(){
        super.render();
        //Reset scaling, rotation and color values to defaults.
        SpriteImageData.ResetProperties();
        //Draw an image

        SpriteImageData.Draw("explosionFlare1",x,y);
    }

    //On touching another object...
    @Override
    public void OnCollision(LegacyGameObject o){
        if(o instanceof WormHead){
            instanceDestroy();
        }
        if(o instanceof Pellet && o.step>step){
            instanceDestroy();
        }

        super.OnCollision(o);
    }

    //Upon dieing...
    @Override
    public void onDeath(){
        super.onDeath();
    }

    //Upon being removed from the world...
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
