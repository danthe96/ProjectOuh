package testingarea;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.Particle;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
 
// Man kann den Test als gescheitert ansehen :(
public class SkyTest extends SimpleApplication {
 
	
	Material mat;
	
	
  public static void main(String[] args) {
    SkyTest app = new SkyTest();
    app.start();
  }
 
  @Override
  public void simpleInitApp() {
	 mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	    mat.setColor("Color", ColorRGBA.White);
	 Node sky= new Node("Sky");
	 Vector3f pos;
	 float alpha;
	 float beta;
	 float dist;
	 float x,y,z;
	 
	 for (int i=0; i<1000; i++) {
		 alpha = (float) (Math.random()*FastMath.PI*2);
		 beta = (float) (Math.random()*FastMath.PI*2);
		 dist = (float) (Math.random()*100f+500f);
		 x = FastMath.sin(alpha)*FastMath.cos(beta)*dist;
		 y = FastMath.sin(alpha)*FastMath.sin(beta)*dist;
		 z = FastMath.cos(beta)*dist;
		 pos = new Vector3f(x,y,z);
		 Line l = new Line(pos, pos.add(0,1f,0));
			Geometry geo = new Geometry("line");
			geo.setMesh(l);
			geo.setMaterial(mat);
		    sky.attachChild(geo);
	 }
	 sky.setQueueBucket(Bucket.Sky); 
	 sky.setCullHint(Spatial.CullHint.Never);
	    
	 rootNode.attachChild(sky);
    
	

  }
}