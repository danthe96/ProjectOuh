package testingarea;
import main.game.art.EmbellishmentManager;
import main.game.art.ExplosionView;
import main.game.art.RocketTrail;
import main.game.entities.controls.RocketControl;
import main.game.entities.controls.RocketControl.RocketPhysicsControl;
import main.game.physics.HitManager;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;

/**
 * This is an adaption of HelloPhysics to test my rocket code. You can play arround with it (the rocket can mess up the wall and stuff). But brace yourself, the code is a huge mess!
 * @author base code by double1984, updated by zathras
 */
public class HelloPhysics extends SimpleApplication {

	public static void main(String args[]) {
		HelloPhysics app = new HelloPhysics();
		app.start();
	}

	/** Prepare the Physics Application State (jBullet) */
	private BulletAppState bulletAppState;

	/** Prepare Materials */
	Material wall_mat;
	Material stone_mat;
	Material floor_mat;
	
	EmbellishmentManager embi;

	/** Prepare geometries and physical nodes for bricks and cannon balls. */
	private RigidBodyControl    brick_phy;
	private static final Box    box;
	private RigidBodyControl    ball_phy;
	private static final Sphere sphere;
	private RigidBodyControl    floor_phy;
	private static final Box    floor;

	/** dimensions used for bricks and wall */
	private static final float brickLength = 0.48f;
	private static final float brickWidth  = 0.24f;
	private static final float brickHeight = 0.12f;

	static {
		/** Initialize the cannon ball geometry */
		sphere = new Sphere(32, 32, 0.4f, true, false);
		sphere.setTextureMode(TextureMode.Projected);
		/** Initialize the brick geometry */
		box = new Box(Vector3f.ZERO, brickLength, brickHeight, brickWidth);
		box.scaleTextureCoordinates(new Vector2f(1f, .5f));
		/** Initialize the floor geometry */
		floor = new Box(Vector3f.ZERO, 10f, 0.1f, 5f);
		floor.scaleTextureCoordinates(new Vector2f(3, 6));
	}

	Spatial target;

	@Override
	public void simpleInitApp() {
		rootNode.attachChild(SkyFactory.createSky(assetManager,
				"assets/Textures/OutputCube2.dds", false));
		
		
		/** Set up Physics Game */
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		//bulletAppState.getPhysicsSpace().enableDebug(assetManager);
		bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);
		/** Configure cam to look at scene */
		cam.setLocation(new Vector3f(0, 4f, 6f));
		cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
		/** Add InputManager action: Left click triggers shooting. */
		inputManager.addMapping("shoot", 
				new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(actionListener, "shoot");
		/** Initialize the scene, materials, and physics space */
		initMaterials();
		initWall();
		//initFloor();
		initCrossHairs();
		initRocket();
	}

	@Override
	 public void simpleRender(RenderManager rm) {
		embi.updateRender();
	}
	/**
	 * Every time the shoot action is triggered, a new cannon ball is produced.
	 * The ball is set up to fly from the camera position in the camera direction.
	 */
	private ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("shoot") && !keyPressed) {
				makeCannonBall();
			}
		}
	};

	/** Initialize the materials used in this scene. */
	public void initMaterials() {
		wall_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
		key.setGenerateMips(true);
		Texture tex = assetManager.loadTexture(key);
		wall_mat.setTexture("ColorMap", tex);

		stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
		key2.setGenerateMips(true);
		Texture tex2 = assetManager.loadTexture(key2);
		stone_mat.setTexture("ColorMap", tex2);

		floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
		key3.setGenerateMips(true);
		Texture tex3 = assetManager.loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);
		floor_mat.setTexture("ColorMap", tex3);
	}

	/** Make a solid floor and add it to the scene. */
	public void initFloor() {
		Geometry floor_geo = new Geometry("Floor", floor);
		floor_geo.setMaterial(floor_mat);
		floor_geo.setLocalTranslation(0, -0.1f, 0);
		this.rootNode.attachChild(floor_geo);
		/* Make the floor physical with mass 0.0f! */
		floor_phy = new RigidBodyControl(0.0f);
		floor_geo.addControl(floor_phy);
		bulletAppState.getPhysicsSpace().add(floor_phy);
	}
	private void initRocket() {
		Node rocketNode = new Node("Rocket");
		rootNode.attachChild(rocketNode);

		flyCam.setMoveSpeed(10f);

		Cylinder c = new Cylinder(10,10, 0.25f,2.5f,true); // create cube shape at the origin
		Geometry geom = new Geometry("Rocket", c);  // create cube geometry from the shape
		Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
		mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
		geom.setMaterial(mat);                   // set the cube's material

		geom.setLocalTranslation(new Vector3f(0,15f,-1000f));
		rocketNode.attachChild(geom);              // make the cube appear in the scene
		RocketControl rc = new RocketControl(target, 2.5f);
		geom.addControl(rc);
		RocketPhysicsControl physics = rc.getRocketPhysicsControl(5f);
		geom.addControl(physics);
		embi = new EmbellishmentManager(rootNode, assetManager, renderManager, viewPort);
		 
		bulletAppState.getPhysicsSpace().add(physics);
		bulletAppState.getPhysicsSpace().addCollisionListener(new HitManager(bulletAppState.getPhysicsSpace(), embi));
		physics.setKinematic(true);

		RocketTrail.loadTextures(assetManager);
		RocketTrail trail = new RocketTrail();
		trail.addControl(rc.getRocketTrailControl());
		rocketNode.attachChild(trail);


		brick_phy = new RigidBodyControl(5f);
		/** Add physical brick to physics space. */
		geom.addControl(brick_phy);
		brick_phy.setKinematic(true);
		bulletAppState.getPhysicsSpace().add(brick_phy);
	}

	/** This loop builds a wall out of individual bricks. */
	public void initWall() {
		float startpt = brickLength / 4;
		float height = 0;
		for (int j = 0; j < 15; j++) {
			for (int i = 0; i < 6; i++) {
				Vector3f vt = new Vector3f(i * brickLength * 2 + startpt, brickHeight + height, 0);
				if (i==3 && j == 7) makeBrick(vt, true);
				else makeBrick(vt, false);
			}
			startpt = -startpt;
			height += 2 * brickHeight;
		}
	}

	/** This method creates one individual physical brick. */
	public void makeBrick(Vector3f loc, boolean special) {
		/** Create a brick geometry and attach to scene graph. */
		Geometry brick_geo = new Geometry("brick", box);
		if (special) {
			Material mat = new Material(assetManager,
					"Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
			mat.setColor("Color", ColorRGBA.Red);   // set color of material to blue
			brick_geo.setMaterial(mat);
			target = brick_geo;
		}
		else brick_geo.setMaterial(wall_mat);
		rootNode.attachChild(brick_geo);
		/** Position the brick geometry  */
		brick_geo.setLocalTranslation(loc);


		/** Make brick physical with a mass > 0.0f. */
		brick_phy = new RigidBodyControl(16f);
		/** Add physical brick to physics space. */
		brick_geo.addControl(brick_phy);
		bulletAppState.getPhysicsSpace().add(brick_phy);
	}

	/** This method creates one individual physical cannon ball.
	 * By defaul, the ball is accelerated and flies
	 * from the camera position in the camera direction.*/
	public void makeCannonBall() {
		/** Create a cannon ball geometry and attach to scene graph. */
		Geometry ball_geo = new Geometry("cannon ball", sphere);
		ball_geo.setMaterial(stone_mat);
		rootNode.attachChild(ball_geo);
		/** Position the cannon ball  */
		ball_geo.setLocalTranslation(cam.getLocation());
		/** Make the ball physcial with a mass > 0.0f */
		ball_phy = new RigidBodyControl(1f);
		/** Add physical ball to physics space. */
		ball_geo.addControl(ball_phy);
		bulletAppState.getPhysicsSpace().add(ball_phy);
		/** Accelerate the physcial ball to shoot it. */
		ball_phy.setLinearVelocity(cam.getDirection().mult(25));
	}

	/** A plus sign used as crosshairs to help the player with aiming.*/
	protected void initCrossHairs() {
		guiNode.detachAllChildren();
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+");        // fake crosshairs :)
		ch.setLocalTranslation( // center
				settings.getWidth() / 2
						- guiFont.getCharSet().getRenderedSize() / 3 * 2,
				settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);
	}
}