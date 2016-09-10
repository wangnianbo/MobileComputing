package com.mobilecomputing.game.menus;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.ViewableObject;
import com.mobilecomputing.game.OldHitDetection.SimpleCollider;

public class MenuElement extends ViewableObject {
	public String _identifier="element_";
	public Shape2D shapeCollider;
	public SimpleCollider simpleCollider;
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
	public Menu parentMenu=null;
    
    public MenuElement(float x,float y)
    {
    	super(x,y);
        parentMenu=null;
    }

	public float ax(){
		return x;
	}

	public float ay(){
		return y;
	}

	public boolean isTapOver(float tapX,float tapY){
		if(shapeCollider==null){
			return true;
		}
		return UGameLogic.CollisionOffsetBetween(shapeCollider,ax(),ay(),fingerCollider,tapX,tapY);
	}



	public static Shape2D fingerCollider=new Rectangle(-32,-32,64,64);

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
