package com.github.jannled.mdiServer.countdown;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * 
 * @author Jannled
 * @version 0.0.1
 */
public class CountdownTick implements Runnable
{
	Plugin plugin;
	ArrayList<Timer> timer = new ArrayList<Timer>();
	int schedularID;
	
	public CountdownTick(Plugin plugin)
	{
		this.plugin = plugin;
		schedularID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20, 20);
	}
	
	/**
	 * Starts the Countdown for the specified class
	 * @param countdown The Class you want to run the countdown method in
	 * @param start The value to start counting down, for e.g. 10
	 */
	public void startCoundown(Countdown countdown, int start)
	{
		Timer timer = new Timer(countdown, start);
		this.timer.add(timer);
	}

	@Override
	public void run()
	{
		for(Timer t : timer)
		{
			t.tick();
			if(t.hasEnded())
				timer.remove(t);
		}
	}
}
