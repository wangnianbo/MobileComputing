package com.mobilecomputing.game;

public class ViewableObject {
    //public SimpleCollider simpleCollider;
    public boolean destroyed = false;
    public float x=0;
    public float y=0;
    public float prevX=0;
    public float prevY=0;
    public int step;
    
    public ViewableObject(float x, float y) {
    	this.x=x;
    	this.y=y;
        this.prevX = this.x;
        this.prevY = this.y;
		// TODO Auto-generated constructor stub
	}

	public void instanceDestroy()
    {
        if (destroyed == false)
        {
            destroyed = true;
            onDestroy();
        }
    }
    
//On destroying the object
 public void onDestroy()
 {
 }
 
 public void onDeath(){
	 
 }


 //Draw the object;
public void render() {
}
//Perform these actions each frame
public void update()
{
	step++;
}
//Actions to perform at the start of each frame;
public void earlyUpdate()
{
    this.prevX = this.x;
    this.prevY = this.y;
    
}







}