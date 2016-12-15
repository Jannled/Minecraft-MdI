package com.github.jannled.mdiServer.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Smokedash extends Abilitie
{
	
	public Smokedash()
	{
		super(Material.COAL, "Smokedash", "Dash a shot distance");
	}

	@Override
	public void playerInteract(PlayerInteractEvent e)
	{
		Snowball dash = e.getPlayer().launchProjectile(Snowball.class);
		dash.setPassenger(e.getPlayer());
	}

	@Override
	public void playerEntityInteract(PlayerInteractAtEntityEvent e)
	{
		
	}
}
