package com.github.jannled.mdiServer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jannled.mdiServer.countdown.CountdownTick;
import com.github.jannled.mdiServer.lobby.LobbyManager;
import com.github.jannled.mdiServer.player.PlayerManager;

public class MdIServer extends JavaPlugin
{
	public static CountdownTick countdown;
	
	private final static String pluginName = "MdI-Server";
	private PlayerManager playerManager;
	private LobbyManager lobbyManager;
	
	public MdIServer()
	{
		
	}
	
	@Override 
	public void onEnable()
	{
		MdIServer.countdown = new CountdownTick(this);
		this.lobbyManager = new LobbyManager(this);
		this.playerManager = new PlayerManager(this);
		getServer().getPluginManager().registerEvents(playerManager, this);
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
		return false;
	}
	
	public LobbyManager getLobbyManager()
	{
		return lobbyManager;
	}
	
	public PlayerManager getPlayerManager()
	{
		return playerManager;
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
