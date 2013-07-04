package main.game.server;

import main.game.net.message;
import main.game.net.utils.messagetype;
import main.game.net.utils.movement;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;

public class serverlistener implements MessageListener<HostedConnection> {
	
	private Server myServer;
	
	public serverlistener(Server myServerts){
		myServer = myServerts;
	}
	
	@Override
	public void messageReceived(HostedConnection source, Message messageInput) {
		if (messageInput instanceof message) {
			message Input = (message) messageInput;
			System.out.println("Client #" + source.getId() + ":");
		    if (Input.getMessagetype() == messagetype.movement){
		    	movement newPos = (movement) Input.getInformation();
		    	System.out.println("Position: " + newPos.getPosition());
		    	System.out.println("Rotation: " + newPos.getRotation());
		    	System.out.println("Speed:    " + newPos.getSpeed());
		    
		    	//broadcasting position messages to all clients
		    	myServer.broadcast(messageInput);
		    }
		    else if (Input.getMessagetype() == messagetype.ping){
		    		
		    }
		    else if (Input.getMessagetype() == messagetype.spawn){
		    		
		    }
		    else if (Input.getMessagetype() == messagetype.game){
		    	
		    }
		    else
		    	System.out.println("unknown messagetype!");
		}
		else
			System.out.println("unknown Message instance!");
			
	}
}