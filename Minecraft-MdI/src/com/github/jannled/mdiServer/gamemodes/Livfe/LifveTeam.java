package com.github.jannled.mdiServer.gamemodes.Livfe;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import com.github.jannled.mdiServer.lobby.Team;

public class LifveTeam extends Team
{
	private int dayCoins;
	private int dayTime;
	private Lifve lifve;
	
	public LifveTeam(ArrayList<LifvePlayer> players, String name, ChatColor color, int dayCoins, int dayTime, Lifve lifve)
	{
		super(name, color, null);
		setPlayers(players);
		this.dayCoins = dayCoins;
		this.dayTime = dayTime;
		this.lifve = lifve;
	}

	/**
	 * Ticks every second, called by the Gamemode Lifve
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
			for(LifvePlayer p : getPlayers())
			{
				if(p.isOnline())
				{
					p.getPlayer().getPlayer().kickPlayer(ChatColor.GOLD + "Dein Team " + ChatColor.RESET + getName() + ChatColor.GOLD + " wurde gekickt, verbleibende Tagesmünzen: " + dayCoins + "!");
				}
			}
			return 0;
		}
	}
	
	public void reset()
	{
		dayCoins = lifve.getDefaultDayCoins();
		dayTime = lifve.getStartTime();
	}
	
	@Override
	public void updateScoreboard()
	{
		getTeamStat().setDisplayName(getName() + ": " + ChatColor.GREEN + toDigiTime(dayTime) + ChatColor.GOLD + " ©" + dayCoins);
		for(LifvePlayer p : getPlayers())
		{
			if(p.isOnline())
			{
				p.getPlayer().getPlayer().setScoreboard(teamScoreboard);
			}
			String prefix = p.isOnline() ? ChatColor.GREEN + "" : ChatColor.GRAY + "";
			prefix += ChatColor.STRIKETHROUGH + "";
			teamStats.getScore(prefix + p.getPlayer().getName()).setScore(0);
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
