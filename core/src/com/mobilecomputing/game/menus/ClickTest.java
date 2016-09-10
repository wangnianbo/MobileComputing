package com.mobilecomputing.game.menus;

import com.mobilecomputing.game.Drawables.SpriteImageData;

/**
 * Created by Venom on 29/08/2016.
 */
public class ClickTest extends MenuElement {
    public ClickTest(float x,float y){
        super(x,y);

    }
    @Override
    public void render(){
        SpriteImageData.ResetProperties();
        SpriteImageData.Draw("explosionFlare1",x,y);
    }
}
