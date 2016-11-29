package com.github.jannled.mdiServer.abilities;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.minecraft.server.v1_11_R1.PacketPlayOutMount;

public class PlayerPickup implements Listener
{
	boolean flip;
	
	public PlayerPickup()
	{
		
	}
	
	@EventHandler
	public void entityClicked(PlayerInteractEntityEvent e)
	{
		if(e.getHand().equals(EquipmentSlot.HAND))
		{
			e.getPlayer().sendMessage(ChatColor.GRAY + "You stacked " + ChatColor.GREEN + e.getRightClicked() + ChatColor.GRAY + "!");
			e.getPlayer().setPassenger(e.getRightClicked());
			
			//Update the ride packet, thanks to you Mojang for not fixing this...
	        PacketPlayOutMount packet = new PacketPlayOutMount(((CraftPlayer) e.getPlayer()).getHandle());
	        ((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket(packet);
		}
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
