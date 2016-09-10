package helperDataStructures;

import com.mobilecomputing.game.UGameLogic;


public class CartesianLine
{

		private double startX;
		public double startX(){
			return startX;
		}
		
		private double startY;
		public double startY(){
			return startY;
		}
		private double endX;
		public double endX(){
			return endX;
		}
		private double endY;
		public double endY(){
			return endY;
		}
        public double midX() { return ((startX + endX) / 2.0); }
        
        public double midY() { return ((startY + endY) / 2.0); }

        public double xMin(){return Math.min(startX, endX);}
        public double xMax(){ return Math.max(startX, endX);  }
        public double yMin(){return Math.min(startY, endY);  }
        public double yMax(){ return Math.max(startY, endY);  }

        //Ax+by=C;    
        private double a;
        private double b;
        private double c;

        private double gradient(){

                if(b==0){
                    return UGameLogic.UNSET_INT;
                }
                return a / -b;
            
        }
        
        
        private double yAxisIntersect()
        {

                if (startX == 0)
                {
                    return startY;
                }
                else if (gradient()>UGameLogic.UNSET_INT)
                {
                    return startY - startX * gradient();
                }
                else
                {
                    return UGameLogic.UNSET_INT;
                }
            
        }

       
        @Override
        public String toString()
        {
            return "({"+startX+","+startY+"}{"+endX+","+endY+"})";
        }

        public CartesianLine(double startX1, double startY1, double endX1, double endY1)
        {
            startX = startX1;
            startY = startY1;
            endX = endX1;
            endY = endY1;
            a = endY - startY;
            b = startX - endX;
            c = a * startX + b * startY;

        }

        //Return the intersecting x and y coordinates;
        public Point2D_Double FindIntersectionWith(CartesianLine line2){
        	return FindIntersectionWith(line2,false);
        }
        

        
        public Point2D_Double FindIntersectionWith(CartesianLine line2,boolean inBounds){
            double x1 = UGameLogic.UNSET_INT;
            double y1 = UGameLogic.UNSET_INT;
            double det = line2.b * a - line2.a * b;


            if (det != 0)
            {


                x1 = (line2.b * c - b * line2.c) / det;
                y1 = (a * line2.c - line2.a * c) / det;
                //Do bounds need to be considered?;
                if (!inBounds)
                {
                    //If they don't just return with x1, y1 as the coordinate of the intersection

                    return new Point2D_Double(x1,y1);
                }
                
                if (InXBounds(x1, line2) && InYBounds(y1, line2))
                {
                    //If the point is 
                    return new Point2D_Double(x1,y1);

                }
                else
                {
                    x1 = UGameLogic.UNSET_INT;
                    y1 = UGameLogic.UNSET_INT;
                }
            }
            //Same y intersect?
            if (c/b==line2.c/line2.b)
            {  
               // return (InXBounds(endX, line2) || InXBounds(startX, line2) || InXBounds(line2.startX, line2));
            }
            return new Point2D_Double(x1,y1);
        }


        public boolean intersectsWith(CartesianLine line2)
        {
            double x1;
            double y1;
            Point2D_Double point=FindIntersectionWith(line2, true);
            x1=point.x;
            y1=point.y;
            if (x1 <= UGameLogic.UNSET_INT || y1 <= UGameLogic.UNSET_INT)
            {
                return false;
            }
            return true;
        }
        
        /*
        public Boolean LineIntersection(CartesianLine line2,out float x1,out float y1){
            x1=0;
            y1=0;
            float det = line2.b*a - line2.a * b;
            if(det!=0)
            {
                x1 = (line2.b * c - b * line2.c)/det;
                y1 = (a * line2.c - line2.a * c)/det;

                if (InXBounds(x1, line2) && InYBounds(y1, line2))
                {
                    return true;
                }
            }
            if (c * b * a == line2.c * line2.b * line2.a)
            {
                return(InXBounds(endX, line2) || InXBounds(startX, line2) || line2.InXBounds(line2.startX, this));
            }

            return false;
        }
        */

         //Find the intersection points of a given circle (if any)
        public Point2D[] IntersectsCircle(double centerX, double centerY, double radius)
        {
        	Point2D pointOnCircle1;
        	Point2D pointOnCircle2;
            //Thanks Wolfram

            //First get the relative starting and ending coordinates of the line relate to the circle;
            double sx = startX-centerX;
            double sy = startY-centerY;
            double ex = endX-centerX;
            double ey = endY - centerY;
            double dx = ex - sx;
            double dy = ey - sy;
            
            //The determinant for whether it has no (dr<0), one (dr==0) or two solutions (dr>0);
            double drSq=dx * dx + dy * dy;

            double dr = Math.sqrt(drSq);
   
            //Vector multiplication of (sx,sy) (ex,ey);
            double vectM = sx * ey - sy * ex;
            double det = radius * radius * drSq - vectM * vectM;
            //No solutions? then stop here
            if (det < 0)
            {
                pointOnCircle1 = null;
                pointOnCircle2 = null;
                return new Point2D[]{pointOnCircle1,pointOnCircle2};
            }
            double signY = UGameLogic.GetSign(dy);
            if (signY == 0)
            {
                signY = 1;
            }
            double solX;
            double solY;
            //Solution 1

            solX = (vectM * dy + signY * dx * Math.sqrt(det)) / (drSq);
            solY = (-vectM * dx + Math.abs(dy) * Math.sqrt(det)) / (drSq);
            /*UGameLogic.LogMsg("");
            UGameLogic.LogMsg("dx: " + dx + " dy: " + dy);
            UGameLogic.LogMsg("line: "+this);
            UGameLogic.LogMsg("({"+(int)sx+","+(int)sy+"}{"+(int)ex+","+(int)ey+"})");
            UGameLogic.LogMsg("center: " +centerX+":"+centerY);
            UGameLogic.LogMsg("radiusAlt: " + radius);
            UGameLogic.LogMsg("distance alt: " + UGameLogic.GetDistanceBetween(0,0,solX,solY));

            UGameLogic.LogMsg("dx"+dx);
            UGameLogic.LogMsg("dy" + dy);
            UGameLogic.LogMsg("det" + det);
            UGameLogic.LogMsg("p1x " + (vectM * dy));
            UGameLogic.LogMsg("p2x " + (signY * dx * Math.Sqrt(det)));
            UGameLogic.LogMsg("p2x " + (-vectM * dx));
            UGameLogic.LogMsg("p2y " + (Math.abs(dy) * Math.Sqrt(det)));

            UGameLogic.LogMsg("Sol {" + solX + "," + solY + "}");
            */
            pointOnCircle1 = new Point2D((float)(solX+centerX), (float)(solY+centerY));
            if (pointOnCircle1!=null && (!InXBounds(pointOnCircle1.x, this) || !InYBounds(pointOnCircle1.y, this)))
            {
                pointOnCircle1 = null;
            }
            if (det > 0)
            {

                solX = (vectM * dy - signY * dx * Math.sqrt(det)) / (drSq);
                solY = (-vectM*dx - Math.abs(dy) * Math.sqrt(det)) / (drSq);
                //UGameLogic.LogMsg("Sol2 {" + solX + "," + solY + "}");
                pointOnCircle2 = new Point2D((float)(solX+centerX), (float)(solY+centerY));
            }
            else
            {
                pointOnCircle2 = null;
            }
            if (pointOnCircle2!=null && (!InXBounds(pointOnCircle2.x, this) || !InYBounds(pointOnCircle2.y, this)))
            {
                pointOnCircle2 = null;
            }
            return new Point2D[]{pointOnCircle1,pointOnCircle2};
        }
        




        //Within the x bounds;
        public boolean InXBounds(double x, CartesianLine line2){
        	return InXBounds(x,line2,0.001f);
        }
        
        public boolean InXBounds(double x, CartesianLine line2, double margin)
        {
            double smallestUnit = margin;
            double minX1 = Math.min(startX, endX) - smallestUnit;
            double maxX1 = Math.max(startX, endX) + smallestUnit;
            double minX2 = Math.min(line2.startX, line2.endX) - smallestUnit;
            double maxX2 = Math.max(line2.startX, line2.endX) + smallestUnit;
            if (x >= minX1 && x <= maxX1 && x >= minX2 && x <= maxX2)
            {
                return true;
            }
            return false;


        }
        

        //Within the y bounds;
        public boolean InYBounds(double y, CartesianLine line2)
        {
        	return InYBounds(y,line2,0.001f);
        }
        
        public boolean InYBounds(double y, CartesianLine line2, double margin)
        {
            double smallestUnit = margin;
            double minY1 = Math.min(startY, endY) - smallestUnit;
            double maxY1 = Math.max(startY, endY) + smallestUnit;
            double minY2 = Math.min(line2.startY, line2.endY) - smallestUnit;
            double maxY2 = Math.max(line2.startY, line2.endY) + smallestUnit;
            if (y >= minY1 && y <= maxY1 && y >= minY2 && y <= maxY2)
            {
                return true;
            }
            return false;
        }

        //Return the angle from the start point to the end point;
        public double GetAngle()
        {
        	return GetAngle(true);
        }
        
        public double GetAngle(boolean inTrueBearings)
        {
            return UGameLogic.dirToPoint(startX,startY,endX,endY,inTrueBearings);
        }

        /*
		private Boolean hasGradient=false;
		private float errorMargin=0.001f;

		public Boolean checkHasGradient(){
			if(Math.abs(endX-startX)>errorMargin)
				hasGradient=true;
			else
				hasGradient=false;
			return false;
		}
		public float GetGradient(){
			return (endY-startY)/(endX-startX);
		}
		public float getC(float gradient){
			return startY-startX*gradient;
		}



		public Boolean intersectsWith(CartesianLine line2){
			float intersectX=0;
			float intersectY=0;
			float m1=GetGradient();
			float m2=line2.GetGradient();



            float c1=0;
			if(m1 !=null){
				 c1=getC(m1);
			}
			if(m2!=null){				
				float c2=getC(m2);
				//The gradient is defined for both lines
				if(m1!=null){
					float diffGrad=m1-m2;
					//The gradients are diffent
					if(Math.abs(diffGrad)>errorMargin){
						intersectX=((c1-c2)/(diffGrad));
						intersectY=m1*intersectX+c1;
                        return InXBounds(intersectX,line2);
					}
					else{
					//The gradients are the same.
						if(c1==c2)
							return true;
						return false;
					}
				}
				//m1 must be a straight vertical line
					intersectY=c2+startX*m2;
					return InYBounds(intersectY,line2);
				}
			else if(m1!=null){
				intersectY=c1+line2.startX*m1;
                return InYBounds(intersectY,line2);
			}
			else
			{
				//B0TH ARE VERTICAL LINES
                return (InYBounds(startY,line2) ||  InYBounds(endY,line2) ||  InYBounds(line2.startY,line2));
			}

		}
    */



    public double yCoordAt(double x){
        if(endX-startX==0){
            if (gradient() == 0)
                return startX;
            return UGameLogic.UNSET_INT;
        }
        return gradient() * x+yAxisIntersect();

    }

    public double xCoordAt(double y){

        if (gradient()==UGameLogic.UNSET_INT)
        {
            return UGameLogic.UNSET_INT;
        }
        if(gradient()==0){
            return y;
        }


        return (y - yAxisIntersect()) / gradient();
    }



        //Are the ends of the lines touching each other within a given margin
        public boolean EndsTouching(CartesianLine line)
        {
        	return EndsTouching(line,2);
        }
        
        public boolean EndsTouching(CartesianLine line, int marginOfError)
        {
            if (UGameLogic.GetDistanceBetween(startX, startY, line.startX, line.startY) < marginOfError)
            {
                return true;
            }
            if (UGameLogic.GetDistanceBetween(startX, startY, line.endX, line.endY) < marginOfError)
            {
                return true;
            }
            if (UGameLogic.GetDistanceBetween(endX, endY, line.startX, line.startY) < marginOfError)
            {
                return true;
            }
            if (UGameLogic.GetDistanceBetween(endX, endY, line.endX, line.endY) < marginOfError)
            {
                return true;
            }
            return false;
        }

        //Generate a new translated line;
        public CartesianLine GenerateNewTranslated(float xOffset, float yOffset)
        {
            return new CartesianLine(startX + xOffset, startY + yOffset, endX + xOffset, endY + yOffset);
        }


        public Point2D GetNearestPointOnLineTo(Point2D point)
        {
            return GetNearestPointOnLineTo(point.x, point.y);
        }

        public Point2D GetNearestPointOnLineTo(double px, double py)
        {
            Point2D SE = new Point2D((float)(endX - startX), (float)(endY - startY));
            Point2D SP = new Point2D((float)(px - startX), (float)(py - startY));
            double t = Point2D.DotProduct(SE, SP) / (SE.magnitudeSquared());
            t = Math.max(0, t);
            t = Math.min(1, t);
           // UGameLogic.LogMsg("SE " + SE);
            //UGameLogic.LogMsg("SP " + SP);
            //UGameLogic.LogMsg("magnitude " + SE.magnitude);
            //UGameLogic.LogMsg("magnitudeSq " + SE.magnitudeSquared);
            //UGameLogic.LogMsg("dotProduct " +  Point2D.DotProduct(SE, SP)+"\n");
            return new Point2D((float)(startX + t * SE.x), (float)(startY + t * SE.y));
        }

        //Get the nearest end coordinate to;
        public Point2D_Double GetNearestEndCoordTo(double px, double py)
        {
            double dist1 = UGameLogic.GetDistanceBetween(px, py, startX, startY);
            double dist2 = UGameLogic.GetDistanceBetween(px, py, endX, endY);
            double resX;
            double resY;
            if (dist1 < dist2)
            {
                resX = startX;
                resY = startY;
            }
            else
            {
                resX = endX;
                resY = endY;
            }
            return new Point2D_Double(resX,resY);
            
        }

        //Gets the nearest coordinate to a given x point;
        public Point2D_Double GetNearestEndCoordToX(double px)
        {
        	double resX;
        	double resY;
            if (Math.abs(startX - px) < Math.abs(endX - px))
            {
                resX = startX;
                resY = startY;
            }
            else
            {
                resX = endX;
                resY = endY;
            }
            return new Point2D_Double(resX,resY);
        }


        public Point2D_Double GetFurthestEndCoordToX(double px)
        {
        	double resX;
        	double resY;
            if (Math.abs(startX - px) < Math.abs(endX - px))
            {
                resX = endX;
                resY = endY;

            }
            else
            {
                resX = startX;
                resY = startY;
            }
            return new Point2D_Double(resX,resY);
        }

        public double GetDistanceToPoint(Point2D point)
        {
            return GetDistanceToPoint(point.x, point.y);
        }

        public double GetDistanceToPoint(double px, double py)
        {
            Point2D nearestPoint =GetNearestPointOnLineTo(px,py);
            return UGameLogic.GetDistanceBetween(nearestPoint.x, nearestPoint.y, px, py);
        }
        
        //UNCONVERTED FUNCTIONS
        /*   
        public Point2D NextPointHorizontallyOnLine(double initX, double hDir,boolean inBounds,out Boolean reachedBounds)
        {
            double angle = GetAngle();
            double angleR = UGameLogic.TrueBearingsToRadians(angle);
            double initY = yCoordAt(initX);
            if (startX > endX)
            {
                hDir = hDir * -1;
            }
            double moveX = hDir*Math.Cos(angleR);
            //double moveY = hDir*-Math.Sin(angleR);
            double xVal = moveX + initX;
            reachedBounds = false;
            //Restrict to bounds;
            if (inBounds)
            {
                double prevXVal = xVal;
                xVal = Math.Max(xMin, xVal);
                xVal = Math.Min(xMax, xVal);
                if (xVal == xMax || xVal == xMin)
                {
                    reachedBounds = true;
                }
            }
            double yVal = yCoordAt(xVal);
            if (yVal == UGameLogic.UNSET_INT)
            {

                return null;
            }
            
            return new Point2D((float)(xVal), (float)(yVal));
            
        }
        
        //Find all the intersection points;
        public List<Point2D> IntersectionWithMultiArc(Dictionary<double, PointWithObj<double>> multiArc){
            List<Point2D> intersects=new List<Point2D>();

            double prevAngle = UGameLogic.UNSET_INT;
                foreach (double ang in multiArc.Keys)
                {
                    if (prevAngle <= UGameLogic.UNSET_INT + 0.01f)
                    {
                        goto setPrevAngle;
                    }

                    double ang2=prevAngle;
                    Point2D point=multiArc[ang].point;
                    double dist=multiArc[ang].obj;
                    Point2D pointFound1;
                    Point2D pointFound2;
                    IntersectsArc(point.x, point.y, dist, ang, ang2, out pointFound1, out pointFound2);
                    if (pointFound1 != null)
                    {
                        intersects.Add(pointFound1);
                    }
                    if(pointFound2!=null)
                    {
                        intersects.Add(pointFound2);
                    }
                    setPrevAngle:
                        prevAngle=ang;
                }


            return intersects;
        }
        
        
                //Find the intersection points of an arc (if any);
        public void IntersectsArc(double centerX,double centerY,double radius,double lowerAngle,double upperAngle,out Point2D pointOnArc1,out Point2D pointOnArc2)
        {
            IntersectsCircle(centerX, centerY, radius, out pointOnArc1, out pointOnArc2);

            //Check both angles are in bounds (if they're not then set them to null;
            double ang;

            //UGameLogic.LogMsg("line: {" + (this.startX - centerX) + "," + (this.startY - centerY) + "} {" + (this.endX - centerX) + "," + (this.endY - centerY) + "}");
            //UGameLogic.LogMsg("Circle {" + centerX + "," + centerY + "} radius: " + radius);

            if (pointOnArc1 != null)
            {

                ang = UGameLogic.DirToPoint(centerX, centerY, pointOnArc1.x, pointOnArc1.y);

                if (!UGameLogic.AngleInBounds(ang, lowerAngle, upperAngle))
                {

                    pointOnArc1 = null;
                }
                else
                {

                }
            }
            if (pointOnArc2 != null)
            {
                ang = UGameLogic.DirToPoint(centerX, centerY, pointOnArc2.x, pointOnArc2.y);

                if (!UGameLogic.AngleInBounds(ang, lowerAngle, upperAngle))
                {
                    pointOnArc2 = null;
                }
                else
                {

                }
            }


            //Find the points in which the line intersects the circle;


        }
*/
}


