package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.jannled.mdiServer.P;
import com.github.jannled.mdiServer.countdown.Countdown;
import com.github.jannled.mdiServer.gamemodes.Gamemode;

public class LobbyGame extends Lobby implements Countdown
{
	private ArrayList<Team> teams = new ArrayList<Team>();
	Gamemode gamemode;
	
	/**
	 * Creates a new Lobby with no teams
	 * @param name The Name of the Lobby, will be displayed if a player is moved to that lobby
	 * @param spawnLocation The Location where the player will be teleported to, when he enters the lobby 
	 */
	public LobbyGame(String name, Location spawnLocation, Gamemode gamemode)
	{
		super(name, spawnLocation);
		gamemode.setLobby(this);
		this.gamemode = gamemode;
	}
	
	/**
	 * Creates a new Lobby with the desired teams
	 * @param name The Name of the Lobby, will be displayed if a player is moved to that lobby
	 * @param spawnLocation The Location where the player will be teleported to, when he enters the lobby 
	 * @param team The teams you want to create
	 */
	public LobbyGame(String name, Location spawnLocation, Gamemode gamemode, Team... team)
	{
		super(name, spawnLocation);
		this.gamemode = gamemode;
		for(Team t : team)
		{
			teams.add(t);
		}
	}
	
	/**
	 * Start the game
	 */
	public void start()
	{
		gamemode.start();
	}
	
	public ArrayList<Team> getTeams()
	{
		return teams;
	}
	
	public Team[] getTeamsAsArray()
	{
		return (Team[]) teams.toArray();
	}

	@Override
	public void tickCounter(int time)
	{
		for(Player p : getPlayers())
		{
			p.sendMessage(P.pluginName + "The round starts in " + ChatColor.DARK_AQUA + time + ChatColor.GOLD + "seconds!");
		}
	}

	@Override
	public void end()
	{
		
	}
}
