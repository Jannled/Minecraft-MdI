package com.github.jannled.mdiServer.gamemodes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.countdown.Countdown;
import com.github.jannled.mdiServer.lobby.Team;

/**
 * Class for creating gamemodes
 * @author Jannled
 * @version 0.0.1
 */
public abstract class Gamemode implements Countdown
{
	public final static int BEFORE_GAME = 1;
	public final static int IN_GAME = 2;
	public final static int AFTER_GAME = 3;
	
	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard scoreboard = manager.getNewScoreboard();
	private ArrayList<org.bukkit.scoreboard.Team> scoreboardTeams = new ArrayList<org.bukkit.scoreboard.Team>();
	
	private Team[] teams;
	
	/** Represents the state the game is currently in */
	int state;
	
	/**
	 * Create a new gamemode.
	 * @param teams The teams 
	 */
	public Gamemode(Team[] teams)
	{
		this.teams = teams;
		start();
	}
	
	/**
	 * Create a new gamemode.
	 */
	public Gamemode()
	{
		start();
	}
	
	private void start()
	{
		//Add every team to the scoreboard
		for(Team t : teams)
		{
			org.bukkit.scoreboard.Team team = scoreboard.registerNewTeam(t.getName());
			team.setAllowFriendlyFire(false);
			team.setPrefix("[" + t.getName() + "]");
			scoreboardTeams.add(team);
		}
		
		state = BEFORE_GAME;
		before();
		MdIServer.countdown.startCoundown(this, 10);
	}
	
	@Override
	/**
	 * 
	 */
	public void end()
	{
		if(state == BEFORE_GAME)
		{
			state = IN_GAME;
			round();
			state = AFTER_GAME;
			after();
			cleanUp();
		}
	}
	
	@Override
	/**
	 * 
	 */
	public void tickCounter(int time)
	{
		countdownTicks(time);
		if(state == BEFORE_GAME)
		{
			for(Team t : teams)
			{
				for(Player p : t.getPlayers())
				{
					countdownForPlayers(p, time);
				}
			}
		}
	}
	
	/**
	 * This method gets called per second while the countdown before roundbegin ticks, for every player in this round 
	 * <code>@Overwrite</code> this to create a custom countdown message.
	 * @param p The player to send the countdown to
	 * @param time The seconds left after the countdown reaches zero
	 */
	public void countdownForPlayers(Player p, int time)
	{
		p.sendMessage(this.getClass().getCanonicalName() + " Die Runde beginnt in " + time + " Sekunden!");
	}
	
	/**
	 * Method gets called every second, when the countdown ticks. <code>@Overwrite</code> this if you want to have something special executed while the countdown
	 * ticks, <code>@Overwrite countdownForPlayers(Player p, int time)</code> if you want to customize the round beginning countdown.
	 */
	public void countdownTicks(int time)
	{
		
	}
	
	public void updateScoreboard(int time)
	{
		
	}
	
	public void cleanUp()
	{
		
	}
	
	/**
	 * Method gets called before the countdown ticks
	 */
	public abstract void before();
	
	/**
	 * Method gets called when the round starts, put your gameloop inside here. Is called after the countdown reaches zero
	 */
	public abstract void round();
	
	/**
	 * Method gets called after <code>round()</code> returns. When this method returns, the game will clean up all traces left behind 
	 */
	public abstract void after();
}
