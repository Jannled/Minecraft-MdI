package com.github.jannled.mdiServer.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
			long ping = ping(p.getAddress().getAddress());
			sender.sendMessage("Pong: " + ping + "ms!");
		}
		else
		{
			try
			{
				long ping = ping(InetAddress.getByName("google.de"));
				sender.sendMessage("Google.de: " + ping + "ms!");
			} catch (UnknownHostException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public long ping(InetAddress target)
	{
		long ping = -11;
		try
		{
			long start = System.currentTimeMillis();
			InetAddress.getByName("google.de").isReachable(300);
			ping = System.currentTimeMillis() - start;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return ping;
	}
}
