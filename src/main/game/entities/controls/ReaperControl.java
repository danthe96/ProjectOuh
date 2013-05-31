package main.game.entities.controls;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class ReaperControl extends SpacecraftControl {

	public ReaperControl(Spatial spatial, CollisionShape shape, float mass) {
		super(spatial, shape, mass);
		// TODO Auto-generated constructor stub
	}
	public ReaperControl(Spatial spatial, float mass) {
		super(spatial, mass);
		// TODO Auto-generated constructor stub
	}

	private static final float SENSITIVITY_X = 3;
	private static final float SENSITIVITY_Y = 3;
	private float velocity = 1;
	private float currentspeed=0;

	// TODO replace REAPER_MULTIPLYIER SENSITIVITY_* with the correct settings
	// SENSITIVITY_Y
	// SENSITIVITY_X
	@Override
	public void leftRotation() {
		Quaternion q=new Quaternion(0, 0, 1, SENSITIVITY_X); 
		setPhysicsRotation(q);
	}

	@Override
	public void rightRotation() {
//		changeDirection(0d, SENSITIVITY_Y);
		Quaternion q=new Quaternion(0, 0, 1, -SENSITIVITY_X); 
		setPhysicsRotation(q);
	}

	@Override
	public void upRotation() {
//		changeDirection((-1) * SENSITIVITY_X, 0d);
		Quaternion q=new Quaternion(1, 0, 0, SENSITIVITY_Y); 
		setPhysicsRotation(q);
	}

	@Override
	public void downRotation() {
//		changeDirection(SENSITIVITY_X, 0d);
		Quaternion q=new Quaternion(1, 0, 0, -SENSITIVITY_Y); 
		setPhysicsRotation(q);

	}

	@Override
	public void accelerate() {
		currentspeed+=velocity;
		setLinearVelocity(spatial.getLocalRotation().getRotationColumn(0).mult(currentspeed));

	}

	@Override
	public void decelerate() {
		currentspeed-=velocity;
		setLinearVelocity(spatial.getLocalRotation().getRotationColumn(0).mult(currentspeed));

	}

	@Override
	public void land() {

	}

	@Override
	public void lift() {

	}

	@Override
	public void primaryShoot() {
		// TODO Auto-generated method stub

	}

	@Override
	public void secondShoot() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void update(float tpf){
		super.update(tpf);		
	}

}
