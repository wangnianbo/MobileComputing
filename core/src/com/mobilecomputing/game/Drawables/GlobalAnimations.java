package com.mobilecomputing.game.Drawables;

import java.util.HashMap;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.mobilecomputing.game.UGameLogic;

public class GlobalAnimations {
    public static HashMap<String, AnimationSet> globalAnimationSets=new HashMap<String,AnimationSet>();


    public static AnimationSet GetAnimationSetByName(String name)
    {
        name = name.toLowerCase();
        if (!globalAnimationSets.containsKey(name))
        {
            UGameLogic.LogError("No globalAnimation exists by the name of " + name);
            return null;
        }
        return globalAnimationSets.get(name);
    }
    static AnimationSet tankSet;
    //Initialize Animations
    public static void InitializeAnimations()
    {
    	tankSet=new AnimationSet("tanks");
        Texture walkSheet = SpriteImageData.GetByName("tankTreads").texture; // #9
        int FRAME_COLS=7;
        int FRAME_ROWS=1;
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, 32, 32);              // #10
        
        
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        Animation walkAnimation = new Animation(0.025f, walkFrames);      // #11
 
    	SimpleAnimation testAnim=new SimpleAnimation(walkAnimation);
    	tankSet.SetAnimation("default",testAnim);

    }
}
