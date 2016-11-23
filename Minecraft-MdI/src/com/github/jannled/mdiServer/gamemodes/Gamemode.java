package com.github.jannled.mdiServer.gamemodes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
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

	private int maxRoundLength;
	private Team[] teams;
	
	/** Represents the state the game is currently in */
	private int state;
	
	/** Round stats */
	private Objective roundStats;
	
	/**
	 * Create a new gamemode.
	 * @param teams The teams 
	 */
	public Gamemode(Team[] teams, int maxRoundLength)
	{
		this.teams = teams;
		this.maxRoundLength = maxRoundLength;
		start();
	}
	
	/**
	 * Create a new gamemode.
	 */
	public Gamemode(int maxRoundLength)
	{
		this.maxRoundLength = maxRoundLength;
		start();
	}
	
	/**
	 * First method called when the gamemode is constructed
	 */
	private void start()
	{
		//Initialize scoreboard for all players
		roundStats = scoreboard.registerNewObjective("Test", "dummy");
		roundStats.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		//Add every team to the scoreboard
		for(Team t : teams)
		{
			org.bukkit.scoreboard.Team team = scoreboard.registerNewTeam(t.getName());
			team.setAllowFriendlyFire(false);
			team.setPrefix("[" + t.getName() + "]");
			scoreboardTeams.add(team);
			
			Score score = roundStats.getScore(t.getName());
			score.setScore(0);
		}
		
		state = BEFORE_GAME;
		before();
		MdIServer.countdown.startCountdown(this, 10);
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
			MdIServer.countdown.startCountdown(this, maxRoundLength);
		}
		else if(state == IN_GAME)
		{
			stopRound();
		}
	}
	
	/**
	 * Call this method if the round should end before the game counter reaches zero
	 */
	public void stopRound()
	{
		if(state == IN_GAME)
		{
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
		else if(state == IN_GAME)
		{
			updateScoreboard(time);
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
		scoreboard.getObjective(DisplaySlot.SIDEBAR).setDisplayName("Time remaining: " + time);
		for(Team t : teams)
		{
			Score score = roundStats.getScore(t.getName());
			score.setScore(t.getTeamPoints());
			for(Player p : t.getPlayers())
			{
				p.setScoreboard(scoreboard);
			}
		}
	}
	
	/**
	 * Method gets called after the game will be destroyed (after <code>after()</code> has returned). 
	 * Put stuff like stopping schedulars or similar inside
	 */
	public void cleanUp()
	{
		roundStats.setDisplaySlot(null);
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
	 * Method gets called after the countdown started at <code>maxRoundLength</code> reaches zero or if <code>stopRound()</code> is called
	 */
	public abstract void after();
}
