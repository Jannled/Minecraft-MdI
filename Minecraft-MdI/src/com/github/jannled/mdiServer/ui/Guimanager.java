package com.github.jannled.mdiServer.ui;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import com.github.jannled.mdiServer.MdIServer;

public class Guimanager implements Listener
{
	private ArrayList<Gui> guis = new ArrayList<Gui>();
	
	public Guimanager(MdIServer main)
	{
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	public Gui newGui(ArrayList<Guientry> entrys, InventoryType type)
	{
		Gui gui = new Gui(entrys, type);
		guis.add(gui);
		return gui;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		e.getWhoClicked().sendMessage("You clicked something!");
		for(Gui g : guis)
		{
			if(e.getClickedInventory() == g.getInventory())
			{
				g.clicked((Player) e.getWhoClicked(), e.getCurrentItem());
				e.setCancelled(true);
			}
		}
	}
}
