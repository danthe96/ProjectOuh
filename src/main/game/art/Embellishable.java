package main.game.art;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

public interface Embellishable {

	void preload(RenderManager rm, ViewPort vp);
	
	void play();
}
