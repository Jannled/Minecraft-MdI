package com.github.jannled.mdiServer.ui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Gui
{
	Inventory menu;
	ArrayList<Guientry> entrys;
	
	public Gui(ArrayList<Guientry> entrys, InventoryType type)
	{
		this.menu = Bukkit.createInventory(null, type);
		this.entrys = entrys;
	}
	
	public void clicked(Player player, ItemStack item)
	{
		player.sendMessage("Item " + item.toString() + " was clicked!");
		for(Guientry e : entrys)
		{
			if(e.getItem().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()))
			{
				player.sendMessage("It worked!!!" + e.getItem().toString());
			}
		}
	}
	
	public void show(Player p)
	{
		p.openInventory(menu);
	}
	
	public Inventory getInventory()
	{
		return menu;
	}
}
