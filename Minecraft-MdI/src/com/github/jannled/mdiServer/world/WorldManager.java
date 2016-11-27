package com.github.jannled.mdiServer.world;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.P;
import com.github.jannled.mdiServer.gamemodes.CaptureTheFlag;
import com.github.jannled.mdiServer.lobby.Lobby;
import com.github.jannled.mdiServer.lobby.LobbyGame;

public class WorldManager implements Listener
{
	MdIServer main;
	
	private int defaultWorld = 0;
	boolean init = false;
	
	public WorldManager(MdIServer main)
	{
		this.main = main;
	}
	
	public void loadWorlds(String[] names)
	{
		for(String worldName : names)
		{
			registerWorld(worldName);
		}
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e)
	{
		if(!init)
		{
			main.getLobbyManager().getLobbys().add(new Lobby("Spawn", new Location(main.getWorldManager().getDefaultWorld(), 0, 100, 0)));
			main.getLobbyManager().getLobbys().add(new LobbyGame("CTF", new Location(main.getWorldManager().getDefaultWorld(), 33, 64, 33), new CaptureTheFlag(360)));
		}
		main.getLogger().info("WorldManager: Loaded world " + e.getWorld().getName() +"!");
	}
	
	@EventHandler
	public void onWorldUnload(WorldUnloadEvent e)
	{
		
	}
	
	public void registerWorld(String name)
	{
		WorldCreator world = WorldCreator.name(name);
		world.createWorld();
	}
	
	public boolean cmdGoto(CommandSender sender, Command command, String name, String[] args)
	{
		if(!sender.hasPermission(P.pluginPermission + "goto") || args.length < 1)
		{
			return false;
		}
		
		//goto [PlayerDestination]
		if(args.length == 1 && sender instanceof Player)
		{
			Player p = (Player) sender;
			Player destination = Bukkit.getPlayer(args[0]);
			if(destination != null)
			{
				p.teleport(destination.getLocation());
				return true;
			}
			else
			{
				p.sendMessage(ChatColor.GOLD + args[0] + ChatColor.DARK_RED + " is not Online");
				return true;
			}
		}
		//goto <PlayerToMove> [PlayerDestination]
		else if(args.length == 2)
		{
			Player p = Bukkit.getPlayer(args[0]);
			Player destination = Bukkit.getPlayer(args[1]);
			if(p != null && destination != null)
			{
				p.teleport(destination.getLocation());
			}
			else
			{
				p.sendMessage(ChatColor.DARK_RED + "Could not teleport " + ChatColor.GOLD + args[0] + ChatColor.DARK_RED + " to " + ChatColor.GOLD + args[1] + ChatColor.DARK_RED + "!");
				return true;
			}
			
		}
		//goto [X] [Y] [Z] <World> <PlayerToMove>
		else if(args.length<6)
		{
			double x,y,z;
			World location = null;
			Player player = null;
			
			try {
				x = Double.parseDouble(args[0]);
				y = Double.parseDouble(args[1]);
				z = Double.parseDouble(args[2]);
			} catch(NumberFormatException e) {
				sender.sendMessage(ChatColor.DARK_RED + "Please enter valid coordinates!");
				return false;
			}
			if(sender instanceof Player)
			{
				player = (Player) sender;
				location = player.getLocation().getWorld();
			}
			if(args.length>3)
			{
				String worlds = ChatColor.DARK_RED + "All Worlds: ";
				boolean found = false;
				
				for(World world : Bukkit.getWorlds())
				{
					worlds += ChatColor.GOLD + world.getName() + ChatColor.DARK_RED + ", ";
					if(world.getName().equals(args[3]))
					{
						location = world;
						found = true;
					}
				}
				if(found == false || args.length > 4)
				{
					for(Player p : Bukkit.getOnlinePlayers())
					{
						if(p.getName().equals(args[3]))
						{
							player = p;
							found = true;
						}
						else if(args.length > 4)
						{
							if(p.getName().equals(args[4]))
							{
								player = p;
								found = true;
							}
						}
					}
				}
				if(found == false)
				{
					sender.sendMessage(worlds);
					return true;
				}
			}
			//Final check
			if(player==null || location==null)
			{
				sender.sendMessage(ChatColor.RED + "Invalid Teleport destination: ");
				return false;
			}
			player.teleport(new Location(location, x, y, z));
			return true;
		}
		return false;
	}
	
	/**
	 * Get the default spawn world
	 * @return The spawn world, usually an Overworld (Not Nether/End) or null if there is no default world
	 */
	public World getDefaultWorld()
	{
		if(defaultWorld<Bukkit.getWorlds().size())
		{
			return Bukkit.getWorlds().get(defaultWorld);
		}
		return null;
	}
	
	public World getWorld(int index)
	{
		if(defaultWorld<Bukkit.getWorlds().size())
		{
			return Bukkit.getWorlds().get(index);
		}
		return null;
	}
}