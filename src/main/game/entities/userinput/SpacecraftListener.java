package main.game.entities.userinput;

import main.game.Core;
import main.game.entities.controls.SpacecraftControl;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public class SpacecraftListener {

	private SpacecraftControl spacecraftControl;
	private Core app;

	public SpacecraftListener(Core app, SpacecraftControl spacecraftControl) {
		this.spacecraftControl = spacecraftControl;
		this.app = app;
	}

	public ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("ACCELERATE")) {
				spacecraftControl.accelerate();
			} else if (name.equals("DECELERATE")) {
				spacecraftControl.decelerate();
			} else if (name.equals("RIGHT")) {
				spacecraftControl.yawRight();
			} else if (name.equals("LEFT")) {
				spacecraftControl.yawLeft();
			} else if (name.equals("CAMERA_SWITCH") && keyPressed) {
				app.switchCam();
			}
		}
	};

	public AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {
			if (name.equals("ROTATE_RIGHT") && value > 0) {
				spacecraftControl.rightRotation(value, tpf);
			} else if (name.equals("ROTATE_LEFT") && value > 0) {
				spacecraftControl.leftRotation(value, tpf);
			} else if (name.equals("STEER_UP") && value > 0) {
				spacecraftControl.upRotation(value, tpf);
			} else if (name.equals("STEER_DOWN") && value > 0) {
				spacecraftControl.downRotation(value, tpf);
			}
		}
	};

}