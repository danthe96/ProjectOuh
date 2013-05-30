package main;

import java.util.HashMap;

public class StandardSettings {
	
	static HashMap<String,String> map;
	
	static{
		map = new HashMap<String,String>();
		map.put("UP", "100");
		map.put("DOWN", "101");
		map.put("LEFT", "102");
		map.put("RIGHT", "103");
		map.put("ETC", "104");
			
	}
	
	static HashMap<String,String> get(){
		return map;
	}
	
}
