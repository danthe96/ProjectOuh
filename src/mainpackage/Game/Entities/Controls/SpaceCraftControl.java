package mainpackage.Game.Entities.Controls;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;

import com.jme3.scene.control.Control;

public abstract class SpaceCraftControl extends RigidBodyControl implements Control{

	public SpaceCraftControl() {
	}
	
	public SpaceCraftControl(float mass) {
		super(mass);
	}

	public SpaceCraftControl(CollisionShape shape) {
		super(shape);
	}

	public SpaceCraftControl(CollisionShape shape, float mass) {
		super(shape, mass);
	}
	
	public abstract void leftRotation();
	public abstract void rightRotation();
	public abstract void upRotation() ;
	public abstract void downRotation();
	public abstract void accelerate()   ;  	
	public abstract void decelerate()    ;	
	public abstract void land()   	;
	public abstract void lift();
	public abstract void primaryShoot();
	public abstract void secondShoot();

	@Override
	public void update(float tpf) {
	}

}
