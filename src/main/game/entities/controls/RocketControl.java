package main.game.entities.controls;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;


/**
 * @author daniel (the awesome one)
 *
 */
public class RocketControl extends AbstractControl{

	/**
	 * Never directly call this variable. Use getTargetLocation() instead. (Needed for moving aims)
	 */
	Spatial target;
	Camera cam;
	float speed = 5f;
	float turnspeed = 1f;
	
	Quaternion currentRotation;
	Vector3f position;
	Vector3f halfrocketlength = new Vector3f(0,0,-1.25f);
	Vector3f traildistance;
	
	public RocketControl(Camera cam) {
		this.cam = cam;
	}
	public RocketTrailControl getRocketTrailControl() {
		return new RocketTrailControl();
	}
	
	Vector3f getTopofSpatial() {
		Vector3f top = new Vector3f(0,1,0); //Up is the top of the Rocket.
		getSpatial().localToWorld(top, top);
		return top;
	}
	
	Vector3f getTargetLocation() {
		return cam.getLocation();
	}
	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void controlUpdate(float tpf) {
		

		updateRocket(tpf);
		
		
		
	}

	/**
	 * @param tpf
	 */
	private void updateRocket(float tpf) {
		Vector3f desiredDirection = getTargetLocation().subtract(getSpatial().getLocalTranslation());
		Quaternion desiredRotation = getSpatial().getLocalRotation().clone();
		desiredRotation.lookAt(desiredDirection, getTopofSpatial());
		
		currentRotation = getSpatial().getLocalRotation();
		currentRotation.slerp(desiredRotation, turnspeed*tpf);
		
		//getSpatial().setLocalRotation(currentRotation);
		Vector3f movement = new Vector3f();
		
		movement = new Vector3f(0,0,1);
		movement = movement.mult(speed*tpf);
		currentRotation.mult(movement, movement);
		
		//The trail will be controlled next and the particles need to render earlier to display the movement better
		//currentRotation.slerp(desiredRotation, turnspeed*tpf*1.5f);
		getSpatial().setLocalRotation(currentRotation);
		position = getSpatial().getLocalTranslation().add(movement);
		getSpatial().setLocalTranslation(position);
		traildistance = new Vector3f();
		currentRotation.mult(halfrocketlength, traildistance);
		
		
		
	}

	public class RocketTrailControl extends AbstractControl{

		@Override
		protected void controlRender(RenderManager arg0, ViewPort arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void controlUpdate(float arg0) {
			getSpatial().setLocalRotation(currentRotation);
			System.out.println(traildistance);
			getSpatial().setLocalTranslation(position.add(traildistance));

			
		}
		
	}
	
}
