package com.mobilecomputing.game.Drawables;

import java.util.ArrayList;
import java.util.HashMap;

import com.mobilecomputing.game.UGameLogic;


public class AnimationSet extends AnimationParentClass
{
    private HashMap<String, SimpleAnimation> animations = new HashMap<String, SimpleAnimation>();
    public String name="";
    public String getName(){
    	return name;
    }
    //construct empty animation set;
    public AnimationSet(String name)
    {

        this.name = name.toLowerCase();
        
        GlobalAnimations.globalAnimationSets.put(this.name,this);
    }


    
    public AnimationSet(HashMap<String, SimpleAnimation> newAnimations,String name)
    {
        this.name = name;
        animations = newAnimations;
    }


    //Create a new animation from the specified frames to be added to the map;
    public void SetAnimation(String animationName, ArrayList<SpriteImageData> images)
    {
        SetAnimation(animationName, new SimpleAnimation(images));
    }




    //Same as above but with a single image
    public void SetAnimation(String animationName, SpriteImageData image)
    {
        ArrayList<SpriteImageData> imagesToAdd = new ArrayList<SpriteImageData>();
        imagesToAdd.add(image);
        SetAnimation(animationName,imagesToAdd);
    }

    //Set an animation;
    public void SetAnimation(String animationName, String prefix, String imageNames)
    {
        ArrayList<SpriteImageData> images = SpriteImageData.GetImagesFromNames(prefix, imageNames);

        SetAnimation(animationName, images);
    }


    //Add an animation to the hash map;
    public void SetAnimation(String animationName, SimpleAnimation newAnimation)
    {
        animations.put(animationName.toLowerCase(), newAnimation);
    }

    //Add an animation to the hash map;
    public void SetAnimation(String animationName, ArrayList<SpriteImageData> images, int delay)
    {
        SimpleAnimation newAnimation = new SimpleAnimation(images, delay);
        SetAnimation(animationName, newAnimation);
    }


    public SimpleAnimation GetAnimationByName(String animationName)
    {
        animationName = animationName.toLowerCase();
        if (animations.containsKey(animationName))
        {
            return animations.get(animationName);
        }
        String genString = "could not find animation \"" + animationName + "\" in \""+name+"\" AnimationSet \n The following keys exist though:";

        for (String key : animations.keySet())
        {
            genString+=("\n\t key "+key);
        }
        UGameLogic.LogMsgUnique(genString);
        return null;
    }
    //For determining render offset;
    public enum Anchor { CENTER, BASE, TOPLEFT };
    public Anchor anchor = Anchor.CENTER;


}

