package main.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Settings;
import main.game.entities.controls.ReaperControl;
import main.game.entities.controls.SpacecraftControl;
import main.game.entities.userinput.GroundListener;
import main.game.entities.userinput.SpacecraftListener;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.util.SkyFactory;

public class Core extends SimpleApplication {
	private Settings settings;
	private BulletAppState bulletAppState;

	//this is the body/machine, where you are inside, which you are playing
	private Spatial character;

	private boolean camBehindChar = false;
	private static final float CAM_DISTANCE_BEHIND_CHAR = 50;
	private SpacecraftControl space;

	public static void main(String[] args) {
		Core coreapp = new Core();
		coreapp.start();
	}

	@Override
	public void simpleInitApp() {
		bulletAppState= new BulletAppState();
		stateManager.attach(bulletAppState);
		settings = new Settings();
		
		rootNode.attachChild(SkyFactory.createSky(assetManager,
				"assets/Textures/OutputCube2.dds", false));

		initSpatials();

		inputManager.setCursorVisible(false);//not mouse

		//ChaseCamera chaseCam = new ChaseCamera(cam, blue);
		//		Cam = new Camera();

		flyCam.setEnabled(false);

		initKeys('a');
	}

	@Override
	public void simpleUpdate(float tpf) {
		inputManager.setCursorVisible(false);//not mouse

		Vector3f camvec= character.getLocalTranslation();
		//	  Quaternion q, p;

		if (camBehindChar){      // has no impact on game

			character.localToWorld(new Vector3f(0, 0, -CAM_DISTANCE_BEHIND_CHAR),camvec);
			//	   p = new Quaternion(0, 0, 1, +CAM_DISTANCE_BEHIND_CHAR); //-cam* or +cam* please test
			//	   p.mult(character.getLocalRotation());
			//	   q.addLocal(p);
		}


		cam.setLocation(camvec);
		cam.setRotation(character.getLocalRotation());
	}

	private void initSpatials() {
		Material mat_brick = new Material( 
				assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_brick.setTexture("ColorMap", 
				assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));

		Spatial box = new Geometry("Box",new Box(new Vector3f(25,-10,75), new Vector3f(30,-5,80)));
		box.setMaterial(mat_brick);

		Node blue = (Node)assetManager.loadModel("assets/Models/testship.j3o");
		blue.setMaterial(mat_brick);

		Node blue2 = blue.clone(true);
		blue2.setLocalTranslation(0, 0, 100);

		space = new ReaperControl(blue, 6);
		blue.addControl(space);
		space.setGravity(new Vector3f(0f, 0f, 0.0001f));
		bulletAppState.getPhysicsSpace().add(space);
		SpacecraftListener.spacecraft = space;
		rootNode.attachChild(blue);
		rootNode.attachChild(blue2);
		rootNode.attachChild(box);

		character = blue;
	}

	private void initKeys(char controlType){
		inputManager.clearMappings();

		HashMap<String, String> controls = null;
		if(controlType == 'a')
		   controls = settings.getSettingsMap("AirControls");
		else if(controlType == 'g')
			controls = settings.getSettingsMap("GroundControls");
		
		List<String> actionKey = new ArrayList<String>();
		List<String> analogKey = new ArrayList<String>();
		for (String key : controls.keySet()) {
			String binding = controls.get(key);
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

		if(controlType == 'a'){
			inputManager.addListener(SpacecraftListener.actionListener,  actionKey.toArray(new String[actionKey.size()]));
			inputManager.addListener(SpacecraftListener.analogListener, analogKey.toArray(new String[analogKey.size()]));
		}
		else if(controlType == 'g'){
			inputManager.addListener(GroundListener.actionListener,  actionKey.toArray(new String[actionKey.size()]));
			inputManager.addListener(GroundListener.analogListener, analogKey.toArray(new String[analogKey.size()]));
		}

	}

}
