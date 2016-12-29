package com.github.jannled.mdiServer.gamemodes.Livfe;

import org.bukkit.OfflinePlayer;

public class LifvePlayer 
{
	OfflinePlayer player;
	boolean dead;
	
	public LifvePlayer(OfflinePlayer player, boolean dead)
	{
		this.player = player;
		this.dead = dead;
	}
	
	public boolean isOnline()
	{
		return player.isOnline();
	}

	public OfflinePlayer getPlayer()
	{
		return player;
	}
	
	public boolean isDead()
	{
		return dead;
	}
}
