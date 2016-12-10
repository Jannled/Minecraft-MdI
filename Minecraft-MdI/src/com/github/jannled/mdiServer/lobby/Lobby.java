package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.P;
import com.github.jannled.mdiServer.ui.Representable;

import org.bukkit.ChatColor;

public class Lobby implements Representable
{
	private String name;
	private ArrayList<Player> players = new ArrayList<Player>();
	
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
