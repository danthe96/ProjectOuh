package main.game.net.utils;

public enum messagetype {
	ping,     //just for testing ;-)
	movement, //new position AND rotation AND velocity of any object/entity/etcpp
	spawn,    //spawn and despawn commands of object/entity/etcpp
	game,     //message about game (nick, score, spawn, despawn, etc)
}
