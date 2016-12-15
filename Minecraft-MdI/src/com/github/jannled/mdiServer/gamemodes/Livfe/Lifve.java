package com.github.jannled.mdiServer.gamemodes.Livfe;

import java.util.ArrayList;
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
	LifveTeam[] teams;
	int schedular;
	
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
		for(LifveTeam t : teams)
		{
			t.tick();
			t.updateScoreboard();
		}
		updateScoreboard(0);
	}
	
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
	
	public int getStartTime()
	{
		return 300;
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
		ArrayList<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		for(int j=0; j<playerNames.size(); j++)
		{
			OfflinePlayer p = null;
			try
			{
				p = Bukkit.getOfflinePlayer(UUID.fromString(playerNames.get(j)));	
			} catch (Exception e)
			{
				p = Bukkit.getOfflinePlayer(playerNames.get(j));
			}
			players.add(p);
		}
		ChatColor color = ChatColor.getByChar(config.getString("teams." + confTeam + ".color"));
		LifveTeam team = new LifveTeam(players, confTeam, color, 1, 3, this);
		teams[i] = team;
		return team;
	}
	
	@Override
	protected void loadSettings(ConfigurationSection config)
	{
		//TODO Config loader for Lifve
	}
}
