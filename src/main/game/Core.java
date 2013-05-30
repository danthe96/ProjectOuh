package main.game;

import main.Settings;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.util.SkyFactory;

public class Core extends SimpleApplication {

	public static void main(String[] args) {
		Core coreapp = new Core();
		coreapp.start();
	}

	@Override
	public void simpleInitApp() {
		Settings settings = new Settings();
		rootNode.attachChild(SkyFactory.createSky(assetManager,
				"assets/Textures/OutputCube.dds", false));
		Vector3f vec;
	}

}
