package main.game.physics;

import java.util.Iterator;

import main.game.entities.controls.RocketControl.RocketPhysicsControl;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsGhostObject;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class HitManager implements PhysicsCollisionListener, PhysicsTickListener{

	float ExplosionStrength = 1f;
	float ExplosionRadius = 1f;
	PhysicsSpace physicsSpace;
	PhysicsGhostObject ghost;
	
	public HitManager(PhysicsSpace physicsSpace) {
		this.physicsSpace = physicsSpace;
		physicsSpace.addTickListener(this);
		physicsSpace.addCollisionListener(this);
	}
	
	@Override
	public void collision(PhysicsCollisionEvent arg0) {
		/*if (rocket == null) {
			if (arg0.getNodeA() != null && arg0.getNodeA().getName().equals("Rocket")) rocket = arg0.getNodeA();
			if (arg0.getNodeB() != null && arg0.getNodeB().getName().equals("Rocket")) rocket = arg0.getNodeA();	
			System.out.println(rocket);
			if (rocket != null) {
				arg0.get
				System.out.println("boom!");
				System.out.println(arg0.getNodeA().getName());
				System.out.println(arg0.getNodeB().getName());
				SphereCollisionShape shape = new SphereCollisionShape(radius);			
				ghost = new PhysicsGhostObject(shape);
			
				physicsSpace.add(ghost);
				ghost.setPhysicsLocation(rocket.getLocalTranslation());
				physicsSpace.addTickListener(this);
				rocket.removeFromParent();

			}
		}*/
		
		if (!arg0.getNodeA().equals(arg0.getNodeB())) checkForExplosion(arg0);
	}

	private void checkForExplosion(PhysicsCollisionEvent event) {
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
	private void triggerExplosion(Explodable explodable, Spatial s,
			float explosionRadius, float explosionStrength) {
		explodable.setTriggered(true);
		removeSpatialFromPhysics(s);

		SphereCollisionShape shape = new SphereCollisionShape(explosionRadius);			
		ghost = new PhysicsGhostObject(shape);
		physicsSpace.add(ghost);
		ghost.setPhysicsLocation(s.getLocalTranslation());
		this.ExplosionStrength = explosionStrength;
		this.ExplosionRadius = explosionRadius;
	}

	private Explodable getExplodableControl(Spatial s) {
		Explodable ExplodableControl=null;
		for (int i=0; i<s.getNumControls(); i++) {
			if (s.getControl(i) instanceof Explodable) ExplodableControl = (Explodable) s.getControl(i);
		}
		if (ExplodableControl == null) return null;
		return ExplodableControl.isTriggered()? null : ExplodableControl;
	}

	private void removeSpatialFromPhysics(Spatial s) {
		System.out.println("removing "+s.getName());
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
		if (ghost != null) {
			Vector3f vector = ghost.getPhysicsLocation();
			Vector3f vector2 = new Vector3f();
			Vector3f vector3 = new Vector3f();
			System.out.println("boom!!!");
	        //get all overlapping objects and apply impulse to them
	        for (Iterator<PhysicsCollisionObject> it = ghost.getOverlappingObjects().iterator(); it.hasNext();) {
	            PhysicsCollisionObject physicsCollisionObject = it.next();
	            if (physicsCollisionObject instanceof PhysicsRigidBody) {
	                PhysicsRigidBody rBody = (PhysicsRigidBody) physicsCollisionObject;
	                rBody.getPhysicsLocation(vector2);
	                vector2.subtractLocal(vector);
	                float force = ExplosionRadius - vector2.length();
	                force *= ExplosionStrength;
	                vector2.normalizeLocal();
	                vector2.multLocal(force);
	                ((PhysicsRigidBody) physicsCollisionObject).applyImpulse(vector2, Vector3f.ZERO);
	            }
	        }
	        physicsSpace.remove(ghost);
	        ghost = null;
			
		}

	}

	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

}