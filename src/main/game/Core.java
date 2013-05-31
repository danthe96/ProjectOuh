package main.game;

import main.Settings;
import main.game.entities.controls.ReaperControl;
import main.game.entities.controls.SpacecraftControl;
import main.game.entities.userinput.ReaperListener;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
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

		Box box1 = new Box(Vector3f.ZERO, 1, 1, 1);
		Geometry blue = new Geometry("Box", box1);
		Material mat1 = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat1.setColor("Color", ColorRGBA.Blue);
		blue.setMaterial(mat1);
		SpacecraftControl space = new ReaperControl(blue, 6);
		blue.addControl(space);
		rootNode.attachChild(blue);

		ReaperListener.spacecraft = space;
		initKeys();
	}

	private void initKeys() {
		inputManager.clearMappings();
		// HashMap<String,String> reaperControls =
		// settings.getSettingsMap("ReaperControls");
		// for(String key: reaperControls.keySet()){
		// inputManager.addMapping(key, new
		// KeyTrigger(Integer.parseInt(reaperControls.get(key))));
		// }
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
		inputManager.addListener(ReaperListener.analogListener, "ROTATE_LEFT",
				"ROTATE_RIGHT", "STEER_UP", "STEER_DOWN");

	}

}
