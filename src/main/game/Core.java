package main.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Settings;
import main.game.art.EmbellishmentManager;
import main.game.entities.controls.GroundControl;
import main.game.entities.controls.ReaperControl;
import main.game.entities.controls.SpacecraftControl;
import main.game.entities.userinput.GroundListener;
import main.game.entities.userinput.SpacecraftListener;
import main.game.entities.userinput.UniversalListener;
import main.game.physics.HitManager;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

import de.lessvoid.nifty.Nifty;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author danielwenzel, danielthevessen, fabiankessler, simonmichalke
 */
public class Core extends SimpleApplication {

	private enum ControlType {

		SPACECRAFT, GROUND, MACHINE, STANDARD_ONLY
	}
	private Settings settings;
	private BulletAppState bulletAppState;
	private HitManager hitManager;
	// this is the body/machine, where you are inside, which you are playing
	private Spatial character;
	private static final float CAM_DISTANCE_BEHIND_CHAR = 50;
	private SpacecraftControl spaceControl;
	private GroundControl groundControl;
	private boolean camBehindChar = false;
	private NiftyJmeDisplay niftyDisplay;
	private boolean menu_active = false;
	private EmbellishmentManager embi;
	// test:
	private CameraNode camNode;

	@Override
	public void simpleInitApp() {
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);

		embi = new EmbellishmentManager(rootNode, assetManager, renderManager,
				viewPort);

		hitManager = new HitManager(bulletAppState.getPhysicsSpace(), embi);

		settings = new Settings();

		Texture northTex = assetManager
				.loadTexture("Textures/AlternativeSkybox/TestSky_back6.png");
		Texture downTex = assetManager
				.loadTexture("Textures/AlternativeSkybox/TestSky_bottom4.png");
		Texture southTex = assetManager
				.loadTexture("Textures/AlternativeSkybox/TestSky_front5.png");
		Texture westTex = assetManager
				.loadTexture("Textures/AlternativeSkybox/TestSky_left2.png");
		Texture eastTex = assetManager
				.loadTexture("Textures/AlternativeSkybox/TestSky_right1.png");
		Texture upTex = assetManager
				.loadTexture("Textures/AlternativeSkybox/TestSky_top3.png");

		final Vector3f normalScale = new Vector3f(-1, 1, 1);
		Spatial skySpatial = SkyFactory.createSky(assetManager, westTex,
				eastTex, northTex, southTex, upTex, downTex, normalScale);
		rootNode.attachChild(skySpatial);

		inputManager.setCursorVisible(false);// hides the cursor

		niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager,
				audioRenderer, guiViewPort);
		Nifty nifty = niftyDisplay.getNifty();
		GameMenuScreen gameMenuScreen = new GameMenuScreen(this);
		gameMenuScreen.initialize(stateManager, this);
		nifty.fromXml("layout/game_menu.xml", "menu", gameMenuScreen);

		flyCam.setEnabled(false);
		cam.setFrustumFar(100000);

		initSpatials();
		initKeys(ControlType.SPACECRAFT);

	}

	private void initKeys(ControlType controlType) {
		inputManager.clearMappings();

		List<String> actionKey = new ArrayList<>();
		List<String> analogKey = new ArrayList<>();

		if (controlType == ControlType.SPACECRAFT) {
			disectSettings(settings.getSettingsMap("SpacecraftControls"),
					actionKey, analogKey);
		} else if (controlType == ControlType.GROUND) {
			disectSettings(settings.getSettingsMap("GroundControls"),
					actionKey, analogKey);
		}

		if (controlType == ControlType.SPACECRAFT) {
			SpacecraftListener spacecraftListener = new SpacecraftListener(
					this, spaceControl);
			inputManager.addListener(spacecraftListener.actionListener,
					actionKey.toArray(new String[actionKey.size()]));
			inputManager.addListener(spacecraftListener.analogListener,
					analogKey.toArray(new String[analogKey.size()]));
		} else if (controlType == ControlType.GROUND) {
			GroundListener groundListener = new GroundListener(this,
					groundControl);
			inputManager.addListener(groundListener.actionListener,
					actionKey.toArray(new String[actionKey.size()]));
			inputManager.addListener(groundListener.analogListener,
					analogKey.toArray(new String[analogKey.size()]));
		}

		disectSettings(settings.getSettingsMap("UniversalControls"), actionKey,
				analogKey);
		UniversalListener universalListener = new UniversalListener(this);
		inputManager.addListener(universalListener.actionListener,
				actionKey.toArray(new String[actionKey.size()]));
		inputManager.addListener(universalListener.analogListener,
				analogKey.toArray(new String[analogKey.size()]));

	}

	private void initSpatials() {
		Material mat_brick = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat_brick.setTexture("ColorMap", assetManager
				.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
		DirectionalLight light = new DirectionalLight();
		light.setDirection(Vector3f.UNIT_XYZ);
		rootNode.addLight(light);
		// Static:

		{// Carrier
			Node carrierNode = (Node) assetManager
					.loadModel("Models/carrier.j3o");
			rootNode.attachChild(carrierNode);
			bulletAppState.getPhysicsSpace().addAll(carrierNode);
			carrierNode.move(0, 0, 500);
		}// \Carrier

		// \Static
		// Not-static:

		{// Physics test Box
			Spatial box = new Geometry("Box", new Box(new Vector3f(-2.5f,
					-2.5f, -2.5f), new Vector3f(2.5f, 2.5f, 2.5f)));
			box.setMaterial(mat_brick);
			rootNode.attachChild(box);
			box.setLocalTranslation(25f, -10f, 75f);
			RigidBodyControl box_rbc = new RigidBodyControl(8f);
			box.addControl(box_rbc);
			bulletAppState.getPhysicsSpace().add(box_rbc);
		}// \Physics test Box

		{// Spaceships
			Node standartReaper = (Node) assetManager
					.loadModel("Models/reaper_fertig.j3o");
			CollisionShape collisionShape = CollisionShapeFactory
					.createDynamicMeshShape(standartReaper);
			standartReaper.setMaterial(mat_brick);
			{    /** Explosion effect. Uses Texture from jme3-test-data library! */


				Node spaceShip = standartReaper.clone(true);
				rootNode.attachChild(spaceShip);
				spaceControl = new ReaperControl(collisionShape, 6f);
				spaceShip.addControl(spaceControl);
				bulletAppState.getPhysicsSpace().addAll(spaceShip);
				//Init cams for spatials
				CameraControl camControl = (CameraControl) spaceShip.getChild("Camera").getControl(0);
				camControl.setCamera(cam);
				camControl.setEnabled(false);
				camControl = (CameraControl) spaceShip.getChild("Camera2").getControl(0);
				camControl.setCamera(cam);
				camControl.setEnabled(false);

				character = spaceShip;
			}

			{// dummy Space ship
				Node spaceShip = standartReaper.clone(true);
				rootNode.attachChild(spaceShip);
				spaceShip.setLocalTranslation(0, 0, 100);
				ReaperControl control = new ReaperControl(collisionShape, 6f);
				spaceShip.addControl(control);
				bulletAppState.getPhysicsSpace().add(control);

			}// \dummy Space ship
		}

	}

	@Override
	public void simpleRender(RenderManager rm) {
		embi.updateRender();
	}

	@Override
	public void simpleUpdate(float tpf) {

		//camControl.update(tpf);
		if (!menu_active) {
			inputManager.setCursorVisible(false);// no cursor
		} else {
			inputManager.setCursorVisible(true);
		}

		// Vector3f camvec = character.getLocalTranslation();
		// // Quaternion q, p;
		//
		// if (camBehindChar) {
		//
		// character.localToWorld(
		// new Vector3f(0, 0, -CAM_DISTANCE_BEHIND_CHAR), camvec);
		// // p = new Quaternion(0, 0, 1, +CAM_DISTANCE_BEHIND_CHAR); //-cam*
		// // or +cam* please test
		// // p.mult(character.getLocalRotation());
		// // q.addLocal(p);
		// }
		//
		// cam.setLocation(camvec);
		// cam.setRotation(character.getLocalRotation());
	}

	private void disectSettings(HashMap<String, String> controls,
			List<String> actionKey, List<String> analogKey) {

		for (String key : controls.keySet()) {
			String binding = controls.get(key);
			if (binding.charAt(0) == 'k') {
				inputManager.addMapping(key,
						new KeyTrigger(Integer.parseInt(binding.substring(1))));
				actionKey.add(key);
			} else if (binding.charAt(0) == 'a') {
				if (binding.charAt(1) == 't') {
					inputManager.addMapping(
							key,
							new MouseAxisTrigger(Integer.parseInt(binding
							.substring(2)), true));
				}
				if (binding.charAt(1) == 'f') {
					inputManager.addMapping(
							key,
							new MouseAxisTrigger(Integer.parseInt(binding
							.substring(2)), false));
				}
				analogKey.add(key);
			} else if (binding.charAt(0) == 'm') {
				inputManager.addMapping(
						key,
						new MouseButtonTrigger(Integer.parseInt(binding
						.substring(1))));
				actionKey.add(key);
			}
		}
	}

	public BulletAppState getBulletAppState() {
		return bulletAppState;
	}

	public void setBulletAppState(BulletAppState bulletAppState) {
		this.bulletAppState = bulletAppState;
	}

	public void switchCam() {
		camBehindChar = !camBehindChar;
			((CameraControl)((CameraNode)((Node)character).getChild("Camera")).getControl(0)).setEnabled(camBehindChar);
			((CameraControl)((CameraNode)((Node)character).getChild("Camera2")).getControl(0)).setEnabled(!camBehindChar);
	}

	public void switchMenu() {
		if (!menu_active) { // We have the choice, create an extra appstate for
			// each
			// kind of input or clear and reassign the keys
			// every
			// time
			guiViewPort.addProcessor(niftyDisplay);
			initKeys(ControlType.STANDARD_ONLY);
		} else {
			guiViewPort.removeProcessor(niftyDisplay);
			initKeys(ControlType.SPACECRAFT);
		}
		menu_active = !menu_active;
	}
}
