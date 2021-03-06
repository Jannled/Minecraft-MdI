package com.github.jannled.mdiServer.gamemodes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.countdown.Countdown;
import com.github.jannled.mdiServer.lobby.LobbyGame;
import com.github.jannled.mdiServer.lobby.Team;

/**
 * Class for creating gamemodes
 * @author Jannled
 * @version 0.0.1
 */
public abstract class Gamemode implements Countdown
{
	ConfigurationSection config;
	
	public final static int BEFORE_GAME = 1;
	public final static int IN_GAME = 2;
	public final static int AFTER_GAME = 3;

	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard scoreboard = manager.getNewScoreboard();
	private Team[] teams;
	private ArrayList<org.bukkit.scoreboard.Team> scoreboardTeams = new ArrayList<org.bukkit.scoreboard.Team>();

	private LobbyGame lobby;
	
	private int maxRoundLength;
	
	/** Represents the state the game is currently in */
	public int state = -1;
	
	/** The Scores on the right side */
	private Objective roundStats;
	
	/**
	 * Create a new gamemode.
	 * @param lobby The Lobby for the gamemode, where the Gamemode gets the players from
	 * @param maxRoundLength The length in seconds after the round ends. It can be stopped by calling <code>stopRound()</code> before the countdown has reached zero
	 */
	public Gamemode(ConfigurationSection config)
	{
		this.config = config;
	}
	
	/**
	 * Method to be called when the game should start
	 */
	public void start()
	{
		if(state != -1)
			return;
		
		//Initialize scoreboard for all players
		roundStats = scoreboard.registerNewObjective("Test", "dummy");
		roundStats.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		//Add every team to the scoreboard
		for(Team t : lobby.getTeams())
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
		lobby.broadcast("The Round is starting!");
		MdIServer.countdown.startCountdown(this, 10);
	}
	
	@Override
	/**
	 * Called when a counter reaches zero. If you want to stop the round manually, call <code>stopRound()</code>
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
			for(Player p : lobby.getPlayers())
			{
				countdownForPlayers(p, time);
			}
		}
		else if(state == IN_GAME)
		{
			updateScoreboard(time);
		}
	}
	
	/**
	 * This method gets called per second while the countdown before roundbegin ticks, for every com.github.jannled.mdiServer.player in this round 
	 * <code>@Overwrite</code> this to create a custom countdown message.
	 * @param p The com.github.jannled.mdiServer.player to send the countdown to
	 * @param time The seconds left after the countdown reaches zero
	 */
	public void countdownForPlayers(Player p, int time)
	{
		lobby.broadcast("Die Runde beginnt in " + ChatColor.DARK_AQUA + time + ChatColor.GOLD + " Sekunden!");
	}
	
	/**
	 * Method gets called every second, when the countdown ticks. <code>@Overwrite</code> this if you want to have something special executed while the countdown
	 * ticks, <code>@Overwrite countdownForPlayers(Player p, int time)</code> if you want to customize the round beginning countdown.
	 */
	public void countdownTicks(int time)
	{
		
	}
	
	/**
	 * Method gets called every second to update the scoreboard!
	 * @param time
	 */
	public void updateScoreboard(int time)
	{
		scoreboard.getObjective(DisplaySlot.SIDEBAR).setDisplayName("Time remaining: " + time);
		for(Team t : lobby.getTeams())
		{
			Score score = roundStats.getScore(t.getName());
			score.setScore(t.getTeamPoints());
		}
		for(Player p : lobby.getPlayers())
		{
			p.setScoreboard(scoreboard);
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
	 * Gets the current status of the game, if it is not yet started, counting down for start, ingame, or in the ending.
	 * @return See the static fields for possible values
	 */
	public int getGameState()
	{
		return state;
	}
	
	public Location[] getSpawns()
	{
		Location[] spawns = new Location[teams.length];
		for(int i=0; i<teams.length; i++)
		{
			spawns[i] = teams[i].getSpawn();
		}
		return spawns;
	}
	
	public Team[] getTeams()
	{
		return teams;
	}
	
	public LobbyGame getLobby()
	{
		return lobby;
	}
	
	/**
	 * The lobby that starts the gamemode, and keeps track of the players and teams
	 * @param lobby The host lobby
	 */
	public void setLobby(LobbyGame lobby)
	{
		this.lobby = lobby;
		try{
			loadConfig(config);
		} catch(NullPointerException e)
		{
			MdIServer.getInstance().getLogger().warning("Gamemode " + this.getClass().getSimpleName() + " threw a NullPointerException!");
			e.printStackTrace();
		}
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
	
	protected void loadConfig(ConfigurationSection config)
	{
		//Load Teams
		Object[] confTeams = config.getConfigurationSection("teams").getKeys(false).toArray();
		teams = new Team[confTeams.length];
		World world = null;
		//TODO REMOVE this debug!
		if(!(lobby==null))
			world = lobby.getSpawnLocation().getWorld();
		else
			System.err.println("Lobby is null!!!1!");
		for(int i=0; i<confTeams.length; i++)
		{
			loadTeam(world, config, (String) confTeams[i], i);
		}
		//Load Settings
		loadSettings(config);
	}
	
	/**
	 * Method is called per team to load it from config
	 * @param world The world where the battle will be
	 * @param config The config to load the Teams from
	 * @param confTeam The TeamName
	 * @param i The position in the team array
	 * @return The created team for convenience, it is stored in the teams array at index i anyways!
	 */
	protected Team loadTeam(World world, ConfigurationSection config, String confTeam, int i)
	{
		double x = config.getDouble("teams." + confTeam + ".xpos");
		double y = config.getDouble("teams." + confTeam + ".xpos");
		double z = config.getDouble("teams." + confTeam + ".xpos");
		Location spawn = new Location(world, x, y, z);
		ChatColor color = ChatColor.getByChar(config.getString("teams." + confTeam + ".color"));
		Team team = new Team(confTeam, color, spawn);
		teams[i] = team;
		return team;
	}
	
	protected void loadSettings(ConfigurationSection config)
	{
		maxRoundLength = config.getInt("gamemode.roundlength");
	}
}
