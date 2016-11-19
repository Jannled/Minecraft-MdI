package com.github.jannled.mdiServer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jannled.mdiServer.countdown.CountdownTick;
import com.github.jannled.mdiServer.lobby.*;

public class MdIServer extends JavaPlugin
{
	public static CountdownTick countdown;
	
	private final String pluginName = "MdI-Server";
	private LobbyManager lobbyManager;
	
	public MdIServer()
	{
		MdIServer.countdown = new CountdownTick(this);
		this.lobbyManager = new LobbyManager(this);
	}
	
	@Override 
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(lobbyManager, this);
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
		if(name.equalsIgnoreCase("join"))
		{
			return lobbyManager.cmdJoin(sender, command, name, args);
		}
		return false;
	}
}
