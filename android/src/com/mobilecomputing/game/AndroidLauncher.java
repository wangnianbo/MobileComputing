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
	InputStream inputStream;
	OutputStream outputStream;
	AndroidApplicationConfiguration config;
	AndroidShareScores shareScores;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = new AndroidApplicationConfiguration();
		androidBluetoothConnection = new AndroidBluetoothConnection(this);
		shareScores = new AndroidShareScores(this);
		initialize(new slitherio(androidBluetoothConnection, shareScores), config);
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume");
		//AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//androidBluetoothConnection = new AndroidBluetoothConnection(this);
		//AndroidShareScores shareScores = new AndroidShareScores(this);
		initialize(new slitherio(androidBluetoothConnection, shareScores), config);
	}

	public void gotoBluetoothSet(){
		Intent intent = new Intent(AndroidLauncher.this, MainActivity.class);

		startActivity(intent);
	}


	public void shareScores(String message){
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, message);

		startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
	}
	public InputStream getInputStream() {
		return inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}
}
