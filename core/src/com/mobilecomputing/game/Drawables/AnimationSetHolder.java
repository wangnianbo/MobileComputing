package com.mobilecomputing.game.Drawables;

import com.mobilecomputing.game.UGameLogic;


public class AnimationSetHolder
{
    public int _frameIndex=0;
    public int getFrameIndex()
    {
        AdjustFrameIndex();
        return _frameIndex; 

    }
    public void setFrameIndex(int value){
    	_frameIndex = value;
    	AdjustFrameIndex();
    }
    
    public boolean looping = true;
    public boolean playing = true;
    private String animationName;
    public String GetAnimationName(){
    	return animationName;
    }
    AnimationParentClass myAnimationData;
    public int delayBetweenFrames = 1;
    public int step = 0;
    public int delaySinceLastFrame = 0;
    public boolean playInReverse = false;

    public void AdjustFrameIndex()
    {
        //Also make sure the frame Index is non negative
        /*
        if (_frameIndex < 0)
        {
            _frameIndex = 0;
        }
        if (_frameIndex == 0)
        {
            return;
        }
        */
        if (myAnimationData == null)
            return;
        SimpleAnimation myAnimation = GetCurrentAnimation();
        if (myAnimation == null)
            return;
        //Ensure that the frame index is in range of the animation frames
        if (_frameIndex >= myAnimation.getFrameNumber())
        {

            if (!looping)
            {
                _frameIndex = myAnimation.getFrameNumber() - 1;
            }
            else
            {
                int frameNo = myAnimation.getFrameNumber();
                if (frameNo > 0)
                {
                    _frameIndex = _frameIndex % frameNo;
                }
            }
        }
        if (_frameIndex < 0)
        {
            if (!looping)
            {
                _frameIndex = 0;
            }
            else
            {
                _frameIndex = myAnimation.getFrameNumber() - 1;
            }
        }
    }




    //Create an instance of the animation holder, as linked to an animation set;
    public AnimationSetHolder(){
        animationName = "default";

    }
    
    public AnimationSetHolder(AnimationSet newSet)
    {
        animationName = "default";
        SetAnimationSet(newSet);
    }

    public boolean SetAnimationData(AnimationParentClass animationData)
    {
        if (animationData == null)
            return false;
        if (myAnimationData != animationData)
        {
            animationName = "default";
            setFrameIndex(0);
            myAnimationData = animationData;
        }
        return true;
    }



    //Choose an animation set true if it exists;_
    public boolean SetAnimationSet(AnimationSet set)
    {

        if (set != null)
        {
            if (set != myAnimationData)
            {
                animationName = "default";
                setFrameIndex(0);
                myAnimationData = set;
            }
            return true;
        }
        return false;
    }

    //Choose an animation set;
    public boolean SetAnimationSet(String setName)
    {
        setName = setName.toLowerCase();
        AnimationSet set = null;
        if (GlobalAnimations.globalAnimationSets.containsKey(setName))
        {
            set =GlobalAnimations.globalAnimationSets.get(setName);
        }
        if (set == null)
        {
            UGameLogic.LogMsg("ERROR in AnimationSet.SetAnimationSet(" + setName + "): AnimationSet " + setName + " does not exist in the global context. Did you forget to import it?");
            return false;
        }
        return SetAnimationSet(set);
    }


    //Set the current animation. I.e. "walk", "run", "parry", "shoot", "blow up"
    public void SetAnimationSequence(String newName)
    {


        if(newName==null)
            return;
        newName = newName.toLowerCase();
        if (myAnimationData == null)
        {
            UGameLogic.LogMsg(this+": can't set any animations, because the animation set is undefined!");
        }
        if (newName == animationName)
        {
            return;
        }
        if (newName == null)
        {
            UGameLogic.LogMsg("ERROR in AnimationSetHolder: newName can't be NULL");
            return;
        }
        if (!(myAnimationData instanceof AnimationSet))
        {
            UGameLogic.LogMsg("ERROR in AnimationSetHolder.SetAnimationSequence() only applies when the 'myAnimations' variable is an AnimationSet");
            return;
        }
        if (((AnimationSet)myAnimationData).GetAnimationByName(newName) != null)
        {
            newName=newName.toLowerCase();
            if (!animationName.equals(newName))
            {
                animationName = newName;
                step = 0;
                setFrameIndex(0);
            }
        }
        else if (newName.contains(".") == false)
        {
            UGameLogic.LogMsg("ERROR in AnimationSetHolder: animation " + newName + " does not exist;");
            UGameLogic.LogMsg("Within the set: \""+((AnimationSet)myAnimationData).name+"\"");

        }
    }

    //Set the animation set and sub sequence from the global dictionary;
    public void SetAnimationSetAndSequence(String globalSetName, String sequenceName)
    {
        if (SetAnimationSet(globalSetName))
        {

            SetAnimationSequence(sequenceName);
        }
        else
        {
            UGameLogic.LogMsg("Animation " + globalSetName + " does not exist");
        }
    }

    //render the animation set;
    public SpriteImageData GetImageFrame(){
    	return GetImageFrame(true);
    }
    
    public SpriteImageData GetImageFrame(boolean progressFrame)
    {
        SpriteImageData returnValue = GetRenderable();
        //increment animation frame (unless the animation is stopped)
        if (progressFrame && returnValue != null)
        {
            Progress();
        }
        return returnValue;
    }

    //Progress the animation by 1 frame;
    public void Progress(){
        step++;
        delaySinceLastFrame++;
        if (playing)
        {
            if (delaySinceLastFrame >= delayBetweenFrames)
            {
                delaySinceLastFrame = 0;
                if (playInReverse)
                {
                	setFrameIndex(getFrameIndex()-1);

                }
                else
                {
                	setFrameIndex(getFrameIndex()+1);

                }
            }
        }
    }

    public SimpleAnimation GetCurrentAnimation()
    {
        SimpleAnimation myAnimation = null;
        if (myAnimationData instanceof SimpleAnimation)
        {
            myAnimation = (SimpleAnimation)myAnimationData;
        }
        if (myAnimationData instanceof  AnimationSet)
        {
            myAnimation = ((AnimationSet)myAnimationData).GetAnimationByName(animationName);
        }
        return myAnimation;
    }

    //Return the image based on the animation sequence name and the frameIndex;
    public SpriteImageData GetRenderable()
    {
        if (myAnimationData == null)
            return null;
        //First check the animation is set;
        SimpleAnimation myAnimation = GetCurrentAnimation();

        if (myAnimation == null)
        {
            return null;
        }
        //Image to return
        SpriteImageData returnValue;



            returnValue = myAnimation.getRenderable(getFrameIndex());
            //System.out.printf("%d\n", frameIndex);
            return returnValue;

    }


    //render the animation as is
    

    public void Render(float x, float y,boolean progressAnimation)
    {

        float xScale=SpriteImageData.scaleX;
        float yScale=SpriteImageData.scaleY;
        SpriteImageData myImage = GetImageFrame(progressAnimation);

        
        if (myImage != null)
        {
            AnimationSet.Anchor anchor = AnimationSet.Anchor.TOPLEFT;
            if (myAnimationData instanceof AnimationSet)
            {
                anchor=((AnimationSet)myAnimationData).anchor;
            }
                if (anchor == AnimationSet.Anchor.TOPLEFT)
                {
                	myImage.Draw(x, y,false);
                    //ViewableObject.DrawImage(myImage, x, y, false);
                }
                else if (anchor == AnimationSet.Anchor.BASE)
                {
                	myImage.Draw(x, y - myImage.getHeight() / 2*yScale);

                }
                else
                {
                	myImage.Draw(x, y ,anchor == AnimationSet.Anchor.CENTER);


                }

        }
    }

    //Porgress the given animation by a set number of frames;
    public void Progress(int frame)
    {

    }



    //Get the number of frames in the current animation
    public int GetCurrentAnimationLength()
    {
        //Get the selected animation
        SimpleAnimation myAnimation = GetCurrentAnimation();
        if (myAnimation != null)
        {
            return myAnimation.getFrameNumber();
        }
        else
        {
            return 0;
        }
    }

    public void SetProgress(double progress)
    {
        int len = GetCurrentAnimationLength();
        int maxFrame = len - 1;
        progress = Math.max(Math.min(progress, 1),0);
        setFrameIndex((int)(Math.min((len) * (progress), maxFrame)));

    }

    public double GetProgress()
    {
        double len = GetCurrentAnimationLength() - 1;
        return Math.min(Math.max(getFrameIndex() / (len),0),len);
    }
    //Check if the animation is complete;
    public boolean isCurAnimationFinished()
    {
        return (getFrameIndex() == GetCurrentAnimationLength() - 1);
    }


}

