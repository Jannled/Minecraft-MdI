package com.github.jannled.mdiServer.countdown;

import org.bukkit.Bukkit;

public class Timer implements Runnable
{
	private Countdown countdown;
	private int taskID;
	private int time;

	public Timer(Countdown countdown, int startTime)
	{
		this.countdown = countdown;
		this.time = startTime;
	}

	public int tickOnce()
	{
		time--;
		return time+1;
	}

	@Override
	public void run()
	{
		System.out.println("Timer: " + time);
		countdown.tickCounter(tickOnce());
		if(time < 1)
		{
			countdown.end();
			if(countdown!=null)
			{
				countdown.end();
				Bukkit.getServer().getScheduler().cancelTask(taskID);
			}
		}
	}
	
	/**
	 * Needs to be set in order to make the event cancelable
	 * @param taskID
	 */
	public void setTaskID(int taskID)
	{
		this.taskID = taskID;
	}
}
