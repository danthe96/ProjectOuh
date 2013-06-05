package main.game.physics;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.objects.PhysicsGhostObject;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class must be added to the physics space. Be careful the Listeners are a bit resource hungry. Do not use them all the time :) 
 * The Listeners contained are PhysicsCollisionListener and PhysicsTickListener.
 * @author daniel
 *
 */
public class HitManager implements PhysicsCollisionListener, PhysicsTickListener{

	float explosionStrength;
	float explosionRadius;
	PhysicsSpace physicsSpace;
	/**
	 * This object is created when an explosion is triggered. After the explosion it is set to null
	 * This means there can NEVER be two explosions at the same tick (1/60 secound). I think this should work in general.
	 */
	PhysicsGhostObject explodingArea;

	/**
	 * Constructor
	 * @param physicsSpace
	 * bulletappstate.getPhysicsSpace()
	 */
	public HitManager(PhysicsSpace physicsSpace) {
		this.physicsSpace = physicsSpace;
		physicsSpace.addTickListener(this);
		physicsSpace.addCollisionListener(this);
	}

	/* (non-Javadoc)
	 * @see com.jme3.bullet.collision.PhysicsCollisionListener#collision(com.jme3.bullet.collision.PhysicsCollisionEvent)
	 */
	@Override
	public void collision(PhysicsCollisionEvent arg0) {

		if (!arg0.getNodeA().equals(arg0.getNodeB())) 
			checkForExplosion(arg0);
	}

	/**
	 * This method checks if the two colliding object will trigger an explosion.
	 * @param event
	 */
	private void checkForExplosion(PhysicsCollisionEvent event) {
		if (isExplosionTriggered()) return;
		Explodable ExplodableA = getExplodableControl(event.getNodeA());
		Explodable ExplodableB = getExplodableControl(event.getNodeB());
		if (ExplodableA != null && ExplodableB != null) {
			triggerExplosion(ExplodableA,event.getNodeA(), Math.max(ExplodableA.getExplosionRadius(), ExplodableB.getExplosionRadius()), ExplodableA.getExplosionStrength()+ExplodableB.getExplosionStrength());
			ExplodableB.setTriggered(true);
			removeSpatialFromPhysics(event.getNodeB());
		}
		if (ExplodableA != null) {
			triggerExplosion(ExplodableA, event.getNodeA(), ExplodableA.getExplosionRadius(), ExplodableA.getExplosionStrength());
		}
		if (ExplodableB != null) {
			triggerExplosion(ExplodableB, event.getNodeB(), ExplodableB.getExplosionRadius(), ExplodableB.getExplosionStrength());
		}


	}
	/**
	 * Will lead to an explosion on the next physical tick. Feel free to use this method to blow some stuff up. 
	 * @param explodable
	 * @param s
	 * @param explosionRadius
	 * @param explosionStrength
	 */
	public void triggerExplosion(Explodable explodable, Spatial s,
			float explosionRadius, float explosionStrength) {
		explodable.setTriggered(true);
		removeSpatialFromPhysics(s);

		SphereCollisionShape shape = new SphereCollisionShape(explosionRadius);			
		explodingArea = new PhysicsGhostObject(shape);
		physicsSpace.add(explodingArea);
		explodingArea.setPhysicsLocation(s.getLocalTranslation());
		this.explosionStrength = explosionStrength;
		this.explosionRadius = explosionRadius;
	}

	/**
	 * Checks if a spatial has an explodable control and can therefore explode :)
	 * @param s
	 * @return explodable
	 */
	private Explodable getExplodableControl(Spatial s) {
		Explodable ExplodableControl=null;
		for (int i=0; i<s.getNumControls(); i++) {
			if (s.getControl(i) instanceof Explodable) ExplodableControl = (Explodable) s.getControl(i);
		}
		if (ExplodableControl == null) return null;
		return ExplodableControl.isTriggered()? null : ExplodableControl;
	}

	/**
	 * Will remove a spatial from the physics. But not before the next tick.
	 * @param s
	 */
	private void removeSpatialFromPhysics(Spatial s) {
		for (int i=0; i<s.getNumControls(); i++) {
			try {
				physicsSpace.remove(s.getControl(i));
			}
			catch (Exception e) {}
		}
		s.removeFromParent();
	}

	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
		if (isExplosionTriggered()) {
			explode();

		}

	}

	/**
	 * Checks whether an explosion is triggered or not.
	 * @return bTriggered
	 */
	private boolean isExplosionTriggered() {
		return explodingArea != null;
	}

	/**
	 * Launches a triggered explosion. Never directly call this method. Use triggerExplosion instead.
	 */
	private void explode() {
		Vector3f CenterofExplosion = explodingArea.getPhysicsLocation();
		Vector3f forcevector = new Vector3f();
		System.out.println("boom!!!");
		//get all overlapping objects and apply impulse to them
		for (PhysicsCollisionObject physicsCollisionObject: explodingArea.getOverlappingObjects()) {
			((PhysicsRigidBody) physicsCollisionObject).getPhysicsLocation(forcevector);
			forcevector.subtractLocal(CenterofExplosion);
			float force = explosionStrength*(explosionRadius - forcevector.length());
			forcevector.normalizeLocal();
			forcevector.multLocal(force);
			((PhysicsRigidBody) physicsCollisionObject).applyImpulse(forcevector, Vector3f.ZERO);

		}
		physicsSpace.remove(explodingArea);
		explodingArea = null;
	}

	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		// TODO Auto-generated method stub

	}

}
