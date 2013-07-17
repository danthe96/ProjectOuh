package main.game.server;

import main.game.net.GameMessage;
import main.game.net.utils.MessageType;
import main.game.net.utils.Movement;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;

/**
 * this class is used by the server to listen
 * for incoming messages
 * @author simon
 */

public class ServerListener implements MessageListener<HostedConnection> {
	
	private Server myServer;
	
	public ServerListener(Server myServerts){
		myServer = myServerts;
	}
	
	@Override
	public void messageReceived(HostedConnection source, Message messageInput) {
		if (messageInput instanceof GameMessage) {
			GameMessage Input = (GameMessage) messageInput;
			System.out.println("Client #" + source.getId() + ":");
		    if (Input.getMessagetype() == MessageType.movement){
		    	Movement newPos = (Movement) Input.getInformation();
		    	System.out.println("Position: " + newPos.getPosition());
		    	System.out.println("Rotation: " + newPos.getRotation());
		    	System.out.println("Speed:    " + newPos.getSpeed());
		    
		    	//broadcasting position messages to all clients
		    	myServer.broadcast(messageInput);
		    }
		    else if (Input.getMessagetype() == MessageType.ping){
		    		
		    }
		    else if (Input.getMessagetype() == MessageType.spawn){
		    		
		    }
		    else if (Input.getMessagetype() == MessageType.game){
		    	
		    }
		    else
		    	System.out.println("unknown MessageType!");
		}
		else
			System.out.println("unknown Message instance!");
			
	}
}