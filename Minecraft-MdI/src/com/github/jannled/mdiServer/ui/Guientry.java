package com.github.jannled.mdiServer.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Guientry
{
	ItemStack item;
	Representable represented;
	int position = 0;
	
	public Guientry(Representable represented)
	{
		if(represented.getItem() == null)
		{
			item = standardItem(represented.getClass().getCanonicalName());
		}
		else
		{
			item = represented.getItem();
		}
		this.represented = represented;
	}
	
	public Guientry(Representable represented, int position)
	{
		if(represented.getItem() == null)
		{
			item = standardItem(represented.getClass().getCanonicalName());
		}
		else
		{
			item = represented.getItem();
		}
		this.represented = represented;
		this.position = position;
	}
	
	public void called(Player player)
	{
		represented.clicked(player);
	}

	public ItemStack getItem()
	{
		return item;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	private ItemStack standardItem(String name)
	{
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(0, ChatColor.GRAY + name);
		ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
		item.getItemMeta().setDisplayName(name);
		item.getItemMeta().setLore(lore);
		return item;
	}
}
