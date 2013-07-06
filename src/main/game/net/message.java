package main.game.net;

import main.game.net.utils.information;
import main.game.net.utils.messagetype;
import main.game.net.utils.movement;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * this is the superclass for all communications
 * so nearly everything is abstract
 * @author simon aka clundxiii
 *
 */

@Serializable
public class message extends AbstractMessage {
	
	protected messagetype typ;
	
	private information info;
	
	//typets := [type]-[t]o-[s]et
	//basic constructor; should be invoked by children class
	
	public message() {
		typ = null;
		setInformation(null);
		return;
	}
	
	public message(messagetype typts, information infots) {
		typ = typts;
		info = infots;
	}
	
	public message(Vector3f positionts,	Quaternion rotationts, float speedts) {
		typ = messagetype.movement;
		info = new movement(positionts, rotationts, speedts);
	}
	
	public void setMessageType(messagetype typts) {
		typ = typts;
	}
	
	public void setInformation(information infots) {
		info = infots;
	}

	public messagetype getMessagetype(){
		return typ;
	}
	
	public information getInformation() {
		return info;
	}
	
	
}