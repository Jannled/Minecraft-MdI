package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.Location;

public class LobbyPvP extends Lobby
{
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	/**
	 * Creates a new Lobby with two Teams: Red and Bue
	 * @param name The Name of the Lobby, will be displayed if a player is moved to that lobby
	 * @param spawnLocation The Location where the player will be teleported to, when he enters the lobby 
	 */
	public LobbyPvP(String name, Location spawnLocation)
	{
		super(name, spawnLocation);
		teams.add(new Team("Red"));
		teams.add(new Team("Blue"));
	}
	
	/**
	 * Creates a new Lobby with the desired teams
	 * @param name The Name of the Lobby, will be displayed if a player is moved to that lobby
	 * @param spawnLocation The Location where the player will be teleported to, when he enters the lobby 
	 * @param team The teams you want to create
	 */
	public LobbyPvP(String name, Location spawnLocation, Team... team)
	{
		super(name, spawnLocation);
		for(Team t : teams)
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
