package main.game.entities.controls;

import main.game.physics.Explodable;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;

import com.jme3.scene.Spatial;

public abstract class SpacecraftControl extends RigidBodyControl
		implements Explodable {

	public SpacecraftControl(CollisionShape shape, float mass) {
		super(shape, mass);
//		this.setSpatial(spatial);
	}

	public SpacecraftControl(float mass) {
		super(mass);
	}

	public abstract void leftRotation(float value);
	public abstract void rightRotation(float value);
	public abstract void upRotation(float value);
	public abstract void downRotation(float value);
	public abstract void accelerate();
	public abstract void decelerate();
	public abstract void yawRight();
	public abstract void yawLeft();
	public abstract void land();
	public abstract void lift();
	public abstract void primaryShoot();
	public abstract void secondShoot();
	public abstract Spatial getSpatial();

	@Override
	public void update(float tpf) {
		super.update(tpf);
	}

}
