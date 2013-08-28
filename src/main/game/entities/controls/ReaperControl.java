package main.game.entities.controls;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.scene.Spatial;
import main.game.physics.Explodable;

/**
 * the ReaperControl is a RigidBodyControl which controls collisions and
 * movementoperations like yaw roll, pitch, accelerating and decelerating
 * @author Febbe, not only me ;)
 */
public class ReaperControl extends SpacecraftControl
		implements Explodable {

	public ReaperControl(CollisionShape shape, float mass) {
		super(shape, mass);
		setKinematic(false);
		// TODO Auto-generated constructor stub
	}
	public ReaperControl(float mass) {
		super(mass);
		// TODO Auto-generated constructor stub
	}

	// see http://www.esparacing.com/sport_pilot/how%20to%20control%20an%20aircraft.htm
	private static final float SENSITIVITY_X = 1.0f; // aka "pitch"
	private static final float SENSITIVITY_Z = 1.0f; // aka "roll"
	private static final float SENSITIVITY_Y = .5f; // aka "yaw"; very low, we want to increase difficulty ;-)
	private static final float MAXIMUM_SPEED = 100;
	private float acceleration = 100;
	private float currentspeed = 0;
	private boolean accelerating = false;
	private boolean decelerating = false;
	private boolean yawRight = false;
	private boolean yawLeft = false;
	private boolean isExploding = false;
	private float explosionRadius = 20;
	private float explosionStrength = 50;


	//@Override
	public void leftRotation(float value) {
		//		System.out.println(value);
		doRotation(new float[]{0f,0f,value*SENSITIVITY_Z});
	}

	//@Override
	public void rightRotation(float value) {
		doRotation(new float[]{0f,0f,-value*SENSITIVITY_Z});
	}

	//@Override
	public void upRotation(float value) {
		doRotation(new float[]{-value*SENSITIVITY_X,0f,0f});
	}

	//@Override
	public void downRotation(float value) {
		doRotation(new float[]{value*SENSITIVITY_X,0f,0f});
	}

	//@Override
	public void yawRight() {
		yawRight = !yawRight;
	}

	//@Override
	public void yawLeft() {
		yawLeft = !yawLeft;
	}

	//@Override
	public void accelerate() {
		accelerating=!accelerating;
	}

	//@Override
	public void decelerate() {
		decelerating=!decelerating;
	}

	//@Override
	public void land() {

	}

	//@Override
	public void lift() {

	}

	//@Override
	public void primaryShoot() {

	}

	//@Override
	public void secondShoot() {

	}

	//@Override
	public Spatial getSpatial() {
		return spatial;
	}

	@Override
	public void update(float tpf){  // tpf = 1/fps  in seconds

		if (accelerating && (currentspeed <= MAXIMUM_SPEED)) {
			currentspeed += acceleration * tpf; // 5 m/s�
		} else {
			if (decelerating && (currentspeed >= 0)) {
				currentspeed -= acceleration * tpf; // 5 m/s�
				if(currentspeed<0) {
					currentspeed=0;
				}
			} else {
				if (currentspeed > 0) {
					currentspeed -= 1.5 * tpf;
				}
				else if (currentspeed < 0) {
					currentspeed += 1.5 * tpf;
				}

			}
		}

		if(yawRight){
			doRotation(new float[]{0f,-SENSITIVITY_Y*tpf,0f});
		}

		if(yawLeft){
			doRotation(new float[]{0f,SENSITIVITY_Y*tpf,0f});
		}

		setLinearVelocity(spatial.getLocalRotation().getRotationColumn(2).mult(+currentspeed));
		super.update(tpf);
	}

	private void doRotation(float[] angles){
		Quaternion oldOne = getPhysicsRotation();
		Quaternion toRotate = new Quaternion(angles);
		setPhysicsRotation(oldOne.mult(toRotate));
		setAngularFactor(3f);

	}

	@Override
	public float getExplosionStrength() {
		return explosionStrength;
	}

	@Override
	public float getExplosionRadius() {
		return explosionRadius;
	}

	@Override
	public boolean isTriggered() {
		return isExploding;
	}

	@Override
	public void setTriggered(boolean bvalue) {
		isExploding = bvalue;
	}

}
