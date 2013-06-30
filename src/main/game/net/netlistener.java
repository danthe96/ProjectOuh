package main.game.net;

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
	public void messageReceived(Client arg0, Message arg1) {
		// TODO Auto-generated method stub
		
	}

}
