/*
package com.mygdx.game.Terrain;

import com.mobilecomputing.game.World;




//Tile object positions are basically instances of the tile in question
//at a given position;
public class TileObjectPosition
{
    
    
    //static int colliderAlt = 0;
    //private static AABB pointBB = new AABB(-2, -2, 4, 4);
    private static int lastUpdateId = 0;

    //INSTANCE VARIABLES
    //#region INSTANCE VARIABLES
        public int displayRandomSeed()
    {

            return tileY * 500 + tileX;
        
    }
    public Random displayRandom()
    {
            return new Random(displayRandomSeed);
        
    }

    public boolean _updateNeeded = false;

    public boolean getUpdateNeeded()
    {
    	return _updateNeeded;
    }
    
    public boolean setUpdateNeeded(boolean value){


            _updateNeeded = value;

    }
    //public TileEntity tileEntity = null;
    
    public void SetUpdateNeeded()
    {
        updateNeeded = true;
        if (world != null)
        {
            this.world.tileRendererAlt.MarkRedrawTilePosition(this);
            if (layer == World.TileLayer.MIDGROUND)
            {
                
                if (world.miniMap != null)
                {
                    this.world.miniMap.midgroundTilesToRedraw[tileX, tileY] = true;
                }
            }
        }
    }

    //POSITION AND DIMENSION VARIABLES
    //#region POSITION AND DIMENSION VARIABLES
    //Position in tile units in the world;
    public int tileX = 0;
    public int tileY = 0;
    //Position in the world;
    public int x() {return tileX * UGameLogic.tileWidth; }
    public int y() {return tileY * UGameLogic.tileWidth;}

    //Layer to which it belongs;
    public World.TileLayer layer;
    //The tile object it's linked to;
    //The chunk coordinates it belongs to;
    int chunkOffsetX;
    int chunkOffsetY;

    //The world it's attached to;
    private World world;
    public World getWorld(){
    	return world;
    }
    public void setWorld(World newWorld){
    	world=newWorld;
    }
    
    private TerrainChunk chunk;
    public TerrainChunk getChunk(){
    	return chunk;
    }
    //#endregion;


    //CACHED INSTANCE DATA SUBJECT TO CHANGE;
    //#region CACHED INSTANCE DATA SUBJECT TO CHANGE
    //Meta data;
    public int _metaData = 0;
    //Get and set the meta data;
    public int metaData()
    {
    	return _metaData;
    }
    
    public int getMetaData(){
    	return metaData();
    }
    
    public int setMetaData(){
    	

            int tileId = tileObject != null ? tileObject.id : 0;
            world.SetTileAndMeta(tileX, tileY, tileId, value, layer);
        
    }

    public TileObject _tileObject = null;
    public TileObject getTileObject()
    {
    	return _tileObject;
    }
    
    public TileObject setTileObject(TileObject value)
    {

            TileObject to = value;
            int tileId = to != null ? to.id : 0;
            world.SetTile(tileX, tileY, tileId, layer);
        
    }

    //The amount of damage it can take before being eliminated;
    //public float initHealthLeft = 4;
    //The percentage of the tile health that is recovered every second;
    //public float healPercentagePerSecond = 0.5f;
    //public float healthLeft = 4;
    //Health at points for terraforming
    //When a point is removed, the slope of the tile changes;
    public bool[] pointsAlive;

    //The tileObject's collider;
    public SimpleCollider collider = null;
    //The image name to be rendered;

    public TileRenderable altRenderable = null;
    public TileRenderData expectedRenderData
    {
        get
        {
            if (tileObject != null)
            {
                return tileObject.GetRenderData(layer);
            }
            return null;
        }
    }


    //The update id is set according to the lastUpdateCounter variable.
    //it is set according to the lastUpdateCounter when the tileObjectPosition updates; 
    public int updateId { get; private set;}
    //Useful for objects that need to know which tileObjectPositions have changed;
    //Last update counter. Incremented each time a position updates.
    public bool _lastSolidPointsOnSurface = true;
    public int _id_lastSolidPointsOnSurface = -1;
    #endregion;
#endregion;

    public bool damagedTop = false;
    public bool damagedRight = false;
    public bool damagedBottom = false;
    public bool damagedLeft = false;

    //Initialization;
    public TileObjectPosition(int tileX1, int tileY1, TileObject tileObject1, int metaData1, World world,World.TileLayer layer)
    {
        
        //Health at points for terraforming
        //When a point is removed, the slope of the tile changes;

        pointsAlive=new bool[TerraformTilePoints.order.Length];
        _tileObject = tileObject1;
        _metaData = metaData1;
        if (tileObject != null)
        {
            collider = tileObject.GetCollider(metaData);
        }
        tileX = tileX1;
        tileY = tileY1;
        this.world = world;
        this.layer = layer;

        //if(world!=null)
        //    UpdateState(tileObject1, metaData1);
    }

    //UPDATE STATE;
    #region UPDATE STATE;
    //Change the update id;
    public void RefreshUpdateId()
    {
        //Set the update id, that other objects may know that it has changed;
        updateId = lastUpdateId;
        lastUpdateId++;
    }


    //Return a list of all the points that are part of this tile object, that can be terraformed;
    public Boolean[] GetTerraformPointIndexes()
    {
        TileObject.Slopes mySlope = GetSlope();
        Boolean[] values = TileObject.GetTerrainPoints(mySlope);
        return values;

    }

    public bool ContainsTerraformPoint(int pointId)
    {
        return GetTerraformPointIndexes()[pointId];
    }

    //Update the information in the TileObjectPosition based on the change in tile and meta data;
    public void UpdateState(TileObject tileObject1,int metaData1)
    {

        shouldUpdateBorderData = true;
        collider = null;
        RefreshUpdateId();
        _metaData = metaData1;
        bool[] pointsActive = GetTerraformPointIndexes();
        //Heal damage to points
        if (tileObject1!=null && tileObject1 != tileObject)
        {

            //healthLeft = tileObject1.maxHealth;

            //Initialize health at each of the points
            for (int i = 0; i < pointsAlive.Length; i++)
            {
                if (pointsActive[i])
                {
                    pointsAlive[i] = true;
                }
                else
                {
                    pointsAlive[i] = false;
                }

            }
        }
        _tileObject = tileObject1;
        //Remove the tile entity;
        if(tileEntity!=null && tileEntity.linkedTileObject !=tileObject){
            tileEntity.InstanceDestroy();
        }
        //Has the tile been deleted?
        if (tileObject==null)
        {
            //Initialize health at each of the points to zero
            for (int i = 0; i < pointsAlive.Length; i++)
            {
                pointsAlive[i] = false;
            }
            if (chunk != null)
                chunk.ClearTileObjectAtPosition(chunkOffsetX,chunkOffsetY,layer);
            return;
        }
        else{
            //Recreate the relevant tile entity
            if(tileEntity==null || tileEntity.destroyed){
                tileEntity = tileObject.GenerateTileEntity(this);
            }
        }
        if(tileObject!=null)
        collider = tileObject.GetCollider(metaData);

    }

    //Update the information in the TileObjectPosition based on the change in tile and meta data;

    public void FullUpdate()
    {

        updateNeeded = false;

        //Get the information from the world;
        TileObject tileObject=world.GetTileAtPosition(tileX,tileY,layer,true);

        int metaData1 = world.GetMetaDataAtPosition(tileX, tileY, layer, true);
        //Call the method above
        UpdateState(tileObject, metaData1);
        if (tileObject != null)
        {
            altRenderable = tileObject.GetRenderable(this);
            tileObject.OnFullUpdate(this);
        }
        else
        {
            altRenderable = null;
        }

        this.world.tileRendererAlt.MarkRedrawTilePosition(this);

    }
    #endregion;

    //Make sure the TileObjectPosition knows its location within the chunk;
    public void SetChunk(TerrainChunk newChunk,int chunkOffsetX0,int chunkOffsetY0,World.TileLayer layer)
    {
        chunk = newChunk;
        chunkOffsetX = chunkOffsetX0;
        chunkOffsetY = chunkOffsetY0;
        this.layer = layer;
    }

    public static bool drawEdgesAndPoints = false;


    //RENDERING
    #region RENDERING
    //Render the tile object position;
    public void Render(int tx = UGameLogic.UNSET_INT, int ty = UGameLogic.UNSET_INT)
    {

        SpriteImageData.ResetProperties();
        if (tx < 0)
            tx = tileX;
        if (ty < 0)
            ty = tileY;


        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                TileObjectPosition to = world.GetTileDataAtPosition(tileX + i, tileY + j, layer, true,false);
                if (to != null && to.altRenderable != null)
                {

                    //to.altRenderable.RenderPart(tx, ty, (int)TileRenderData.SubImage.BASE);
                    to.altRenderable.RenderPartsOnAllLayers(tx, ty, -i, -j);
                }
            }
        }
        if(altRenderable!=null)
        {
            //altRenderable.RenderPartsOnAllLayers(tx, ty);
        }
    }

    public void OldRender(int tx = UGameLogic.UNSET_INT, int ty = UGameLogic.UNSET_INT)
    {
        SpriteImageData.ResetProperties();
        if (tx < 0)
            tx = tileX;
        if (ty < 0)
            ty = tileY;
        TileRenderable posTop = RenderableTop;
        TileRenderable posRight = RenderableRight;
        TileRenderable posBottom = RenderableBottom;
        TileRenderable posLeft = RenderableLeft;


        bool haxOnOverlap = altRenderable != null && GetSlope()==TileObject.Slopes.FULL;
        bool hr = altRenderable != null;
        
        if (posRight != null)
        {
            posRight.RenderPart(tx, ty, (int)TileRenderData.SubImage.LEFT, -1, 0);
        }
        if (posLeft != null)
            posLeft.RenderPart(tx, ty, (int)TileRenderData.SubImage.RIGHT, 1, 0);
        if (posBottom != null)
            posBottom.RenderPart(tx, ty, (int)TileRenderData.SubImage.TOP, 0, -1);
        if (posTop != null)
            posTop.RenderPart(tx, ty, (int)TileRenderData.SubImage.BOTTOM, 0, 1);

        //Center
        if (hr)
            altRenderable.RenderPart(tx, ty);

        //Right
        if (posRight != null && haxOnOverlap)
            posRight.RenderPart(tx, ty, (int)TileRenderData.SubImage.RIGHT, -1, 0);
        if (hr)
            altRenderable.RenderPart(tx, ty, (int)TileRenderData.SubImage.RIGHT);
        //Left
        if (hr)
            altRenderable.RenderPart(tx, ty, (int)TileRenderData.SubImage.LEFT);
        if (posLeft != null && haxOnOverlap)
            posLeft.RenderPart(tx, ty, (int)TileRenderData.SubImage.LEFT, 1, 0);
        //BOTTOM
        if (posBottom != null && haxOnOverlap)
            posBottom.RenderPart(tx, ty, (int)TileRenderData.SubImage.BOTTOM, 0, -1);
        if (hr)
            altRenderable.RenderPart(tx, ty, (int)TileRenderData.SubImage.BOTTOM);
        //TOP
        if (hr)
            altRenderable.RenderPart(tx, ty, (int)TileRenderData.SubImage.TOP);
        if (posTop != null && haxOnOverlap)
            posTop.RenderPart(tx, ty, (int)TileRenderData.SubImage.TOP, 0, 1);
        //Draw the outline
        if (layer == World.TileLayer.MIDGROUND && collider != null)
        {

            List<Point2D> possibleSurfacePoints = GetPossibleSurfacePointsToReturn();
            if(drawEdgesAndPoints){
                List<CartesianLine> lines = GetSolidEdges();
                if (lines != null)
                {
                    foreach (CartesianLine line in lines)
                    {
                        ViewableObject.DrawBetweenLine(line, -x + tx * UGameLogic.tileWidth, -y + ty * UGameLogic.tileWidth, SFML.Graphics.Color.Red);
                        ViewableObject.DrawBetweenLine(line, -x + tx * UGameLogic.tileWidth - 1, -y + ty * UGameLogic.tileWidth + 1, SFML.Graphics.Color.Red);


                        //pointBB.DrawOutline((int)(line.startX) - x + tx * UGameLogic.tileWidth, (int)(line.startY) - y + ty * UGameLogic.tileWidth, SFML.Graphics.Color.Blue);
                        //pointBB.DrawOutline((int)(line.endX) - x + tx * UGameLogic.tileWidth, (int)(line.endY) - y + ty * UGameLogic.tileWidth, SFML.Graphics.Color.Blue);
                    }
                    foreach (Point2D point in GetSolidPointsAtSurface())
                    {
                        if (world.IsSolidPointInMatrixProtruding(point))
                        {

                            pointBB.DrawOutline((int)(point.x) - x + tx * UGameLogic.tileWidth, (int)(point.y) - y + ty * UGameLogic.tileWidth, SFML.Graphics.Color.Blue);
                        }
                    }
                }
            }
        }
    }


    public void DrawOutline(int tx = UGameLogic.UNSET_INT, int ty = UGameLogic.UNSET_INT)
    {
        SpriteImageData.ResetProperties();
        if (tx < 0)
            tx = tileX;
        if (ty < 0)
            ty = tileY;
        //Draw the outline
        if (layer == World.TileLayer.MIDGROUND && collider != null)
        {

            List<Point2D> possibleSurfacePoints = GetPossibleSurfacePointsToReturn();
            if (true)
            {
                List<CartesianLine> lines = GetSolidEdges();
                if (lines != null)
                {
                    foreach (CartesianLine line in lines)
                    {
                        SFML.Graphics.Color color = new SFML.Graphics.Color(128, 64, 0);
                        ViewableObject.DrawBetweenLine(line, -x + tx * UGameLogic.tileWidth, -y + ty * UGameLogic.tileWidth, color);
                        ViewableObject.DrawBetweenLine(line, -x + tx * UGameLogic.tileWidth - 1, -y + ty * UGameLogic.tileWidth + 1, color);


                        //pointBB.DrawOutline((int)(line.startX) - x + tx * UGameLogic.tileWidth, (int)(line.startY) - y + ty * UGameLogic.tileWidth, SFML.Graphics.Color.Blue);
                        //pointBB.DrawOutline((int)(line.endX) - x + tx * UGameLogic.tileWidth, (int)(line.endY) - y + ty * UGameLogic.tileWidth, SFML.Graphics.Color.Blue);
                    }
                    if (TileObjectPosition.drawEdgesAndPoints)
                    {
                        foreach (Point2D point in GetSolidPointsAtSurface())
                        {
                            if (world.IsSolidPointInMatrixProtruding(point))
                            {

                                pointBB.DrawOutline((int)(point.x) - x + tx * UGameLogic.tileWidth, (int)(point.y) - y + ty * UGameLogic.tileWidth, SFML.Graphics.Color.Blue);
                            }
                        }
                    }
                    
                }
            }
        }
    }

    //Get the renderables;
    #region GET RENDERABLES

    //Get the renderables of the current tile;
    public TileRenderable GetAltRenderable()
    {

            if (tileObject == null)
                altRenderable = null;
            else if (altRenderable == null)
            {
                altRenderable = tileObject.GetRenderable(this);
            }
            return altRenderable;

    }


    //Get the renderable of the tile above;
    public TileRenderable RenderableTop
    {
        get
        {
            TileObjectPosition to = world.GetTileDataAtPosition(tileX, tileY-1, layer, true);
            if (to != null && to.altRenderable != null)
                return to.altRenderable;
            return null;
        }
    }

    //Get the renderable of the right tile;
    public TileRenderable RenderableRight
    {
        get
        {
            TileObjectPosition to = world.GetTileDataAtPosition(tileX+1, tileY, layer, true);
            if (to != null && to.altRenderable != null)
                return to.altRenderable;
            return null;
        }
    }

    //Get the renderable of the bottom tile;
    public TileRenderable RenderableBottom
    {
        get
        {
            TileObjectPosition to = world.GetTileDataAtPosition(tileX, tileY + 1, layer, true);
            if (to != null && to.altRenderable != null)
                return to.altRenderable;
            return null;
        }
    }


    //Get the renderable of the right tile;
    public TileRenderable RenderableLeft
    {
        get
        {
            TileObjectPosition to = world.GetTileDataAtPosition(tileX - 1, tileY, layer, true);
            if (to != null && to.altRenderable != null)
                return to.altRenderable;
            return null;
        }
    }
    #endregion;
    #endregion;

    //DATA ABOUT SOLID BORDER EDGES AND POINTS;
    #region DATA ABOUT SOLID BORDER EDGES AND POINTS;
    public bool NoSolidPointsOnSurface()
    {
        if (!IsSolid)
        {
            return true;
        }
        if (_id_lastSolidPointsOnSurface == lastUpdateId)
        {
            return _lastSolidPointsOnSurface;
        }
        _id_lastSolidPointsOnSurface = lastUpdateId;


        _lastSolidPointsOnSurface = FullSolidBlock(this) && FullSolidBlock(this.GetTopPos()) && FullSolidBlock(this.GetRightPos()) && FullSolidBlock(this.GetBottomPos()) && FullSolidBlock(this.GetLeftPos());
        return _lastSolidPointsOnSurface;
    }



    //Get the points present in the world
    public List<Point2D> GetPointsInWorld()
    {
        List<Point2D> pointsToReturn = new List<Point2D>();
        Boolean[] originalPoints = GetTerraformPointIndexes();
        for(int i=0;i<originalPoints.Length;i++){
            if(originalPoints[i]){
                pointsToReturn.Add(WorldlyPointAtIndex(i));
            }
        }
        return pointsToReturn;
    }

    //Return a given point in the world at a given index; 
    public Point2D WorldlyPointAtIndex(int i){
        Point2D point = TileObject.TERRAFORM_POINT_COORDINATES[i];
        return world.GetPointRoundedDown(x + point.x, y + point.y);
    }



    public List<Point2D> GetPointsInWorldClockwise(bool cornersOnly)
    {
        List<Point2D> pointsToReturn = new List<Point2D>();
        Boolean[] originalPoints = GetTerraformPointIndexes();
        foreach (int j in TerraformTilePoints.clockwiseOrder)
        {
            if (originalPoints[j])
            {
                if (cornersOnly)
                {
                    if (j == 1 && originalPoints[0] && originalPoints[2])
                    {
                        continue;
                    }
                    if (j == 3 && originalPoints[0] && originalPoints[5])
                    {
                        continue;
                    }
                    if (j == 4 && originalPoints[2] && originalPoints[7])
                    {
                        continue;
                    }
                    if (j == 6 && originalPoints[5] && originalPoints[7])
                    {
                        continue;
                    }
                }
                //Point2D point = TileObject.TERRAFORM_POINT_COORDINATES[j];
                pointsToReturn.Add(WorldlyPointAtIndex(j));
            }
        }
        return pointsToReturn;
    }


    //Possible Surface points;
    public List<Point2D> GetPossibleSurfacePointsToReturn()
    {
        if (!NoSolidPointsOnSurface())
        {
            return GetPointsInWorldClockwise(true);
        }
        return null;
    }

    private List<CartesianLine> _edgesOfExposedSolidBorder = null;
    private List<Point2D> _pointsOfExposedSolidBorder = null;
    bool shouldUpdateBorderData = true;
    //Update the data about the solid points at the surface (if the surface is visible); 
    private void UpdateSolidBorderData()
    {
        //No need to proceed if everything is in order;
        if (!shouldUpdateBorderData)
        {
            return;
        }
        shouldUpdateBorderData = false;
        //Get all the points on the surface;
        List<Point2D> possibleSurfacePoints = GetPossibleSurfacePointsToReturn();
        //No points found so return;
        if (possibleSurfacePoints == null || collider==null)
        {
            _edgesOfExposedSolidBorder = null;
            _pointsOfExposedSolidBorder = null;
            return;
        }
        //Start setting aside space for them;
        _edgesOfExposedSolidBorder = new List<CartesianLine>();
        _pointsOfExposedSolidBorder = new List<Point2D>();
        //Surrounding positions to check;
        TileObjectPosition[] positionsToCheck = { GetTopPos(), GetRightPos(), GetBottomPos(), GetLeftPos() };
        for (int i = 0; i < possibleSurfacePoints.Count(); i++)
        {
            //point and next point;
            Point2D point = possibleSurfacePoints[i];
            int nextPointIndex = 0;
            if (i + 1 < possibleSurfacePoints.Count)
            {
                nextPointIndex = i + 1;
            }
            Point2D nextPoint = possibleSurfacePoints[nextPointIndex];
            bool pairingFailed = false;
            //Check the surrounding tile positions;
            foreach (TileObjectPosition to in positionsToCheck)
            {

                if (IsSolidStatic(to))
                {
                    List<Point2D> possibleSurfacePointsOther = to.GetPointsInWorldClockwise(true);
                    if (possibleSurfacePointsOther != null && possibleSurfacePointsOther.Contains(point) && possibleSurfacePointsOther.Contains(nextPoint))
                    {
                        pairingFailed = true;
                        continue;
                    }
                }
            }

            if (pairingFailed)
            {
                continue;
            }

            //UGameLogic.LogMsg("Slope " + this.GetSlope());

           // UGameLogic.LogMsg("next point index " +nextPointIndex);
            _edgesOfExposedSolidBorder.Add(new CartesianLine(point.x, point.y, nextPoint.x, nextPoint.y));
            if(!_pointsOfExposedSolidBorder.Contains(point))
                _pointsOfExposedSolidBorder.Add(point);
            if (!_pointsOfExposedSolidBorder.Contains(nextPoint))
                _pointsOfExposedSolidBorder.Add(nextPoint);
        }
    }

    public List<Point2D> GetSolidPointsAtSurface()
    {
         UpdateSolidBorderData();
         return _pointsOfExposedSolidBorder;
    }

    //Get the solid edges in the world;
    public List<CartesianLine> GetSolidEdges()
    {
        UpdateSolidBorderData();
        return _edgesOfExposedSolidBorder;
    }
    #endregion;

    //TERRAFORMING FUNCTIONS
    #region TERRAFORMING
    //Return points covered by the collider;
    public List<int> GetPointsCovered(int collider_xOffset, int collider_yOffset, SimpleCollider collider = null)
    {
        int u = UGameLogic.tileWidth;
        //Construct a list for the point indexes
        List<int> pointIndexes = new List<int>();
        for (int p = 0; p < TileObject.TERRAFORM_POINT_COORDINATES.Length; p++)
        {
            Point2D point = TileObject.TERRAFORM_POINT_COORDINATES[p];

                float px = point.x;
                float py = point.y;
                int margin = 3;
                if (collider as HitCircle != null)
                    margin = 0;
                bool cornerPiece = false;
                if (px > u - margin)
                {
                    cornerPiece = true;
                    px = u - margin;
                }
                else if (px < margin)
                {
                    cornerPiece = true;
                    px = margin;
                }
                if (py > u - margin)
                {
                    cornerPiece = true;
                    py = u - margin;
                }
                else if (py < margin)
                {
                    cornerPiece = true;
                    py = margin;
                }

                point = new Point2D(px, py);


                bool touching = (collider == null);
                if (!touching)
                {
                    if (cornerPiece && (collider as HitCircle==null))
                    {
                        touching = collider.CheckOffsetTouching(collider_xOffset, collider_yOffset, pointBB, point.x + x, point.y + y, false);
                    }
                    else
                    {
                    
                        touching = collider.ContainsPoint((int)point.x+x, (int)point.y+y, collider_xOffset, collider_yOffset);
                    }
                    //touching = true;
                    //touching = collider.ContainsPoint((int)point.x+x, (int)point.y+y, collider_xOffset, collider_yOffset);
                    //touching = collider.CheckOffsetTouching(collider_xOffset, collider_yOffset, pointBB, point.x + x, point.y + y, false);
                }
            if (touching)
            {
                pointIndexes.Add(p);
            }
        }
        return pointIndexes;
    }

    //ADDITION
    #region ADDITION
    //Add to the tilePosition
    public Boolean AddToTile(int collider_xOffset, int collider_yOffset, SimpleCollider collider, TileObject to)
    {

        if (to == null){
            if (this.tileObject == null)
                return false;
            else
                to = this.tileObject;
        }
        List<int> pointIndexes = GetPointsCovered(collider_xOffset, collider_yOffset, collider);
        //Now damage the tile according to the points that the collider touched;
        return AddToTile(pointIndexes, tileObject);
    }


    //Replace part of a tile
    //Replace part of a tile
    public Boolean AddToTile(int collider_xOffset, int collider_yOffset, SimpleCollider collider, int tileId = UGameLogic.UNSET_INT)
    {
        //Default to current tile if tileId is not specified;
        if (tileId < 0)
        {
            if (tileObject == null)
                return false;
        }
        else
        {*/
            /*
            TileObject newTileObject = TileObject.tileIds[tileId];
            if (newTileObject != null)
            {
                tileObject = newTileObject;
            }
            else
            {
                return false;
            }
             */
        /*}
        
        List<int> pointIndexes = GetPointsCovered(collider_xOffset, collider_yOffset, collider);
        //Now damage the tile according to the points that the collider touched;
        return AddToTile(pointIndexes, tileId);
    }


    //Add to a given tile;
    public Boolean AddToTile(IEnumerable<int> pointsToAdd, int tileId)
    {
        return AddToTile(pointsToAdd,TileObject.tileIds[tileId]);
    }

    //Add points to a given tile;
    public Boolean AddToTile(IEnumerable<int> pointsToAdd,TileObject to)
    {
        ResetDamaged();
         Boolean[] newPoints;
         Boolean[] tempArray = new Boolean[8];
         if (tileObject == null || tileObject!=to)
         {

             for (int c = 0; c < tempArray.Length; c++)
             {
                 tempArray[c] = false;
             }
             newPoints = TerraformTilePoints.TryAddPoints(tempArray, pointsToAdd,to.terraformType);
         }
         else
             newPoints = TerraformTilePoints.TryAddPoints(GetTerraformPointIndexes(), pointsToAdd,to.terraformType); ;
        if (pointsToAdd.Count() < 1)
            return false;
        int nextSlopeId = TileObject.GetSlopeIdFromTerraformPointData(newPoints);
        if (nextSlopeId >= 0)
        {
            if (to != null)
                tileObject = to;
            metaData = nextSlopeId;

        }
        return true;
    }
    #endregion;
    
    //SUBTRACTION AND DAMAGE
    #region SUBTRACTION AND DAMAGE
    //Clear this tile;

    public void ClearTile()
    {
        ResetDamaged();
        world.SetTile(tileX, tileY, 0, layer);
    }

    public void ResetDamaged()
    {
        damagedTop = false;
        damagedRight = false;
        damagedBottom = false;
        damagedLeft = false;
    }

    //Get the points the collider has touched, and damage them;
    public Boolean DamageTile(int collider_xOffset, int collider_yOffset, int amount, SimpleCollider collider)
    {

        List<int> pointIndexes = GetPointsCovered(collider_xOffset, collider_yOffset, collider);
        //Now damage the tile according to the points that the collider touched;
        return DamageTile(pointIndexes, amount, true);
    }


    //Damage set points in the tile;
    public Boolean DamageTile(IEnumerable<int> pointIndexes, int amount, Boolean spreadDamage = true)
    {
        if (pointIndexes.Count() < 1)
            return false;
        Boolean[] originalPoints = GetTerraformPointIndexes();

       
        if (tileObject == null)
        {
            return false;
        }
        List<int> pointsToRemove = TerraformTilePoints.GetPointsToRemove(originalPoints, pointIndexes, tileObject.terraformType);
        //Can the terrain piece be removed yet?
        Boolean canRemoveNow = true;

        //For each of the given points;
        //UGameLogic.LogMsg("\n pointsToRemove: ");
        foreach (int pointIndex in pointsToRemove)
        {
            pointsAlive[pointIndex] = false;

        }
        //Print out the values;

        //Should the tile be changed now?
        Boolean tileCleared=false;
        if (canRemoveNow)
        {
            Boolean[] newPoints = TerraformTilePoints.tryRemovePoints(originalPoints, pointsToRemove,tileObject.terraformType);
            int nextSlopeId = TileObject.GetSlopeIdFromTerraformPointData(newPoints);
            if (nextSlopeId >= 0)
            {
                metaData = nextSlopeId;
            }
            else
            {
                tileCleared=true;
                ClearTile();
            }
        }
        //Did you successfully damage the tile;
        if (pointsToRemove.Count > 0)
        {
            TileObject.Slopes slope=GetSlope();
            TileObjectPosition top = GetTopPos();
            TileObjectPosition left = GetLeftPos();
            TileObjectPosition bottom = GetBottomPos();
            TileObjectPosition right = GetRightPos();
            switch(slope){
                case TileObject.Slopes.D45_2:
                    damagedLeft = true;
                    damagedTop = true;
                    break;
                case TileObject.Slopes.D135_2:
                    damagedRight = true;
                    damagedTop = true;
                break;
                case TileObject.Slopes.D225_2:
                    damagedRight = true;
                    damagedBottom = true;
                break;
                case TileObject.Slopes.D315_2:
                    damagedBottom = true;
                    damagedLeft = true;
                
                break;
            }
            if ((tileCleared || damagedTop) && (top!=null && top.tileObject!=null))
            {
                top.damagedBottom = true;
            }
            if ((tileCleared || damagedLeft) && (left!=null && left.tileObject!=null))
            {
                left.damagedRight = true;
            }
            if ((tileCleared || damagedBottom) && (bottom!=null && bottom.tileObject!=null))
            {
                bottom.damagedTop = true;
            }
            if ((tileCleared || damagedRight) && (right!=null && right.tileObject!=null))
            {
                right.damagedLeft = true;
            }

            return true;
        }
        return false;
    }

    #endregion;
    #endregion;

    //GETTERS
    #region GETTERS;

    //GET SLOPES or SLANTS
    #region GET SLOPES OR SLANTS;
    //Get the slope object of the TileObjectPosition
    public TileObject.Slopes GetSlope()
    {

        return (TileObject.Slopes)(metaData & 15);
    }


    public int GetSlantInQuad(QuadrantEnum quad)
    {
        TileObject.Slopes slope = this.GetSlope();
        return TileObject.GetSlantInQuad(quad, slope);
    }
    #endregion;

    //GET RELATIVE POSITIONS;
    #region GET POSITIONS
    //Get the top right position;
    public TileObjectPosition GetTopRightPos()
    {
        if (world != null)
        {
            return world.GetTileDataAtPosition(tileX + 1, tileY - 1, this.layer, true,false);
        }
        return null;
    }

    //Get the bottom right position;
    public TileObjectPosition GetBottomRightPos()
    {
        if (world != null)
        {
            return world.GetTileDataAtPosition(tileX + 1, tileY + 1, this.layer, true,false);
        }
        return null;
    }


    //Get the bottom left position;
    public TileObjectPosition GetBottomLeftPos()
    {
        if (world != null)
        {
            return world.GetTileDataAtPosition(tileX - 1, tileY + 1, this.layer, true,false);
        }
        return null;
    }


    //Get the top left position;
    public TileObjectPosition GetTopLeftPos()
    {
        if (world != null)
        {
            return world.GetTileDataAtPosition(tileX - 1, tileY - 1, this.layer, true,false);
        }
        return null;
    }

    //Get the tileObject position above;
    public TileObjectPosition GetTopPos()
    {
        if (world != null)
        {
            return world.GetTileDataAtPosition(tileX, tileY - 1, this.layer, true,false);
        }
        return null;
    }



    //Get the tileObject position on the right;
    public TileObjectPosition GetRightPos()
    {
        if (world != null)
        {
            return world.GetTileDataAtPosition(tileX + 1, tileY, this.layer, true,false);
        }
        return null;
    }



    //Get the tileObject position below;
    public TileObjectPosition GetBottomPos()
    {
        if (world != null)
        {
            return world.GetTileDataAtPosition(tileX, tileY + 1, this.layer, true,false);
        }
        return null;
    }
    //Get the tileObject position on the left;
    public TileObjectPosition GetLeftPos()
    {
        if (world != null)
        {
            return world.GetTileDataAtPosition(tileX - 1, tileY, this.layer, true,false);
        }
        return null;
    }
    #endregion;

    //Check if the tile object position is in the background;
    public Boolean InBackground()
    {

        if (chunk != null)
        {
            foreach (World.TileLayer l in RendererOfTiles.backgroundLayers)
            {
                if (layer == l)
                {
                    return true;
                }
            }
        }
        return false;
    }

    //Check if the tile object position is in the background;
    public Boolean InMidground()
    {

        if (chunk != null)
        {
            foreach (World.TileLayer l in RendererOfTiles.midgroundLayers)
            {
                if (layer == l)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public TileObjectPosition[] GetAdjPosList()
    {
        TileObjectPosition[] posList = new TileObjectPosition[Enum.GetValues(typeof(pN)).Length];

        foreach (pN relPos in Enum.GetValues(typeof(pN)))
        {
            int xo = 0;
            int yo = 0;
            TileObjectPosition posScanned = null;
            //Find the offset;
            if (relPos == pN.UL || relPos == pN.UM || relPos == pN.UR)
            {
                yo = -1;
            }
            if (relPos == pN.BL || relPos == pN.BM || relPos == pN.BR)
            {
                yo = 1;
            }
            if (relPos == pN.UL || relPos == pN.ML || relPos == pN.BL)
            {
                xo = -1;
            }
            if (relPos == pN.UR || relPos == pN.MR || relPos == pN.BR)
            {
                xo = 1;
            }
            if (world != null)
            {
                posScanned = world.GetTileDataAtPosition(tileX + xo, tileY + yo, layer, true);
            }
            posList[(int)relPos] = posScanned;
        }

        return posList;

    }

    public static Boolean IsSolidStatic(TileObjectPosition to)
    {
        return to != null && to.IsSolid;
    }

    public Boolean IsSolid
    {
        get {
            return this != null && this.tileObject != null && tileObject as JumpThroughPlatformTile == null && tileObject as SpikesTile == null && tileObject as SandTile == null;
        }
    }

    public static Boolean FullSolidBlock(TileObjectPosition to)
    {

        if (to != null && IsSolidStatic(to) && to.tileObject.PercentBlockFilled(to.metaData) == 1)
        {
            return true;
        }
        return false;
    }
    //Check if the slope attached to the tile has a given restriction;
    public Boolean CheckRestriction(TileObject.SIDE_RESTRICTIONS restriction)
    {
        if(tileObject==null)
            return false;
        if (TileObject.SlopeHasRestriction(GetSlope(), restriction))
            return true;
        return false;
    }

    //Is this tileposition blocking to a specific object
    public Boolean IsBlockingTo(LegacyGameObject o)
    {
        return (tileObject != null && tileObject.IsBlockingToo(o));
    }

    public Boolean ConcealsBorders()
    {
        if (tileObject != null && tileObject.ConcealsBorders())
        {
            return true;
        }
        return false;
    }

    //Return the collider pertaining to the tileobject and slope this position corresponds to;
    public SimpleCollider GetCollider()
    {
        return collider;
        
        if (tileObject == null)
            return null;
        else
            return TileObject.GetColliderFromSlope(GetSlope());
         
    }

    public override string ToString()
    {
        return ("{\n tileObject: "+tileObject+",\n"+"tileId:"+",\n tileCoords: ["+tileX+","+tileY+"]\n meta="+metaData+"\n}");
    }


    #endregion;






}
*/

