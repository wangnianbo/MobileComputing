package com.mobilecomputing.game;

import java.util.ArrayList;


import com.badlogic.gdx.math.Rectangle;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.GameObjects.LegacyGameObject;
import com.mobilecomputing.game.GameObjects.Pellet;
import com.mobilecomputing.game.GameObjects.Tanks.Tank;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormTemplate;
import com.mobilecomputing.game.GameObjects.WormTemplate_Enemy;
import com.mobilecomputing.game.GameObjects.WormTemplate_Player;
import com.mobilecomputing.game.Terrain.HaxWall;
import com.mobilecomputing.game.menus.GameOverMenu;

import helperDataStructures.Point2D;


public class World {
	public int width=2000;
	public int height=2000;
	private ArrayList<LegacyGameObject> activeLocalObjects = new ArrayList<LegacyGameObject>();
	public ArrayList<LegacyGameObject> getActiveLocalObjects(){
		return activeLocalObjects;
	}
	public Tank activeTank=null;
	//public Tank activeCharacter=null;
	//Local objects;
	/*

    //Tank Variables:
	ArrayList<TankTeam> teams=new ArrayList<TankTeam>();
	public int activeTeamIndex=0;
	public TankTeam getActiveTeam(){
		return teams.get(activeTeamIndex);
	}
	public TankTeam AddTeam(TankTeam team){
		UGameLogic.LogMsg("Adding Team "+team.getAlignment());
		teams.add(team);
		return team;
	}
	private int initChangeCharacterDelay=UGameLogic.lengthOfSecond*4/3;
	private int changeCharacterDelay=initChangeCharacterDelay;



	//Change the team and player
	public void nextTurn(){
		dueForNextTurn=false;
		MultiplayerController.HostType hostType=MultiplayerController.hostType;
		//Set the delay for next time
		switchToNextTeam();
		if(activeTeamIndex==0 && teams.get(activeTeamIndex).getLastMemberIndex()==0){
			if(incrementRound()==false){
				endGame();
			}
		}
	}

	public TankTeam GetTeamByAlignment(int i){
		for(TankTeam team : teams){
			if(team.getAlignment()==i){
				return team;
			}
		}
		return null;
	}
	public void switchToNextTeam(){
		changeCharacterDelay=initChangeCharacterDelay;
			int nextTeamIndex;
			//Next team
			nextTeamIndex=activeTeamIndex+1;
			UGameLogic.LogMsg("Next Team Index " +nextTeamIndex);
			//Go back to the first team, if the index exceeds the vector size
			if(teams==null)
				return;
			if(nextTeamIndex>=teams.size())
				nextTeamIndex=0;

			RespawnDeadCharacters();
			changeActiveTeam(nextTeamIndex);

	}



	TankTeam winningTeam=null;
	public void changeActiveTeam(int nextTeamIndex){

		int prevTeamIndex=activeTeamIndex;
		activeTeamIndex=nextTeamIndex;
		//No teams or no team members==nothing to do
		if(teams==null){
			activeCharacter=null;
			return;
		}
		if(prevTeamIndex!=activeTeamIndex){
			System.out.println("Swapping characters");
			changeActiveCharacter();
		}
	}

	//Change the active character.
	public Boolean changeActiveCharacter(){

		//For determining whether to give up control over the game state, for the sake of multiplayer
		boolean endTurnInMultiPlayer=false;
		//Only change the character if the timer delay for doing so
		//is 0


		//No teams or no team members==nothing to do
		if(teams==null || activeTeamIndex>=teams.size()){
			activeCharacter=null;
			return false;
		}
		if(activeCharacter!=null)
			activeCharacter.endTurn();
		//Change the character that's in use to the next player in the next team.
		activeCharacter=teams.get(activeTeamIndex).getNextMember();
		activeCharacter.startTurn();
		//If no player is found in this team, search the others
		if(activeCharacter==null)
			return false;
		return true;
	}

	//ROUND
	private int maxRound=2;
	private int round=1;
	public ArrayList<GameEventMessage> scheduledMessages=new ArrayList<GameEventMessage>();
		public int getRound(){
			return round;
		}
		public void setRound(int val){
			round=val;
		}


	//Increment the current round
	public boolean incrementRound(){
		round+=1;
		if(round>maxRound){

			round=maxRound;
			return false;
		}
		return true;
	}

	*/

	public WormTemplate activeCharacter;
	public boolean isMultiplayer=false;
	public World(Boolean isMultiplayer){
		construct(isMultiplayer);
	}

	public World(){
		construct(false);

	}

	public void construct(boolean isMultiplayer){

		this.activeCharacter=((WormTemplate_Player)addObject(new WormTemplate_Player(width/2,height*3/4,5)));
		addObject(new WormTemplate_Enemy(width/2+200,height*3/4,10));
		addObject(new WormTemplate_Enemy(width/2-200,height*3/4,5));
		this.isMultiplayer=isMultiplayer;

		int dimW=640/UGameLogic.tileWidth;
		int dimH=480/UGameLogic.tileWidth;
		boolean[][] tempWallArray=new boolean[dimW][dimH];
		for(int i=1;i<dimW-1;i++){
			if(i<3 || i>5)
				tempWallArray[i][3]=true;

		}
		for(int j=1;j<dimH;j++){
			if(j!=3)
			tempWallArray[4][j]=true;

		}


		for(int i=0;i<dimW;i++){
			for(int j=0;j<dimH;j++){
				if(tempWallArray[i][j]){
					addObject(new HaxWall(i*UGameLogic.tileWidth+200,j*UGameLogic.tileWidth+200));
				}

			}
		}




		//addObject(new Tank(50,50));

	}




	public static int idToAssignNext=0;
	public ArrayList<LegacyGameObject> objectsCreatedThisTurn=new ArrayList<LegacyGameObject>();
	//Add an object to the world;
	public LegacyGameObject addObject(LegacyGameObject legacyGameObject)
	{
		return addObject(legacyGameObject,true);
	}

	public LegacyGameObject addObject(LegacyGameObject legacyGameObject, boolean test)
	{
		if(test){
			objectsCreatedThisTurn.add(legacyGameObject);
		}
		if (legacyGameObject == null)
		{
			UGameLogic.LogMsg("Error with World.addObject(...) gameObject supplied is null");
			return null;
		}
		if(this.activeLocalObjects.contains(legacyGameObject)){
			return null;
		}
		if(legacyGameObject.identifier<0){
			legacyGameObject.identifier=idToAssignNext;
			idToAssignNext=idToAssignNext+1;
		}
		legacyGameObject.SetWorld(this);
		legacyGameObject.OnAddToWorld();
		legacyGameObject.UpdateRegionsTouching();

		//If the object didn't activate make sure that it's still added to the records;
		//!legacyGameObject.active ||
		if (true)
		{
			this._updateActivatedRecord(legacyGameObject);
		}
		return legacyGameObject;
		//return activeLevelArea.addObjectToLevelArea(legacyGameObject);
	}




	private void _updateActivatedRecord(LegacyGameObject g){
		if (isRemoved(g))
			this.activeLocalObjects.remove(g);
		else
			this.activeLocalObjects.add(g);

	}

	public boolean isRemoved(LegacyGameObject o)
	{
		if (o != null && o.getWorld() == this && !o.destroyed)
		{
			return false;
		}
		return true;
	}

	public int waitSize=0;
	public boolean dueForNextTurn=false;
	public void update(){
		manageHitDetectionUpdate();
		updateGameObjects(activeLocalObjects);
		objectsCreatedThisTurn.clear();
		//Spawn pallets
		handlePelletSpawning();
		//Handle camera zooming based on the length of the current snake;
		float zoomDiff=Math.abs(getIntendedCameraZoomRatio()-cameraZoomRatio);

		float minZoomDelta=0.1f;
		float zoomDelta=Math.max(zoomDiff/10,minZoomDelta);
		if (zoomDiff < minZoomDelta) {

			cameraZoomRatio=getIntendedCameraZoomRatio();
		}
		else{
			if(cameraZoomRatio<getIntendedCameraZoomRatio()) {
				cameraZoomRatio = cameraZoomRatio + zoomDiff;
			}
			else{
				cameraZoomRatio = cameraZoomRatio - zoomDiff;
			}
		}
		if(activeCharacter!=null && activeCharacter.destroyed){
			Controller.activeMenu=new GameOverMenu(0,0);
		}


	}

	public void handlePelletSpawning(){
		int pelletCount=0;
		for(LegacyGameObject o:getActiveLocalObjects()){
			if(o instanceof Pellet){
				pelletCount++;
			}
		}
		if(Controller.step%100==5 && pelletCount<20) {
			spawnPellet();
		}



	}

	public void spawnPellet(){
		Pellet newPellet=new Pellet(0,0);
		newPellet.SetWorld(this);
		int u=64;
		ArrayList<Point2D> possiblePoints=new ArrayList<Point2D>();
		for(int i=u;i<width -u;i+=u){

			for(int j=u;j<height-u;j+=u) {

				//if(newPellet.CheckMove(i,j)){
					possiblePoints.add(new Point2D(i,j));
				//}

			}
		}
		if(possiblePoints.size()>0){
			int spawnIndex=Controller.spawnRandom.nextInt(possiblePoints.size());
			//UGameLogic.LogMsg("Spawn Index "+spawnIndex);
			Point2D nextPoint=possiblePoints.get(spawnIndex);

			newPellet.x=nextPoint.x;
			//UGameLogic.LogMsg("new pellet X "+newPellet.x);
			newPellet.y=nextPoint.y;
			addObject(newPellet);
		}

	}




	public LegacyGameObject getObjectById(int id){
		for(LegacyGameObject o :activeLocalObjects){
			if(o.identifier==id){
				return o;
			}
		}
		return null;
	}

	public void Render(){

		FontController.ResetProperties();
		renderGameObjects(activeLocalObjects);


		SpriteImageData.DrawShape(new Rectangle(1,0,3,height),0,0);
		SpriteImageData.DrawShape(new Rectangle(1,0,3,height),width-3,0);
		SpriteImageData.DrawShape(new Rectangle(0,1,width,3),0,0);
		SpriteImageData.DrawShape(new Rectangle(0,1,width,3),0,height-3);
	}



	//Handle  hit detection between all objects;
	public void manageHitDetectionUpdate()
	{

		ArrayList<LegacyGameObject> collisionList = new ArrayList<LegacyGameObject>();
		for(LegacyGameObject o : activeLocalObjects){
			collisionList.add(o);
		}

		for (int i = 0; i < collisionList.size(); i++)
		{
			LegacyGameObject o = collisionList.get(i);
			collisionList.remove(i);
			i = i - 1;
			if (o.active && isRemoved(o) == false)
			{
				o.ReactToObjectCollisions(o.getObjectsTouchingAtPosition(o.x, o.y, true, false, false, collisionList));

			}
		}
		collisionList.clear();

        /*
        ArrayList<LegacyGameObject> collisionList = new ArrayList<LegacyGameObject>();
        foreach (TerrainChunk chunk in activeChunks)
        {
            foreach (LegacyGameObject o in chunk.GetRegularObjectsIn())
            {
                if (o as Shield != null)
                {
                    collisionList.Insert(0, o);
                }
                else
                {
                    collisionList.Add(o);
                }
            }

            for (int i = 0; i < collisionList.Count; i++)
            {
                LegacyGameObject o = collisionList[i];
                collisionList.RemoveAt(i);
                i = i - 1;
                if (o.active && isRemoved(o) == false)
                {
                    o.ReactToObjectCollisions(o.getObjectsTouchingAtPosition(o.x, o.y, true, false, true, collisionList));
                    o.ReactToTileCollisions();
                }
            }
            collisionList.Clear();
        }
        */
	}





	public void updateGameObjects(ArrayList<LegacyGameObject> hashSet)
	{
		//List<LegacyGameObject> collisionList=new List<LegacyGameObject>();
		LegacyGameObject[] list = hashSet.toArray(new LegacyGameObject[hashSet.size()]);
		for (LegacyGameObject o : list)
		{
			for (int i = 0; i < 2; i++)
			{
				//Still alive?
				if (!isRemoved(o))
				{
					if (i == 0)
					{
						o.earlyUpdate();
					}
					else
					{
						o.update();
					}
				}
				else
				{
					//Remove it from the list;
					hashSet.remove(o);
				}
			}
		}
	}

	public void renderGameObjects(ArrayList<LegacyGameObject> hashSet)
	{


		//List<LegacyGameObject> collisionList=new List<LegacyGameObject>();
		LegacyGameObject[] list = hashSet.toArray(new LegacyGameObject[hashSet.size()]);

		for (LegacyGameObject o : list)
		{

			//Still alive?
			if (!isRemoved(o))
			{
				o.render();
			}
			else
			{
				//Remove it from the list;
				hashSet.remove(o);
			}

		}
	}

	//TEAM AND TBS RELATED STUFF
	private boolean gameEnded;
	public boolean hasGameEnded(){
		return gameEnded;
	}

	public void endGame(){
		gameEnded=true;

	}

	public float getIntendedCameraZoomRatio(){
		if(activeCharacter!=null){
			return activeCharacter.getCameraZoomRatio();

		}
		return 1;
	}


	public float cameraZoomRatio=1;

	
}
