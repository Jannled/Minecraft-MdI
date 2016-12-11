package com.github.jannled.mdiServer.abilities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Abilitiemanager
{
	JavaPlugin plugin;
	ArrayList<Abilitie> abilities = new ArrayList<Abilitie>();
	
	public Abilitiemanager(JavaPlugin plugin)
	{
		this.plugin = plugin;
		addAbilitie(new Smokedash());
		addAbilitie(new PlayerPickup());
	}

	public void addAbilitie(Abilitie abilitie)
	{
		plugin.getServer().getPluginManager().registerEvents(abilitie, plugin);
		abilities.add(abilitie);
	}
	
	public boolean cmdAbilities(CommandSender sender, Command command, String name, String[] args)
	{
		Player p = null;
		if(args.length < 1)
		{
			return false;
		}
		if(sender instanceof Player)
		{
			p = (Player) sender;
		}
		else if(args.length == 2)
		{
			p = Bukkit.getPlayer(args[1]);
		}
		if(p==null)
		{
			sender.sendMessage(ChatColor.RED + "Please specify a valid player name as second argument!");
			return false;
		}
		for(Abilitie a : abilities)
		{
			sender.sendMessage(a.getClass().getSimpleName());
			if(a.getClass().getSimpleName().equalsIgnoreCase(args[0]))
			{
				p.getInventory().addItem(a.getItem());
				return true;
			}
		}
		return false;
	}
}
