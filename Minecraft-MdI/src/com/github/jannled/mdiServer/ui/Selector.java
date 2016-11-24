package com.github.jannled.mdiServer.ui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.github.jannled.mdiServer.MdIServer;

public class Selector implements Listener
{
	MdIServer main;
	
	ItemStack lobbySelector;
	
	public Selector(MdIServer main)
	{
		lobbySelector = new ItemStack(Material.COMPASS, 1);
		lobbySelector.getItemMeta().setDisplayName("" + ChatColor.BOLD + ChatColor.GOLD + "Select your Lobby");
	}
	
	public void giveLobbySelector(Player player)
	{
		player.getInventory().setItem(0, lobbySelector);
	}
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent e)
	{
		e.getPlayer().sendMessage("" + e.getItem() + ":" + lobbySelector);
		if(compareItems(e.getItem(), lobbySelector))
		{
			e.getPlayer().sendMessage("Hiiiiiiiiiii");
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		giveLobbySelector(e.getPlayer());
	}
	
	public static boolean compareItems(ItemStack item1, ItemStack item2)
	{
		if(!item1.getItemMeta().get)
		if(!item1.getItemMeta().getDisplayName().equals(item2.getItemMeta().getDisplayName()))
		{
			return false;
		}
		if(!item1.getType().equals(item2.getType()))
		{
			return false;
		}
		return true;
	}
}
