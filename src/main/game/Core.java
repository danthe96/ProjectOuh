package main.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Settings;
import main.game.entities.controls.ReaperControl;
import main.game.entities.controls.SpacecraftControl;
import main.game.entities.userinput.ReaperListener;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.util.SkyFactory;

public class Core extends SimpleApplication {
	private Settings settings;
	private BulletAppState bulletAppState;

	//this is the body/machine, where you are inside, which you are playing
	private Spatial character;

	private boolean camBehindChar = false;
	private static final float camDistanceBehindChar;

	public static void main(String[] args) {
		Core coreapp = new Core();
		coreapp.start();
	}

	@Override
	public void simpleUpdate(float tpf) {
		inputManager.setCursorVisible(false);//not mouse
	
		Quaternion q, p;
		q.set(character.getLocalTranslation());

		if (camBehindChar){
			p = new Quaternion(0, 0, 1, -camDistanceBehindChar); //-cam* or +cam* please test
			p.mult(character.getLocalRotation());
			q.addLocal(p);
		}

		cam.setLocation(q);
		cam.setRotation(character.getLocalRotation());

	}

	@Override
	public void simpleInitApp() {
		bulletAppState= new BulletAppState();
		stateManager.attach(bulletAppState);
		settings = new Settings();
		rootNode.attachChild(SkyFactory.createSky(assetManager,
				"assets/Textures/OutputCube2.dds", false));

		initSpacials();
		
		inputManager.setCursorVisible(false);//not mouse

		//ChaseCamera chaseCam = new ChaseCamera(cam, blue);
//		Cam = new Camera();

		flyCam.setEnabled(false);
		
		initKeys();
	}

	private void initSpacials() {
		Box box1 = new Box(Vector3f.ZERO, 1, 1, 1);
		Box boxstatic = new Box(Vector3f.ZERO, 5, 1, 1);
		Geometry blue = new Geometry("Box", box1);
		Geometry blue2 = new Geometry("Box2", boxstatic);
		Material mat1 = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		Material mat2 = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat1.setColor("Color", ColorRGBA.Blue);
		mat2.setColor("Color", ColorRGBA.Green);
		blue.setMaterial(mat1);
		blue2.setMaterial(mat2);
		SpacecraftControl space = new ReaperControl(blue, 6);
		blue.addControl(space);
		space.setGravity(new Vector3f(0f, 0f, 0.0001f));
		bulletAppState.getPhysicsSpace().add(space);
		ReaperListener.spacecraft = space;
		rootNode.attachChild(blue);
		rootNode.attachChild(blue2);

		character = blue;
	}

	private void initKeys() {
		inputManager.clearMappings();

		HashMap<String, String> reaperControls = settings.getSettingsMap("ReaperControls");
		List<String> actionKey = new ArrayList<String>();
		List<String> analogKey = new ArrayList<String>();
		for (String key : reaperControls.keySet()) {
			String binding = reaperControls.get(key);
			if (binding.charAt(0) == 'k') {
				inputManager.addMapping(key, new KeyTrigger(Integer.parseInt(binding.substring(1))));
				actionKey.add(key);
			} else if (binding.charAt(0) == 'a') {
				if(binding.charAt(1) == 't')
					inputManager.addMapping(key, new MouseAxisTrigger(Integer.parseInt(binding.substring(2)),true));
				if(binding.charAt(1) == 'f')
					inputManager.addMapping(key, new MouseAxisTrigger(Integer.parseInt(binding.substring(2)),false));
				analogKey.add(key);
			} else if (binding.charAt(0) == 'm') {
				inputManager.addMapping(key, new MouseButtonTrigger(Integer.parseInt(binding.substring(1))));
				actionKey.add(key);
			}
		}

		ReaperListener reaperListener = new ReaperListener();//Temporarily an object, maybe it will changed to static again 
		inputManager.addListener(reaperListener.actionListener,  actionKey.toArray(new String[actionKey.size()]));

		inputManager.addListener(reaperListener.analogListener, analogKey.toArray(new String[analogKey.size()]));

	}

}
