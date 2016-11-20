package com.github.jannled.mdiServer.countdown;

public interface Countdown
{
	/**
	 * Called whenever the counter ticks
	 * @param time The current time
	 */
	public void tickCounter(int time);
	
	/**
	 * Called when the counter reaches zero
	 */
	public void end();
}
