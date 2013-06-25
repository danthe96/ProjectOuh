package main.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Settings;
import main.game.entities.controls.GroundControl;
import main.game.entities.controls.ReaperControl;
import main.game.entities.controls.SpacecraftControl;
import main.game.entities.userinput.GroundListener;
import main.game.entities.userinput.SpacecraftListener;
import main.game.physics.HitManager;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
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
	private HitManager hitManager;
	
	//this is the body/machine, where you are inside, which you are playing
	private Spatial character;	
	private static final float CAM_DISTANCE_BEHIND_CHAR = 50;
	private SpacecraftControl spaceControl;
	private GroundControl groundControl;
	private boolean camBehindChar = false;
	
	public static void main(String[] args) {
		Core coreapp = new Core();
		coreapp.start();
	}

	@Override
	public void simpleInitApp() {
		bulletAppState= new BulletAppState();
		stateManager.attach(bulletAppState);
		hitManager = new HitManager(bulletAppState.getPhysicsSpace());
		
		settings = new Settings();
		
		rootNode.attachChild(SkyFactory.createSky(assetManager,
				"assets/Textures/OutputCube2.dds", false));
		
		initSpatials();

		inputManager.setCursorVisible(false);//hides the cursor

		//ChaseCamera chaseCam = new ChaseCamera(cam, blue);
		//		Cam = new Camera();
				
		flyCam.setEnabled(false);
		bulletAppState.setDebugEnabled(true);
		
		initKeys(ControlType.SPACECRAFT);		
	}

	@Override
	public void simpleUpdate(float tpf){ 
		bulletAppState.getPhysicsSpace().update(tpf);
		
		inputManager.setCursorVisible(false);//no cursor

		Vector3f camvec = character.getLocalTranslation();
		//	  Quaternion q, p;

		if (camBehindChar){
			
			character.localToWorld(new Vector3f(0, 0, -CAM_DISTANCE_BEHIND_CHAR),camvec);
			//	   p = new Quaternion(0, 0, 1, +CAM_DISTANCE_BEHIND_CHAR); //-cam* or +cam* please test
			//	   p.mult(character.getLocalRotation());
			//	   q.addLocal(p);
		}
		
		cam.setLocation(camvec);
		cam.setRotation(character.getLocalRotation());
	}
	
	public void switchCam(){
		camBehindChar = !camBehindChar;		
	}

	private void initSpatials() {
		Material mat_brick = new Material( 
				assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_brick.setTexture("ColorMap", 
				assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));

		Spatial box = new Geometry("Box",new Box(new Vector3f(-2.5f,-2.5f,-2.5f), new Vector3f(2.5f,2.5f,2.5f)));
		box.setMaterial(mat_brick);
		rootNode.attachChild(box);
		box.setLocalTranslation(25f,-10f,75f);
		RigidBodyControl box_rbc = new RigidBodyControl(0f);
		box.addControl(box_rbc);
		bulletAppState.getPhysicsSpace().add(box_rbc);
		
		Node spaceShip = (Node)assetManager.loadModel("assets/Models/testship.j3o");
		spaceShip.setMaterial(mat_brick);
		rootNode.attachChild(spaceShip);
		
		Node dummySpaceShip = spaceShip.clone(true);
		rootNode.attachChild(dummySpaceShip);
		dummySpaceShip.setLocalTranslation(0, 0, 100);

		spaceControl = new ReaperControl(spaceShip, CollisionShapeFactory.createMeshShape(spaceShip), 6);
		spaceShip.addControl(spaceControl);
		bulletAppState.getPhysicsSpace().add(spaceControl);
    spaceControl.setGravity(new Vector3f(0f, 0f, 0.0001f));
		
		character = spaceShip;
	}
	
	private void initKeys(ControlType controlType){
		inputManager.clearMappings();

		HashMap<String, String> controls = null;
		if(controlType == ControlType.SPACECRAFT)
		   controls = settings.getSettingsMap("SpacecraftControls");
		else if(controlType == ControlType.GROUND)
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

		if(controlType == ControlType.SPACECRAFT){
			SpacecraftListener spacecraftListener = new SpacecraftListener(this, spaceControl);
			inputManager.addListener(spacecraftListener.actionListener,  actionKey.toArray(new String[actionKey.size()]));
			inputManager.addListener(spacecraftListener.analogListener, analogKey.toArray(new String[analogKey.size()]));
		}
		else if(controlType == ControlType.GROUND){
			GroundListener groundListener = new GroundListener(this, groundControl);
			inputManager.addListener(groundListener.actionListener,  actionKey.toArray(new String[actionKey.size()]));
			inputManager.addListener(groundListener.analogListener, analogKey.toArray(new String[analogKey.size()]));
		}

	}	
	
	private enum ControlType {
		SPACECRAFT,GROUND,MACHINE
	}

}
