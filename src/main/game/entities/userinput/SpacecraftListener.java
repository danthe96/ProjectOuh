package main.game.entities.userinput;

import main.game.entities.controls.SpacecraftControl;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public abstract class SpacecraftListener {

	public static SpacecraftControl spacecraft = null;

	static ActionListener actionListener = new ActionListener() {
		@Override
		public void onAction(String name, boolean isPressed, float tpf) {
			// TODO Auto-generated method stub

		}
	};
	static AnalogListener analogListener = new AnalogListener() {
		@Override
		public void onAnalog(String name, float value, float tpf) {
			// TODO Auto-generated method stub
		}

	};

}