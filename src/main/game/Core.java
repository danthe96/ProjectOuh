package main.game;

import main.Settings;

import com.jme3.app.SimpleApplication;
<<<<<<< HEAD
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
=======
>>>>>>> ddd22fb7eeee5840beac4058e69cfb3ab54ced60
import com.jme3.util.SkyFactory;

public class Core extends SimpleApplication {
	Settings settings;

	public static void main(String[] args) {
		Core coreapp = new Core();
		coreapp.start();
	}

	@Override
	public void simpleInitApp() {
		settings = new Settings();
		rootNode.attachChild(SkyFactory.createSky(assetManager,
				"assets/Textures/OutputCube2.dds", false));
		initKeys();
	}

	private void initKeys() {

		inputManager.clearMappings();
		inputManager.addMapping("ACCELERATE", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("DECELERATE", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("RIGHT", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("LEFT", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("ROTATE_RIGHT", new MouseAxisTrigger(
				MouseInput.AXIS_X, true));
		inputManager.addMapping("ROTATE_LEFT", new MouseAxisTrigger(
				MouseInput.AXIS_X, false));
		inputManager.addMapping("STEER_UP", new MouseAxisTrigger(MouseInput.AXIS_Y,
				false));
		inputManager.addMapping("STEER_DOWN", new MouseAxisTrigger(
				MouseInput.AXIS_Y, true));
		inputManager.addListener(ReaperListener.actionListener, "ACCELERATE",
				"DECELERATE", "RIGHT", "LEFT");
		inputManager.addListener(ReaperListener.analogListener, "ACCELERATE",
				"DECELERATE", "RIGHT", "LEFT");

	}

}
