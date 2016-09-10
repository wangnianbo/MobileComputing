package com.mobilecomputing.game;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;


public class SoundController {

	        public static float defaultVolume = 100;
	        //(The minimum amount of milliseconds before a sound can be replayed);
	        public static int minDelayBetweenSoundReplay=250;
	        //All the sound buffers used in the game
	        private static HashMap<String, Sound> allSoundBuffers ;
	        
	        //Used as a stack to keep track of which sounds should be deleted;
	        static HashSet<Sound> recentSounds = new HashSet<Sound>();
	        


	        //The last sounds that were derived from a given buffer name;
	        private static HashMap<String,Sound> lastSoundsUsed=new HashMap<String,Sound>();
	        private static HashMap<Sound, String> lastSoundsUsedR = new HashMap<Sound, String>();

	        //Directory path for loading sounds;
	        public static final String soundsDirectory = "resources/sounds/";
	        public static final String musicDirectory = "resources/music/";

	        //On loading this class
	        static
	        {

	            //Initialize space for all sound buffers:
	            allSoundBuffers = new HashMap<String, Sound>();

	        }



	        public static Sound PlaySound(String s)
	        {
	            Sound sound=GetByName(s);
	            if(sound!=null){
	            	long id=sound.play();
	            }
	            return sound;
	        }


	        //Play a given sound
	        public static Sound PlaySound(String s, float pitch,float volume)
	        {

	            Sound sound=GetByName(s);
	            if(sound!=null){
	            	long id=sound.play();
	            	sound.setPitch(id,pitch);
	            	sound.setVolume(id,volume);
	            }
	            return sound;
	        }
	        
	    	public static Sound GetByName(String name){
	    		name= UGameLogic.FixFileSeperators(name);
	    		name=name.toLowerCase();
	    		
	    		if(name.contains(".")){
	    			name=name.split(".")[0];
	    		}
	    		

	    		if(allSoundBuffers.get(name)!=null){
	    			return allSoundBuffers.get(name);
	    		}
	    		UGameLogic.LogError("No sound data by the name of "+name+" was loaded.");
	    		return null;
	    	}

	        private static String[] supportedImportSoundExtensions = { "wav","ogg"};


	public static void RecurseForSounds(String dirName){
		FileHandle[] fileArray = Gdx.files.internal(dirName).list();
		UGameLogic.LogMsg("Recursing Sounds");
		for(int i=0; i<fileArray.length; i++){
			//if it is not a directory
			if(!(fileArray[i].isDirectory())){
				String stringPath = fileArray[i].path();
				UGameLogic.LogMsg(stringPath);
				if(stringPath.startsWith("sounds")){
					loadSound(stringPath);
				}
				//load file
				//manager.load(stringPath, Texture.class);
			}
			else{
				String path=fileArray[i].path();
				UGameLogic.LogMsg("Test Path "+path);
				RecurseForSounds(path);
			}
		}
	}

	public static  Sound loadSound(String subFileName){
		subFileName=UGameLogic.FixFileSeperators(subFileName);

		Sound newSoundData=null;
		try {
			newSoundData = Gdx.audio.newSound(Gdx.files.internal(subFileName));


			String abbreviator = UGameLogic.FixFileSeperators("sounds\\");
			String soundName = subFileName.toLowerCase().split("\\.")[0];
			if (soundName != null && soundName.startsWith(abbreviator)) {
				soundName = soundName.substring(abbreviator.length(), soundName.length());
			}
			//UGameLogic.LogMsg("texture "+texture);
			UGameLogic.LogMsg("Adding " + soundName + " from \"" + subFileName + "\"");
			allSoundBuffers.put(soundName, newSoundData);
		}
		catch(Exception e){
			UGameLogic.LogError(e.toString());
		}
			return newSoundData;
	}



	public static void loadAllSounds(){
	    		String topFileName="bin"+File.separator;
	    		String abbreviator="sounds"+File.separator;
		RecurseForSounds("sounds");


				/*
	    		String directoryString="bin"+File.separator+"sounds"+File.separator;
	    		ArrayList<File> files=UGameLogic.RecurseDirectory(directoryString);
	    		for(File file : files){
	        		String subFileName=file.getPath();
	        		
	        		if(topFileName!=null && subFileName.startsWith(topFileName)){
	        			subFileName=subFileName.substring(topFileName.length(),subFileName.length());
	        		}
	        			String subFileName_lower=subFileName.toLowerCase();

	        			boolean isSoundFile=false;
	        			for(String imageExtension:supportedImportSoundExtensions){
	        				if(subFileName_lower.endsWith(imageExtension)){
	        					isSoundFile=true;
	        				}
	        			}
	        			if(!isSoundFile){
	        				continue;
	        			}
	        			//UGameLogic.LogMsg("Loading Image "+subFileName_lower.split("."));
	        			
	        			String soundName=subFileName_lower.split("\\.")[0];
	            		if(soundName!=null && soundName.startsWith(abbreviator)){
	            			soundName=soundName.substring(abbreviator.length(),soundName.length());
	            		}
	        			//UGameLogic.LogMsg("Loading Image "+imageName+" from "+subFileName);
	        			
	        			
	        			
	        			if(!allSoundBuffers.containsKey(soundName)){
	        				//Texture texture=new Texture(subFileName);
	        				
	        				//UGameLogic.LogMsg("texture "+texture);
	        				Sound newSoundData=Gdx.audio.newSound(Gdx.files.internal(subFileName));
	        				UGameLogic.LogMsg("Adding "+soundName+" from \""+subFileName+"\"");
	        				allSoundBuffers.put(soundName,newSoundData);
	        			}
	        			else{
	        				UGameLogic.LogError("SoundController.loadAllSounds(...): There is already an image by the name of "+soundName+" in the set of all images");
	        			}
	        			
	        		}
	    		*/
	    		
	    		
	    	}
	    
}
