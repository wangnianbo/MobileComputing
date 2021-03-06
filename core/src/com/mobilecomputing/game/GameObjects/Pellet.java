package com.mobilecomputing.game.GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.UGameLogic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Venom on 29/08/2016.
 */
public class Pellet extends LegacyGameObject{
    public int value=1;

    public Color color;
    public int pelletId;
    private static int lastAssignedPelletId=-1;
    public Pellet(float x, float y){
        super(x,y);
        //For detecting collisions
        Random random= Controller.spawnRandom;
        value=1+random.nextInt(4);
        construct();
    }

    public Pellet(float x, float y,int value) {
        super(x, y);
        this.value=value;
        construct();
    }

    public void construct(){
        //Determine color;
        //Draw an image
        Random random= Controller.spawnRandom;
        pelletId=lastAssignedPelletId+1;

        lastAssignedPelletId++;


        float s=(float)Math.sqrt(value);
        value=value*2;
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
    @Override
    public void OnAddToWorld(){
        super.update();
    }

    //What to do on each step
    @Override
    public void update(){

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

    @Override
    public boolean ShouldRegisterTouch(LegacyGameObject o,boolean caller){
        if(o instanceof Pellet){
            if(o.step>2 && step>2){
                return true;
            }
        }
        return super.ShouldRegisterTouch(o,caller);
    }

    //On touching another object...
    @Override
    public void OnCollision(LegacyGameObject o){
        if(o instanceof WormHead){
            instanceDestroy();
        }
        if(o instanceof Pellet && ((Pellet) o).pelletId<pelletId){

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
