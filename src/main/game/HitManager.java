package main.game;

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
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class HitManager implements PhysicsCollisionListener, PhysicsTickListener{

	float radius = 5f;
	Spatial rocket = null;
	PhysicsSpace physicsSpace;
	PhysicsGhostObject ghost;
	
	public HitManager(PhysicsSpace physicsSpace) {
		this.physicsSpace = physicsSpace;
	}
	
	@Override
	public void collision(PhysicsCollisionEvent arg0) {
		if (rocket == null) {
			if (arg0.getNodeA() != null && arg0.getNodeA().getName().equals("Rocket")) rocket = arg0.getNodeA();
			if (arg0.getNodeB() != null && arg0.getNodeB().getName().equals("Rocket")) rocket = arg0.getNodeA();	
			System.out.println(rocket);
			if (rocket != null) {
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
		}
	}

	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
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
                float force = radius - vector2.length();
                force *= 100;
                vector2.normalizeLocal();
                vector2.multLocal(force);
                ((PhysicsRigidBody) physicsCollisionObject).applyImpulse(vector2, Vector3f.ZERO);
            }
        }
        rocket = null;

	}

	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

}
