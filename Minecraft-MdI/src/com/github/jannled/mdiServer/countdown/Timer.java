package com.github.jannled.mdiServer.countdown;

public class Timer
{
	Countdown countdown;
	private int time;
	private int start;
	private boolean remove = false;

	/**
	 * 
	 * @param countdown The class that should recieve the countdown
	 * @param start The value to start ticking down, for e.g. 10
	 */
	public Timer(Countdown countdown, int start)
	{
		this.countdown = countdown;
		this.start = start;
		this.time = start;
	}
	
	public int tick()
	{
		if(time>0)
		{
			time--;
			countdown.tickCounter(time);
			return time;
		}
		else
		{
			remove = true;
			countdown.end();
			time = 0;
			return time;
		}
	}
	
	public int getStartTime()
	{
		return start;
	}
	
	/**
	 * 
	 * @return The current time
	 */
	public int getTickTime()
	{
		return time;
	}
	
	/**
	 * Check if the countdown reached zero.
	 * @return True if the countdown reached zero, false if not
	 */
	public boolean hasEnded()
	{
		return remove;
	}
}
