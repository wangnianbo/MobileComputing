package com.mobilecomputing.game.OldHitDetection;


public class AABB extends HitBox
{

    //Instanciate a hit box using only width and height;
    public AABB(int width, int height)
    {
    	super(width,height);
    }

    public AABB(int x1, int y1, int width, int height)
    {
    	super(x1,y1,width,height);
    }



      //Check if two hit shapes are touching
    @Override
    public boolean do_CheckOffsetTouching(int xo1, int yo1, SimpleCollider sc2, int xo2, int yo2, int margin)
    {

        if (!(sc2 instanceof AABB))
        {
  
            return super.do_CheckOffsetTouching(xo1, yo1, sc2, xo2, yo2, margin);
        }
             int x1 = this.xMin + xo1;
            int y1 = this.yMin + yo1;
            int w1 = this.GetBoundWidth();
            int h1 = this.GetBoundHeight();
            if (margin>0)
            {
                x1 = x1 - margin;
                y1 = y1 - margin;
                w1 = w1 + margin*2;
                h1 = h1 + margin*2;
            }
            

            int x2 = sc2.xMin + xo2;
            int y2 = sc2.yMin + yo2;
            int w2 = sc2.GetBoundWidth();
            int h2 = sc2.GetBoundHeight();
            float b1 = y1 + h1-1;
            float b2 = y2 + h2-1;
            float r1 = x1 + w1 - 1;
            float r2 = x2 + w2 - 1;
            //|| (y2+h2>=y1 && y2+h2<=y1+h1)
            //Check y's are in range of each other
            if ((y1 >= y2 && y1 <= b2) || (b1 >= y2 && b1 <= b2) || (y2 >= y1 && y2 <= b1))
            {

                //Check x's are in range of each other
                // ||(x2+w2>=x1 && x2+w2<=x1+w1)
                if ((x1 >= x2 && x1 <= r2) || (r1>= x2 && r1 <= r2) || (x2 >= x1 && x2 <= r1))
                {
                    return true;
                }
            }

        return false;

    }

}
