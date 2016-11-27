package com.github.jannled.mdiServer.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ping
{

	public Ping()
	{
		
	}
	
	public boolean cmdPing(CommandSender sender, Command command, String name, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			long ping = -88;
			try
			{
				long start = System.currentTimeMillis();
				p.getAddress().getAddress().isReachable(300);
				ping = System.currentTimeMillis() - start;
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			sender.sendMessage("Pong: " + ping + "ms!");
		}
		return true;
	}
}
