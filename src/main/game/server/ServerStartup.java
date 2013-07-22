package main.game.server;

import java.io.IOException;

import main.game.net.GameMessage;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.JmeContext;

/**
 * this class will start the server
 * as you can see, you simple have to
 * run the constructor, thats it!
 * @author simon
 */

public class ServerStartup extends SimpleApplication {

	private static final boolean debug_Mode = true;

	// The Client classes
	private Client Clients[];

	private Server myServer;

	// Setting Vars
	private int clientCount;
	private int port;



	private ServerListener listener;

	@Override
	public void simpleInitApp() {
		int result;
		result = initServer();
		if (result != 0){
			System.out.println("error " + result + " while starting server!");
			return;
		}
		System.out.println("successfully started server!");
	}

	public static void main(String[] args) {
		ServerStartup app = new ServerStartup();
	}

	public ServerStartup() {
		this.start(JmeContext.Type.Headless); // headless type for servers!
	}

	public ServerStartup(boolean b) {
		// just a 2nd constructor done with overloading
		// used in Startup.java
	}
	
	public void stop(){
		exit();
	}
	
	public void exit(){
		//cleanup();
		System.out.println("quitting Server ...");
		destroy();
	}
	
	// TODO add config type ... maybe a struct
	public int initServer(/*serversetting setting*/){
		System.out.println("starting Server ....");

		// TODO fill the setting Vars!


		Serializer.registerClass(GameMessage.class);

		//dummy values:
		clientCount = 42;
		port = 4242;

		Clients = new Client[clientCount];
		if (debug_Mode) System.out.println("created Client field of " + clientCount + " clients");
		for (int i=0; i<clientCount; i++){
			Clients[i] = new Client();
			if (debug_Mode) System.out.println("created Client #" + i + "!");
		}
		if (debug_Mode) System.out.println("... all clients created. Initializing networking ... ");
		if (debug_Mode) System.out.println("create Server at Port " + port + " ....");
		try {
			myServer = Network.createServer(port);
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		if (debug_Mode) System.out.println("start Server ...");
		myServer.start();
		if (debug_Mode) System.out.println("success!");

		if (debug_Mode) System.out.println("register serverlistener:");
		listener = new ServerListener(myServer);
		myServer.addMessageListener(listener, GameMessage.class);


		//success, return 0
		return 0;
	}

}
