package main.game;

import main.Settings;

import com.jme3.app.SimpleApplication;

public class Core extends SimpleApplication{

	public static void main(String[] args){
		Core coreapp = new Core();
		coreapp.start();
	}
	
	@Override
	public void simpleInitApp() {
		Settings settings = new Settings();
		
		
	}

}
