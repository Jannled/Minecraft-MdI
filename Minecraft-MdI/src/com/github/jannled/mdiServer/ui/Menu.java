package com.github.jannled.mdiServer.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu
{
	String[] names;
	ItemStack[] icons;
	Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER);
	
	public Menu()
	{
		
	}

	public void showInventory(Player player)
	{
		
	}
}
