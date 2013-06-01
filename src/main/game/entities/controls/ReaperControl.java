package main.game.entities.controls;

import com.jme3.bullet.collision.shapes.CollisionShape;
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

	// see http://www.esparacing.com/sport_pilot/how%20to%20control%20an%20aircraft.htm
	private static final float SENSITIVITY_X = 500f; // aka "pitch"
	private static final float SENSITIVITY_Y = 500f; // aka "roll"
	private static final float SENSITIVITY_Z = 10000f; // aka "yaw"; very low, we want to increase difficulty ;-)
	private float acceleration = 5;
	private float currentspeed = 0;
	private boolean accelerating = false;
	private boolean decelerating = false;
	private boolean yawRight = false;
	private boolean yawLeft = false;

	@Override
	public void leftRotation(float value) {
		Quaternion oldOne=new Quaternion();
		getPhysicsRotation(oldOne);
		float multiplier=value*mass;
		Quaternion toRotate=new Quaternion(0, multiplier, 1, SENSITIVITY_X);
		setPhysicsRotation(oldOne.mult(toRotate));
	}

	@Override
	public void rightRotation(float value) {
		Quaternion oldOne=new Quaternion();
		getPhysicsRotation(oldOne);
		float multiplier=value*mass;
		Quaternion toRotate=new Quaternion(0, multiplier, 1, -SENSITIVITY_X);
		setPhysicsRotation(oldOne.mult(toRotate));
	}

	@Override
	public void upRotation(float value) {
		Quaternion oldOne=new Quaternion();
		getPhysicsRotation(oldOne);
		float multiplier=value*mass;
		Quaternion toRotate=new Quaternion(1, 0, multiplier, -SENSITIVITY_Y);
		setPhysicsRotation(oldOne.mult(toRotate));
	}

	@Override
	public void downRotation(float value) {
		Quaternion oldOne=new Quaternion();
		getPhysicsRotation(oldOne);
		float multiplier=value*mass;
		Quaternion toRotate=new Quaternion(1, 0, multiplier, SENSITIVITY_Y);
		setPhysicsRotation(oldOne.mult(toRotate));
	}

	@Override
	public void yawRight() {
		yawRight = !yawRight;
	}

	@Override
	public void yawLeft() {
		yawLeft = !yawLeft;		
	}


	@Override
	public void accelerate() {
		accelerating=!accelerating;
		//currentspeed+=velocity;
		//setLinearVelocity(spatial.getLocalRotation().getRotationColumn(0).mult(+currentspeed));

	}

	@Override
	public void decelerate() {
		decelerating=!decelerating;
		//	currentspeed-=velocity;
		//	setLinearVelocity(spatial.getLocalRotation().getRotationColumn(0).mult(-currentspeed));

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
	public void update(float tpf){  // tpf = 1/fps  in seconds

		if (accelerating && (currentspeed <= 30)) {
			currentspeed+=acceleration*tpf;   // 5 m/s²
			System.out.println("accelerating "+currentspeed);
		}else {
			if (decelerating && (currentspeed >= -30)) {
				currentspeed-=acceleration*tpf;  // 5 m/s²
				System.out.println("decelerating "+currentspeed);
			} else {
				if(currentspeed>0)
					currentspeed-=1.5*tpf;
				else if(currentspeed<0)
					currentspeed+=1.5*tpf;

			}
		}

		if(yawRight){
			Quaternion oldOne=new Quaternion();
			getPhysicsRotation(oldOne);
			//float multiplier = mass;  DON'T USE THIS
			Quaternion toRotate=new Quaternion(0, 1, 0, -SENSITIVITY_Z);
			setPhysicsRotation(oldOne.mult(toRotate));	
		}

		if(yawLeft){
			Quaternion oldOne=new Quaternion();
			getPhysicsRotation(oldOne);
			//float multiplier = mass;  DON'T USE THIS, fucks up direction AND sensitivity
			Quaternion toRotate=new Quaternion(0, 1, 0, SENSITIVITY_Z);
			setPhysicsRotation(oldOne.mult(toRotate));	
		}



		setLinearVelocity(spatial.getLocalRotation().getRotationColumn(2).mult(+currentspeed));
		super.update(tpf);

	}

}
