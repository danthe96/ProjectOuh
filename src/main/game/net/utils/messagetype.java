package main.game.net.utils;

/**
 * this is used by GameMessage to define the messagetyp
 * @author simon
 */

public enum messagetype {
	ping,       //just for testing ;-)
	everything, //initial array of objects
	movement,   //new position AND rotation AND velocity of any object/entity/etcpp
	spawn,      //spawn and despawn commands of object/entity/etcpp
	game,       //message about game (nick, score, spawn, despawn, etc)
}
