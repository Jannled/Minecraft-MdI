package com.github.jannled.mdiServer.abilities;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.minecraft.server.v1_11_R1.PacketPlayOutMount;

public class PlayerPickup extends Abilitie
{
	boolean flip;
	
	public PlayerPickup()
	{
		super(Material.TRIPWIRE_HOOK, "Player pickup", "Right click a player to carry him!");
	}
	
	@EventHandler
	public void entityClicked(PlayerInteractEntityEvent e)
	{
		
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

	@Override
	public void playerInteract(PlayerInteractEvent e)
	{
		
	}

	@Override
	public void playerEntityInteract(PlayerInteractAtEntityEvent e)
	{
		e.getPlayer().sendMessage(ChatColor.GRAY + "You stacked " + ChatColor.GREEN + e.getRightClicked() + ChatColor.GRAY + "!");
		e.getPlayer().setPassenger(e.getRightClicked());
		
		//Update the ride packet, thanks to you Mojang for not fixing this...
        PacketPlayOutMount packet = new PacketPlayOutMount(((CraftPlayer) e.getPlayer()).getHandle());
        ((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket(packet);
	}
}
