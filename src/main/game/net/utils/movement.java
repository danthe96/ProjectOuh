package main.game.net.utils;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class movement extends information{
	
	private Vector3f position;
	private Quaternion rotation;
	private float speed;

	public movement() {
		setPosition(null);
		setRotation(null);
		setSpeed(0);
	}

	public movement(Vector3f positionts, Quaternion rotationts, float speedts) {
		position = positionts;
		rotation = rotationts;
		speed = speedts;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
