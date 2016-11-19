package com.github.jannled.mdiServer.countdown;

public interface Countdown
{
	/**
	 * Called whenever the counter ticks
	 * @param time The value to start counting down, for e.g. 10
	 */
	public void tickCounter(int time);
	
	/**
	 * Called when the counter reaches zero
	 */
	public void end();
}
