package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.Location;

import com.github.jannled.mdiServer.gamemodes.Gamemode;

public class LobbyGame extends Lobby
{
	private ArrayList<Team> teams = new ArrayList<Team>();
	Gamemode gamemode;
	
	/**
	 * Creates a new Lobby with two Teams: Red and Bue
	 * @param name The Name of the Lobby, will be displayed if a player is moved to that lobby
	 * @param spawnLocation The Location where the player will be teleported to, when he enters the lobby 
	 */
	public LobbyGame(String name, Location spawnLocation, Gamemode gamemode)
	{
		super(name, spawnLocation);
		this.gamemode = gamemode;
		teams.add(new Team("Red"));
		teams.add(new Team("Blue"));
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
	
	public ArrayList<Team> getTeams()
	{
		return teams;
	}
	
	public Team[] getTeamsAsArray()
	{
		return (Team[]) teams.toArray();
	}
}
