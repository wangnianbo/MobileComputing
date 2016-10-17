package com.mobilecomputing.game.menus;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.ViewableObject;
import com.mobilecomputing.game.OldHitDetection.SimpleCollider;

public class MenuElement extends ViewableObject {
	public String _identifier="element_";


	//Collider is used to determine if finger is over the menu element;
	public Shape2D shapeCollider;
	//Finger collider is quite large to accomodate margin of errors with tapping screen;
	public static Shape2D fingerCollider=new Rectangle(-32,-32,64,64);

	//public SimpleCollider simpleCollider;

	//Menu it's a part of
	public Menu parentMenu=null;
	//Each MenuElement has an identifier or message to send to the containing menu when reacting to the user;
    public String getIdentifier() {

            if (_identifier == null)
                return null;
            String returnValue = _identifier;
            returnValue = returnValue.toLowerCase();
            return returnValue;
    }

	public void setIdentifier(String newIdentifier){
		if (newIdentifier != null)
		{
			_identifier = newIdentifier;
		}
	}

	//Coordinates relative to gui camera;
	public float ax(){
		return x;
	}

	public float ay(){
		return y;
	}
	//Is the user tapping the element?
	public boolean isTapOver(float tapX,float tapY){
		if(shapeCollider==null){
			return true;
		}
		return UGameLogic.CollisionOffsetBetween(shapeCollider,ax(),ay(),fingerCollider,tapX,tapY);
	}




    
    public MenuElement(float x,float y)
    {
    	super(x,y);
        parentMenu=null;
    }





	public void onGlobalTap(float x, float y){

	}

    
    public void sendMessage(String message){
    	if(parentMenu!=null){
    		parentMenu.receiveMessage(this,message);
    	}
    }
	
	
	public void onMouseClick(){
		OnPressed();
	}
	
	public void OnPressed(){
		sendMessage(getIdentifier());
	}
	
	/*
    public Boolean IsMouseOver()
    {
        float mouseX = UGameLogic.mouseX;
        float mouseY = UGameLogic.mouseY;
        if(this as AuraOfWorldsNamespace.SelectBox != null)
        {
            return true;
        }
        if ( lastUpdateVal==Menu.staticLastUpdateVal)
        {

            return IsGlobalPointOver(mouseX, mouseY);
        }
        return false;
    }
    */
}
