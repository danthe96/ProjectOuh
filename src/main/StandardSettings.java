package main;

import java.util.HashMap;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;

public class StandardSettings {
	
	static HashMap<String,HashMap<String,String>> map;
	
	static{
		
		map = new HashMap<String,HashMap<String,String>>();
		
		HashMap<String,String> airControls = new HashMap<String,String>();
		airControls.put("ACCELERATE", "k" + KeyInput.KEY_W);
		airControls.put("DECELERATE", "k" + KeyInput.KEY_S);
		airControls.put("RIGHT", "k" + KeyInput.KEY_D);
		airControls.put("LEFT", "k" + KeyInput.KEY_A);
		airControls.put("ROTATE_RIGHT", "at" + MouseInput.AXIS_X);
		airControls.put("ROTATE_LEFT", "af" + MouseInput.AXIS_X);
		airControls.put("STEER_UP", "at" + MouseInput.AXIS_Y);
		airControls.put("STEER_DOWN", "af" + MouseInput.AXIS_Y);
		
		map.put("AirControls", airControls);
		
		HashMap<String,String> groundControls = new HashMap<String,String>();
		groundControls.put("FORWARD", "k" + KeyInput.KEY_W);
		groundControls.put("BACK", "k" + KeyInput.KEY_S);
		groundControls.put("RIGHT", "k" + KeyInput.KEY_D);
		groundControls.put("LEFT", "k" + KeyInput.KEY_A);
		groundControls.put("CAMERA_RIGHT", "at" + MouseInput.AXIS_X);
		groundControls.put("CAMERA_LEFT", "af" + MouseInput.AXIS_X);
		groundControls.put("CAMERA_UP", "at" + MouseInput.AXIS_Y);
		groundControls.put("CAMERA_DOWN", "af" + MouseInput.AXIS_Y);
		
		map.put("GroundControls", groundControls);
 			
	}
	
	static HashMap<String,HashMap<String,String>> get(){
		return map;
	}
	
}
