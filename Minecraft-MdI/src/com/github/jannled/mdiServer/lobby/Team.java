package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Team
{
	private String name;
	private ChatColor color;
	private Location spawn;
	private ArrayList<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
	private int teamScore;
	protected Scoreboard teamScoreboard;
	protected Objective teamStats;
	
	public Team(String name, ChatColor color, Location spawn)
	{
		this.name = name;
		this.color = color;
		this.spawn = spawn;
		
		teamScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		teamStats = teamScoreboard.registerNewObjective(name + "Stats", "dummy");
		teamStats.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	/**
	 * This method sets the scoreboard for every in this team, this might override the scoreboard from the gamelobby!
	 */
	public void updateScoreboard()
	{
		for(OfflinePlayer p : players)
		{
			((Player) p).setScoreboard(teamScoreboard);
		}
	}
	
	public void addPlayer(OfflinePlayer player)
	{
		players.add(player);
	}
	
	public void removePlayer(OfflinePlayer player)
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
	
	public ArrayList<OfflinePlayer> getPlayers()
	{
		return players;
	}
	
	public Player[] getPlayersAsArray()
	{
		return (Player[]) players.toArray();
	}
	
	public Objective getTeamStat()
	{
		return teamStats;
	}
	
	public void setPlayers(ArrayList<OfflinePlayer> players)
	{
		this.players = players;
	}
	
	public void setSpawn(Location location)
	{
		this.spawn = location;
	}
}
