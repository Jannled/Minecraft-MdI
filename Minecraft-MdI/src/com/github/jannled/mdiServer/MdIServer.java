package com.github.jannled.mdiServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jannled.mdiServer.abilities.Abilitiemanager;
import com.github.jannled.mdiServer.commands.Ping;
import com.github.jannled.mdiServer.countdown.CountdownTick;
import com.github.jannled.mdiServer.lobby.LobbyManager;
import com.github.jannled.mdiServer.player.PlayerManager;
import com.github.jannled.mdiServer.ui.Guimanager;
import com.github.jannled.mdiServer.world.WorldManager;

import net.md_5.bungee.api.ChatColor;

public class MdIServer extends JavaPlugin
{
	public static CountdownTick countdown;
	private FileConfiguration config = getConfig();
	
	private final static String pluginName = "MdI-Server";
	
	//All commands that are not in other classes (Like in the Lobby manager)
	private Ping cmdPing = new Ping();
	
	
	//All Manager instances
	private WorldManager worldManager;
	private LobbyManager lobbyManager;
	private PlayerManager playerManager;
	private Guimanager guiManager;
	private Abilitiemanager abilitieManager;
	
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
		this.guiManager = new Guimanager(this);
		this.abilitieManager = new Abilitiemanager(this);
		
		getServer().getPluginManager().registerEvents(worldManager, this);
		getServer().getPluginManager().registerEvents(playerManager, this);
		
		saveDefaultConfig();
		
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() 
		{
			@Override 
			public void run()
			{
				loadConfig();
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
		else if(command.getLabel().equalsIgnoreCase("mdiserver"))
		{
			return cmdMdIServer(sender, command, name, args);
		}
		else if(command.getLabel().equalsIgnoreCase("ping"))
		{
			return cmdPing.cmdPing(sender, command, name, args);
		}
		else if(command.getLabel().equalsIgnoreCase("abilities"))
		{
			return abilitieManager.cmdAbilities(sender, command, name, args);
		}
		return false;
	}
	
	/**
	 * Reload all configuration files
	 */
	public void loadConfig()
	{
		Bukkit.broadcast(ChatColor.GREEN + "Loading config files! ", "mdiServer.mdiServer");
		getLogger().info("Loading config files!");
		reloadConfig();
		Set<String> lobbysConf = config.getConfigurationSection("Lobbys").getKeys(false);
		String[] lobbys = lobbysConf.toArray(new String[lobbysConf.size()]);
		
		getLogger().info("Lobbys in config" + Arrays.toString(lobbys));
		ArrayList<String> worlds = new ArrayList<String>();
		for(String l : lobbys)
		{
			String wn = config.getString("Lobbys." + l + ".world");
			if(!worlds.contains(wn))
			{
				worldManager.registerWorld(wn);
			}
		}
		lobbyManager.loadLobbys(lobbys);
		getLogger().info("Loaded config files! ");
	}
	
	public boolean cmdMdIServer(CommandSender sender, Command command, String name, String[] args)
	{
		if(args.length > 0 && args[0].equals("reload"))
		{
			loadConfig();
			return true;
		}
		return false;
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
	
	public Guimanager getGuimanager()
	{
		return guiManager;
	}
	
	public Abilitiemanager getAbilitieManager()
	{
		return abilitieManager;
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
