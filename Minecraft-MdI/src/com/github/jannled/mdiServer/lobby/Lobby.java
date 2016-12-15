package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.P;
import com.github.jannled.mdiServer.ui.Representable;

public class Lobby implements Representable
{
	protected boolean invincible = true;
	private String name;
	private ArrayList<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
	
	Location spawn;
	ItemStack item;
	
	public Lobby(String name, Location spawnLocation)
	{
		this.name = name;
		this.spawn = spawnLocation;
		spawn.setX(spawnLocation.getX()+0.5);
		spawn.setY(spawnLocation.getY()+0.5);
		spawn.setZ(spawnLocation.getZ()+0.5);
		defaultItem(name, ChatColor.GRAY + "Go to Lobby " + ChatColor.GREEN + name + ChatColor.GRAY + "!", Material.GLOWSTONE);
	}
	
	public Lobby(String name, Location spawnLocation, String lore, Material material)
	{
		this.name = name;
		this.spawn = spawnLocation;
		spawn.setX(spawnLocation.getX()+0.5);
		spawn.setY(spawnLocation.getY()+0.5);
		spawn.setZ(spawnLocation.getZ()+0.5);
		defaultItem(name, lore, material);
	}
	
	public Lobby(String name, Location spawnLocation, ItemStack item)
	{
		this.name = name;
		this.spawn = spawnLocation;
		spawn.setX(spawnLocation.getX()+0.5);
		spawn.setY(spawnLocation.getY()+0.5);
		spawn.setZ(spawnLocation.getZ()+0.5);
		
		this.item = item;
	}

	/**
	 * Add a Player to this Lobby
	 * @param player The player to add to this lobby
	 */
	public void joinLobby(Player player)
	{
		//TODO It is checked somewhere else if the Lobby also contains this player, think about it!
		player.setInvulnerable(invincible);
		if(!players.contains(player))
		{
			players.add(player);
		}
		player.teleport(spawn);
		player.sendMessage(P.pluginName + "You were moved to " + name);
	}
	
	/**
	 * Remove a player from this lobby
	 * @param player The player that should leave the lobby
	 */
	public void leaveLobby(Player player)
	{
		//TODO It is checked somewhere else if the Lobby also contains this player, think about it!
		ArrayList<Integer> removes = new ArrayList<Integer>();
		for(int i=0; i<players.size(); i++)
		{
			if(players.get(i).getUniqueId().equals(player.getUniqueId()))
			{
				removes.add(new Integer(i));
			}
		}
		for(Integer i : removes)
		{
			players.remove(i);
		}
	}
	
	/**
	 * Sends a message to everybody in this lobby. The message gets prefixed by the lobby name
	 * @param message The Message to send
	 */
	public void broadcast(String message)
	{
		for(OfflinePlayer p : players)
		{
			if(p.isOnline())
			{
				p.getPlayer().sendMessage(P.getPref(name) + "" + message);
			}
		}
	}
	
	public boolean isInLobby(Player player)
	{
		for(OfflinePlayer p : players)
		{
			if(p.getUniqueId().equals(player.getUniqueId()))
			{
				return true;
			}
		}
		return false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Player> getPlayers()
	{
		ArrayList<Player> buffer = new ArrayList<Player>(players.size());
		for(OfflinePlayer p : players)
		{
			if(p.isOnline())
			{
				buffer.add(p.getPlayer());
			}
		}
		return buffer;
	}
	
	public Player[] getPlayersAsArray()
	{
		return (Player[]) getPlayers().toArray();
	}
	
	public Location getSpawnLocation()
	{
		return spawn;
	}

	@Override
	public boolean clicked(Player player)
	{
		MdIServer.getInstance().getLobbyManager().joinLobby(player, this);
		return true;
	}

	@Override
	public ItemStack getItem()
	{
		return this.item;
	}
	
	public void defaultItem(String name, String lore, Material material)
	{
		item = new ItemStack(material);
		ItemMeta itmeta = item.getItemMeta();
		itmeta.setDisplayName(name);
		ArrayList<String> itemLore = new ArrayList<String>();
		itemLore.add(lore);
		itmeta.setLore(itemLore);
		item.setItemMeta(itmeta);
	}
	
	public void setItem(ItemStack item)
	{
		this.item = item; 
	}
}
