package com.github.jannled.mdiServer.lobby;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.P;
import com.github.jannled.mdiServer.gamemodes.Gamemode;
import com.github.jannled.mdiServer.ui.Gui;
import com.github.jannled.mdiServer.ui.Guientry;

public class LobbyManager
{
	MdIServer main;
	
	int defaultLobby = 0;
	ArrayList<Lobby> lobbys = new ArrayList<Lobby>();
	
	public LobbyManager(MdIServer main)
	{
		this.main = main;
		//lobbys.add(new LobbyGame("CTF", new Location(main.getWorldManager().getDefaultWorld(), 33, 64, 33), new CaptureTheFlag(360)));
	}
	
	public void loadLobbys(String[] lobbys)
	{
		ArrayList<Lobby> newLobbys = new ArrayList<Lobby>();
		ArrayList<Guientry> guiEntrys = new ArrayList<Guientry>();
		FileConfiguration config = main.getConfig();
		for(String l : lobbys)
		{
			main.getLogger().info("LobbyManager: Loading lobby " + l);
			Lobby newLobby = null;
			String[] split = l.split("\\.");
			String name = split[split.length-1];
			World world = Bukkit.getWorld(config.getConfigurationSection("Lobbys." + l).getString("world"));
			double x = config.getConfigurationSection("Lobbys." + l).getDouble("xpos");
			double y = config.getConfigurationSection("Lobbys." + l).getDouble("ypos");
			double z = config.getConfigurationSection("Lobbys." + l).getDouble("zpos");
			String gamemodeName = config.getConfigurationSection("Lobbys." + l).getString("gamemode");
			Gamemode gamemode = null;
			if(!gamemodeName.equals("none"))
			{
				gamemode = loadGamemode(world, l, gamemodeName);
				if(gamemode == null)
				{
					main.getLogger().warning("The gamemode " + gamemodeName + " was not found");
					continue;
				}
				newLobby = new LobbyGame(name, new Location(world, x, y, z), gamemode);
			}
			else
			{
				newLobby = new Lobby(name, new Location(world, x, y, z));
			}
			System.out.println("Dead code? Now at Lobby " + newLobby);
			newLobbys.add(newLobby);
			guiEntrys.add(new Guientry(newLobby));
		}
		System.out.println(Arrays.toString(newLobbys.toArray()));
		this.lobbys = newLobbys;
		main.getGuimanager().registerGui(new Gui(guiEntrys, InventoryType.HOPPER));
	}
	
	public Gamemode loadGamemode(World world, String lobbyName, String gamemodeName)
	{
		final String path = "Lobbys." + lobbyName + ".gamemode";
		final FileConfiguration config = main.getConfig();
		Gamemode gamemode = null;
		Team[] teams = null;
		
		//Load teams
		System.out.println("Now loading: " + path);
		ConfigurationSection c = config.getConfigurationSection(path);
		teams = new Team[c.getKeys(false).size()];
		int pos = 0;
		for(String s : c.getKeys(false))
		{	
			final String teamPath = "." + s + ".";
			ChatColor color = ChatColor.getByChar(teamPath + "color");
			double x = c.getDouble(teamPath + "spawnx");
			double y = c.getDouble(teamPath + "spawny");
			double z = c.getDouble(teamPath + "spawnz");
			teams[pos] = new Team(s, color, new Location(world, x, y, z));
			pos++;
		}
		
		if(gamemodeName.equals("CaptureTheFlag"))
		{
			
		}
		else if(gamemodeName.equals("Spleef"))
		{
			//gamemode = new Spleef(300);
		}
		else
		{
			main.getLogger().warning("Could not find specified Gamemode " + gamemodeName + "!");
		}
		return gamemode;
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
	
	public boolean cmdLobby(CommandSender sender, Command command, String name, String[] args)
	{
		if(sender instanceof Player)
		{
			joinLobby((Player) sender, lobbys.get(defaultLobby));
			return true;
		}
		sender.sendMessage(ChatColor.DARK_RED + "Only players can use " + ChatColor.RED + "/" + name + ChatColor.DARK_RED + "!");
		return true;
	}
	
	public boolean cmdStartGame(CommandSender sender, Command command, String name, String[] args)
	{
		sender.sendMessage("You try to forcefully start the game!");
		if(args.length==0 && sender instanceof Player)
		{
			Player p = (Player) sender;
			Lobby lobby = getLobby(p);
			if(lobby==null)
			{
				sender.sendMessage(ChatColor.RED + "Exception: No Lobby found for that player!");
				return false;
			}
			if(lobby instanceof LobbyGame)
			{
				LobbyGame l = (LobbyGame) lobby;
				l.start();
				return true;
			}
			sender.sendMessage(ChatColor.DARK_RED + "This lobby doesn't have a gamemode!");
			
		}
		else if(args.length == 1)
		{
			Lobby lobby = getLobby(args[0]);
			if(lobby==null)
			{
				sender.sendMessage(ChatColor.RED + "No lobby found for name " + args[0] + "!");
				return false;
			}
			if(lobby instanceof LobbyGame)
			{
				LobbyGame l = (LobbyGame) lobby;
				l.start();
				return true;
			}
			sender.sendMessage(ChatColor.DARK_RED + "This lobby doesn't have a gamemode!");
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
	
	/**
	 * Gets a lobby by its name
	 * @param name The name if the lobby
	 * @return The Lobby with the name, or null if a lobby with this name doesn't exist
	 */
	public Lobby getLobby(String name)
	{
		for(Lobby lobby : lobbys)
		{
			if(lobby.getName().equalsIgnoreCase(name))
				return lobby;
		}
		return null;
	}
	
	/**
	 * Gets the lobby this player is in
	 * @param player The player to get it's lobby
	 * @return The Lobbby the player is in, or null if it can't be found
	 */
	public Lobby getLobby(Player player)
	{
		for(Lobby l : lobbys)
		{
			for(Player p : l.getPlayers())
			{
				if(p.equals(player))
				{
					return l;
				}
			}
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
