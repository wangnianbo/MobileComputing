package com.mobilecomputing.game.menus;

import com.mobilecomputing.game.UGameLogic;

/**
 * Created by Venom on 5/09/2016.
 */
public class Button extends MenuElement {
    public Button(float x,float y){
        super(x,y);
    }

    @Override
    public void onGlobalTap(float x, float y){
        super.onGlobalTap(x,y);
        //UGameLogic.LogMsg("Global Tap detected on button");

    }
    @Override
    public void onMouseClick(){
        super.onMouseClick();
        //UGameLogic.LogMsg("Tapped button ");
    }


}
