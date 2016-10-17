package com.mobilecomputing.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mobilecomputing.game.GameObjects.WormHead;
import com.mobilecomputing.game.GameObjects.WormHead_Player;
import com.mobilecomputing.game.network.Bluetooth.BluetoothConnection;
import com.mobilecomputing.game.shareScores.ShareScores;

public class slitherio extends ApplicationAdapter implements InputProcessor {
	/*SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		int screenWidth=Gdx.graphics.getWidth();
		int screenHeight=Gdx.graphics.getHeight();
		System.out.println("screenWidth "+screenWidth);
		System.out.println("screenHeight "+screenHeight);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	*/

	SpriteBatch batch;
	//Texture img;
	public static int screenWidth;
	public static int screenHeight;
	public static BluetoothConnection bluetoothConnection;
	public static ShareScores shareScores;
	public static boolean isNetGame = false;
	public static boolean jumpInMultiplayer=false;
	public slitherio(){
		this.bluetoothConnection = null;

	}
	public slitherio(BluetoothConnection bluetoothConnection, ShareScores shareScores){
		this.bluetoothConnection = bluetoothConnection;
		this.shareScores = shareScores;
	}
	@Override
	public void create () {

		/*File dir = new File(".");
		File[] filesList = dir.listFiles();
		for (File file : filesList) {
			if (file.isFile()) {
				System.out.println(file.getName());
			}
		}
		*/

		FileHandle dirHandle;
		if (Gdx.app.getType() == Application.ApplicationType.Android)
		{
			UGameLogic.LogMsg("Android");
			dirHandle = Gdx.files.internal("data");
		}
		else
		{
			UGameLogic.LogMsg("Desktop");
			dirHandle = Gdx.files.internal("./bin/data");
		}
		Array<FileHandle> handles = new Array<FileHandle>();
		UGameLogic.LogMsg("handles "+dirHandle);
		/*(for(FileHandle handle:dirHandle){
			UGameLogic.LogMsg("handle "+dirHandle);
		}*/
		getHandles(dirHandle,handles);

		Controller.initializeGame(bluetoothConnection, shareScores);
		Gdx.input.setInputProcessor(this);
		//RecurseDirectory("bin"+ File.separator +"images","bin"+File.separator);

	}
	public void getHandles(FileHandle begin, Array<FileHandle> handles)
	{
		FileHandle[] newHandles = begin.list();
		UGameLogic.LogMsg("list "+newHandles+" "+newHandles.length);
		for (FileHandle f : newHandles)
		{
			if (f.isDirectory())
			{
				UGameLogic.LogMsg("folder "+ f);
				Gdx.app.log("Loop", "isFolder!");
				getHandles(f, handles);
			}
			else
			{
				Gdx.app.log("Loop", "isFile!");
				UGameLogic.LogMsg("file "+ f);
				handles.add(f);
			}
		}
	}
/*


	void displayFiles (AssetManager mgr, String path, int level) {

		Log.v(TAG,"enter displayFiles("+path+")");
		try {
			String list[] = mgr.list(path);
			Log.v(TAG,"L"+level+": list:"+ Arrays.asList(list));

			if (list != null)
				for (int i=0; i<list.length; ++i)
				{
					if(level>=1){
						displayFiles(mgr, path + "/" + list[i], level+1);
					}else{
						displayFiles(mgr, list[i], level+1);
					}
				}
		} catch (IOException e) {
			Log.v(TAG,"List error: can't list" + path);
		}

	}
	*/

    public static float framesForUpdateDoubled = 0;
	public static int countUPS=0;
	public static float lastUPS=0;
	public static float lastTickUPS=0;
	public static float tDiff=0;
	@Override
	public void render () {

		float deltaTime= Gdx.graphics.getDeltaTime();
		tDiff +=(deltaTime*1000);
		if (tDiff > 1000)
		{
			lastUPS = countUPS * 1000 / (tDiff);
			//lastTickUPS = currentTick;
			tDiff=0;
			countUPS = 0;
		}

		//UGameLogic.LogMsg("time delta"+deltaTime);
		float lastFrameDiff = deltaTime*60f;
		//UGameLogic.LogMsg("last frame diff"+lastFrameDiff);
		boolean allowDoublingOfFrames=true;
		 double minExpectedSpeed = 1;
        if (allowDoublingOfFrames)
        {
            int maxFrameMultiplier = 2;
            if (lastFrameDiff > minExpectedSpeed)
            {
                framesForUpdateDoubled += (float)Math.min(((lastFrameDiff - minExpectedSpeed)) / minExpectedSpeed, 2*maxFrameMultiplier);

            }
            else
            {
                framesForUpdateDoubled += (float)Math.max((lastFrameDiff - minExpectedSpeed) / minExpectedSpeed, -1*maxFrameMultiplier);
            }
            //Use to be 3 instead of 6
            framesForUpdateDoubled = Math.max(framesForUpdateDoubled, -1);
            framesForUpdateDoubled = Math.min(framesForUpdateDoubled, 4);
            //lastFramesForUpdatesDoubled = (int)framesForUpdateDoubled;
            for (int i = 0; i < maxFrameMultiplier*2-1; i++)
            {
                if (framesForUpdateDoubled >= 1f)
                {
                    framesForUpdateDoubled -= 1f;
                    Controller.update();
                }

            }
        }

		
		
		Controller.update();
		//UGameLogic.LogMsg("Rendering");
		Controller.render();

	}

	@Override
	public void dispose () {
		//batch.dispose();
		//img.dispose();
	}

	//Vector3 tp = new Vector3();
	public static boolean dragging;
	@Override public boolean mouseMoved (int screenX, int screenY) {
		// we can also handle mouse movement without anything pressed
//      camera.unproject(tp.set(screenX, screenY, 0));
		return false;
	}


	public static float RightGuiScreenX(){
	
		float val=Controller.guiCam.unproject(new Vector3(Controller.screenWidth,0,0)).x;
		return val;
	}
	
	public static float LeftGuiScreenX(){
		return Controller.guiCam.unproject(new Vector3(0,0,0)).x;
		
	}
	
	public static float lastGuiTapX =Controller.projectionWidth/2;
	public static float lastGuiTapY =0;
	public static float lastWorldTapX=Controller.projectionWidth/2;
	public static float lastWorldTapY=0;
	public static float lastWorldDragX=Controller.projectionWidth/2;
	public static float lastWorldDragY=0;

	
	

	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		// ignore if its not left mouse button or first touch pointer
		//UGameLogic.LogMsg("Coordinate "+screenX+" "+screenY);
		//UGameLogic.LogMsg("Pointer "+pointer);
		if (button != Input.Buttons.LEFT || pointer > 0) return false;


		//UGameLogic.LogMsg("Coordinate "+screenX+" "+screenY);
		Vector3 guiCoord=new Vector3(screenX,screenY,0);
		//coord.mul(Controller.guiCam.projection);
		OrthographicCamera cam=Controller.guiCam;
		guiCoord=Controller.guiCam.unproject(guiCoord);
		guiCoord.y=Controller.screenHeight-guiCoord.y;
		lastGuiTapX =guiCoord.x;
		lastGuiTapY =guiCoord.y;

		if(Controller.activeMenu!=null){
			Controller.activeMenu.respondToTapAtTopLevel(guiCoord.x,guiCoord.y);
		}
		Vector3 worldCoord=new Vector3(screenX,screenY,0);
		//coord.mul(Controller.guiCam.projection);
		cam=Controller.worldCam;
		worldCoord=Controller.worldCam.unproject(worldCoord);
		worldCoord.y=Controller.screenHeight-worldCoord.y;
		lastWorldTapX=worldCoord.x;
		lastWorldTapY=worldCoord.y;
		//camera.unproject(tp.set(screenX, screenY, 0));
		dragging = true;
		return true;
	}

	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
		if (!dragging) return false;
		//camera.unproject(tp.set(screenX, screenY, 0));
		Vector3 worldCoord=new Vector3(screenX,screenY,0);
		//coord.mul(Controller.guiCam.projection);
		OrthographicCamera cam=Controller.worldCam;
		worldCoord=Controller.worldCam.unproject(worldCoord);
		worldCoord.y=Controller.screenHeight-worldCoord.y;
		lastWorldDragX=worldCoord.x;
		lastWorldDragY=worldCoord.y;
		//camera.unproject(tp.set(screenX, screenY, 0));
		dragging = true;


		return true;
	}

	@Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if (button != Input.Buttons.LEFT || pointer > 0) return false;
		//camera.unproject(tp.set(screenX, screenY, 0));
		WormHead_Player.SignalReleased();

		dragging = false;
		return true;
	}

	@Override public boolean keyDown (int keycode) {
		return false;
	}

	@Override public boolean keyUp (int keycode) {
		return false;
	}

	@Override public boolean keyTyped (char character) {
		return false;
	}

	@Override public boolean scrolled (int amount) {
		return false;
	}
}
