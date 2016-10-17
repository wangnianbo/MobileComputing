package com.mobilecomputing.game.GameObjects;

import java.util.ArrayList;

import com.mobilecomputing.game.Controller;

/**
 * Created by Venom on 18/09/2016.
 */
public class WormTemplate_Enemy extends WormTemplate{
	
	public static ArrayList<String> possibleNames=new ArrayList<String>();
	public static void resetPossibleNames(){
		possibleNames.add("Homer");
		possibleNames.add("That Guy");
		possibleNames.add("Blaze");
		possibleNames.add("Hungry");
		possibleNames.add("The Destroyer");
		possibleNames.add("1337");
		possibleNames.add("Seeker");
		possibleNames.add("Tracker");
		possibleNames.add("Finder");
		
	}
	
	
    public WormTemplate_Enemy(float x, float y, WormSegment startingSegment) {
    	super(x,y,startingSegment);
        //Assign a random name to the enemy
    	if(possibleNames.size()<1){
    		resetPossibleNames();
    	}
    	
    	int randNameIndex=Controller.spawnRandom.nextInt(possibleNames.size());
    	name=possibleNames.get(randNameIndex);
    	possibleNames.remove(randNameIndex); 
    }
    


    public WormTemplate_Enemy(float x, float y, int segmentNo){
        super(x,y,segmentNo);
        //Assign a random name to the enemy
    	if(possibleNames.size()<1){
    		resetPossibleNames();
    	}
    	
    	int randNameIndex=Controller.spawnRandom.nextInt(possibleNames.size());
    	name=possibleNames.get(randNameIndex);
    	possibleNames.remove(randNameIndex); 
    }

    @Override
    public WormHead ConstructHead() {
        head=new WormHead_Enemy(x,y,this);
        return head;
    }


}
