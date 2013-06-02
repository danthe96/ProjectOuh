package main.game.physics;

import com.jme3.scene.Spatial;

public interface Explodable {

	public float getExplosionStrength();
	public float getExplosionRadius();
	public boolean isTriggered();
	public void setTriggered(boolean bvalue);
}
