package com.mobilecomputing.game.Drawables;
import java.io.File;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.UGameLogic;


public class SpriteImageData extends BasicImageData {
	private static HashMap<String,SpriteImageData> allImages=new HashMap<String,SpriteImageData>();
	public static HashMap<String,SpriteImageData> getAllImages(){
		return allImages;
	}
	
	public static Color color=Color.WHITE;
	
	public String sourcePathString="";
	public SpriteBatch spriteBatch=null;
	public Texture texture=null;
	public Sprite sprite=null;

	public TextureRegion textureRect=null;
	float local_originX;
	float local_originY;



	public static SpriteImageData GetByName(String name){
		name=UGameLogic.FixFileSeperators(name);
		name=name.toLowerCase();
		
		if(name.contains(".")){
			name=name.split(".")[0];
		}
		

		if(allImages.get(name)!=null){
			return allImages.get(name);
		}
		UGameLogic.LogError("No image data by the name of "+name+" was loaded.");
		return null;
	}




	public static void loadAllSprites(SpriteBatch spriteBatch){
		String topFileName="";

		String abbreviator="images"+File.separator;
		RecurseForImages("images");
		String directoryString=topFileName+"images"+File.separator;
		ArrayList<File> files=UGameLogic.RecurseDirectory(directoryString);

		/*
		for(File file : files){
    		String subFileName=file.getPath();

    		if(topFileName!=null && subFileName.startsWith(topFileName)){
    			subFileName=subFileName.substring(topFileName.length(),subFileName.length());
    		}
    			String subFileName_lower=subFileName.toLowerCase();
    			List<String> allowedImageExtensions=Arrays.asList(".gif",".jpeg",".jpg",".png",".bmp");
    			boolean isImageFile=false;
    			for(String imageExtension:allowedImageExtensions){
    				if(subFileName_lower.endsWith(imageExtension)){
    					isImageFile=true;
    				}
    			}
    			if(!isImageFile){
    				continue;
    			}
    			//UGameLogic.LogMsg("Loading Image "+subFileName_lower.split("."));
    			
    			String imageName=subFileName_lower.split("\\.")[0];
        		if(imageName!=null && imageName.startsWith(abbreviator)){
        			imageName=imageName.substring(abbreviator.length(),imageName.length());
        		}
    			//UGameLogic.LogMsg("Loading Image "+imageName+" from "+subFileName);
    			
    			
    			
    			if(!allImages.containsKey(imageName)){
    				Texture texture=new Texture(subFileName);
    				
    				//UGameLogic.LogMsg("texture "+texture);
    				SpriteImageData newImageData=new SpriteImageData(imageName,texture);
    				UGameLogic.LogMsg("Adding "+imageName+" from \""+subFileName+"\"");
    				allImages.put(imageName,newImageData);
    			}
    			else{
    				UGameLogic.LogError("SpriteImageData.loadAllImages(...): There is already an image by the name of "+imageName+" in the set of all images");
    			}
    			
    		}
		*/
			/*loadImage("images\\tanksSplit.png");
			loadImage("images\\tankTreads.png");

			loadImage("images\\ui\\button_hostGame.png");
			loadImage("images\\ui\\button_joinGame.png");
			loadImage("images\\ui\\button_localGame.png");
			*/

	}

	public static void RecurseForImages(String dirName){
		FileHandle[] fileArray = Gdx.files.internal(dirName).list();
		for(int i=0; i<fileArray.length; i++){
			//if it is not a directory
			if(!(fileArray[i].isDirectory())){
				String stringPath = fileArray[i].path();
				UGameLogic.LogMsg(stringPath);
				if(stringPath.startsWith("images")){
					loadImage(stringPath);
				}
				//load file
				//manager.load(stringPath, Texture.class);
			}
			else{
				String path=fileArray[i].path();
				UGameLogic.LogMsg("Test Path "+path);
				RecurseForImages(path);
			}
		}
	}

	public static SpriteImageData loadImage(String subFileName){
		subFileName=UGameLogic.FixFileSeperators(subFileName);
		Texture texture=new Texture(subFileName);
		String abbreviator=UGameLogic.FixFileSeperators("images\\");
		String imageName=subFileName.toLowerCase().split("\\.")[0];
		if(imageName!=null && imageName.startsWith(abbreviator)){
			imageName=imageName.substring(abbreviator.length(),imageName.length());
		}
		//UGameLogic.LogMsg("texture "+texture);
		SpriteImageData newImageData=new SpriteImageData(imageName,texture);
		UGameLogic.LogMsg("Adding "+imageName+" from \""+subFileName+"\"");
		allImages.put(imageName,newImageData);
		UGameLogic.LogMsg("Loaded Image "+texture);
		return newImageData;
	}

	
	public SpriteImageData(String sourcePath,Texture texture){
		this.spriteBatch=Controller.batch;
		this.texture=texture;
		this.textureRect=new TextureRegion(texture,0,0,texture.getWidth(),texture.getHeight());
		this.sprite=new Sprite(textureRect);
		this.local_originX=this.sprite.getOriginX();
		this.local_originY=this.sprite.getOriginY();
	}
	
	public SpriteImageData(String sourcePath,TextureRegion textureRegion){
		this.spriteBatch=Controller.batch;
		this.texture=textureRegion.getTexture();
		this.textureRect=textureRegion;
				//new TextureRegion(texture,0,0,texture.getWidth()/2,texture.getHeight()/2);
		this.sprite=new Sprite(textureRect);
		this.local_originX=this.sprite.getOriginX();
		this.local_originY=this.sprite.getOriginY();
	}
	
	public static float rotation=0;
	//public static 
	
	public static float scaleX=1;
	public static float scaleY=1;
	

	public static void ResetProperties(){
		rotation=0;
		//public static 
		color=Color.WHITE;
	    scaleX=1;
		scaleY=1;
	}
	
	public int getHeight(){
		if(this.textureRect==null){
			return 0;
		}
		return this.textureRect.getRegionHeight();
	}
	
	public int getWidth(){
		if(this.texture==null){
			return 0;
		}
		return this.textureRect.getRegionWidth();
	}
	
	
	
	public void Draw(float x,float y){
		Draw(x,y,true);
	}
	

	public static boolean offsetCentered=true;
	
	public static SpriteImageData Draw(String identifier,float x,float y){
		SpriteImageData image=GetByName(identifier);
		if(image!=null){
			image.Draw(x,y);
		}
		return image;
		
	}
	
	public static SpriteImageData Draw(String identifier,float x,float y,boolean centered){
		SpriteImageData image=GetByName(identifier);
		if(image!=null){
			image.Draw(x,y,centered);
		}
		return image;
	}
	
	
	public void Draw(float x,float y,boolean centered){
		float proposedOriginX=0;
		float proposedOriginY=0;
		if(centered){
			proposedOriginY=getHeight()/2f;
			proposedOriginX=getWidth()/2f;
			if(offsetCentered){
				x=x-getWidth()/2;
				y=y-getHeight()/2;
			}
		}
		Draw(x,y,proposedOriginX,proposedOriginY);
	}
	
	public void Draw(float x,float y,float proposedOriginX,float proposedOriginY){
		//rotation=rotation+0.5f;
		//UGameLogic.LogMsg("origin X " +sprite.getOriginX()+" Height " +sprite.getHeight());
		y=Controller.screenHeight-getHeight()-y;

		if(local_originX!=proposedOriginX || local_originY!=proposedOriginY){
			local_originX=proposedOriginX;
			local_originY=proposedOriginY;
			sprite.setOrigin(local_originX, sprite.getHeight()-local_originY);
			//UGameLogic.LogMsg("Setting origin");
		}
		
		
		
		//spriteBatch.draw(texture,x,y);
		sprite.setScale(scaleX,scaleY);
		//UGameLogic.LogMsg("rotation "+rotation);
		sprite.setRotation(rotation);
		sprite.setColor(color);
		sprite.setPosition(x, y);
		sprite.draw(spriteBatch);
	}
	
    public static ArrayList<SpriteImageData> GetImagesFromNames(String prefix, String names)
    {
        ArrayList<SpriteImageData> images = new ArrayList<SpriteImageData>();
        String[] namesList = names.split(",");
        for (String name : namesList)
        {
            SpriteImageData image = GetByName(prefix+name);
            if (image != null)
            {
                images.add(image);
            }
        }
        return images;
    }

    
    public static ShapeRenderer shapeRenderer=new ShapeRenderer();
	public static void DrawShape(Shape2D shapeCollider, float x, float y) {
		DrawShape(shapeCollider,x,y,color);
	}
    
	public static void DrawShape(Shape2D shapeCollider, float x, float y,Color color) {
		// TODO Auto-generated method stub
		Controller.batch.end();


		shapeRenderer.setProjectionMatrix(Controller.activeCamera.combined);
		 shapeRenderer.begin(ShapeType.Line);

		 shapeRenderer.setColor(color);
		 if(shapeCollider instanceof Rectangle){
			 Rectangle rect1=((Rectangle)shapeCollider);
			 //rect1.setPosition(x,Controller.screenHeight-y-rect1.getHeight());
			 shapeRenderer.rect(rect1.getX()+x,Controller.screenHeight-rect1.getY()-rect1.getHeight()-y,rect1.width,rect1.height);
		 }
		 if(shapeCollider instanceof Polygon){
			 Rectangle rect1=((Polygon)shapeCollider).getBoundingRectangle();
			 //rect1.setPosition(x,Controller.screenHeight-y-rect1.getHeight());
			 shapeRenderer.rect(rect1.getX()+x,Controller.screenHeight-rect1.getY()-rect1.getHeight()-y,rect1.width,rect1.height);
			 
			 /*
			 Polygon poly=((Polygon)shapeCollider);
			 //rect1.setPosition(x,Controller.screenHeight-y-rect1.getHeight());
			 Rectangle rect1=poly.getBoundingRectangle();
			 UGameLogic.LogMsg("Before "+poly.getOriginX()+" "+poly.getOriginY());
			 //poly.setOrigin(x+poly.getX(),Controller.screenHeight-rect1.getHeight()-poly.getOriginY()-y+poly.getY());
			 UGameLogic.LogMsg("After "+poly.getOriginX()+" "+poly.getOriginY());
			 shapeRenderer.polygon(poly.getVertices());
			// poly.setOrigin(0, 0);

					 */
		 }
		 else if(shapeCollider instanceof Circle){
			 Circle circle1=((Circle)shapeCollider);
			 //rect1.setPosition(x,Controller.screenHeight-y-rect1.getHeight());
			 shapeRenderer.circle(circle1.x+x,Controller.screenHeight-circle1.y-circle1.radius*2-y,circle1.radius);
		 }
		 
		 shapeRenderer.end();
		 Controller.batch.begin();
	}
}
