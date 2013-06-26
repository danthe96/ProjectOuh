package main.game.net;

import main.game.net.utils.messagetype;

import com.jme3.network.AbstractMessage;

/**
 * this is the superclass for all communications
 * so nearly everything is abstract
 * @author simon aka clundxiii
 *
 */

public class message extends AbstractMessage {
	
	protected messagetype typ;
	
	//typets := [type]-[t]o-[s]et
	//basic constructor; should be invoked by children class 
	public message(messagetype typts) {
		typ = typts;
	}
}