package com.github.jannled.mdiServer.gamemodes.Livfe;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.github.jannled.mdiServer.MdIServer;
import com.github.jannled.mdiServer.gamemodes.Gamemode;

public class Lifve extends Gamemode
{
	int daycoins;
	int daytime;
	
	int schedular;
	Date resetTime = null;
	LifveTeam[] teams;
	ConfigurationSection config;
	
	public Lifve(ConfigurationSection config)
	{
		super(config);
	}
	
	@Override
	/**
	 * There is no time time limit for a Lifve round, so I need to stop it manually by calling this method when it should terminate!
	 */
	public void end()
	{
		if(state == BEFORE_GAME)
		{
			state = IN_GAME;
			round();
		}
		else if(state == IN_GAME)
		{
			stopRound();
		}
	}
	
	/**
	 * Called in game loop every second
	 */
	public void tick()
	{
		if(System.currentTimeMillis() > resetTime.getTime())
		{
			resetTime.setTime(System.currentTimeMillis() + 604800000);
			MdIServer.getInstance().getLogger().info("Lifve: Daycoin reset. Next reset will be on " + resetTime.toString() + "!");
			for(LifveTeam t : teams)
			{
				t.reset();
			}
			config.set("date", resetTime.getTime());
		}
		for(LifveTeam t : teams)
		{
			t.tick();
			t.updateScoreboard();
		}
		updateScoreboard(0);
	}
	
	/**
	 * Updates the scoreboard every tick
	 */
	@Override
	public void updateScoreboard(int time)
	{
		
	}

	@Override
	public void before()
	{
		
	}

	@Override
	public void round()
	{
		schedular = Bukkit.getScheduler().scheduleSyncRepeatingTask(MdIServer.getInstance(), new Runnable() {
			@Override
			public void run()
			{
				tick();
			}
		}, 20, 20);
	}

	@Override
	public void after()
	{
		
	}
	
	@Override
	protected void loadConfig(ConfigurationSection config)
	{
		teams = new LifveTeam[config.getConfigurationSection("teams").getKeys(false).size()];
		super.loadConfig(config);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	protected LifveTeam loadTeam(World world, ConfigurationSection config, String confTeam, int i)
	{
		List<String> playerNames = config.getStringList("teams." + confTeam + ".players");
		ArrayList<LifvePlayer> players = new ArrayList<LifvePlayer>();
		for(int j=0; j<playerNames.size(); j++)
		{
			LifvePlayer p = null;
			try
			{
				// TODO p = Bukkit.getOfflinePlayer(UUID.fromString(playerNames.get(j)));	
			} catch (Exception e)
			{
				//TODO p = Bukkit.getOfflinePlayer(playerNames.get(j));
			}
			if(p!=null)
			{
				players.add(p);
			}
		}
		ChatColor color = ChatColor.getByChar(config.getString("teams." + confTeam + ".color"));
		LifveTeam team = new LifveTeam(players, confTeam, color, 1, 3, this);
		teams[i] = team;
		return team;
	}
	
	@Override
	protected void loadSettings(ConfigurationSection config)
	{
		this.config = config;
		
		String timeInConf = config.getString("date");
		daytime = config.getInt("daytime");
		daycoins = config.getInt("daycoins");
		
		resetTime = Date.from(Instant.parse(timeInConf));
	}
	
	public int getDefaultDayCoins()
	{
		return daycoins;
	}
	
	public int getStartTime()
	{
		return daytime;			
	}
}
