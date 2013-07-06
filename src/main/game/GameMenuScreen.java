package main.game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GameMenuScreen extends AbstractAppState implements
		ScreenController {

	private Nifty nifty;
	private Screen screen;
	private SimpleApplication app;
	private Core core;
	
	public GameMenuScreen(Core core){
		this.core = core;
	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub

	}
	
	 @Override
	  public void initialize(AppStateManager stateManager, Application app) {
	    super.initialize(stateManager, app);
	    this.app=(SimpleApplication)app;
	  }
	 
	 public void exitGame(){
		 app.stop();
	 }
	 
	 public void continueGame(){
		 core.switchMenu();
	 }
	 

}
