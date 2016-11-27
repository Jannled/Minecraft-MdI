package com.github.jannled.mdiServer.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerPickup implements Listener
{
	boolean flip;
	
	public PlayerPickup()
	{
		
	}
	
	@EventHandler
	public void entityClicked(PlayerInteractEntityEvent e)
	{
		if(flip)
		{
			e.getPlayer().sendMessage(ChatColor.GRAY + "You stacked " + ChatColor.GREEN + e.getRightClicked() + ChatColor.GRAY + "!");
			getTop(e.getPlayer()).setPassenger(e.getRightClicked());
		}
		flip = !flip;
	}
	
	public Entity getTop(Entity entity)
	{
		Entity holder = entity;
		while(holder.getPassenger() != null)
		{
			holder = entity.getPassenger();
		}
		return holder;
	}
}
