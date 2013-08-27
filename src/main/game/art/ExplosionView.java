package main.game.art;


import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;


/**
 * The ExplosionView can be specified, pre-loaded (optional) and played.
 * This class only contains viewable things. Nothing in this class will affect the game mechanics.
 * @author danielwenzel
 *
 */
public class ExplosionView implements Embellishable{
	static private ColorRGBA startRGBA= new ColorRGBA(0,0, 1, 0.8f);
	static private ColorRGBA endRGBA= new ColorRGBA(0.25f, 0.25f, 1, 0.5f);
	
	static String explosionpath = "Effects/Explosion/flame.png";
	static String flashpath = "Effects/Explosion/flash.png";
	static String shockwavepath = "Effects/Shockwave4.png";
	static String soundpath = "Audio/Effects/110114__ryansnook__nuclear-explosion.wav";
	
	static final float strengthfactor = 0.025f;
	

	static String name = "Default Explosion";
	
	float strength=1f;
	float time=1f;
	
	ParticleEmitter flash;
	ParticleEmitter explosion;
	ParticleEmitter shockwave;
	
	Vector3f position;
	
	AudioNode sound;
	Node node;
	AssetManager am;
	
	Node rootNode;
	
	
	public ExplosionView(float strength, float time, Vector3f position, Node rootNode, AssetManager am) {
		this.am = am;
		this.time = time;
		this.strength = strength*strengthfactor;
		this.position = position;
		this.rootNode = rootNode;
		
		init(am);
	}


	private void init(AssetManager am) {
		node = new Node(name);
		node.setLocalTranslation(position);
		
		initFlash(am);
		initExplosion(am);
		initShockwave(am);
	}


	public void preload(RenderManager rm, ViewPort vp) {
		rootNode.attachChild(node);
		flash.preload(rm, vp);
		explosion.preload(rm, vp);
		shockwave.preload(rm, vp);
		
		initAudio();
		
	}
	/**
	 * Only preloads the audio.
	 */
	public void preload() {
		rootNode.attachChild(node);
		initAudio();
	}
	
	
	private void initAudio() {
    	sound = new AudioNode(am, soundpath,false);
    	sound.setLooping(false);
		node.attachChild(sound);
	}


	public void play() {
		if (sound == null) preload();
		
		flash.emitAllParticles();
		explosion.emitAllParticles();
		shockwave.emitAllParticles();
		sound.play();
	}

	private void initFlash(AssetManager assetManager) {
		flash = 
                new ParticleEmitter("flash", ParticleMesh.Type.Triangle, 30);
        Material mat_red = new Material(assetManager, 
                "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture(
                flashpath));
        flash.setMaterial(mat_red);
        flash.setImagesX(2); 
        flash.setImagesY(2); // 2x2 texture animation
        flash.setStartColor(startRGBA);   // red
        flash.setEndColor(endRGBA); // yellow
        //explosion.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        flash.setStartSize(0.0f);
        flash.setEndSize(25f*strength);
        flash.setGravity(0, 0, 0);
        flash.setLowLife(0.2f*time);
        flash.setHighLife(0.25f*time);
        //explosion.getParticleInfluencer().setVelocityVariation(0.3f);
        flash.setParticlesPerSec(0);
        flash.setRotateSpeed(100f);
        node.attachChild(flash);
	}

	private void initShockwave(AssetManager assetManager) {
		shockwave = 
                new ParticleEmitter("shockwave", ParticleMesh.Type.Triangle, 30);
        Material mat_red = new Material(assetManager, 
                "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture(
                shockwavepath));
        shockwave.setMaterial(mat_red);
        shockwave.setImagesX(1); 
        shockwave.setImagesY(1); // 2x2 texture animation
        shockwave.setStartColor(startRGBA);   // red
        shockwave.setEndColor(endRGBA); // yellow
        //explosion.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        shockwave.setStartSize(0f);
        shockwave.setEndSize(10f*strength);
        shockwave.setGravity(0, 0, 0);
        shockwave.setLowLife(0.5f*time);
        shockwave.setHighLife(0.6f*time);
        //explosion.getParticleInfluencer().setVelocityVariation(0.3f);
        shockwave.setParticlesPerSec(0);
        Vector3f vec = new Vector3f(0, 0, 1);
        Quaternion q = new Quaternion();
        shockwave.setLocalRotation(q);
        node.attachChild(shockwave);
	}

	private void initExplosion(AssetManager assetManager) {
		explosion = 
                new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        Material mat_red = new Material(assetManager, 
                "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture(
                explosionpath));
        explosion.setMaterial(mat_red);
        explosion.setImagesX(2); 
        explosion.setImagesY(2); // 2x2 texture animation
        explosion.setStartColor(startRGBA);   // red
        explosion.setEndColor(endRGBA); // yellow
        //explosion.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        explosion.setStartSize(0f);
        explosion.setEndSize(5f*strength);
        explosion.setGravity(0, 0, 0);
        explosion.setLowLife(1f*time);
        explosion.setHighLife(1.1f*time);
        //explosion.getParticleInfluencer().setVelocityVariation(0.3f);
        explosion.setParticlesPerSec(0);
        node.attachChild(explosion);
	}	
	
}
