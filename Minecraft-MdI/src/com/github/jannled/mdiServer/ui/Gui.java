package com.github.jannled.mdiServer.ui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.jannled.mdiServer.MdIServer;

public class Gui
{
	Inventory menu;
	ArrayList<Guientry> entrys;
	ItemStack opener;
	
	public Gui(ArrayList<Guientry> entrys, InventoryType type)
	{
		this.menu = Bukkit.createInventory(null, type);
		addToInv(entrys);
		this.opener = new ItemStack(Material.COMPASS);
		ItemMeta itmeta = opener.getItemMeta();
		itmeta.setDisplayName("GUI");
		opener.setItemMeta(itmeta);
	}
	
	public Gui(ArrayList<Guientry> entrys, InventoryType type, ItemStack opener)
	{
		this.menu = Bukkit.createInventory(null, type);
		addToInv(entrys);
		this.opener = opener;
	}
	
	public void clicked(Player player, ItemStack item)
	{
		if(item == null)
			return;
		
		player.sendMessage("Item " + item.toString() + " was clicked!");
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
		this.entrys = entrys;
		for(Guientry e : entrys)
		{
			int pos = e.getPosition();
			while(menu.getItem(pos)!=null)
			{
				System.out.println("Item at index " + pos + menu.getItem(pos));
				if(pos+1>menu.getSize()-1)
				{
					MdIServer.getInstance().getLogger().warning("The item " + e.getItem().toString() + " exceeds the bounds of the Inventory");
					return false;
				}
				pos++;
			}
			menu.setItem(pos, e.getItem());
			return true;
		}
		return false;
	}
}
