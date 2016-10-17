package com.mobilecomputing.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.Drawables.SpriteImageData;

import java.util.ArrayList;

public class Menu extends MenuElement{
	ArrayList<MenuElement> elements=new ArrayList<MenuElement>();

	public Menu(float x,float y){
		super(x,y);
	}
	
	public MenuElement addElement(MenuElement element){
		elements.add(element);
		element.parentMenu=this;
		return element;
	}

	public void respondToTapAtTopLevel(float x, float y){
		onGlobalTap(x,y);
		if(!destroyed){
			if(isTapOver(x,y)){
				onMouseClick();
			}
		}
		//UGameLogic.LogMsg("Tapped "+this+" "+x+" "+y);
		for(MenuElement el:elements){
			if(el!=null && !el.destroyed){
				el.onGlobalTap(x,y);
				if(el.isTapOver(x,y)){
					el.onMouseClick();
				}
			}

		}

	}

	public Menu prevGameMenu=null;



	public void onBackPressed(){
		if(prevGameMenu!=null){
			Controller.activeMenu=prevGameMenu;
		}
		UGameLogic.LogMsg("Back Pressed");
	}
	
	public ImageButton backButton=null;
	public void addBackButton(){
		if(backButton==null){
			SpriteImageData backImage=SpriteImageData.GetByName("ui/backButton");
		
			backButton=new ImageButton(0,0,backImage,"back");
			float s=32/(float)(backImage.getHeight());
			backButton.setScale(s, s);
			backButton.setCentered(false);
			addElement(backButton);
			
		}
	}


	@Override
	public void update(){
		super.update();
		if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
			onBackPressed();
		}
		for(MenuElement element : elements){
			element.update();
		}
		for(MenuElement element : elements){

		}
	}
	
	@Override
	public void render(){
		for(MenuElement element : elements){
			element.render();
		}
	}
	
	public void receiveMessage(MenuElement element,String message){
		if(message!=null && message.toLowerCase().equals("back")){
			onBackPressed();
		}
	}




	  /*private int selectedIndex=0;
		public int getSelectedIndex(){
			return selectedIndex;
		}
		public int setSelectedIndex(int newVal){
			selectedIndex=newVal;

			if(selectedIndex>elements.size()-1){
				selectedIndex=0;
			}
			if(selectedIndex<0){
				selectedIndex=elements.size()-1;
			}
			return selectedIndex;
		}
		public MenuElement getSelectedElement(){
			if(selectedIndex<0){
				return null;
			}
			return elements.get(selectedIndex);
		}
	*/

}
