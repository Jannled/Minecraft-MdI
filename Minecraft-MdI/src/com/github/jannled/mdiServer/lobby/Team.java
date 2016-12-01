package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Team
{
	private String name;
	private ChatColor color;
	private Location spawn;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int teamScore;
	
	public Team(String name, ChatColor color, Location spawn)
	{
		this.name = name;
		this.color = color;
		this.spawn = spawn;
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
	
	public ChatColor getTeamColor()
	{
		return color;
	}
	
	public int getTeamPoints()
	{
		return teamScore;
	}
	
	public void addTeamPoints(int points)
	{
		teamScore += points;
	}
	
	public Location getSpawn()
	{
		return spawn;
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
