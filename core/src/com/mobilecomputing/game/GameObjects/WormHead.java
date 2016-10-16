package com.mobilecomputing.game.GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.WormSkins.WormSkin_Chameleon;
import com.mobilecomputing.game.WormSkins.WormSkin_Chameleon2;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.menus.GameOverMenu;
import com.mobilecomputing.game.slitherio;

import static com.mobilecomputing.game.slitherio.*;

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
    float moveSpeed=5.5f;
    float baseMoveSpeed=5.5f;
    float baseBoostSpeed=8.5f;
    //public int score;

    double moveDir=0;

    boolean boosting=false;
    public int timeBoosting=0;

    @Override
    public void update(){
        super.update();

        //lastDragX !=slitherio.lastWorldDragX || lastDragY !=slitherio.lastWorldDragY
        double moveDirection = getTargetDir();
        if(slitherio.bluetoothConnection!=null && slitherio.bluetoothConnection.isNetGame()){

        }
        moveDir= UGameLogic.TryRotateTowardsAlt(moveDir,getTargetDir(),7,0,360);


        moveSpeed=baseMoveSpeed;
        if(worm.wormLength<=20){
            boosting=false;
        }
        if(boosting){
            moveSpeed=baseBoostSpeed;
            timeBoosting++;
            if(timeBoosting%(UGameLogic.lengthOfSecond/5)==UGameLogic.lengthOfSecond/5-1){
                worm.wormLength--;

            }
        }

        double dirR=UGameLogic.TrueBearingsToRadians(moveDir);
        if(!CheckMove((float)(x+moveSpeed*Math.cos(dirR)),(float)(y-moveSpeed*Math.sin(dirR)))){
          Die();
        }

        //CheckMove((float)(x),(float)(y-moveSpeed*Math.sin(dirR)));
        //CheckMove((float)(x+moveSpeed*Math.cos(dirR)),(float)(y));
    }

    public double getTargetDir(){
        return moveDir;
    }


    //What to draw to represent the object
    @Override
    public void render(){
        super.render();
        //Reset scaling, rotation and color values to defaults.
        SpriteImageData.ResetProperties();
        	subRender();
    }
    
    public void subRender(){
        // UGameLogic.LogMsg("Rotation "+moveDir);
        SpriteImageData.rotation=(float)-moveDir;
        //Draw an image
        if(boosting){
           // SpriteImageData.color= Color.RED;
        }
    	if(worm!=null && worm.skin!=null){
    		worm.skin.subRenderHead(this);
    	}
    	else{
    		SpriteImageData.Draw("wormSegment",x,y);
        	SpriteImageData.color=Color.WHITE;
        	if(destroyed){
            	SpriteImageData.Draw("wormFaceDead",x,y);
        	}
        	else{
        		SpriteImageData.Draw("wormFace",x,y);
        	}
    	}
    }
    
    /*
    public void subRenderDead(){
        SpriteImageData.rotation=(float)-moveDir;
    	if(worm!=null && worm.skin!=null){
    		SpriteImageData.color=worm.skin.getHeadColor();
    	}
        SpriteImageData.Draw("wormSegment",x,y);
        SpriteImageData.color=Color.WHITE;
        SpriteImageData.Draw("wormFaceDead",x,y);
    }
    */

    //On touching another object...
   
    @Override
    public void OnCollision(LegacyGameObject o){
        super.OnCollision(o);
        if(o instanceof Pellet){
            //score+=((Pellet)o).value;
            worm.wormLength+=((Pellet)o).value;
            if( worm.skin instanceof WormSkin_Chameleon2){
            	((WormSkin_Chameleon2)(worm.skin)).onConsumePellet((Pellet)o);
            }
            else if( worm.skin instanceof WormSkin_Chameleon){
            	((WormSkin_Chameleon)(worm.skin)).onConsumePellet((Pellet)o);
            }
            /*
            for(int i=0;i<((Pellet)o).value;i++){
                worm.growByOneSegment();
            }
            */
        }
        if(o instanceof WormTemplate){
            if(worm!=o){
                Die();
            }
        }
    }

    //Upon dieing...
    @Override
    public void onDeath(){
        super.onDeath();
        worm.Die();
    }

    //Upon being removed from the world...
    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onInjury(LegacyGameObject src,int damageAmount){
        super.onInjury(src,damageAmount);
    }
}
