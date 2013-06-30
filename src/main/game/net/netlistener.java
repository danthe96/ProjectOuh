package main.game.net;

import main.game.net.utils.messagetype;
import main.game.net.utils.movement;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.serializing.Serializer;

/**
 * this class is made for the client
 * to listen for incoming messages
 * @author simon
 *
 */

public class netlistener implements MessageListener<Client> {
	
	public netlistener() {
		Serializer.registerClass(message.class);
		System.out.println("netlistener gestartet!");
	}
	
	@Override
	public void messageReceived(Client source, Message messageInput) {
	    if (messageInput instanceof message) {
			message Input = (message) messageInput;
			System.out.println("Client #" + source.getId() + ":");
	    	if (Input.getMessagetype() == messagetype.movement){
	    		movement newPos = (movement) Input.getInformation();
	    		System.out.println("Position: " + newPos.getPosition());
	    		System.out.println("Rotation: " + newPos.getRotation());
	    		System.out.println("Speed:    " + newPos.getSpeed());
	    		
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
