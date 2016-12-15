package com.github.jannled.mdiServer.abilities;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public abstract class Abilitie implements Listener
{
	ItemStack item;
	
	public Abilitie(Material material, String name, String description)
	{
		setItem(new ItemStack(material));
		ItemMeta itm = item.getItemMeta();
		itm.setDisplayName(ChatColor.DARK_PURPLE + name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + description);
		itm.setLore(lore);
		item.setItemMeta(itm);
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
