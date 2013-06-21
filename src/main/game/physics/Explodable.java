package main.game.physics;

public interface Explodable {

	public float getExplosionStrength();
	public float getExplosionRadius();
	public boolean isTriggered();
	public void setTriggered(boolean bvalue);
	
}