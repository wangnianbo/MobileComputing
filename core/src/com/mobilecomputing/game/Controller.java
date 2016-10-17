package com.mobilecomputing.game;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mobilecomputing.game.Drawables.GlobalAnimations;
import com.mobilecomputing.game.Drawables.SpriteImageData;
import com.mobilecomputing.game.WormSkins.WormSkin;
import com.mobilecomputing.game.menus.DefaultGameHud;
import com.mobilecomputing.game.menus.LaunchMenu;
import com.mobilecomputing.game.menus.Menu;
import com.mobilecomputing.game.network.Bluetooth.BluetoothConnection;
import com.mobilecomputing.game.shareScores.ShareScores;

public class Controller {
	public static int offsetX = 0;
	public static int screenWidth;
	public static int screenHeight;
	public static int projectionWidth=640;
	public static int projectionHeight=480;
	public static SpriteBatch batch;
	public static World world;
	public static World prevWorld;
	public static int lastScore= 0;
	public static int hiScore=0;

	
	public static World SetWorld(World newWorld){
		if(world == null){
			prevWorld=newWorld;
		}else {
			prevWorld=world;
		}

		world=newWorld;
		return world;
	}


	public static Menu activeMenu;
	public static OrthographicCamera activeCamera;
	public static OrthographicCamera guiCam;
	public static OrthographicCamera worldCam;
	public static Random spawnRandom;
	public static BluetoothConnection _bluetoothConnection = null;
	public static ShareScores _shareScores = null;
	public static boolean isServer = false;

	public static InputStream inputStream;
	public static OutputStream outputStream;

	public enum RenderMode{GAME,GUI};
	private static RenderMode getRenderMode(){
		if(activeCamera==worldCam){
			return RenderMode.GAME;
		}
		return RenderMode.GUI;
	}

	public static void setRenderMode(RenderMode newRenderMode){

			if(newRenderMode==RenderMode.GAME){
				activeCamera=worldCam;
			}
			else{
				activeCamera=guiCam;

			}
			batch.setProjectionMatrix(activeCamera.combined);

	}



	public static void initializeGame(BluetoothConnection bluetoothConnection, ShareScores shareScores) {
		_bluetoothConnection = bluetoothConnection;
		_shareScores = shareScores;
		spawnRandom=new Random();
		for (Character c = 'A'; c < 'Z'; c++) {
			String testString = "" + c;
			testString = testString.toLowerCase();
			/*UGameLogic.LogMsg("keyMapping.put(Input.Keys." + c + ",\"" + testString + "\")");*/

		}

		batch = new SpriteBatch();
		// img = new Texture("images/badlogic.jpg");


		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		//GUI Camera setup
		guiCam =new OrthographicCamera(screenWidth,screenHeight);
		guiCam.zoom= Math.max(projectionWidth/((float)screenWidth),projectionHeight/((float)screenHeight));
		//		guiCam.position.set(projectionWidth / (2f), (screenHeight*2-projectionHeight) / (2f), 0);

		guiCam.position.set(projectionWidth / (2f), (screenHeight-projectionHeight/2f), 0);
		activeCamera=guiCam;
		//World Camera setup
		worldCam =new OrthographicCamera(screenWidth,screenHeight);
		worldCam.zoom= Math.max(projectionWidth/((float)screenWidth),projectionHeight/((float)screenHeight));
		worldCam.position.set(projectionWidth / (2f), (screenHeight-projectionHeight/2f), 0);

		//UGameLogic.LogMsg("Screen Width: "+screenHeight+"Position.y"+ guiCam.position.x);
		//UGameLogic.LogMsg("Screen Height: "+screenHeight+"Position.y"+ guiCam.position.y);
		//guiCam.position.set(screenWidth / (2f), screenHeight / (2f), 0);
		//UGameLogic.LogMsg("Loading Sprites");
		SpriteImageData.loadAllSprites(batch);
		SoundController.loadAllSounds();
		GlobalAnimations.InitializeAnimations();
		FontController.InitializeFonts();
		if(!slitherio.jumpInMultiplayer) {
			swapGameMode(GameMode.SPLASH);
		}
		else{
			swapGameMode(GameMode.SP_GAME);
		}
		slitherio.jumpInMultiplayer=false;
	}


	//public static float camRotation=0;
	public static void update() {
		slitherio.countUPS ++;
		if(step%20==0){
			//UGameLogic.LogMsg("Camera zoom "+guiCam.zoom);
		}
		//camRotation+=0.1f;
		//camRotation=(float)UGameLogic.PMod(camRotation,360);
		//guiCam.zoom=0.9f;
		float worldZoom=1;
		if(world!=null){
			worldZoom=world.cameraZoomRatio;
		}

		worldCam.zoom= Math.max(projectionWidth/((float)screenWidth),projectionHeight/((float)screenHeight))*worldZoom;


		if(activeMenu!=null){
			activeMenu.update();
		}

		if(world!=null){
			if(world.activeCharacter!=null){
				worldCam.position.x=world.activeCharacter.x;
				worldCam.position.y=screenHeight-world.activeCharacter.y;
			}

			int m=0;
			/*
			float w=(projectionWidth)/(2*worldCam.zoom);
			float h=(projectionHeight)/(2*worldCam.zoom);
			if (worldCam.position.x < w-m)
				worldCam.position.x = w-m;
			if (worldCam.position.y < h-m)
				worldCam.position.y = h-m;
				if (worldCam.position.x > world.width+m - w)
					worldCam.position.x = world.width+m - w;
				if ( worldCam.position.y > world.height+m - h)
					worldCam.position.y = world.height+m - h;
				if (w > (world.width+m)/2)
				{
					worldCam.position.x += w - (world.width+m) / 2;
				}
				if (h > (world.height+m)/2)
				{
					worldCam.position.y += h - (world.height+m) / 2;
				}
			*/

			world.update();
		}
		

		step++;
		offsetX = offsetX + 1;

		
	}

	public static int step = 0;

	public static void render() {

		guiCam.update();
		worldCam.update();

		setRenderMode(RenderMode.GAME);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		if(world!=null){
			world.Render();
		}
		setRenderMode(RenderMode.GUI);

		if(activeMenu!=null){
			activeMenu.render();
		}

		// batch.draw(img, 0, 100);
		batch.end();

	}

	public enum GameMode{SPLASH,SP_GAME,MP_GAME, share_scores, MP_GAME_HOST};
	private static GameMode gameMode;
	public GameMode getGameMode(){
		return gameMode;
	}
	public static void swapGameMode(GameMode newMode){
		if(newMode!=gameMode){
			if(newMode==GameMode.SPLASH){
				activeMenu=new LaunchMenu(0,0, _bluetoothConnection);
				if(world != null){
					prevWorld = world;
				}
				world=null;

			}
			else{

				activeMenu=null;
				switch(newMode){
					case SP_GAME:
						
						activeMenu=new DefaultGameHud(0,0);
						world= SetWorld( new World());
					break;
					case MP_GAME:
						System.out.println(_bluetoothConnection != null);
						_bluetoothConnection.gotoBluetoothSet();


						//activeMenu=new DefaultGameHud(0,0);
						//world=new World();



					break;
					case share_scores:
						_shareScores.alreadyShared();
						//world=new World();


						_shareScores.shareSconresOnSocialMedia("I just played slither and got a length of "+hiScore+"! Can you beat that?");
						WormSkin.unlockSkins();

						//activeMenu=new DefaultGameHud(0,0);
						//world=new World();



						break;
					case MP_GAME_HOST:
						world = SetWorld( new World());



					break;
				}
			}

		}
	}

}
