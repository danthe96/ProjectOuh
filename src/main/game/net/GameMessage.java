package main.game.net;

import main.game.net.utils.Information;
import main.game.net.utils.MessageType;
import main.game.net.utils.Movement;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * this is the class for all communications
 * we will only send "message"s, which
 * includes the information "info" defined
 * by the typ
 * @author simon aka clundxiii
 *
 */

@Serializable
public class GameMessage extends AbstractMessage {
	
	protected MessageType typ;
	
	private Information info;
	
	//typets := [type]-[t]o-[s]et
	//basic constructor; should be invoked by children class
	
	public GameMessage() {
		typ = null;
		setInformation(null);
		return;
	}
	
	public GameMessage(MessageType typts, Information infots) {
		typ = typts;
		info = infots;
	}
	
	public GameMessage(Vector3f positionts,	Quaternion rotationts, float speedts) {
		typ = MessageType.movement;
		info = new Movement(positionts, rotationts, speedts);
	}
	
	public void setMessageType(MessageType typts) {
		typ = typts;
	}
	
	public void setInformation(Information infots) {
		info = infots;
	}

	public MessageType getMessagetype(){
		return typ;
	}
	
	public Information getInformation() {
		return info;
	}
	
	
}