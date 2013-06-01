package main.game.entities.userinput;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public class GroundListener{

	public static ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("FORWARD")) {

			} else if (name.equals("BACK")) {

			} else if (name.equals("RIGHT")) {

			} else if (name.equals("LEFT")) {

			}
		}
	};

	public static AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {
				if(name.equals("CAMERA_RIGHT") && value > 0){

				}
				else if(name.equals("CAMERA_LEFT") && value > 0){

				}
				else if(name.equals("CAMERA_UP") && value > 0){

				}
				else if (name.equals("CAMERA_DOWN") && value > 0){

				}
			}
	};
	
	

}
