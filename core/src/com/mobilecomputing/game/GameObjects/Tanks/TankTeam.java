package com.mobilecomputing.game.GameObjects.Tanks;

import helperDataStructures.Point2D;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.FontController;
import com.mobilecomputing.game.Drawables.SpriteImageData;



public class TankTeam {
	
	//Create a team
	public TankTeam(int alignment1){
		alignment=alignment1;
		members=new ArrayList<Tank>();
	}
	
	public Color getColor(){
		if(alignment==1){
			return new Color(1,0,0,1);
		}
		else if(alignment==2){
			return new Color(0,1,0,1);
		}
		else if(alignment==3){
			return new Color(0.25f,0.5f,1,1);

		}
		else if(alignment==4){ 
			return new Color(1,1,0,1);
		}
		return new Color(1,1,1,1);
		
	}
	
	public String TeamName(){
		return "Team"+alignment;
	}
	
	public Point2D getScorePosition(){
        int posX=10;
        int posY=10;
        if(alignment%2==0){
        	posX=Controller.screenWidth-80;
        }
        if(alignment>2){
        	posY=Controller.screenHeight-17;
        }
        
        return new Point2D(posX,posY);
	}
	
	public void RenderDetails(){
		FontController.ResetProperties();
		SpriteImageData.ResetProperties();
        FontController.color=getColor();
        Point2D scorePosition=getScorePosition();
        FontController.DrawString(TeamName()+": "+getScore(),scorePosition.x,scorePosition.y);
        /*FontController.color=Color.GREEN;
        FontController.DrawString("Green: "getScore(),scorePosition.x,scorePosition.y);
        */
    	for(Tank tank :members){
    		if(tank.destroyed){
    			Circle circle=new Circle(0,0,20);
    			SpriteImageData.color=getColor();
    			SpriteImageData.DrawShape(circle,tank.spawnX,tank.spawnY);
    		}
    	}
		
	}
	
	
	
	/*
	//Create a team based on a details set;
	public Team(BasicTeamDetails newDetailsSet){
		alignment=newDetailsSet.alignment;
		members=new ArrayList<Tank>();
		//Create new objects to be members of this team
		for(BasicPlayerDetails member:newDetailsSet.detailsOfMembers){
			Tank.addTeamInstanceFromDetails(this,member);
		}
		overrideDetails(newDetailsSet);
	}
	
	//Override the data in the team, with that contained within a basic team details structure.
	public void overrideDetails(BasicTeamDetails newDetailsSet){
		
		score=newDetailsSet.score;

		alignment=newDetailsSet.alignment;
		lastMemberIndex=newDetailsSet.lastMemberIndex;
		if(newDetailsSet.detailsOfMembers.size()!=members.size()){
			System.out.println("ERROR: Team.overrideDetails, team sizes are inconsistent");
		}
		for(int d=0;d<newDetailsSet.detailsOfMembers.size();d++){
			members.get(d).overrideDetails(newDetailsSet.detailsOfMembers.get(d));
		}

	}
	*/
	//Points earned by the current team.
	private int score;
		//Change the score
		public void incScore(int inc){
			setScore(score+inc);
		}
	
		public void setScore(int newScore){
			
			score=Math.max(newScore,0);
			
			
		}
		public int getScore(){
			return score;
		}

	

	//Whose side does the team belong to
	private int alignment;
		public int getAlignment(){
			return alignment;
		}
		
	//Members that are a part of the team.
	public ArrayList<Tank> members;
	public ArrayList<Tank> getMembers(){
		return members;
	}
	//Last active member of the team.
	private int lastMemberIndex=0;
		public int getLastMemberIndex(){
			return lastMemberIndex;
		}
		public void setLastMemberIndex(int newIndex){
			lastMemberIndex=newIndex;
		}
	//Returns whether or not the team is on the last of its members;
	public boolean onLastCharacter(){
		if(members==null)
			return false;
		return lastMemberIndex==members.size()-1;
	}
	//Returns whether or not the team is selecting the first of its members
	public boolean onFirstCharacter(){
		if(members==null)
			return false;
		return lastMemberIndex==0;
	}
	//The team has no members
	public boolean isEmpty(){
		return members.size()==0;
	}

	//Add a member to the team
	public void addMember(Tank newMember){
		members.add(newMember);
	}
	
	//Get the next member in the team
	public Tank getNextMember(){
		lastMemberIndex+=1;
		if(isEmpty()){
			return null;
		}
		if(lastMemberIndex>=members.size()){
			lastMemberIndex=0;
		}
		Tank testMember=members.get(lastMemberIndex);
		return members.get(lastMemberIndex);
		/*if(testMember.destroyed!=true)
		{
			return members.get(lastMemberIndex);
		}
		else
		{
			members.remove(lastMemberIndex);
			lastMemberIndex--;
			return getNextMember();
		}
		*/
	}
	
	
	

}

