package main.game;

import main.game.entities.Spacecraft;
import main.game.entities.controls.SpacecraftControl;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;



public class ReaperListener {

	public static SpacecraftControl spacecraft=null;

	static ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			switch (name){
			case "ACCELERATE":
				spacecraft.accelerate();
				break;
			case "DECELERATE":
				spacecraft.decelerate();
				break;
			case "RIGHT":
				//
				break;
			case "LEFT":
				//
				break;

			}

		}
	};

	static AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {
			switch(name){
			case "ROTATE_RIGHT":
				spacecraft.rightRotation();
				break;
			case "ROTATE_LEFT":
				spacecraft.leftRotation();
				break;
			case "STEER_UP":
				spacecraft.upRotation();
				break;
			case "STEER_DOWN":
				spacecraft.downRotation();
				break;
			}
		}
	};

}
