package main.game.art;

import java.util.LinkedList;
import java.util.Queue;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

public class EmbellishmentManager {
	
	Node node;
	AssetManager am;
	RenderManager rm;
	ViewPort vp;
	
	Queue<Embellishable> renderqueue = new LinkedList<>();
	
	
	
	public EmbellishmentManager(Node root, AssetManager am, RenderManager rm, ViewPort vp) {
		node = new Node("Embellishment");
		this.am = am;
		this.rm = rm;
		this.vp = vp;
		root.attachChild(node);
	}
	
	public void createExplosionView(Vector3f pos, float strength, float time) {
		ExplosionView explo = new ExplosionView(strength, time, pos, node, am);
		renderqueue.add(explo);

	}
	
	public void updateRender() {
		while(!renderqueue.isEmpty()) renderqueue.poll().play();

	}

}
