package com.mobilecomputing.game.OldHitDetection;
import java.util.ArrayList;

import helperDataStructures.*;
public class HitCircle_Unfinished extends SimpleCollider {

	public HitCircle_Unfinished(int x, int y, int rotation) {
		super(x, y, rotation);
		// TODO Auto-generated constructor stub
	}

	@Override
    public ArrayList<CartesianLine> GetEdges(ArrayList<Point2D> points)
    {
        return null;
    }
	
	private int xOffset;
	private int yOffset;
	private int radius;
	public int getRadius(){
		return radius;
	}
	
	
    public float FirstX()
    {
        return xOffset;
    }

    public float FirstY()
    {
        return yOffset;
    }
	
    //Is the circle in contact with any of these points or edges;
    public boolean CheckTouchingLinesAndEdges(int xo1,int yo1,ArrayList<Point2D> points,ArrayList<CartesianLine> edges)
    {

        int centerX = xOffset + xo1;
        int centerY = yOffset + yo1;
        //Check if any of the points are in range
        for(Point2D p : points){
            //If a point is
            if (((centerX - p.x)*(centerX - p.x) + (centerY - p.y)*(centerY - p.y))<radius*radius)
            {
                //return true;
            }
        }

        //Check for collisions with edges

        if(edges!=null)
        for (CartesianLine edge : edges)
        {
            double c1x = edge.startX() - centerX;
            double c1y = edge.startY() - centerY;
           double e1x = edge.endX() - edge.startX();
           double e1y=edge.endY()-edge.startY();
           double k = c1x*e1x + c1y*e1y;

            if(k > 0 || true)
            {
                double len = e1x*e1x + e1y*e1y;

                double c1sqr = c1x * c1x + c1y * c1y - radius*radius;
                if(k < len)
                {
                    if (c1sqr * len <= k * k)
                    {
                        return true;
                    }
                }

            }
        }

        return false;
    }

	@Override
	public int GetBoundWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int GetBoundHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Point2D> GetPoints(int xOffset, int yOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean PointInside(float x1, float y1, ArrayList<Point2D> points,
			ArrayList<CartesianLine> edges, float xo, float yo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean CheckTouchingEdge(CartesianLine edge, float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
