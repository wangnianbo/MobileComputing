package com.mobilecomputing.game.Drawables;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mobilecomputing.game.UGameLogic;

import java.util.ArrayList;


public class SimpleAnimation extends AnimationParentClass{
	//Create an instance of the simple animation./
    public ArrayList<SpriteImageData> images=new ArrayList<SpriteImageData>();
	public SimpleAnimation(ArrayList<SpriteImageData> images){

		for(SpriteImageData image : images){
			this.images.add(image);
		}
		this.delay=1;
	}
	public SimpleAnimation(ArrayList<SpriteImageData>  images,int delay){

		for(SpriteImageData image: images){
			this.images.add(image);
		}
		this.delay=delay;
	}

	
	//Delay in ticks, before displaying the next image;
	private int delay=1;
		public void setDelay(int newDelay){
			if(newDelay<1){
				UGameLogic.LogMsg("ERROR in SimpleAnimation.setDelay(); Are you crazy? if you divide by 0 the world will implode");
			}
			delay=newDelay;
		}
		public int getDelay(int delay){
			return delay;
		}

		//Return an image;
		public SpriteImageData getRenderable(int frame){
			//Retrieve an image from the vector of images;
			//Not worth continuing if there are no images in the collection
			if(images.size()==0){
				return null;
			}
			//Frame's can be repeated as many times as specified by the delay (hence the divide);
			//The % images.size() is just to prevent it from going out of boundes

			int imageIndex=(frame/delay)%(images.size());
			if(imageIndex<images.size() && imageIndex>=0){
				return images.get(imageIndex);
			}
			return null;
		}


		public int getFrameNumber(){
			return images.size() *delay;
		}
		
		//Append an image to the animation sequence;
		public void AppendImage(SpriteImageData newImage){
			images.add(newImage);
		}

        //Append Images;
        public void AppendImages(ArrayList<SpriteImageData> imagesToAdd)
        {
            for (SpriteImageData img : imagesToAdd)
            {
                images.add(img);
            }
        }

    //Add the reverse to the list;
        public void AppendReverse()
        {
            for (int i = images.size() - 1; i >= 0; i--)
            {
                images.add(images.get(i));
            }
        }

        public void Reverse()
        {
            UGameLogic.ReverseArrayList(images);
        }
        
		//Replace all the images in the collection
		public void ReplaceImages(ArrayList<SpriteImageData> newImages){
			if(images!=null){
				images=newImages;
			}
			else{
				UGameLogic.LogMsg("ERROR in SimpleAnimation.replaceImages(...): argument can't be null");
			}
		}
		
		public SimpleAnimation(Animation animation){
			//ArrayList<SpriteImageData> newImages=new ArrayList<SpriteImageData>();
			for(TextureRegion textureRegion : animation.getKeyFrames()){
				if(textureRegion!=null && textureRegion.getTexture()!=null)
					images.add(new SpriteImageData("",textureRegion));
			}
		}
		
}
