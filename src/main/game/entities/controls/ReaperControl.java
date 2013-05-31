package main.game.entities.controls;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Quaternion;
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

	private static final float SENSITIVITY_X = 100f;
	private static final float SENSITIVITY_Y = 100f;
	private float velocity = 2;
	private float currentspeed = 0;

	@Override
	public void leftRotation(float value) {
		Quaternion oldOne=new Quaternion();
		getPhysicsRotation(oldOne);
		float multiplier=value*mass;
		Quaternion toRotate=new Quaternion(1, 0, multiplier, SENSITIVITY_X);
		setPhysicsRotation(oldOne.mult(toRotate));
	}

	@Override
	public void rightRotation(float value) {
		Quaternion oldOne=new Quaternion();
		getPhysicsRotation(oldOne);
		float multiplier=value*mass;
		Quaternion toRotate=new Quaternion(1, 0, multiplier, -SENSITIVITY_X);
		setPhysicsRotation(oldOne.mult(toRotate));
	}

	@Override
	public void upRotation(float value) {
		Quaternion oldOne=new Quaternion();
		getPhysicsRotation(oldOne);
		float multiplier=value*mass;
		Quaternion toRotate=new Quaternion(0, multiplier, 1, SENSITIVITY_Y);
		setPhysicsRotation(oldOne.mult(toRotate));
	}

	@Override
	public void downRotation(float value) {
		Quaternion oldOne=new Quaternion();
		getPhysicsRotation(oldOne);
		float multiplier=value*mass;
		Quaternion toRotate=new Quaternion(0, multiplier, 1, -SENSITIVITY_Y);
		setPhysicsRotation(oldOne.mult(toRotate));
	}

	@Override
	public void accelerate() {
		currentspeed+=velocity;
		setLinearVelocity(spatial.getLocalRotation().getRotationColumn(0).mult(+currentspeed));

	}

	@Override
	public void decelerate() {
		currentspeed-=velocity;
		setLinearVelocity(spatial.getLocalRotation().getRotationColumn(0).mult(-currentspeed));

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
