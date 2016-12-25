package com.github.jannled.mdiServer.antiCheat;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.github.jannled.mdiServer.MdIServer;

import net.md_5.bungee.api.ChatColor;;

public class ItemBlacklist implements Listener
{
	private ItemStack[] blacklist;
	ConfigurationSection conf;
	
	public ItemBlacklist(ConfigurationSection conf)
	{
		loadConfig(conf);
		Bukkit.getServer().getPluginManager().registerEvents(this, MdIServer.getInstance());
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e)
	{
		checkItem(e.getPlayer(), e.getItem().getItemStack());
	}
	
	@EventHandler
	public void onItemSwap(PlayerItemHeldEvent e)
	{
		checkItem(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
	}

	/**
	 * Remove the item from the players inventory if it is not enabled on the server.
	 * @param item The item to check if it is allowed
	 */
	public void checkItem(Player p, ItemStack item)
	{
		for(ItemStack i : blacklist)
		{
			ItemStack a = i.clone();
			ItemStack b = item.clone();
			a.setAmount(1);
			b.setAmount(1);
			if(a.equals(b))
			{
				if(p != null)
				{
					MdIServer.getInstance().getLogger().info(p.getName() + " had the forbidden item " + item.toString());
					p.sendMessage(ChatColor.RED + "The item " + ChatColor.GOLD + item.getType() + ChatColor.RED + " is not allowed!");
				}
				if(item != null)
				{
					p.getInventory().remove(item);
				}
			}
		}
	}
	
	public boolean cmdBanItem(CommandSender sender, Command command, String name, String[] args)
	{
		if(args.length == 1 && args[0].equalsIgnoreCase("add"))
		{
			if(sender instanceof Player)
			{
				Player p = (Player) sender;
				if(p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType().equals(Material.AIR))
					return false;
				addToBlacklist(p.getInventory().getItemInMainHand());
				return true;
			}
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("remove"))
		{
			return true;
		}
		return false;
	}
	
	public void addToBlacklist(ItemStack item)
	{
		ItemStack[] buffer = blacklist;
		blacklist = new ItemStack[blacklist.length+1];
		for(int i=0; i<buffer.length; i++)
		{
			blacklist[i] = buffer[i];
		}
		blacklist[blacklist.length-1] = item;
		conf.set("blacklist.items", blacklist);
	}
	
	public void loadConfig(ConfigurationSection conf)
	{
		blacklist = new ItemStack[0];
		this.conf = conf;
		if(conf.getList("blacklist.items") == null)
		{
			return;
		}
		List<?> items = conf.getList("blacklist.items");
		blacklist = new ItemStack[items.size()];
		for(int i=0; i<items.size(); i++)
		{
			if(items.get(i) instanceof ItemStack)
			{
				blacklist[i] = (ItemStack) items.get(i);
			}
			else
			{
				MdIServer.getInstance().getLogger().warning("[ItemBlacklist] Unsupported item found in Blacklist");
			}
		}
	}
}
