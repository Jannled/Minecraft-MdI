package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.P;

public class LobbyManager implements Listener
{
	MdIServer main;
	
	int defaultLobby = 0;
	ArrayList<Lobby> lobbys = new ArrayList<Lobby>();
	
	public LobbyManager(MdIServer main)
	{
		this.main = main;
		lobbys.add(new Lobby("Spawn", new Location(Bukkit.getServer().getWorlds().get(0), 0, 100, 0)));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		main.getLogger().info("Player " + e.getPlayer().getDisplayName() + " connected");
		lobbys.get(defaultLobby).joinLobby(e.getPlayer());
	}
	
	public boolean cmdJoin(CommandSender sender, Command command, String name, String[] args)
	{
		if(sender instanceof Player)
		{
			if(sender.hasPermission(P.pluginPermission) && args.length > 0 && args.length < 3)
			{
				Lobby lobby = getLobby(args[0]);
				if(lobby==null)
				{
					sender.sendMessage(ChatColor.DARK_RED + "Could not find lobby " + ChatColor.GOLD + args[0] + ChatColor.DARK_RED + "!");
					return true;
				}
				lobby.joinLobby((Player) sender);
				return true;
			}
		}
		else 
		{
			if(args.length == 2)
			{
				Lobby lobby = getLobby(args[0]);
				if(lobby==null)
				{
					sender.sendMessage(ChatColor.DARK_RED + "Could not find lobby " + ChatColor.GOLD + args[0] + ChatColor.DARK_RED + "!");
					return true;
				}
				Player player = Bukkit.getPlayer(args[1]);
				if(player==null)
				{
					sender.sendMessage(ChatColor.DARK_RED + "Could not find player " + ChatColor.GOLD + args[1] + ChatColor.DARK_RED + "!");
					return true;
				}
				lobby.joinLobby(player);
				return true;
			}
		}
		return false;
	}
	
	public Lobby getLobby(String name)
	{
		for(Lobby lobby : lobbys)
		{
			if(lobby.getName().equals(name))
				return lobby;
		}
		return null;
	}
	
	public void addLobby(Lobby lobby)
	{
		lobbys.add(lobby);
	}
	
	public void removeLobby(Lobby lobby)
	{
		lobbys.remove(lobby);
	}
}
