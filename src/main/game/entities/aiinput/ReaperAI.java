package main.game.entities.aiinput;

import java.io.IOException;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import main.game.entities.controls.ReaperControl;

public class ReaperAI implements Control{
	ReaperControl control;
	Spatial spatial;

	public ReaperAI(){
	}
	public ReaperAI(ReaperControl control){
		this.control=control;
	}

	@Override
	public Control cloneForSpatial(Spatial spatial) {
		ReaperAI rA=new ReaperAI();
		rA.setSpatial(spatial);
		return rA;
	}

	@Override
	public void setSpatial(Spatial spatial) {
		this.spatial=spatial;
	}
	
	public void setControl(ReaperControl control){
		this.control=control;
	}

	@Override
	public void update(float tpf) {
		control.accelerate();
		control.upRotation(tpf);
	}

	@Override
	public void render(RenderManager rm, ViewPort vp) {
	}


	@Override
	public void write(JmeExporter ex) throws IOException {
	}

	@Override
	public void read(JmeImporter im) throws IOException {
	}

}
