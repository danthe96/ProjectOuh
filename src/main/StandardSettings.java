package main;

import java.util.HashMap;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;

public class StandardSettings {
	
	static HashMap<String,HashMap<String,String>> map;
	
	static{
		map = new HashMap<String,HashMap<String,String>>();
		
		HashMap<String,String> reaperControls = new HashMap<String,String>();
		reaperControls.put("ACCELERATE", "k" + KeyInput.KEY_W);
		reaperControls.put("DECELERATE", "k" + KeyInput.KEY_S);
		reaperControls.put("RIGHT", "k" + KeyInput.KEY_D);
		reaperControls.put("LEFT", "k" + KeyInput.KEY_A);
		reaperControls.put("ROTATE_RIGHT", "at" + MouseInput.AXIS_X);
		reaperControls.put("ROTATE_LEFT", "af" + MouseInput.AXIS_X);
		reaperControls.put("STEER_UP", "at" + MouseInput.AXIS_Y);
		reaperControls.put("STEER_DOWN", "af" + MouseInput.AXIS_Y);
		
		map.put("ReaperControls", reaperControls);
		
		HashMap<String,String> crawlerControls = new HashMap<String,String>();
		crawlerControls.put("ACCELERATE", "k" + KeyInput.KEY_W);
		crawlerControls.put("DECELERATE", "k" + KeyInput.KEY_S);
		crawlerControls.put("RIGHT", "k" + KeyInput.KEY_D);
		crawlerControls.put("LEFT", "k" + KeyInput.KEY_A);
		crawlerControls.put("ROTATE_RIGHT", "at" + MouseInput.AXIS_X);
		crawlerControls.put("ROTATE_LEFT", "af" + MouseInput.AXIS_X);
		crawlerControls.put("STEER_UP", "at" + MouseInput.AXIS_Y);
		crawlerControls.put("STEER_DOWN", "af" + MouseInput.AXIS_Y);
		
		map.put("CrawlerControls", crawlerControls);
 			
	}
	
	static HashMap<String,HashMap<String,String>> get(){
		return map;
	}
	
}
