package com.github.jannled.mdiServer.gamemodes;

import org.bukkit.entity.Player;

import com.github.jannled.mdiServer.countdown.Countdown;
import com.github.jannled.mdiServer.lobby.Team;

/**
 * Class for creating gamemodes
 * @author Jannled
 * @version 0.0.1
 */
public abstract class Gamemode implements Countdown
{
	public final static int BEFORE_GAME = 1;
	public final static int IN_GAME = 2;
	public final static int AFTER_GAME = 3;
	
	private Team[] teams;
	int state;
	
	/**
	 * Create a new gamemode
	 * @param teams 
	 */
	public Gamemode(Team[] teams)
	{
		this.teams = teams;
		gameloop();
	}
	
	public Gamemode(Player[] players)
	{
		teams = new Team[players.length];
		for(int i=0; i<players.length; i++)
		{
			teams[i] = new Team(players[i].getName());
			teams[i].addPlayer(players[i]);
		}
		gameloop();
	}
	
	public void gameloop()
	{
		state = BEFORE_GAME;
		Countd
	}
	
	@Override
	public void end()
	{
		if(state == BEFORE_GAME)
			before();
		else if(state == IN_GAME)
			round();
		else if(state == AFTER_GAME);
	}
	
	/**
	 * Method gets called before the countdown ticks
	 */
	public abstract void before();
	
	/**
	 * Method gets called when the round starts, put your gameloop inside here
	 */
	public abstract void round();
	
	/**
	 * Method get called when the round ends
	 */
	public abstract void after();
}
