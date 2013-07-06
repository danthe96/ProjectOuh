package testingarea;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
 
/** Sample 1 - how to get started with the most simple JME 3 application.
 * Display a blue 3D cube and view from all sides by
 * moving the mouse and pressing the WASD keys. */
public class SkyGenerator extends SimpleApplication {
	
	static int resolution = 1024;
	int[] triangles = {0,0,0,0,0,0,0,0,0,0,0,0};
	
	
	
	double phi;
	double lampda;
	double len;
	double starsize;
	float brigtness; //TODO implement
	double gamma;
	Vector3f posmin;
	Vector3f posmax;
	
	BufferedImage[] images = new BufferedImage[6];
	Node skybox = new Node("Skybox");
	
	
    private ActionListener actionListener = new ActionListener() {
   	 
        public void onAction(String name, boolean keyPressed, float tpf) {
          if (name.equals("Shoot") && !keyPressed) {
              CollisionResults results = new CollisionResults();
              // 2. Aim the ray from cam loc to cam direction.
              Ray ray = new Ray(cam.getLocation(), cam.getDirection());
              // 3. Collect intersections between Ray and Shootables in results list.
              rootNode.collideWith(ray, results);
              System.out.println(results.getClosestCollision().getContactPoint());
              System.out.println(results.getClosestCollision().getTriangleIndex()/2);
              System.out.println("---------");
          }
        }
      };
      
    public static void main(String[] args){
        SkyGenerator app = new SkyGenerator();
        app.start(); // start the game
    }
 
    @Override
    public void simpleInitApp() {
        Box b = new Box(1,1,1);
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        //geom.setLocalTranslation(-1f, -1f, -1f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        com.jme3.texture.Texture tex_ml = assetManager.loadTexture("Interface/Logo/Monkey.jpg");
        mat.setTexture("ColorMap", tex_ml);
        mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        geom.setMaterial(mat);                   // set the cube's material
        rootNode.attachChild(skybox);              // make the cube appear in the scene
        skybox.attachChild(geom);
        cam.setLocation(Vector3f.ZERO);
        initKeys();


        
        initImages();
        
        for (int i=0; i<1000; i++) {
        	generateStar();
        	System.out.println(i);
        }
        for (int i: triangles) System.out.println(i);

        
        saveImages();
        
    }
    void generateStar() {
    	lampda = (float) (Math.random()*2*Math.PI);
    	phi = (float) (Math.random()*Math.PI-Math.PI/2);
    	len = 1f;
    	starsize = 0.01f;
    	gamma = 2*Math.asin(starsize/len/2);
    	
    	posmin = polarToVec(lampda, phi, len);
    	posmax = polarToVec(lampda+gamma, phi+gamma, len);
		System.out.println(posmin.length());
		System.out.println(posmax.length());
		System.out.println("///////");
    	
    	drawStarOnImage(collide(posmin,skybox), collide(posmax, skybox));
		
		
    }

    private void drawStarOnImage(CollisionResult collmin,
			CollisionResult collmax) {
		Vector2f coord1 = getCollisionPoin(collmin.getContactPoint(), collmin.getTriangleIndex());
		Vector2f coord2 = getCollisionPoin(collmax.getContactPoint(), collmin.getTriangleIndex());
		if (collmax.getTriangleIndex()/2 != collmin.getTriangleIndex()/2) return; //TODO implement overlapping
		int image = collmin.getTriangleIndex()/2;
		System.out.println(coord2.subtract(coord1).mult(resolution));
		if (coord1.getX() > coord2.getX()) {
			float temp = coord2.getX();
			coord2.setX(coord1.getX());
			coord1.setX(temp);
		}
		if (coord1.getY() > coord2.getY()) {
			float temp = coord2.getY();
			coord2.setY(coord1.getY());
			coord1.setY(temp);
		}		
		if (coord2.subtract(coord1).mult(resolution).length() > 50f) {
			System.out.println("bad star");
			return;
		}
		System.out.println(coord2.subtract(coord1).mult(resolution));
		System.out.println("/////////7");
		triangles[collmin.getTriangleIndex()]++;

		
		coord1= coord1.mult(resolution-1);
		coord2= coord2.mult(resolution-1);

		//System.out.println(coord1);
		//System.out.println(coord2);
		//images[image].setRGB((int)coord1.getX(), (int)coord1.getY(), Color.white.getRGB());
		Color current = Color.white;
		
		
		for (int x=(int) coord1.getX(); x<= (int)coord2.getX()+1; x++) 
			for (int y=(int) coord1.getY(); y<= (int)coord2.getY()+1; y++) {
				try {
					current = Color.white;
					if (x<coord1.getX()) current = interpolate(current, Color.black, coord1.getX()-x);
					if (y<coord1.getY()) current = interpolate(current, Color.black, coord1.getY()-y);
					
					if (x>coord2.getX()) current = interpolate(current, Color.black, x-coord2.getX());
					if (y>coord2.getY()) current = interpolate(current, Color.black, y-coord2.getY());
					
					images[image].setRGB(x, y, current.getRGB());
					}
					catch (Exception e){}

		}
	}

    private Color interpolate(Color c1, Color c2, float amount) {
    	return new Color((int)(c1.getRed()*(1-amount)+c2.getRed()*amount),(int)(c1.getGreen()*(1-amount)+c2.getGreen()*amount),(int)(c1.getBlue()*(1-amount)+c2.getBlue()*amount));
    }
	private Vector2f getCollisionPoin(Vector3f contactPoint, int TriangleIndex) {
		Vector2f result;
		result = new Vector2f(contactPoint.getX(), contactPoint.getY());
		if (TriangleIndex/2 == 0) result = new Vector2f(contactPoint.getX(), contactPoint.getY());
		if (TriangleIndex/2 == 1) result = new Vector2f(contactPoint.getY(), contactPoint.getZ());
		if (TriangleIndex/2 == 2) result = new Vector2f(contactPoint.getX(), contactPoint.getY());
		if (TriangleIndex/2 == 3) result = new Vector2f(contactPoint.getY(), contactPoint.getZ());
		if (TriangleIndex/2 == 4) result = new Vector2f(contactPoint.getX(), contactPoint.getZ());
		if (TriangleIndex/2 == 5) result = new Vector2f(contactPoint.getX(), contactPoint.getZ());
		
		
		if (result.getX() < 0) result.setX(result.getX()+1);
		if (result.getY() < 0) result.setY(result.getY()+1);

		
		return result;
	}

	Vector3f polarToVec(double lampda, double phi, double len) {
		double x = Math.cos(phi)*Math.cos(lampda)*len;
		double y = Math.cos(phi)*Math.sin(lampda)*len;
		double z = Math.sin(phi)*len;
		return new Vector3f((float)x,(float)y,(float)z);   
    }
    
    CollisionResult collide(Vector3f vec, Node n) {
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(Vector3f.ZERO, vec);
        n.collideWith(ray, results);
        return results.getClosestCollision();
    	
    }
    void initImages() {
    	for (int i=0; i<6; i++) {
    		images[i] = new BufferedImage(resolution, resolution, BufferedImage.TYPE_3BYTE_BGR);
    		
    	}
    }

    void saveImages() {

    	try {
        	for (int i=0; i<6; i++){
        		File outputfile = new File("Picture " +i+".png");
    	    	ImageIO.write(images[i], "png", outputfile);
    		}
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }

private void initKeys() {
    inputManager.addMapping("Shoot",
      new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
      new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click
    inputManager.addListener(actionListener, "Shoot");
  }
}