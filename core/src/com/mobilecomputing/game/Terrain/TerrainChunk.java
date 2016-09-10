/*
package com.mygdx.game.Terrain;

import java.util.HashSet;

import com.mobilecomputing.game.TileObjectPosition;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.World;
import LegacyGameObject;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SFML.Graphics;

//Basically a set of TileObjectPositions;
public class TerrainChunk
{










    private boolean active;
    public boolean getActive(){
    	return active;
    }
    public boolean setActive(boolean value){
    	active=value;
    }
    
    
    private boolean recentlyActivated;
    public boolean getRecentlyActivated(){
    	return recentlyActivated;
    }

    public void setRecentlyActivated(boolean activation){
        recentlyActivated=activation;
    }

    public static final int widthInTiles = 16;
    public static final int heightInTiles = 16;

    //Has the initial chunk data been loaded;
    private boolean hasLoadedData;
    public boolean getHasLoadedData(){
    	return hasLoadedData;
    }
    
    
    
    //Has the chunk been changed;

    /// <summary>
    /// Positions that have been modified over the course o the game;
    /// </summary>
    public boolean[] modifiedPositions;

    //The top left coordinate of the chunk within the world;
    private int x;
    public int getX(){
    	return x;
    }
    private int y;
    public int getY(){
    	return y;
    }
    private int tileX;
    public int getTileX(){
    	return tileX;
    }
    
    private int tileY;
    public int getTileY(){
    	return tileY;
    }
    private int chunkX(){
    	return tileX/widthInTiles;
    }
    
    
    public int getChunkX(){
    	return chunkX();
    }
    

    public int chunkY(){
    	return tileY/heightInTiles;
    }
    
    public int getChunkY(){
    	return chunkY();
    }

    public World world;
    //public static static int layers = Enum.GetNames(typeof(World.TileLayer)).Length;
    public TileObjectPosition[,,] tiles;

    //DEALING WITH OBJECTS WITHIN THE CHUNK;

    //Objects contained within the chunk;
    private HashSet<LegacyGameObject> localObjects;
    public HashSet<LegacyGameObject> GetLocalObjects(){
    	return localObjects;
    }
    



    public HashSet<LegacyGameObject> GetRegularObjectsIn()
    {
        return localObjects;
    }

    //Add a game object to the chunk's hash set of contained objects
    public void AddGameObject(LegacyGameObject g)
    {
        localObjects.add(g);
    }
    //Add a game object to the chunk's hash set of contained objects
    public void RemoveGameObject(LegacyGameObject g)
    {
        if (localObjects.contains(g))
        {
            localObjects.remove(g);
        }
    }


    //INITIALIZATION/SET UP

    //Create a chunk of tiles and load in the data;
    public TerrainChunk(World world, int tileX, int tileY)
    {
        this.recentlyActivated = false;
        this.world = world;
        active = false;
        localObjects = new HashSet<LegacyGameObject>();

        //Size of a tile;
        int u = UGameLogic.tileWidth;
        //The top left coordinate of the chunk within the world;
        this.tileX = tileX;
        this.tileY = tileY;
        x = tileX * u;
        y = tileY * u;
        //Create the array of tiles;
        tiles = new TileObjectPosition[widthInTiles, heightInTiles,layers];
        hasLoadedData = false;
        //Width of a tile;
        //Great now load the tiles;

        //Activate();
    }

    //Notify a chunk that it has been modified
    public void SetModify()
    {


    }

    //Update the chunk to reflect changes in the world;
    public void ResetChunkData()
    {

        int u = UGameLogic.tileWidth;
        //Set aside memory and structures for the region the TerrainChunk covers;
        //#region set up region covered
        //Taking into account worlds that don't fit terrain chunk dimensions;
        for (int i = 0; i < Math.Min(widthInTiles,world.tileWidth-tileX); i++)
        {
            for (int j = 0; j < Math.Min(heightInTiles,world.tileHeight-tileY); j++)
            {
                for (int l = 0; l < layers; l++)
                {
                    TileObjectPosition newData = world.GetRawTileDataAtPosition(x + i * u, y + j * u, (World.TileLayer)l);
                    if (newData != null)
                    {
                        newData.SetChunk(this, i, j, (World.TileLayer)l);
                    }
                    tiles[i, j, l] = newData;
                    if (tiles[i, j, l] != null)
                        tiles[i, j, l].FullUpdate();
                }

            }
        }
        //#endregion;

    }
    //#endregion;

    //TASKS TO PERFORM EACH STEP;




    //Return the tileObject in a given position within the chunk;
    public TileObjectPosition GetTileObjectPosition(int tileXOffset, int tileYOffset, World.TileLayer layer = World.TileLayer.MIDGROUND,bool selfUpdateIfRequired=true)
    {
        int u = UGameLogic.tileWidth;
        TileObjectPosition ret = tiles[tileXOffset, tileYOffset, (int)layer];
        if (ret == null)
        {
            ret = world.GetRawTileDataAtPosition(x + tileXOffset * u, y + tileYOffset * u, layer);
            if (world.tilesWithNotify)
            {
                tiles[tileXOffset, tileYOffset, (int)layer] = ret;
            }
        }
        if (selfUpdateIfRequired && ret.updateNeeded)
        {

            ret.FullUpdate();
        }
        return ret;
    }

    //clear the tileObject at a given position within the chunk
    public void ClearTileObjectAtPosition(int tileXOffset, int tileYOffset, World.TileLayer layer)
    {
        tiles[tileXOffset, tileYOffset,(int)layer] = null;
    }

    //Steps to perform each step when active
    public void Update()
    {
        //TryActivateObjects();
    }

    //Activate the chunks
    public void Activate()
    {
        recentlyActivated = true;
        if (active == false)
        {
            active = true;
            if (!hasLoadedData)
            {
                hasLoadedData = true;
                //ResetChunkData();
            }

            OnActivate();
            world.activeChunks.Add(this);
        }
        
        TryActivateObjects();
    }

    //Attempt to activate the objects within;
    public void TryActivateObjects()
    {
        List<LegacyGameObject> objectsIn = localObjects.ToList();
        //Activate all objects within;
        foreach (LegacyGameObject v in objectsIn)
        {
            if (!v.destroyed)
            {
                world.TryActivateGameObject(v);
            }
            else
            {
                localObjects.Remove(v);
            }
        }
    }

    //Deactivate the chunk
    public void Deactivate()
    {
        recentlyActivated = false;
        if (active)
        {
            active = false;
            if (world.activeChunks.Contains(this))
            {
                world.activeChunks.Remove(this);
            }
            OnDeactivate();
        }
    }

    //When activate is called
    public virtual void OnActivate()
    {
        //UGameLogic.LogMsg("Activate: tileX-" + tileX + " tileY-" + tileY);
    }

    //When deactivate is called;
    public virtual void OnDeactivate()
    {
       // UGameLogic.LogMsgUnique("Deactivate: chunkX " + chunkX + " chunkY " + chunkY);
    }


    

}
*/
