package com.github.jannled.mdiServer.ui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.jannled.mdiServer.MdIServer;

public class Gui
{
	String name;
	Inventory menu;
	ArrayList<Guientry> entrys;
	ItemStack opener;
	
	public Gui(ArrayList<Guientry> entrys, InventoryType type)
	{
		this.name = ChatColor.GOLD + "GUI";
		this.menu = Bukkit.createInventory(null, type, name);
		this.opener = new ItemStack(Material.COMPASS);
		addToInv(entrys);
		ItemMeta itmeta = opener.getItemMeta();
		itmeta.setDisplayName(name);
		opener.setItemMeta(itmeta);
	}
	
	public Gui(ArrayList<Guientry> entrys, InventoryType type, ItemStack opener)
	{
		if(opener.getItemMeta().getDisplayName()!=null)
			this.name = opener.getItemMeta().getDisplayName();
		else
			this.name = ChatColor.GOLD + "GUI";
		this.menu = Bukkit.createInventory(null, type, name);
		
		this.opener = opener;
		addToInv(entrys);
	}
	
	public Gui(ArrayList<Guientry> entrys, int type)
	{
		this.name = ChatColor.GOLD + "GUI";
		this.menu = Bukkit.createInventory(null, type, name);
		this.opener = new ItemStack(Material.COMPASS);
		addToInv(entrys);
		ItemMeta itmeta = opener.getItemMeta();
		itmeta.setDisplayName(name);
		opener.setItemMeta(itmeta);
	}
	
	public Gui(ArrayList<Guientry> entrys, int type, ItemStack opener)
	{
		if(opener.getItemMeta().getDisplayName()!=null)
			this.name = opener.getItemMeta().getDisplayName();
		else
			this.name = ChatColor.GOLD + "GUI";
		this.menu = Bukkit.createInventory(null, type, name);
		this.opener = opener;
		addToInv(entrys);
	}
	
	public void clicked(Player player, ItemStack item)
	{
		if(item == null)
			return;
		
		for(Guientry e : entrys)
		{
			if(e.getItem().equals(item))
			{
				e.called(player);
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
	
	public ItemStack getOpener()
	{
		return opener;
	}
	
	private boolean addToInv(ArrayList<Guientry> entrys)
	{
		boolean state = false;
		this.entrys = entrys;
		for(Guientry e : entrys)
		{
			if(menu == null)
			{
				MdIServer.getInstance().getLogger().warning("The Menu for Gui " + name + " is null!");
				continue;
			}
			int pos = e.getPosition();
			while(menu.getItem(pos) != null)
			{
				if(pos+1>menu.getSize()-1)
				{
					MdIServer.getInstance().getLogger().warning("The item " + e.getItem().toString() + " exceeds the bounds of the Inventory");
					return false;
				}
				pos++;
			}
			menu.setItem(pos, e.getItem());
			state = true;
		}
		return state;
	}
	
	public String getName()
	{
		return name;
	}
}
