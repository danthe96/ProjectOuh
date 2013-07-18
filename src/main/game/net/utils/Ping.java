package main.game.net.utils;

import java.util.*;

/**
 * this class is used in GameMessage to send
 * a ping / test the connection
 * @author simon
 */

public class Ping extends Information{

	private boolean pong; //if true, this is the way back,
			   //if false, this is the first way

	private Date timestampAtSending;

	public Ping(){
		pong = false;
		timestampAtSending = new Date(System.currentTimeMillis());
	}

	//execute this before sending back ;-)
	public void setPong(){
		pong = true;
	}

	public int getSendingTime(){
		return (int) (System.currentTimeMillis() - timestampAtSending.getDate());
	}
}
