package com.mobilecomputing.game.menus;

import com.mobilecomputing.game.Controller;
import com.mobilecomputing.game.Controller.GameMode;
import com.mobilecomputing.game.FontController;
import com.mobilecomputing.game.SoundController;
import com.mobilecomputing.game.UGameLogic;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.network.Bluetooth.BluetoothConnection;

public class LaunchMenu extends Menu {
	private BluetoothConnection bluetoothConnection;
	public LaunchMenu(float x, float y, BluetoothConnection bluetoothConnection){
		super(x,y);
		addElement(new ImageButton(Controller.projectionWidth/2,Controller.projectionHeight/3,SpriteImageData.GetByName("ui/button_localGame"),"local"));
		addElement(new ImageButton(Controller.projectionWidth/2,Controller.projectionHeight*2/4,SpriteImageData.GetByName("ui/button_BluetoothGame"),"Bluetooth Game"));
		addElement(new ImageButton(Controller.projectionWidth/2,Controller.projectionHeight*3/4,SpriteImageData.GetByName("ui/button_ShareScore"),"shareScore"));
		//addElement(new ImageButton(Controller.projectionWidth/2,Controller.projectionHeight*3/4,SpriteImageData.GetByName("ui/button_hostGame"),"host"));
		this.bluetoothConnection = bluetoothConnection;
			SoundController.PlaySound("explosion");
			}



	@Override
	public void receiveMessage(MenuElement sender,String msg){
		super.receiveMessage(sender, msg);
		msg=msg.toLowerCase();
		switch(msg){
			case "local":
				UGameLogic.LogMsg("Local");
				Controller.swapGameMode(GameMode.SP_GAME);
			break;
			case "bluetooth game":
				UGameLogic.LogMsg("Bluetooth Game");
				Controller.swapGameMode(GameMode.MP_GAME);

				//Controller.swapGameMode(GameMode.MP_GAME);
				//UGameLogic.LogMsg("Join Game");
			break;
			case "sharescore":
				UGameLogic.LogMsg("shareScore");
				Controller.swapGameMode(GameMode.share_scores);

				//Controller.swapGameMode(GameMode.MP_GAME);
				//UGameLogic.LogMsg("Join Game");
				break;
			case "host":
				Controller.swapGameMode(GameMode.MP_GAME_HOST);
				UGameLogic.LogMsg("Host Game");
			break;
			case "test":
				UGameLogic.LogMsg("Testing");
			break;
		}

	}
	
	public void update(){
		super.update();
		/*
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			setSelectedIndex(getSelectedIndex()-1);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			setSelectedIndex(getSelectedIndex()+1);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			MenuElement element=getSelectedElement();
			if(element!=null){
				element.OnPressed();
			}
		}
		*/
	}
	@Override
	public void onGlobalTap(float x,float y){
		super.onGlobalTap(x,y);
		//addElement(new ClickTest(x,y));
	}

	@Override
	public void render(){
		SpriteImageData.ResetProperties();
		SpriteImageData.Draw("title",Controller.projectionWidth/2,Controller.projectionHeight/8);
		super.render();


	}
  
}
