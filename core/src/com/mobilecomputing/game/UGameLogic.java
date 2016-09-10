package com.mobilecomputing.game;

import helperDataStructures.Point2D;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.mobilecomputing.game.GameObjects.LegacyGameObject;
import com.badlogic.gdx.math.Vector2;

public class UGameLogic {
	public static <T> ArrayList<T> hashSetToList(HashSet<T> hashSet){
		ArrayList<T> listToReturn=new ArrayList<T>();
		for(T o : hashSet){
			listToReturn.add(o);
		}
		return listToReturn;
	}
	public static final int tileWidth=64;
	public static final int UNSET_INT=-10000;
	public static void LogMsg(String string){
		LogMsg(string,null);
	}
	public static void LogMsg(String string,String additionalArgument){
		if(additionalArgument!=null && additionalArgument=="test"){
			//return;
		}

		System.out.println(string);
	}
	
	public static void LogError(String errorString){
		System.out.println("ERROR: "+errorString);
	}
	
	public static String FixFileSeperators(String oldString){
		String newString=oldString.replace("/", File.separator);
		newString=newString.replace("\\", File.separator);
		return newString;
	}
	
	public static void defaultThreadWait(){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<File> RecurseDirectory(String directoryPath){
		File dir = new File(directoryPath);
		UGameLogic.LogMsg(System.getProperty("user.dir"));
		return RecurseDirectory(dir);
	}
	
	public static ArrayList<File> RecurseDirectory(File dir){
		UGameLogic.LogMsg("Recursing "+dir);
		ArrayList<File> listToReturn=new ArrayList<File>();
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
			  UGameLogic.LogMsg("Found 1");
		    for (File child : directoryListing) {
		    	
		      // Do something with child
		    	if(child.isDirectory()){
		    		UGameLogic.LogMsg("Recurse Next "+child);
		    		listToReturn.addAll(RecurseDirectory(child));
		    	}
		    	else{
		    		listToReturn.add(child);
		    		String subFileName=child.getPath();

		    		
		    		UGameLogic.LogMsg("Sub File Name "+subFileName);
		    	}
		    }
		  } else {
			  UGameLogic.LogMsg("Found NONE");
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }

		return listToReturn;
	}
	
	public static void ReverseArrayList(ArrayList list){
	    for(int i = 0, j = list.size() - 1; i < j; i++) {
	        list.add(i, list.remove(j));
	    }
	    //return list;
		
	}
	
    public static HashSet<String> uniqueMessages = new HashSet<String>();
	public static final int lengthOfSecond=60;
	public static final int smallestUnit=1;
    //Display the following message ONLY if it's hasn't been shown before
    public static void LogMsgUnique(String msg)
    {

        if (msg == null)
            return;
        String key = msg.toLowerCase();
        if (!uniqueMessages.contains(key))
        {
            uniqueMessages.add(key);
            LogMsg(msg);
        }
    }

    

    public static boolean NumExceedsIn(double n1, double n2, int sign)
    {
        boolean toReturn = false;
        if (sign < 0)
        {
            toReturn=n1 < n2;
        }
        else if (sign > 0)
        {
            toReturn=n1 > n2;
        }
        //UGameLogic.LogMsg("toReturn "+n1+" "+n2+ toReturn);
        return toReturn;
    }

    //Increase a number in the positive or negative direction, but not beyond the bounds of the increment.
    //For example IncreaseInAndLimit(5,-7) would return -2;
    //TryIncreaseInAndLimit(-1,-7) would return -7;
    //TryIncreaseInAndLimit(-12,-7) would return -12;
    //TryIncreaseInAndLimit(12,5) would return 12;

    public static double IncreaseInAndLimit(double originalVal, double increment)
    {
        return IncreaseInAndLimit(originalVal, increment, increment);
    }
    public static double IncreaseInAndLimit(double originalVal, double increment,double limitMagnitude)
    {
        double newValue = originalVal + increment;
        int n_sign=(int)Math.signum(increment);
        limitMagnitude = Math.abs(limitMagnitude) * n_sign;
        if(NumExceedsIn(newValue,limitMagnitude,n_sign)){
            if (NumExceedsIn(limitMagnitude, originalVal, n_sign))
            {
                return limitMagnitude;
            }
            else
            {
                return originalVal;
            }
        }
        return newValue;
    }


    //Cropping numbers
    public static double CropDoubleIfOppositeTo(double num1,double num2,boolean zeroIfOpposite)
    {
        double num3 = num1;
        if (UGameLogic.GetSign(num1) != UGameLogic.GetSign(num2))
        {
            num3 = num1 + num2;
        }
        if (UGameLogic.GetSign(num3) != UGameLogic.GetSign(num1) && zeroIfOpposite)
        {
            return 0;
        }
        return num3;
    }


    public static float CropFloatIfOppositeTo(float num1, float num2,boolean zeroIfOpposite)
    {
        return (float)CropDoubleIfOppositeTo(num1, num2,zeroIfOpposite);
    }

    public static int CropIntIfOppositeTo(int num1, int num2,boolean zeroIfOpposite)
    {
        int num3 = num1;
        if (UGameLogic.GetSign(num1) != UGameLogic.GetSign(num2))
        {
            num3 = num1 + num2;
        }
        if (UGameLogic.GetSign(num3) != UGameLogic.GetSign(num1) && zeroIfOpposite)
        {
            return 0;
        }
        return num3;
    }

    //Round to a given number of decimal places;
    public static int RoundToFactor(double num, int factor)
    {
        num = num * factor;
        return (int)Math.round(num / factor);
    }

    public static int GetSign(double no){
    	return GetSign(no,true);
    }
    
    //Return the sign of a number;
    public static int GetSign(double no,boolean allowZero)
    {
        if (no > 0)
        {
            return 1;
        }
        if (no == 0 && allowZero)
        {
            return 0;
        }
        return -1;
    }


    //Positive mod of a given double
    public static double PMod(double num, double mod)
    {
        if (num >= 0)
        {
            return num % mod;
        }
        else
        {
            return num % mod + mod;
        }
    }


    //Finds the distance between two objects
    public static float GetDistanceBetween(LegacyGameObject obj1, LegacyGameObject obj2)
    {

        if (obj1 == null || obj2 == null)
        {
            return 0;
        }
        return (float)Math.sqrt((Math.pow((obj1.x - obj2.x), 2) + Math.pow((obj1.y - obj2.y), 2)));
    }

    //Get the distance between two points;
    public static float GetDistanceBetween(Point2D p1, Point2D p2)
    {
        return (float)Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
    }

    //Quickly grab the distance between two viewable objects;
    public static float GetDistanceBetween(ViewableObject v1, ViewableObject v2)
    {
        if (v1 == null || v2 == null)
            return 0;
        else
        {

            return (float)Math.sqrt((v1.x - v2.x) * (v1.x - v2.x) + (v1.y - v2.y) * (v1.y - v2.y));

        }
    }

    //Get the distance between different x and y coordinates;
    public static double GetDistanceBetween(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }



    //ANGLE MATHS;
    //region ANGLE MATHS;
    //Convert 360 degree clockwise from north bearings to radians to be used with sin and cos;
    public static double TrueBearingsToRadians(double bearing)
    {
        return ((90 - bearing) / 180f * Math.PI);
    }

    //Convert radians to 360 degree clockwise from north bearings;
    public static double RadiansToTrueBearings(double radians)
    {
        double res = -radians * 180 / Math.PI + 90;
        if (res < 0)
        {
            res = res + 360;
        }
        return (res);
    }

    public static double dirToPoint(Point2D startPoint,Point2D endPoint)
    {
    	return dirToPoint(startPoint,endPoint,true);
    }
    
    public static double dirToPoint(Point2D startPoint,Point2D endPoint, Boolean inTrueBearings)
    {
        return dirToPoint(startPoint.x, startPoint.y, endPoint.x, endPoint.y, inTrueBearings);
    }

    //Get the direction to a given point;
    public static double dirToPoint(double originX, double originY, double destX, double destY)
    {
    	return dirToPoint(originX,originY,destX,destY,true);
    }
    public static double dirToPoint(double originX, double originY, double destX, double destY, Boolean inTrueBearings)
    {
        double ang;
        if (inTrueBearings)
        {
            /*
            if (Math.Abs(destX - originX) < 0.001f)
            {
                return 0;
            }
             */
            ang = Math.atan2(originY - destY, destX - originX);
            ang = RadiansToTrueBearings(ang);
        }
        else
        {
            /*
            if (Math.Abs(destX - originX) < 0.001f)
            {
                return Math.PI / 2;
            }
             */
            ang = Math.atan2(originY-destY, destX - originX);
        }
        return ang;
    }


    //Rotate a list of points around a pivot point;
    public static void RotatePointsAroundPivot(List<Point2D> points, int rotation, Point2D pivot)
    {
        if (rotation != 0)
        {
            for (Point2D point : points)
            {
                point.rotateAroundPoint(pivot, rotation);
            }
        }
    }
    
    //Try rotate angle towards a given target;
    public static double TryRotateTowards(double dir, double targetDir,double rotateSpeed, double boundBottom, double boundTop)
    {
        double angDiff = UGameLogic.AngleDifferenceAttemptWithBounds(dir, targetDir, boundBottom, boundTop);
        //Should I snap to the direction, because the current angle is out of bounds?
        boolean snapCauseOutOfBounds = !UGameLogic.AngleInBounds(dir, boundBottom, boundTop);

        if (Math.abs(angDiff) <= rotateSpeed || snapCauseOutOfBounds)
        {
            dir = dir + angDiff;
        }
        else
        {
            dir = dir + UGameLogic.GetSign(angDiff) * rotateSpeed;
        }

        dir = UGameLogic.PMod(dir, 360);
        return dir;
    }


    //Try rotate angle towards a given target;
    public static double TryRotateTowardsAlt(double dir, double targetDir, double rotateSpeed, double boundBottom, double boundTop)
    {
        double angDiff = UGameLogic.AngleDifference(dir, targetDir);
        //Should I snap to the direction, because the current angle is out of bounds?

        if (Math.abs(angDiff) <= rotateSpeed)
        {
            dir = dir + angDiff;
        }
        else
        {
            dir = dir + UGameLogic.GetSign(angDiff) * rotateSpeed;
        }

        dir = UGameLogic.PMod(dir, 360);
        return dir;
    }

    /// <summary>
    ///	Find by which angle (from the vertical), That the game needs to rotate angle 1 by to reach angle 2 
    /// </summary>
    public static double AngleDifference(double angle1, double angle2)
    {
    	return AngleDifference(angle1,angle2,0);
    }
    
    public static double AngleDifference(double angle1, double angle2,int forceRotationalDir)
    {
        angle1 = PMod(angle1, 360);
        angle2 = PMod(angle2, 360);
        double clockwiseAngle = PMod((angle2 - angle1),360);

        double antiClockwiseAngle = (360 - clockwiseAngle);
        if ((antiClockwiseAngle > clockwiseAngle && forceRotationalDir==0) || forceRotationalDir>0)
        {
            return clockwiseAngle;
        }
        else
        {
            return -antiClockwiseAngle;
        }
    }

    //Check that a given angle is in the rotational bounds of 2 other angles;
    public static boolean AngleInBounds(double angle, double bottom, double top)
    {
        bottom = PMod(bottom, 360);
        top = PMod(top, 360);

        double clockwiseToBottom = UGameLogic.AngleDifference(angle,bottom,1);

        double clockwiseToTop = UGameLogic.AngleDifference(angle,top,1);
        if (clockwiseToTop <= clockwiseToBottom || clockwiseToBottom==0)
        {
            return true;
        }
        return false;
    }
    
    //Returns the angle difference for rotating towards a specific object, when bounds are involved;
    public static double AngleDifferenceAttemptWithBounds(double angle, double targetAngle, double bottom, double top)
    {
        //Rotational distances in their respective directions;
        double clockwiseToTop = AngleDifference(angle, top, 1);
        double antiClockwiseToBottom = AngleDifference(angle, bottom, -1);
        //Simplistic rotational distances, (without having to worry about always restricting the direction in a certain way; 
        double simpleDiffTop = AngleDifference(angle, top);
        double simpleDiffBottom = AngleDifference(angle, bottom);

        //To the nearest bound;
        if (!AngleInBounds(angle,bottom,top))
        {

            if (Math.abs(simpleDiffTop)<Math.abs(simpleDiffBottom))
            {
                return simpleDiffTop;
            }
            else
            {
                return simpleDiffBottom;
            }
        }
        else if (!AngleInBounds(targetAngle, bottom, top))
        {
            double targetAngDiffTop = UGameLogic.AngleDifference(targetAngle,top, 0);
            double targetAngDiffBottom = UGameLogic.AngleDifference(targetAngle, bottom, 0);
            if (Math.abs(targetAngDiffTop) < Math.abs(targetAngDiffBottom))
            {
                return clockwiseToTop;
            }
            else
            {
                return antiClockwiseToBottom;
            }

        }
        //BOTH IN BOUNDS;
        else
        {
            double angDiff = UGameLogic.AngleDifference(angle, targetAngle, 0);
            if (angDiff < antiClockwiseToBottom)
            {
                return 360 + angDiff;
            }
            else if(angDiff>clockwiseToTop)
            {
                return angDiff - 360;
            }
            return angDiff;
        }
    }

    private static HashMap<String,Rectangle> cachedRectangles=new HashMap<String,Rectangle>();
    public static Rectangle getCachedRectangle(float w,float h){
    	String key=((int)w)+" "+((int)h);
    	
    	if(!cachedRectangles.containsKey(key)){
    		cachedRectangles.put(key, new Rectangle(0,0,w,h));
    	}
    	return cachedRectangles.get(key);
    }
    private static HashMap<String,Polygon> cachedPolyQuads=new HashMap<String,Polygon>();
    public static Polygon getCachedPolyQuad(float w,float h){
    	String key=((int)w)+" "+((int)h);
    	
    	if(!cachedPolyQuads.containsKey(key)){
    		cachedPolyQuads.put(key, new Polygon(new float[] { 0, 0, 
            		0, h,
                    w,h,
                    w,0 }));
    	}
    	return cachedPolyQuads.get(key);
    }

    public static boolean CollisionOffsetBetween(Shape2D collider1, float newX, float newY,Shape2D collider2, float x2, float y2)
    {

            if (collider1 != null && collider2 != null)
            {


                //boolean res = simpleCollider.CheckOffsetTouching(newX, newY, collider2, x2, y2, useMargin);
            	/*if(collider1 instanceof Polygon && collider2 instanceof Polygon){
            		Polygon poly1=((Polygon)collider1);
            		Polygon poly2=((Polygon)collider1);
                  	poly1.setPosition(newX, newY);
                  	poly2.setPosition(x2,y2);
                  	return Intersector.overlapConvexPolygons(poly1,poly2);
            	}
            	*/
                if(collider1 instanceof Rectangle && collider2 instanceof Rectangle){

                    Rectangle rec1=((Rectangle)collider1);
                    Rectangle rec2=((Rectangle)collider2);
                    float prev_x1=rec1.x;
                    float prev_y1=rec1.y;
                    float prev_x2=rec2.x;
                    float prev_y2=rec2.y;


                    rec1.setPosition(newX+prev_x1, newY+prev_y1);
                    rec2.setPosition(x2+prev_x2,y2+prev_y2);
                    boolean res=Intersector.overlaps(rec1,rec2);
                    rec1.setPosition(prev_x1, prev_y1);
                    rec2.setPosition(prev_x2,prev_y2);
                    return res;
                }
                else if(collider1 instanceof Rectangle && collider2 instanceof Circle){



                    Rectangle rec1=((Rectangle)collider1);
                    Circle circle2=((Circle)collider2);
                    float prev_x1=rec1.x;
                    float prev_y1=rec1.y;
                    float prev_x2=circle2.x;
                    float prev_y2=circle2.y;
                    rec1.setPosition(newX+prev_x1, newY+prev_y1);
                    circle2.setPosition(x2+prev_x2,y2+prev_y2);
                    boolean res=Intersector.overlaps(circle2,rec1);
                    rec1.setPosition(prev_x1, prev_y1);
                    circle2.setPosition(prev_x2,prev_y2);
                    return res;
                }
                else if(collider1 instanceof Circle && collider2 instanceof Rectangle){

                    Rectangle rect2=((Rectangle)collider2);
                    Circle circle1=((Circle)collider1);
                    float prev_x1=circle1.x;
                    float prev_y1=circle1.y;
                    float prev_x2=rect2.x;
                    float prev_y2=rect2.y;
                    circle1.setPosition(newX+prev_x1, newY+prev_y1);
                    rect2.setPosition(x2+prev_x2,y2+prev_y2);
                    boolean res=Intersector.overlaps(circle1,rect2);
                    circle1.setPosition(prev_x1, prev_y1);
                    rect2.setPosition(prev_x2,prev_y2);
                    return res;

                }
                else if(collider1 instanceof Circle && collider2 instanceof Circle){


                    Circle circle1=((Circle)collider1);
                    Circle circle2=((Circle)collider2);
                    float prev_x1=circle1.x;
                    float prev_y1=circle1.y;
                    float prev_x2=circle2.x;
                    float prev_y2=circle2.y;
                    circle1.setPosition(newX+prev_x1, newY+prev_y1);
                    circle2.setPosition(x2+prev_x2,y2+prev_y2);
                    boolean res=Intersector.overlaps(circle1,circle2);
                    circle1.setPosition(prev_x1, prev_y1);
                    circle2.setPosition(prev_x2,prev_y2);
                    return res;
                }

                else if(collider1 instanceof Polygon && collider2 instanceof Polygon){

                    Polygon poly1=((Polygon)collider1);
                    Polygon poly2=((Polygon)collider2);
                    float prev_x1=poly1.getX();
                    float prev_y1=poly1.getY();
                    float prev_x2=poly2.getX();
                    float prev_y2=poly2.getY();
                    poly1.setPosition(newX+prev_x1, newY+prev_y1);
                    poly2.setPosition(x2+prev_x2,y2+prev_y2);

                    boolean res=Intersector.overlapConvexPolygons(poly1,poly2);
                    poly1.setPosition(prev_x1, prev_y1);
                    poly2.setPosition(prev_x2,prev_y2);
                    return res;
                }

                else if(collider1 instanceof Polygon || collider2 instanceof Polygon){
                    float prev_x1=0;
                    float prev_y1=0;
                    float prev_x2=0;
                    float prev_y2=0;
                    Polygon poly1;
                    Polygon poly2;
                    Rectangle rect2;
                    Circle circle2;
                    Rectangle rect1;
                    Circle circle1;
                    if(collider1 instanceof Polygon){
                        poly1=((Polygon)collider1);
                        prev_x1=poly1.getX();
                        prev_y1=poly1.getY();
                        poly1.setPosition(newX+prev_x1, newY+prev_y1);
                        boolean retVal=false;
                        if(collider2 instanceof Circle){
                            circle2=((Circle)collider2);
                            prev_x2=circle2.x;
                            prev_y2=circle2.y;
                            circle2.setPosition(x2+prev_x2, y2+prev_y2);
                            retVal=UGameLogic.isCollision(poly1, circle2);
                            circle2.setPosition(prev_x2,prev_y2);

                        }
                        if(collider2 instanceof Rectangle){
                            rect2=((Rectangle)collider2);
                            prev_x2=rect2.x;
                            prev_y2=rect2.y;
                            rect2.setPosition(x2+prev_x2, y2+prev_y2);
                            retVal=UGameLogic.isCollision(poly1,rect2);
                            rect2.setPosition(prev_x2,prev_y2);

                        }
                        poly1.setPosition(prev_x1, prev_y1);
                        return retVal;

                    }

                    else if(collider2 instanceof Polygon){
                        poly2=((Polygon)collider2);
                        prev_x2=poly2.getX();
                        prev_y2=poly2.getY();
                        poly2.setPosition(x2+prev_x2,y2+prev_y2);
                        boolean retVal=false;
                        if(collider1 instanceof Circle){
                            circle1=((Circle)collider1);
                            prev_x1=circle1.x;
                            prev_y1=circle1.y;
                            circle1.setPosition(newX+prev_x1, newY+prev_y1);
                            UGameLogic.isCollision(poly2,circle1);
                            circle1.setPosition(prev_x1,prev_y1);

                        }
                        if(collider1 instanceof Rectangle){
                            rect1=((Rectangle)collider1);
                            prev_x1=rect1.x;
                            prev_y1=rect1.y;
                            rect1.setPosition(newX+prev_x1, newY+prev_y1);
                            retVal= UGameLogic.isCollision(poly2,rect1);
                            rect1.setPosition(prev_x1,prev_y1);

                        }
                        poly2.setPosition(prev_x2,prev_y2);
                        return retVal;
                    }



                }

                LogError("Unsupported collision type between "+collider1+" & "+collider2);
            }
            return false;
    }





    public static boolean isCollision(Polygon p, Rectangle r) {
        Polygon rPoly = getCachedPolyQuad(r.width,r.height);
        rPoly.setPosition(r.x, r.y);
        if (Intersector.overlapConvexPolygons(rPoly, p))
            return true;
        return false;
    }
    
 // Check if Polygon intersects Circle
    public static boolean isCollision(Polygon p, Circle c) {
        float[] vertices = p.getTransformedVertices();
        Vector2 center = new Vector2(c.x, c.y);
        float squareRadius = c.radius * c.radius;
        for (int i = 0; i < vertices.length; i += 2) {
            if (i == 0) {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[vertices.length - 2],
                        vertices[vertices.length - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[i - 2], vertices[i - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            }
        }
        return false;
    }
    
	
}
