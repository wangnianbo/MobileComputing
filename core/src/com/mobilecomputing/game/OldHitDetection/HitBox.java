package com.mobilecomputing.game.OldHitDetection;
import java.util.ArrayList;

import helperDataStructures.*;
public class HitBox extends SimpleCollider
{
    
    //Used in broad phase hit detection:
	@Override
    public  float FirstX()
    {
        return xMin;
    }
	@Override
    public float FirstY()
    {
        return yMin;
    }

    //position is inherited;

    //Dimensions of the hit box

    private int width;
    private int height;

    //Getters for the sake of inheritance;
    @Override
    public int GetBoundWidth()
    {
        return width;
    }
    @Override
    public int GetBoundHeight()
    {
        return height;
    }

    //Set the width of the hit box;
    public void setWidth(int newWidth)
    {
        this.width = newWidth;
    }

    //Set the height of the hit box;
    public void setHeight(int newHeight)
    {
        this.height = newHeight;
    }

    //Instanciate a hit box using only width and height;
    public HitBox(int width, int height)
    {
    	super(0,0,0);
        this.width = width;
        this.height = height;
    }

    //Instanciate a hit box using width, height and rotation;
    public HitBox(int width, int height, int rotation)
    {
    	super(0,0,rotation);
        this.width = width;
        this.height = height;
    }

    //Instantiate a hit box by specifying all arguments;
    public HitBox(int x1, int y1, int width, int height)
    {
    	super(x1,y1,0);
        this.width = width;
        this.height = height;
    }



    //Return a list of each of the points in turn;
    @Override
    public ArrayList<Point2D> GetPoints(int xOffset, int yOffset)
    {
    	ArrayList<Point2D> points = new ArrayList<Point2D>();

            points.add(new Point2D(xMin + xOffset, yMin + yOffset));
            points.add(new Point2D(xMin + width + xOffset - 1, yMin + yOffset));
            points.add(new Point2D(xMin + width + xOffset - 1, yMin + height + yOffset - 1));
            points.add(new Point2D(xMin + xOffset, yMin + height + yOffset - 1));

        if(rotation!=0)
        {
    
            for(Point2D point : points){
                point.rotateAroundPoint(xOffset, yOffset,rotation);
            }
        }
        return points;
    }


    //Check if a given point is inside the HitBox;
    @Override 
    public boolean PointInside(float x1, float y1, ArrayList<Point2D> points, ArrayList<CartesianLine> edges,float xo,float yo)
    {


        x1 -=xo;
        y1 -= yo;

        if (x1 >= xMin && x1 <= xMin + width)
        {
            if (y1 >= yMin && y1 <= yMin + height)
            {
                return true;
            }
        }
        return false;
    }

    //Set the bounds of the hit box;
    public void SetBounds(int xMin, int yMin, int w, int h)
    {
        this.xMin = xMin;
        this.yMin = yMin;
        this.width = w;
        this.height = h;
    }

    public void SetBoundsF(float xMin, float yMin, float w, float h)
    {
        SetBounds((int)xMin,(int)yMin,(int)w,(int)h);
    }

    //Return whether or not the collider is touching a given edge;
    @Override
    public boolean CheckTouchingEdge(CartesianLine edge, float x, float y)
    {
        CartesianLine edge2 = new CartesianLine(edge.startX() + x, edge.startY() + y, edge.endX() + x, edge.endY() + y);
        for(CartesianLine l : this.GetEdges(0, 0))
        {
            if (l.intersectsWith(edge2))
            {
                return true;
            }
        }
        return false;
    }
}
