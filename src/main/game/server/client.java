/**
 * see main.game.net.server for other point of connection
 * @author simon
 */

package main.game.server;

import main.game.net.utils.messagetype;

public class client {
	
	//private static final boolean debug_Mode = true;
	
	private boolean connected;
	
	private String nickname;
	
	public client(){
		connected = false;
	}

	public boolean isConnected() {
		return connected;
	}
	
	public void disconnect() {
		
	}
	
	// "bind" an incoming login to a client
	//returning true, if nothing fails
	public boolean bind(String nick){
		nickname = nick;
		System.out.println(nickname + " logging in ...");
		
		
		//if we reach this point, there was/is no Problem
		connected = true;
		return true;
	}
	
	//send a message to the client
	public int sendData(messagetype message) {
		
		return 0; //everything ok!
	}
	
}
