package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Team
{
	private String name;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public Team(String name)
	{
		this.name = name;
	}
	
	public void addPlayer(Player player)
	{
		players.add(player);
	}
	
	public void removePlayer(Player player)
	{
		players.remove(player);
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	public Player[] getPlayersAsArray()
	{
		return (Player[]) players.toArray();
	}
}
