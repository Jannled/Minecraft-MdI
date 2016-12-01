package com.github.jannled.mdiServer.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Guientry
{
	ItemStack item;
	Representable represented;
	int position = -1;
	
	public Guientry(String name, Material material, Representable represented)
	{
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(0, ChatColor.GRAY + name);
		item = new ItemStack(material);
		item.getItemMeta().setDisplayName(name);
		item.getItemMeta().setLore(lore);
	}
	
	public Guientry(String name, Material material, List<String> description, Representable represented)
	{
		item = new ItemStack(material);
		item.getItemMeta().setDisplayName(name);
		item.getItemMeta().setLore(description);
	}
	
	public Guientry(String name, Material material, Representable represented, int position)
	{
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(0, ChatColor.GRAY + name);
		item = new ItemStack(material);
		item.getItemMeta().setDisplayName(name);
		item.getItemMeta().setLore(lore);
		this.position = position;
	}
	
	public Guientry(String name, Material material, List<String> description, Representable represented, int position)
	{
		item = new ItemStack(material);
		item.getItemMeta().setDisplayName(name);
		item.getItemMeta().setLore(description);
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
}
