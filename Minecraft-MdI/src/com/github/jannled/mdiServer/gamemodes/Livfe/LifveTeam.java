package com.github.jannled.mdiServer.gamemodes.Livfe;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import com.github.jannled.mdiServer.lobby.Team;

public class LifveTeam extends Team
{
	private int dayCoins;
	private int dayTime;
	private Lifve lifve;
	private Scoreboard scoreboard;
	
	public LifveTeam(String name, ChatColor color, Location spawn, int dayCoins, int dayTime, Lifve lifve)
	{
		super(name, color, spawn);
		this.dayCoins = dayCoins;
		this.dayTime = dayTime;
		this.lifve = lifve;
	}

	/**
	 * 
	 * @return The time before the counter has ticked, or 0 if the counter reaches zero and the team gets kicked!
	 */
	public int tick()
	{
		if(dayTime > 0)
		{
			dayTime--;
			return dayTime+1;
		}
		else
		{
			dayTime = lifve.getStartTime();
			lifve.getLobby().broadcast("Das Team " + ChatColor.RESET + getName() + ChatColor.GOLD + " hat seine Tageszeit aufgebraucht!");
			for(OfflinePlayer p : getPlayers())
			{
				if(p.isOnline())
				{
					((Player) p).kickPlayer(ChatColor.GOLD + "Dein Team " + ChatColor.RESET + getName() + ChatColor.GOLD + " wurde gekickt, verbleibende Tagesmünzen: " + dayCoins + "!");
				}
			}
			return 0;
		}
	}
	
	@Override
	public void updateScoreboard()
	{
		getTeamStat().setDisplayName(getName() + ": " + ChatColor.GREEN + toDigiTime(dayTime) + ChatColor.GOLD + " ©" + dayCoins);
		for(OfflinePlayer p : getPlayers())
		{
			if(p.isOnline())
			{
				((Player) p).setScoreboard(scoreboard);
			}
		}
	}
	
	public int getDayCoins()
	{
		return dayCoins;
	}
	
	public int getDayTime()
	{
		return dayTime;
	}
	
	/**
	 * 
	 * @param Zeit Time in seconds to be converted into digital time.
	 * @return Returns a string with with the time seperated by a : like you would see it on a digital clock
	 */
	public static String toDigiTime(int Zeit)
	{
		String Digi;
		Digi = "" + Zeit/60 + ":" + ((Zeit-Zeit/60*60) < 10 ? "0" + (Zeit-Zeit/60*60) : (Zeit-Zeit/60*60));
		return Digi;
	}
}
