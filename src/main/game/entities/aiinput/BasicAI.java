package main.game.entities.aiinput;

import java.io.IOException;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

public class BasicAI implements Control {

	@Override
	public void write(JmeExporter ex) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read(JmeImporter im) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Control cloneForSpatial(Spatial spatial) {
		BasicAI ba=new BasicAI();
		ba.setSpatial(spatial);
		return ba;
	}

	@Override
	public void setSpatial(Spatial spatial) {
	}

	@Override
	public void update(float tpf) {
	}

	@Override
	public void render(RenderManager rm, ViewPort vp) {
	}

}
