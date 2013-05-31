package main.game;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public class ReaperListener {
	 
  static ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
    	switch (name){
    		case "ACCELERATE":
    			//
    			break;
    		case "DECELERATE":
    			//
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
    			//
    			break;
    		case "ROTATE_LEFT":
    			//
    			break;
    		case "STEER_UP":
    			//
    			break;
    		case "STEER_DOWN":
    			//
    			break;
    	}
    }
  };
	
}
