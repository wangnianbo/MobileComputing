package com.mobilecomputing.game.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.menus.GameOverMenu;
import com.mobilecomputing.game.slitherio;

/**
 * Created by Venom on 29/08/2016.
 */
public class WormHead extends LegacyGameObject{

    WormTemplate worm;
    public WormTemplate getWorm(){
        return worm;
    }

    public WormHead(float x, float y,WormTemplate worm){
        super(x,y);
        //For detecting collisions
        this.worm=worm;
        shapeCollider=new Rectangle(-14,-14,28,28);
    }
    float moveSpeed=5;
    //public int score;
    public float lastDragX =UGameLogic.UNSET_INT;
    public float lastDragY =UGameLogic.UNSET_INT;
    double moveDir=0;
    //What to do on each step
    @Override
    public void update(){
        super.update();
        //lastDragX !=slitherio.lastWorldDragX || lastDragY !=slitherio.lastWorldDragY
        if(slitherio.dragging){
            double targetDir= UGameLogic.dirToPoint(x,y, slitherio.lastWorldDragX,slitherio.lastWorldDragY);
            moveDir=UGameLogic.TryRotateTowardsAlt(moveDir,targetDir,7,-720,720);

            lastDragX =slitherio.lastWorldDragX;
            lastDragY =slitherio.lastWorldDragY;
        }

        double dirR=UGameLogic.TrueBearingsToRadians(moveDir);
        if(!CheckMove((float)(x+moveSpeed*Math.cos(dirR)),(float)(y-moveSpeed*Math.sin(dirR)))){
          Die();
        }

        //CheckMove((float)(x),(float)(y-moveSpeed*Math.sin(dirR)));
        //CheckMove((float)(x+moveSpeed*Math.cos(dirR)),(float)(y));
    }

    //What to draw to represent the object
    @Override
    public void render(){
        super.render();
        //Reset scaling, rotation and color values to defaults.
        SpriteImageData.ResetProperties();
       // UGameLogic.LogMsg("Rotation "+moveDir);
        SpriteImageData.rotation=(float)-moveDir;
        //Draw an image
        SpriteImageData.Draw("wormHeadSegment",x,y);
    }

    //On touching another object...
    @Override
    public void OnCollision(LegacyGameObject o){
        super.OnCollision(o);
        if(o instanceof Pellet){
            //score+=((Pellet)o).value;
            worm.grow();
        }
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
        Controller.activeMenu=new GameOverMenu(0,0);
    }

    @Override
    public void onInjury(LegacyGameObject src,int damageAmount){
        super.onInjury(src,damageAmount);
    }
}
