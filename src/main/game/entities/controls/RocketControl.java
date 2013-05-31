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
	float speed = 1f;
	float turnspeed = 1f;
	
	RocketControl(Spatial target) {
		this.target = target;
	}
	
	Vector3f getTopofSpatial() {
		Vector3f top = new Vector3f(0,1,0); //Up is the top of the Rocket.
		getSpatial().localToWorld(top, top);
		return top;
	}
	
	Vector3f getTargetLocation() {
		return target.getWorldTranslation();
	}
	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void controlUpdate(float tpf) {
		
		System.out.println(tpf);
		System.out.println(getSpatial().getLocalTranslation());
		
		
		Vector3f desiredDirection = getTargetLocation().subtract(getSpatial().getLocalTranslation());
		Quaternion desiredRotation = getSpatial().getLocalRotation().clone();
		desiredRotation.lookAt(desiredDirection, getTopofSpatial());
		
		Quaternion currentRotation = getSpatial().getLocalRotation();
		currentRotation.slerp(desiredRotation, turnspeed*tpf);
		System.out.println(currentRotation);
		
		//getSpatial().setLocalRotation(currentRotation);
		Vector3f movement = new Vector3f();
		getSpatial().setLocalRotation(currentRotation);
		
		movement = new Vector3f(0,0,1);
		movement = movement.mult(speed*tpf);
		currentRotation.mult(movement, movement);
		getSpatial().setLocalTranslation(getSpatial().getLocalTranslation().add(movement));
		
	}

	
}
