package main.game.art;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class RocketTrail extends ParticleEmitter{

	private static Material mat_red;    
	
	public static void loadTextures(AssetManager assetmanager) {
		mat_red = new Material(assetmanager, 
	            "Common/MatDefs/Misc/Particle.j3md");
	    mat_red.setTexture("Texture", assetmanager.loadTexture(
	            "Effects/Explosion/smoketrail.png"));
	}
	    
	public RocketTrail() {
		super("Trail", com.jme3.effect.ParticleMesh.Type.Triangle, 30);
		setMaterial(mat_red);
	    setImagesX(1); 
	    rotateUpTo(new Vector3f(0,0,1));
	    setImagesY(3); 
	    setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   
	    setStartColor(new ColorRGBA(0f, 0f, 1f, 0.5f)); 
	    getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, -1));
	    setFacingVelocity(true);
	    setStartSize(3f);
	    setEndSize(3f);
	    setGravity(0, 0, 0);
	    setLowLife(0.5f);
	    setHighLife(1.5f);
	    getParticleInfluencer().setVelocityVariation(0.3f);		
	}


  
}
