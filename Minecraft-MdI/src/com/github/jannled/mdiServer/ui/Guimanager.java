package com.github.jannled.mdiServer.ui;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

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
		//Add the Opener for the Lobby Selector to the Player Inventory
		if(!e.getPlayer().getInventory().contains(guis.get(0).getOpener()))
		{
			e.getPlayer().getInventory().addItem(guis.get(0).getOpener());
		}
	}
	
	@EventHandler
	public void open(PlayerInteractEvent e)
	{
		if(e.getAction() == Action.PHYSICAL || !e.getHand().equals(EquipmentSlot.HAND) || e.getItem() == null)
			return;
		
		for(Gui g : guis)
		{
			if(e.getItem().equals(g.getOpener()))
			{
				g.show(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void interact(InventoryClickEvent e)
	{
		for(Gui g : guis)
		{
			if(compareInventorys(e.getClickedInventory(), g.getInventory()))
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
	
	public boolean compareInventorys(Inventory inv1, Inventory inv2)
	{
		//Check if the number of stored items is equal
		if(inv1.getContents().length == inv2.getContents().length)
		{
			for(int i=0; i<inv1.getContents().length; i++)
			{
				if(inv1.getContents()[i]==null && inv2.getContents()[i]==null)
				{
					continue;
				}
				else if(inv1.getContents()[i]==null || inv2.getContents()[i]==null)
				{
					return false;
				}
				if(!inv1.getContents()[i].equals(inv2.getContents()[i]))
					return false;
			}
			//Check if Names are equal
			if(inv1.getName()!=null && inv2.getName()!=null)
			{
				if(inv1.getName().equals(inv2.getName()))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
