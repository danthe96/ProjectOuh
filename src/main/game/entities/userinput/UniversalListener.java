package main.game.entities.userinput;

import main.game.Core;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public class UniversalListener {

	//private SpacecraftControl spacecraftControl;
	private Core app;

	public UniversalListener(Core app) {
		//this.spacecraftControl = spacecraftControl;
		this.app = app;
	}
	
	public ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			if(name.equals("ESCAPE") && keyPressed){
				app.switchMenu();
			}else if(name.equals("DEBUG") && keyPressed){
				app.getBulletAppState().setDebugEnabled(!app.getBulletAppState().isDebugEnabled());
			}
		   

		}
	};

	public AnalogListener analogListener = new AnalogListener() {
		@Override
		public void onAnalog(String name, float value, float tpf) {
			// TODO Auto-generated method stub
			
		}
	};

}
