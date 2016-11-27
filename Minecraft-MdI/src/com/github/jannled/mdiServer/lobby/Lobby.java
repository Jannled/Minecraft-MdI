package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.jannled.mdiServer.P;

public class Lobby
{
	private String name;
	private ArrayList<Player> players = new ArrayList<Player>();
	//Inventory inventory = Bukkit.createBossBar("Hello", BarColor.GREEN, BarStyle.SEGMENTED_20);
	
	Location spawn;
	
	public Lobby(String name, Location spawnLocation)
	{
		this.name = name;
		this.spawn = spawnLocation;
		spawnLocation.setX(spawnLocation.getX()+0.5);
		spawnLocation.setY(spawnLocation.getY()+0.5);
		spawnLocation.setZ(spawnLocation.getZ()+0.5);
	}

	public void joinLobby(Player player)
	{
		if(!players.contains(player))
		{
			players.add(player);
		}
		player.teleport(spawn);
		player.sendMessage(P.pluginName + "You were moved to " + name);
	}
	
	public void leaveLobby(Player player)
	{
		players.remove(player);
	}
	
	public boolean isInLobby(Player player)
	{
		return players.contains(player);
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
	
	public Location getSpawnLocation()
	{
		return spawn;
	}
}
