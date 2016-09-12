package com.mobilecomputing.game.GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Drawables.SpriteImageData;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Venom on 29/08/2016.
 */
public class Pellet extends LegacyGameObject{
    int value=1;

    Color color;
    public Pellet(float x, float y){
        super(x,y);
        //For detecting collisions

        //Determine color;
        //Draw an image

        Random random=new Random();
        value=1+random.nextInt(4);
        float s=(float)Math.sqrt(value);
        shapeCollider=new Rectangle(-11*s,-11*s,22*s,22*s);
        int redIndex=0;
        int greenIndex=1;
        int blueIndex=2;
        ArrayList<Integer> colors=new ArrayList<Integer>();
        colors.add(redIndex);
        colors.add(greenIndex);
        colors.add(blueIndex);

        int highlightedColorIndex=random.nextInt(3);
        colors.remove(highlightedColorIndex);
        int indexToExclude=random.nextInt(colors.size());

        int excludedColorIndex=colors.get(indexToExclude);
        colors.remove(indexToExclude);
        int remainingColor=colors.get(0);
        float[] colorIntensities=new float[]{0,0,0};
        colorIntensities[remainingColor]=random.nextFloat();
        colorIntensities[highlightedColorIndex]=random.nextFloat()*0.45f+0.55f;
        color=new Color(colorIntensities[0],colorIntensities[1],colorIntensities[2],1);
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
        SpriteImageData.scaleX=(float)Math.sqrt(value);
        SpriteImageData.scaleY=(float)Math.sqrt(value);
        SpriteImageData.color=color;
        SpriteImageData.Draw("explosionFlare",x,y);
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
