package main;

import java.util.HashMap;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;

public class StandardSettings {
	
	static HashMap<String,HashMap<String,String>> map;
	
	static{
		
		map = new HashMap<String,HashMap<String,String>>();
		
		HashMap<String,String> spacecraftControls = new HashMap<String,String>();
		spacecraftControls.put("ACCELERATE", "k" + KeyInput.KEY_W);
		spacecraftControls.put("DECELERATE", "k" + KeyInput.KEY_S);
		spacecraftControls.put("RIGHT", "k" + KeyInput.KEY_D);
		spacecraftControls.put("LEFT", "k" + KeyInput.KEY_A);
		spacecraftControls.put("ROTATE_RIGHT", "at" + MouseInput.AXIS_X);
		spacecraftControls.put("ROTATE_LEFT", "af" + MouseInput.AXIS_X);
		spacecraftControls.put("STEER_UP", "at" + MouseInput.AXIS_Y);
		spacecraftControls.put("STEER_DOWN", "af" + MouseInput.AXIS_Y);
		spacecraftControls.put("CAMERA_SWITCH", "k" + KeyInput.KEY_C);
		
		map.put("SpacecraftControls", spacecraftControls);
		
		HashMap<String,String> groundControls = new HashMap<String,String>();
		groundControls.put("FORWARD", "k" + KeyInput.KEY_W);
		groundControls.put("BACK", "k" + KeyInput.KEY_S);
		groundControls.put("RIGHT", "k" + KeyInput.KEY_D);
		groundControls.put("LEFT", "k" + KeyInput.KEY_A);
		groundControls.put("CAMERA_RIGHT", "at" + MouseInput.AXIS_X);
		groundControls.put("CAMERA_LEFT", "af" + MouseInput.AXIS_X);
		groundControls.put("CAMERA_UP", "at" + MouseInput.AXIS_Y);
		groundControls.put("CAMERA_DOWN", "af" + MouseInput.AXIS_Y);
		groundControls.put("CAMERA_SWITCH", "k" + KeyInput.KEY_C);
		
		map.put("GroundControls", groundControls);
 			
	}
	
	static HashMap<String,HashMap<String,String>> get(){
		return map;
	}
	
}
