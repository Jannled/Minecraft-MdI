package com.github.jannled.mdiServer.abilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public abstract class Abilitie implements Listener
{
	ItemStack item;
	
	public Abilitie()
	{
		
	}
	
	@EventHandler
	public void onRightclick(PlayerInteractEvent e)
	{
		if(e.getHand()!=EquipmentSlot.HAND || e.getAction() == Action.PHYSICAL || e.getItem() == null)
			return;
		
		if(e.getItem().equals(item))
		{
			playerInteract(e);
		}
	}
	
	@EventHandler
	public void onRightclick(PlayerInteractAtEntityEvent e)
	{
		if(e.getHand()!=EquipmentSlot.HAND || e.getPlayer().getInventory().getItemInMainHand() == null)
			return;
		
		if(e.getPlayer().getInventory().getItemInMainHand().equals(item))
		{
			playerEntityInteract(e);
		}
	}
	
	public abstract void playerInteract(PlayerInteractEvent e);
	
	public abstract void playerEntityInteract(PlayerInteractAtEntityEvent e);
	
	public ItemStack getItem()
	{
		return item;
	}

	public void setItem(ItemStack item)
	{
		this.item = item;
	}
}
