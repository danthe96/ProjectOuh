package main.game.entities.controls;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;

import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

public abstract class SpacecraftControl extends RigidBodyControl
		implements
			Control {

	public SpacecraftControl(Spatial spatial,CollisionShape shape, float mass) {
		super(shape, mass);
		this.setSpatial(spatial);
	}
	
	public SpacecraftControl(Spatial spatial, float mass) {
		super(mass);
	}

	public abstract void leftRotation(float value, float tpf);
	public abstract void rightRotation(float value, float tpf);
	public abstract void upRotation(float value, float tpf);
	public abstract void downRotation(float value, float tpf);
	public abstract void accelerate();
	public abstract void decelerate();
	public abstract void yawRight();
	public abstract void yawLeft();
	public abstract void land();
	public abstract void lift();
	public abstract void primaryShoot();
	public abstract void secondShoot();

	@Override
	public void update(float tpf) {
		super.update(tpf);
	}

}
