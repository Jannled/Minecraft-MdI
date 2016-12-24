package com.github.jannled.mdiServer.antiCheat;

import org.bukkit.Material;
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
	
	public ItemBlacklist(ConfigurationSection conf)
	{
		loadConfig(conf);
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
			if(i.equals(item))
			{
				if(p != null)
				{
					p.sendMessage(ChatColor.RED + "The item " + ChatColor.GOLD + item.toString() + ChatColor.RED + " is not allowed!");
				}
				if(item != null)
				{
					item.setAmount(0);
				}
			}
		}
	}
	
	public void loadConfig(ConfigurationSection conf)
	{
		MdIServer.getInstance().getLogger().info("LOADINGIANGANGANGIANOIGN");
		conf.set("blacklist.items", new ItemStack(Material.PRISMARINE));
		conf.set("enabled", false);
		blacklist = new ItemStack[3];
		MdIServer.getInstance().saveConfigFile();
	}
}
