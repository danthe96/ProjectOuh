package main.game.entities.controls;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;

import com.jme3.scene.control.Control;

public abstract class SpacecraftControl extends RigidBodyControl implements Control{

	public SpacecraftControl() {
	}
	
	public SpacecraftControl(float mass) {
		super(mass);
	}

	public SpacecraftControl(CollisionShape shape) {
		super(shape);
	}

	public SpacecraftControl(CollisionShape shape, float mass) {
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
