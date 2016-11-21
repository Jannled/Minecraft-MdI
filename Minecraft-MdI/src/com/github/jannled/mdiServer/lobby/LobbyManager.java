package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.P;
import com.github.jannled.mdiServer.gamemodes.CaptureTheFlag;

public class LobbyManager
{
	MdIServer main;
	
	int defaultLobby = 0;
	ArrayList<Lobby> lobbys = new ArrayList<Lobby>();
	
	public LobbyManager(MdIServer main)
	{
		this.main = main;
		lobbys.add(new Lobby("Spawn", new Location(Bukkit.getServer().getWorlds().get(0), 0, 100, 0)));
		lobbys.add(new LobbyGame("CTF", new Location(Bukkit.getWorlds().get(0), 33, 64, 33), new CaptureTheFlag(new Team[] {new Team("Red"), new Team("Blue")})));
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
				joinLobby((Player) sender, lobby);
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
				joinLobby(player, lobby);
				return true;
			}
		}
		return false;
	}
	
	public void joinLobby(Player player, Lobby lobby)
	{
		for(Lobby l : lobbys)
		{
			l.leaveLobby(player);
		}
		lobby.joinLobby(player);
	}
	
	/**
	 * THis method checks if the player is still in any Lobby and teleports him there, if it still exists or brings him to spawn
	 * @param player The player to reconnect to its lobby
	 * @return The Lobby where the player is reconnected to. If the player is in no lobby, the default spawn lobby is returned
	 */
	public Lobby reconnectLobby(Player player)
	{
		for(Lobby l : lobbys)
		{
			for(int i=0; i<l.getPlayers().size(); i++)
			{
				if(l.getPlayers().get(i).getUniqueId().equals(player.getUniqueId()))
				{
					player.teleport(l.getSpawnLocation());
					player.sendMessage(P.pluginName + "You were reconnected to " + l.getName());
					l.getPlayers().set(i, player);
					return l;
				}
			}
		}
		lobbys.get(defaultLobby).joinLobby(player);
		return lobbys.get(defaultLobby);
	}
	
	public Lobby getLobby(String name)
	{
		for(Lobby lobby : lobbys)
		{
			if(lobby.getName().equalsIgnoreCase(name))
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
	
	public ArrayList<Lobby> getLobbys()
	{
		return lobbys;
	}
	
	public int getDefaultLobby()
	{
		return defaultLobby;
	}
}
