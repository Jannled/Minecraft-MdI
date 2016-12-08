package com.github.jannled.mdiServer.ui;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.github.jannled.mdiServer.MdIServer;

public class Guimanager implements Listener
{
	private MdIServer main;
	
	private ArrayList<Gui> guis = new ArrayList<Gui>();
	
	public Guimanager(MdIServer main)
	{
		this.main = main;
		this.main.getServer().getPluginManager().registerEvents(this, main);
	}

	public Gui newGui(ArrayList<Guientry> entrys, InventoryType type)
	{
		Gui gui = new Gui(entrys, type);
		guis.add(gui);
		return gui;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		e.getPlayer().getInventory().setItem(0, guis.get(0).getOpener());
	}
	
	@EventHandler
	public void open(PlayerInteractEvent e)
	{
		if(!e.getHand().equals(EquipmentSlot.HAND))
			return;
		
		for(Gui g : guis)
		{
			System.out.println(e.getItem());
			System.out.println(g.getOpener());
			if(e.getItem().equals(g.getOpener()))
			{
				g.show(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void interact(InventoryClickEvent e)
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
	
	public void registerGui(Gui gui)
	{
		guis.add(gui);
	}
}
