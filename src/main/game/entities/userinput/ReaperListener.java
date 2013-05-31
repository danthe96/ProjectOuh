package main.game.entities.userinput;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public class ReaperListener extends SpacecraftListener {
	
	public ReaperListener(){
		
	}

	public ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("ACCELERATE")) {
				spacecraft.accelerate();
			} else if (name.equals("DECELERATE")) {
				spacecraft.decelerate();
			} else if (name.equals("RIGHT")) {

			} else if (name.equals("LEFT")) {

			}
		}
	};

	public AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {
				if(name.equals("ROTATE_RIGHT") && value > 0){
					spacecraft.rightRotation(value);
				}
				else if(name.equals("ROTATE_LEFT") && value > 0){
					spacecraft.leftRotation(value);
				}
				else if(name.equals("STEER_UP") && value > 0){
					spacecraft.upRotation(value);
				}
				else if (name.equals("STEER_DOWN") && value > 0){
					spacecraft.downRotation(value);
				}
			}
	};
	
	

}
