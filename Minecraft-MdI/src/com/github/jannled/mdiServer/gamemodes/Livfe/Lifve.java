package com.github.jannled.mdiServer.gamemodes.Livfe;

import org.bukkit.Bukkit;
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
	 * There is no time limit for the round, so you need to stop it manually by calling this method when it should terminate!
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
	
	public void tick()
	{
		for(LifveTeam t : teams)
		{
			t.tick();
		}
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
				updateScoreboard(0);
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
}
