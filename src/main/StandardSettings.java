package main;

import java.util.HashMap;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;

public class StandardSettings {
	
	static HashMap<String,HashMap<String,String>> map;
	
	static{
		map = new HashMap<String,HashMap<String,String>>();
		
		HashMap<String,String> reaperControls = new HashMap<String,String>();
		reaperControls.put("ACCELERATE", "" + KeyInput.KEY_W);
		reaperControls.put("DECELERATE", "" + KeyInput.KEY_S);
		reaperControls.put("RIGHT", "" + KeyInput.KEY_D);
		reaperControls.put("LEFT", "" + KeyInput.KEY_A);
		reaperControls.put("ROTATE_RIGHT", "" + MouseInput.AXIS_X);
		reaperControls.put("ROTATE_LEFT", "" + MouseInput.AXIS_X);
		reaperControls.put("STEER_UP", "" + MouseInput.AXIS_Y);
		reaperControls.put("STEER_DOWN", "" + MouseInput.AXIS_Y);
		
		map.put("ReaperControls", reaperControls);
		
		HashMap<String,String> crawlerControls = new HashMap<String,String>();
		crawlerControls.put("ACCELERATE", "" + KeyInput.KEY_W);
		crawlerControls.put("DECELERATE", "" + KeyInput.KEY_S);
		crawlerControls.put("RIGHT", "" + KeyInput.KEY_D);
		crawlerControls.put("LEFT", "" + KeyInput.KEY_A);
		crawlerControls.put("ROTATE_RIGHT", "" + MouseInput.AXIS_X);
		crawlerControls.put("ROTATE_LEFT", "" + MouseInput.AXIS_X);
		crawlerControls.put("STEER_UP", "" + MouseInput.AXIS_Y);
		crawlerControls.put("STEER_DOWN", "" + MouseInput.AXIS_Y);
		
		map.put("CrawlerControls", crawlerControls);
 			
	}
	
	static HashMap<String,HashMap<String,String>> get(){
		return map;
	}
	
}
