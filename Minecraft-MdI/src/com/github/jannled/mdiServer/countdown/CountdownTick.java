package com.github.jannled.mdiServer.countdown;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * 
 * @author Jannled
 * @version 0.0.1
 */
public class CountdownTick
{
	Plugin plugin;
	
	public CountdownTick(Plugin plugin)
	{
		this.plugin = plugin;
	}
	
	/**
	 * Starts the Countdown for the specified class
	 * @param countdown The Class you want to run the countdown method in
	 * @param start The value to start counting down, for e.g. 10
	 */
	public void startCoundown(Countdown countdown, int start)
	{
		Timer timer = new Timer(countdown, start);
		int taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, timer, 20, 20);
		timer.setTaskID(taskID);
	}
}
