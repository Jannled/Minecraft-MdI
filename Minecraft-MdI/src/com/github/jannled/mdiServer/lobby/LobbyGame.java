package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.jannled.mdiServer.P;
import com.github.jannled.mdiServer.countdown.Countdown;
import com.github.jannled.mdiServer.gamemodes.Gamemode;

public class LobbyGame extends Lobby implements Countdown
{
	private ArrayList<Team> teams = new ArrayList<Team>();
	Gamemode gamemode;
	
	/**
	 * Creates a new Lobby with no teams
	 * @param name The Name of the Lobby, will be displayed if a com.github.jannled.mdiServer.player is moved to that lobby
	 * @param spawnLocation The Location where the com.github.jannled.mdiServer.player will be teleported to, when he enters the lobby 
	 */
	public LobbyGame(String name, Location spawnLocation, Gamemode gamemode)
	{
		super(name, spawnLocation, ChatColor.GRAY + "Go to PvP-Lobby " + ChatColor.RED + name + ChatColor.GRAY + "!", Material.REDSTONE_LAMP_OFF);
		gamemode.setLobby(this);
		this.gamemode = gamemode;
		invincible = false;
	}
	
	/**
	 * Creates a new Lobby with no teams
	 * @param name The Name of the Lobby, will be displayed if a com.github.jannled.mdiServer.player is moved to that lobby
	 * @param spawnLocation The Location where the com.github.jannled.mdiServer.player will be teleported to, when he enters the lobby 
	 */
	public LobbyGame(String name, Location spawnLocation, Gamemode gamemode, ItemStack item)
	{
		super(name, spawnLocation, item);
		gamemode.setLobby(this);
		this.gamemode = gamemode;
		invincible = false;
	}
	
	/**
	 * Creates a new Lobby with the desired teams
	 * @param name The Name of the Lobby, will be displayed if a com.github.jannled.mdiServer.player is moved to that lobby
	 * @param spawnLocation The Location where the com.github.jannled.mdiServer.player will be teleported to, when he enters the lobby 
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
		invincible = false;
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
