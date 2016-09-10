package com.mobilecomputing.game.OldHitDetection;

import java.util.ArrayList;

import com.mobilecomputing.game.UGameLogic;

import helperDataStructures.*;


public abstract class SimpleCollider
{
    public float GetArea()
    {
        ArrayList<Point2D> points = GetPoints();
        if (points.size() > 1)
        {
            
            int counterX = 0;
            int counterY = 1;
            float area = 0;
            //UGameLogic.LogMsg("");
            for (counterX = 0; counterX < points.size(); counterX++)
            {
                //UGameLogic.LogMsg("Point " + points[counterX]);
                counterY = counterX + 1;
                if (counterY >= points.size())
                {
                    counterY = 0;
                }

                area += (points.get(counterX).x * points.get(counterY).y);
                area -= (points.get(counterY).x * points.get(counterX).y);
            }
            area /= 2;
            return area;
        }
        return 0;
    }

    //Position of the collider;
    public int xMin;
    public int yMin;
    public int xMax;
    public int yMax;
    public int rotation;
    public void SetRotation(int newRotation){
    	
    }

    public abstract float FirstX();

    public abstract float FirstY();


    //The radius of the object;
    public int get_hitRadius()
    {

            float w = GetBoundWidth();
            float h = GetBoundHeight();
            return (int)Math.ceil((Math.sqrt(w * w + h * h)) / 2);

    }
    //All subclasses must call this method;
    public SimpleCollider(int x,int y,int rotation){
        this.xMin = x;
        this.yMin = y;
        this.rotation = rotation;
    }

    //Return dimensions;
    public abstract int GetBoundWidth();
    public abstract int GetBoundHeight();


    //Usually they're the same except with regaurds to rotation
    public int GetWidth()
    {
        return GetBoundWidth();
    }

    //Get the height of the collider;
    public int GetHeight()
    {
        return GetBoundHeight();
    }



    //Return the list of edges;
    public ArrayList<CartesianLine> GetEdges(int xOffset,int yOffset)
    {
        return GetEdges(GetPoints(xOffset,yOffset));
    }

    /*
    public void GetExpandedBounds(out int minX,out int minY,out int maxX,out int maxY){

        minX = xMin;
            minY = yMin;
            maxX = xMax;
            maxY = yMax;


    }
    */




    //Return the list of edges;
    public ArrayList<CartesianLine> GetEdges(ArrayList<Point2D> points){
        ArrayList<CartesianLine> lines=new ArrayList<CartesianLine>();
        for (int i = 0; i < points.size(); i++)
        {
            Point2D point1;
            Point2D point2;
            if (i == 0)
            {
                point1 = points.get(points.size() - 1);
                point2 = points.get(0);
            }
            else
            {
                point1 = points.get(i-1);
                point2 = points.get(i);
            }

            lines.add(new CartesianLine(point1.x, point1.y, point2.x, point2.y));
        }
        return lines;
    }
    
    //Return if a given point is inside the bounds;
    public boolean PointInside(float x1,float y1)
    {
        ArrayList<Point2D> points = GetPoints();
        return PointInside(x1,y1,points,GetEdges(points),0,0);
    }
    //Return a list of vertices for the shape;
    public ArrayList<Point2D> GetPoints(){
    	return GetPoints(0,0);
    }
    
    public abstract ArrayList<Point2D> GetPoints(int xOffset,int yOffset);
    //Check that a point is inside the shape;
    public abstract boolean PointInside(float x1, float y1,ArrayList<Point2D> points,ArrayList<CartesianLine> edges,float xo,float yo);


    //Return the center of the shape;
    public Point2D GetFirstPointInside(int xOffset,int yOffset)
    {
        ArrayList<Point2D> points = GetPoints(xOffset, yOffset);
        return GetFirstPointInside(points);
    }

    //Return a point in the center of the shape;
    public Point2D GetFirstPointInside(ArrayList<Point2D> points)
    {       
        float sumX=0;
        float sumY=0;
        int count=0;
        Point2D pointToReturn = new Point2D(0, 0);

        for (Point2D point : points)
        {
            
            count = count + 1;
            sumX = sumX + point.x;
            sumY = sumY + point.y;

        }

        if (count > 0)
        {

            pointToReturn.x = sumX / count;
            pointToReturn.y = sumY / count;


        }
        return pointToReturn;

    }

    //The amount in which this collider penetrates the next;
    public int PenetrationAmount(float xo1, float yo1, SimpleCollider sc2, float xo2, float yo2,double dir)
    {
        
        if(sc2==null || !CheckOffsetTouching(xo1,yo1,sc2,xo2,yo2,true)){
            return 0;
        }
        int max = (int)Math.ceil((double)(sc2.get_hitRadius()*2 + this.get_hitRadius()*2));
        int min = 0;
            //(int)Math.Floor(UGameLogic.GetDistanceBetween(xo1,yo1,xo2,yo2);
        dir = UGameLogic.PMod(dir + 180, 360);
        double dirR=UGameLogic.TrueBearingsToRadians(dir);
        int low = min;
        int high = max;
        int lastVal = min;
        while (low + 1 < high)
        {
            lastVal = (low + high) / 2;
            float xo3 = xo1 + (float)(lastVal * Math.cos(dirR));
            float yo3 = yo1 + (float)(lastVal* -Math.sin(dirR));
            if (CheckOffsetTouching(xo3, yo3, sc2, xo2, yo2, true))
            {

                low = (low + high) / 2;
            }
            else
            {
                high = (low + high) / 2;
            }
            //UGameLogic.LogMsg("low " + low +" high"+high+"\n");
        }
        //UGameLogic.LogMsg("Last Value " + lastVal+"\n");


        return lastVal;
    }

    public boolean CheckOffsetTouching(float xo1, float yo1, SimpleCollider sc2, float xo2, float yo2, int margin)
    {

        return do_CheckOffsetTouching((int)(xo1), (int)(yo1), sc2, (int)(xo2), (int)(yo2), margin);
        //return do_CheckOffsetTouching((int)(xo1-xo2), (int)(yo1-yo2), sc2, 0, 0, useMargin);
    }


    public boolean CheckOffsetTouching(float xo1, float yo1, SimpleCollider sc2, float xo2, float yo2, boolean useMargin)
    {
        int margin = 0;
        if (useMargin)
        {
            margin = 2;
        }
        return do_CheckOffsetTouching((int)xo1, (int)yo1, sc2, (int)xo2, (int)yo2, margin);
    }

    //Check if two hit shapes are touching
    public boolean do_CheckOffsetTouching(int xo1,int yo1,SimpleCollider sc2,int xo2,int yo2,int margin){

        if (sc2 == null)
            return false;

        //Special case both circles;
        if (this instanceof HitCircle_Unfinished && sc2 instanceof HitCircle_Unfinished){
            //Check they are within distance;
            float nx1 = FirstX() + xo1;
            float ny1 = FirstY() + yo1;
            float nx2 = sc2.FirstX() + xo2;
            float ny2 = sc2.FirstY() + yo2;
            double dist1 = Math.sqrt((nx1 - nx2) * (nx1 - nx2) + (ny1 - ny2) * (ny1 - ny2));
            double distTolerance = (this.get_hitRadius() + sc2.get_hitRadius());
            if (margin>0){
                distTolerance = distTolerance + margin;
            }
            if (dist1 <= distTolerance){
                return true;
            }
        }

        //Normal case, both polygons;
        int x1 = this.xMin + xo1;
        int y1 = this.yMin + yo1;


        float r1 = this.xMax+xo1;
        float b1 = this.yMax+yo1;
        int su = UGameLogic.smallestUnit;


        if (margin>0)
        {
            x1 -= margin;
            y1 -= margin;
            r1 += margin;
            b1 += margin;
        }



        int x2 = sc2.xMin + xo2;
        int y2 = sc2.yMin + yo2;


        float r2 = sc2.xMax + xo2;

        float b2 = sc2.yMax + yo2;
        /*
        r1 -= 0.5f;
        r2 -= 0.5f;
        b1 -= 0.5f;
        b2 -= 0.5f;
        */

        //|| (y2+h2>=y1 && y2+h2<=y1+h1)

            //Check y's are in range of each other
            if (!((y1 >= y2 && y1 <= b2) || (b1 >= y2 && b1 <= b2) || (y2 >= y1 && y2 <= b1)))
            {
                return false;
            }
            //Check x's are in range of each other
            // ||(x2+w2>=x1 && x2+w2<=x1+w1)
            if (!((x1 >= x2 && x1 <= r2) || (r1 >= x2 && r1 <= r2) || (x2 >= x1 && x2 <= r1)))
            {
                return false;
            }
        //OFFSET USING THE MARGIN;
        //Move point 1 closer to point2;
            if (margin >= 0)
            {
                Point2D pointInside2=sc2.GetFirstPointInside(xo2,yo2);
                //if(UGameLogic.debugFlag1)
                //    UGameLogic.LogMsg("dir "+UGameLogic.DirToPoint(xo1, yo1, pointInside2.x, pointInside2.y, true));
                double dirR = UGameLogic.dirToPoint(xo1, yo1, pointInside2.x, pointInside2.y, false);
                double dist = UGameLogic.GetDistanceBetween(xo1, yo1, xo2, yo2);
                if (dist > margin)
                {
                    xo1 = (int)(xo1 + margin*Math.cos(dirR));
                    yo1 = (int)(yo1 - margin*Math.sin(dirR));
                }
                else
                {
                    xo1 = xo2;
                    yo1 = yo2;
                }
            }
        //GET ALL THE POINTS;
        ArrayList<Point2D> points1=GetPoints(xo1,yo1);
        Point2D innerPoint1 = GetFirstPointInside(points1);
        ArrayList<Point2D> points2 = sc2.GetPoints(xo2, yo2);
        Point2D innerPoint2 = sc2.GetFirstPointInside(points2);
        //Offset and transform the points as nescessary;

        





            if (!(this instanceof HitCircle_Unfinished))
            {
               /* if (rotation != 0)
                UGameLogic.RotatePointsAroundPivot(points1, rotation, innerPoint1);
                */
                if(margin>0)
                    OffsetPoints(points1, innerPoint1);
            }
        ArrayList<CartesianLine> edges1 = GetEdges(points1);
        /*
        if (sc2.rotation != 0)
        {
            if (sc2 as HitCircle == null)
            {
                UGameLogic.RotatePointsAroundPivot(points2, sc2.rotation, innerPoint2);
            }
        }
         */
        ArrayList<CartesianLine> edges2 = sc2.GetEdges(points2);

        //SPECIAL CASE, CIRCLES


        if (sc2 instanceof HitCircle_Unfinished)
        {

            return (((HitCircle_Unfinished)sc2).CheckTouchingLinesAndEdges(xo2,yo2,points1,edges1));
            //sc2.checkTouchingPointsAndLines(points1, edges1);
        }

        if (this instanceof HitCircle_Unfinished)
        {
            return (((HitCircle_Unfinished)this).CheckTouchingLinesAndEdges(xo1, yo1, points2, edges2));
             //checkTouchingPointsAndLines(points2, edges2);
        }

        int count = 0;
            if (edges1 != null)
            {
                for (CartesianLine edge1 : edges1)
                {
                    for (CartesianLine edge2 : edges2)
                    {
                        if (edge1.intersectsWith(edge2))
                        {

                            return true;
                        }
                        count++;
                    }

                }
            }


        if (PointInside(innerPoint2.x, innerPoint2.y, points1, edges1,xo1,yo1))
        {
            //UGameLogic.LogMsg(("Checkpoint 2a");
            return true;
        }
   
        if (sc2.PointInside(innerPoint1.x,innerPoint1.y,points2,edges2,xo2,yo2))
        {
            //UGameLogic.LogMsg(("Checkpoint 2b");
            return true;
        }
        
        return false;
    }

    //Check if a given point is contained within the collider;
    public boolean ContainsPoint(int pointX, int pointY){
    	return ContainsPoint(pointX,pointY,0,0);
    }
    
    public boolean ContainsPoint(int pointX, int pointY,int collider_xOffset,int collider_yOffset)
    {
        //List<Point2D> points1 = GetPoints(collider_xOffset, collider_yOffset);
        //List<CartesianLine> edges1 = GetEdges(points1);
        return this.PointInside(pointX - collider_xOffset, pointY - collider_yOffset);
    }



    //Offset points by the smallest margin;
    public void OffsetPoints(ArrayList<Point2D> points, Point2D pointWithin)
    {
        float centerX = pointWithin.x;
        float centerY = pointWithin.y;
        int smallestUnit = UGameLogic.smallestUnit;
        
        for (int i = 0; i < points.size();i++ )
        {
            
            int xOffset = 1;
            int yOffset = 1;
            if (points.get(i).x < centerX)
            {
                xOffset = -1;
            }
            if (points.get(i).y < centerY)
            {
                yOffset = -1;
            }

            Point2D newPoint=new Point2D(points.get(i).x + xOffset * smallestUnit, points.get(i).y + yOffset * smallestUnit);
            points.set(i, newPoint);
            if (points.get(i) == points.get(0))
            {
               // UGameLogic.LogMsg(("Testing 0");
            }
        }


    }

    //Draw the outline of the collider;
    //Hopefully it shall prove useful in situations in which the outline needs to be shown; 
    /*public virtual void DrawOutline(float x,float y,Color? colorN=null,DrawShapeMode drawMode= DrawShapeMode.LINES_AND_FILL)
    {

       ViewableObject.DrawBetweenPoints(GetPoints(), x, y, drawMode, colorN);
    }

    public virtual void DrawShape(float x, float y, Color? colorN = null)
    {

        ViewableObject.DrawBetweenPoints(GetPoints(), x, y, DrawShapeMode.FILL, colorN);
    }
    */


    public abstract boolean CheckTouchingEdge(CartesianLine edge,float x, float y);
}