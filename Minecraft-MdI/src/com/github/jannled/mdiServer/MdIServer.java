package com.github.jannled.mdiServer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jannled.mdiServer.countdown.CountdownTick;
import com.github.jannled.mdiServer.lobby.LobbyManager;
import com.github.jannled.mdiServer.player.PlayerManager;
import com.github.jannled.mdiServer.ui.Selector;
import com.github.jannled.mdiServer.world.WorldManager;

public class MdIServer extends JavaPlugin
{
	public static CountdownTick countdown;
	
	private final static String pluginName = "MdI-Server";
	private WorldManager worldManager;
	private PlayerManager playerManager;
	private LobbyManager lobbyManager;
	private Selector selector;
	
	public MdIServer()
	{
		
	}
	
	@Override 
	public void onEnable()
	{
		MdIServer.countdown = new CountdownTick(this);
		this.worldManager = new WorldManager(this);
		this.lobbyManager = new LobbyManager(this);
		this.playerManager = new PlayerManager(this);
		this.selector = new Selector(this);
		getServer().getPluginManager().registerEvents(worldManager, this);
		getServer().getPluginManager().registerEvents(playerManager, this);
		getServer().getPluginManager().registerEvents(selector, this);
		
		saveDefaultConfig();
		
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() 
		{
			@Override 
			public void run()
			{
				afterServerStart();
			}
		});
		
		getLogger().info(pluginName + " lock'n'load!");
	}
	
	@Override 
	public void onDisable()
	{
		getLogger().info("Unloading " + pluginName + "!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String name, String[] args)
	{
		if(command.getLabel().equalsIgnoreCase("join"))
		{
			return lobbyManager.cmdJoin(sender, command, name, args);
		}
		else if(command.getLabel().equalsIgnoreCase("lobby"))
		{
			return lobbyManager.cmdLobby(sender, command, name, args);
		}
		else if(command.getLabel().equalsIgnoreCase("startgame"))
		{
			return lobbyManager.cmdStartGame(sender, command, name, args);
		}
		else if(command.getLabel().equalsIgnoreCase("goto"))
		{
			return worldManager.cmdGoto(sender, command, name, args);
		}
		return false;
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e)
	{
		if(worldManager == null)
		{
			getLogger().info("WorldManager hooked after world load");
			this.worldManager = new WorldManager(this);
			getServer().getPluginManager().registerEvents(worldManager, this);
		}
	}
	
	/**
	 * Method gets called when the server has started and all worlds are loaded.
	 */
	public void afterServerStart()
	{
		worldManager.loadWorlds();
		lobbyManager.loadLobbys();
	}
	
	public WorldManager getWorldManager()
	{
		return worldManager;
	}
	
	public LobbyManager getLobbyManager()
	{
		return lobbyManager;
	}
	
	public PlayerManager getPlayerManager()
	{
		return playerManager;
	}
	
	public Selector getSelector()
	{
		return selector;
	}
	
    public static MdIServer getInstance() 
    {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginName);
        if (plugin == null || !(plugin instanceof MdIServer))
        {
            throw new RuntimeException(pluginName + " not found. Is " + pluginName + " disabled?");
        }
 
        return ((MdIServer) plugin);
    }
}
