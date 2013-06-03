package main.game.entities.controls;
import main.game.physics.Explodable;


import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;


/**
 * Use this class to control a rocket. It contains two sub-classes for easy controlling.
 * 
 * Uses of RocketControl: 
 * The RocketControl moves the object around. It will be controlled by RocketAI in future.
 * 
 * @author daniel (the awesome one)
 *
 */
public class RocketControl extends AbstractControl{
	
	
	/**
	 * Theres a small time between the hit of the rocket and the next update, when it will explode. Yet the time is long enough to call the collision multiple times, thus I have created this variable to prevent multiple triggering.
	 */
	boolean btriggered=false;

	/**
	 * Never directly call this variable. Use getTargetLocation() instead. (Needed for moving aims)
	 */
	private Spatial target;
	private Camera cam;
	/**
	 * Currently it does not accelerate or deccelarate. Speed is in meters per secound
	 */
	private float speed = 100f;
	/**
	 * In percent of desireddirection/currentRotation per secound
	 */
	private float turnspeed = 1f;
	
	private Quaternion currentRotation;
	private Vector3f position;
	private Vector3f halfrocketlength = new Vector3f(0,0,-1f);
	/**
	 * This is the top of the rocket. (The direction in with it flies)
	 */
	private Vector3f top = new Vector3f(0,1,0);
	private Vector3f traildistance;
	
	
	
	public RocketControl(Spatial target, float rocketlength) {
		this.target = target;
		this.halfrocketlength = halfrocketlength.mult(rocketlength/2);
	}
	
	/**
	 * Use this method to create a RocketTrailControl matching the rocket.
	 * @return RocketTrailControl
	 */
	public RocketTrailControl getRocketTrailControl() {
		return new RocketTrailControl();
	}
	/**
	 * Use this method to create a RocketTrailControl matching the rocket.
	 * @param mass
	 * @return RocketPhysicsControl
	 */
	public RocketPhysicsControl getRocketPhysicsControl(float mass) {
		return new RocketPhysicsControl(mass);
	}
	
	Vector3f getTopofSpatial() {
	 //Up is the top of the Rocket.
		getSpatial().localToWorld(top, top);
		return top;
	}
	
	/**
	 * Returns the current location of the target.
	 * @return targetLocation
	 */
	Vector3f getTargetLocation() {
		Vector3f result = target.getLocalTranslation();
		
		//target.localToWorld(result,result);
		return result;
	}
	/**
	 * Not used atm.
	 * @param arg0
	 * @param arg1
	 */
	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void controlUpdate(float tpf) {
		

		updateRocket(tpf);
		
		
		
	}

	/**
	 * Updates the basic rocket stuff.
	 * @param tpf
	 * Time per frame
	 */
	private void updateRocket(float tpf) {
		Vector3f desiredDirection = getTargetLocation().subtract(getPosition());
		
		Quaternion desiredRotation = getSpatial().getLocalRotation().clone();
		desiredRotation.lookAt(desiredDirection, getTopofSpatial());


		currentRotation = getSpatial().getLocalRotation();
		currentRotation.slerp(desiredRotation, turnspeed*tpf);
		
		
		getSpatial().setLocalRotation(currentRotation);
		Vector3f movement = new Vector3f();
		
		movement = new Vector3f(0,0,1);
		movement = movement.mult(speed*tpf);
		currentRotation.mult(movement, movement);
		
		getSpatial().setLocalRotation(currentRotation);
		position = getSpatial().getLocalTranslation().add(movement);
		getSpatial().setLocalTranslation(position);
		traildistance = new Vector3f();
		currentRotation.mult(halfrocketlength, traildistance);
		
		
		
	}

	private Vector3f getPosition() {
		Vector3f pos = getSpatial().getLocalTranslation().clone();
		//getSpatial().localToWorld(pos, pos);
		return pos;
	}

	/**
	 * This class is used to control the trail of a rocket. Do not use this class without a RocketControl!
	 * @author daniel
	 *
	 */
	public class RocketTrailControl extends AbstractControl{

		@Override
		protected void controlRender(RenderManager arg0, ViewPort arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void controlUpdate(float arg0) {
			getSpatial().setLocalRotation(currentRotation);
			getSpatial().setLocalTranslation(position.add(traildistance));

			if (btriggered) {
				getSpatial().removeFromParent();
				
			}
		}
		
	}
	
	/**
	 * This class makes the rocket physical. You must add it to the physics space AND SET IT KINEMATIC. THATS IMPORTANT.
	 * @author daniel
	 *
	 */
	public class RocketPhysicsControl extends RigidBodyControl implements Explodable{
		
		RocketPhysicsControl(float mass) {
			super(mass);
		}

		@Override
		public float getExplosionStrength() {
			return 20f;
		}

		@Override
		public float getExplosionRadius() {
			return 5f;
		}

		@Override
		public boolean isTriggered() {
			return btriggered;
		}

		@Override
		public void setTriggered(boolean bvalue) {
			btriggered=bvalue;
		}
	    
	}
}
