package helperDataStructures;

public class Point2D {

	    public float x = 0;
	    public float y = 0;
	    

	    public int intX() 
	    	{
	    	return ((int)Math.round(x));
	    	}
	    
	    public int intY(){ 
	    	return (int)Math.round(y); 
	    }


	    public Point2D(float x1, float y1)
	    {
	        x = x1;
	        y = y1;
	    }



	    public Point2D(Point2D pointToDuplicate){
	        x = pointToDuplicate.x;
	        y = pointToDuplicate.y;
	    }

	    public static Point2D Copy(Point2D other)
	    {
	        return new Point2D(other);
	    }

	    public boolean RangeOverlapping(Point2D p2)
	    {
	        float max = Math.max(this.x, this.y);
	        float min = Math.min(this.x, this.y);
	        float max2 = Math.max(p2.x, p2.y);
	        float min2 = Math.min(p2.x, p2.y);

	        if ((max > min2 && max < max2) || (min > min2 && min < max2) || (min2 > min && min2 < max))
	        {
	            return true;
	        }
	        return false;
	    }


	   
	    public void rotateAroundOrigin(double rotation1)
	    {
	    	rotateAroundOrigin(rotation1,true);
	    }
	    public void rotateAroundOrigin(double rotation1,boolean inTrueBearings)
	    {
	        double dist = Math.sqrt(x * x + y * y);
	        double ang = Math.atan2(y, x);
	        if (inTrueBearings)
	        {
	            rotation1 = (rotation1) / 180 * Math.PI;

	        }
	        double rotation2 = ang + rotation1;
	        x = (int)(dist * Math.cos(rotation2));
	        y = (int)(dist * Math.sin(rotation2));
	    }
	    public void rotateAroundPoint(Point2D point, double rotation1)
	    {
	    	rotateAroundPoint(point,rotation1,true);
	    }
	    public void rotateAroundPoint(Point2D point, double rotation1, boolean inTrueBearings)
	    {
	        float diffX = x - point.x;
	        float diffY = y - point.y;
	        Point2D diffPoint = new Point2D(diffX, diffY);
	        diffPoint.rotateAroundOrigin(rotation1,inTrueBearings);

	        x = diffPoint.x + point.x;

	        y = diffPoint.y + point.y;

	    }

	    public void rotateAroundPoint(float xo, float yo, double rotation1)
	    {
	    	rotateAroundPoint(xo,yo,rotation1,true);
	    }
	    
	    public void rotateAroundPoint(float xo, float yo, double rotation1, boolean inTrueBearings)
	    {
	        float diffX = xo-x;
	        float diffY = yo-y;
	        Point2D diffPoint = new Point2D(diffX, diffY);
	        diffPoint.rotateAroundOrigin(rotation1,inTrueBearings);

	        x = diffPoint.x + xo;

	        y = diffPoint.y + yo;

	    }




	    public static float CrossProductZ(Point2D p1, Point2D p2)
	    {
	        return (p1.x * p2.y - p2.x * p1.y);
	    }

	    public static float DotProduct(Point2D p1, Point2D p2)
	    {
	        return (p1.x * p2.x + p1.y * p2.y);
	    }

	    public float magnitude()
	    {

	            return (float)Math.sqrt(x * x + y * y);
	        
	    }

	    public float magnitudeSquared()
	    {

	            return (x * x + y * y);
	        
	    }
	    @Override
	    public String toString()
	    {
	        return "{" + x + ":" + y + "}";
	    }
	






}
