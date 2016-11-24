package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

public class Team
{
	private String name;
	private DyeColor color;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int teamScore;
	
	public Team(String name, DyeColor color)
	{
		this.name = name;
		this.color = color;
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
	
	public DyeColor getTeamColor()
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
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	public Player[] getPlayersAsArray()
	{
		return (Player[]) players.toArray();
	}
}
