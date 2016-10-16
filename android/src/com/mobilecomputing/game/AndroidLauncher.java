package com.mobilecomputing.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mobilecomputing.game.bluetooth.AndroidBluetoothConnection;
import com.mobilecomputing.game.bluetooth.MainActivity;
import com.mobilecomputing.game.shareScores.AndroidShareScores;

import java.io.InputStream;
import java.io.OutputStream;

public class AndroidLauncher extends AndroidApplication {
	AndroidBluetoothConnection androidBluetoothConnection;
	AndroidApplicationConfiguration config;
	AndroidShareScores shareScores;

	/**
	 * Initialize the game!
	 * @param savedInstanceState the Instance which is saved!
	 *
     */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = new AndroidApplicationConfiguration();
		androidBluetoothConnection = new AndroidBluetoothConnection(this);
		shareScores = new AndroidShareScores(this);
		initialize(new slitherio(androidBluetoothConnection, shareScores), config);
	}

	/**
	 * When the game Resume, Create the game object.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume");
		//AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//androidBluetoothConnection = new AndroidBluetoothConnection(this);
		//AndroidShareScores shareScores = new AndroidShareScores(this);
		initialize(new slitherio(androidBluetoothConnection, shareScores), config);
	}

	/**
	 * Swap to Bluetooth connection activity
	 */
	public void gotoBluetoothSet(){
		Intent intent = new Intent(AndroidLauncher.this, MainActivity.class);

		startActivity(intent);
	}

	/**
	 * Switch to Share Scores Activity
	 * @param message The message which wants to share.
     */
	public void shareScores(String message){
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, message);

		startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
	}

}
